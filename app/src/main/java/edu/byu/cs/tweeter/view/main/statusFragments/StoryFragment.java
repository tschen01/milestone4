package edu.byu.cs.tweeter.view.main.statusFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import byu.cs.cs340.model.domain.Status;
import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.model.services.request.SearchUserRequest;
import byu.cs.cs340.model.services.request.StatusRequest;
import byu.cs.cs340.model.services.response.SearchUserResponse;
import byu.cs.cs340.model.services.response.StatusResponse;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetPersonalStatusTask;
import edu.byu.cs.tweeter.view.asyncTasks.SearchUserTask;
import edu.byu.cs.tweeter.view.cache.DataCache;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.UserPageActivity;

public class StoryFragment extends Fragment implements StatusPresenter.View, LoginPresenter.View {
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private DataCache dataCache = DataCache.getInstance();
    private static final int PAGE_SIZE = 10;

    private StatusPresenter presenter;
    private LoginPresenter loginPresenter;

    private StatusRecyclerViewAdapter statusRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        presenter = new StatusPresenter(this);
        loginPresenter = new LoginPresenter(this);

        RecyclerView statusRecyclerView = view.findViewById(R.id.statusRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        statusRecyclerView.setLayoutManager(layoutManager);

        statusRecyclerViewAdapter = new StatusRecyclerViewAdapter();
        statusRecyclerView.setAdapter(statusRecyclerViewAdapter);

        statusRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }


    private class StatusHolder extends RecyclerView.ViewHolder implements SearchUserTask.SearchUserObserver {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView content;
        private final TextView timestamp;
        private SearchUserTask.SearchUserObserver observer = this;

        StatusHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);
            content = itemView.findViewById(R.id.content);
            timestamp = itemView.findViewById(R.id.timestamp);

        }

        void bindStatus(Status status) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(status.getUser()));
            userAlias.setText(status.getAlias());
            userName.setText(status.getName());
            content.setText(status.getContent());
            timestamp.setText(status.getTimestamp());

            final String message = status.getContent();
            SpannableString spannableString = new SpannableString(message);

            if (message.contains("www.") || message.contains("@")) {
                String temp = message;
                int padding = 0;
                while (temp.contains("www.")) {
                    final int sIndex = temp.indexOf("www.");
                    String url = temp.substring(sIndex);
                    final int eIndex;
                    if (url.contains(" ")) {
                        eIndex = url.indexOf(" ");
                    }
                    else {
                        eIndex = url.length();
                    }
                    final int PADDING = padding;

                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + message.substring(PADDING + sIndex, PADDING + sIndex + eIndex)));
                            startActivity(browserIntent);
                        }
                    };
                    spannableString.setSpan(clickableSpan, PADDING + sIndex,  PADDING + sIndex + eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    padding += eIndex;
                    temp = temp.substring(eIndex);
                }
                //mentions
                temp = message;
                padding = 0;
                while (temp.contains("@")) {
                    final int sIndex = temp.indexOf("@");
                    String url = temp.substring(sIndex);
                    final int eIndex;
                    if (url.contains(" ") || url.substring(1).contains("@")) {
                        eIndex = url.indexOf(" ");
                    }
                    else {
                        eIndex = url.length();
                    }
                    final int PADDING = padding;

                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            SearchUserTask searchUserTask = new SearchUserTask(loginPresenter, observer);
                            searchUserTask.execute(new SearchUserRequest(message.substring(0, eIndex)));
                        }
                    };
                    spannableString.setSpan(clickableSpan, PADDING + sIndex,  PADDING + sIndex + eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    padding += eIndex;
                    temp = temp.substring(sIndex + eIndex);
                }

            }
            //Url

            content.setText(spannableString);
            content.setMovementMethod(LinkMovementMethod.getInstance());

        }

        String timeFormat(Timestamp timestamp) {
            Date date = new Date();
            date.setTime(timestamp.getTime());
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }

        @Override
        public void searchUserRetrieved(SearchUserResponse response) {
            if (response != null && response.isSuccess()) {
                DataCache.getInstance().setSelectedUser(response.getUser());
                getActivity().finish();
                Intent intent = new Intent(getContext(), UserPageActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getContext(),"User Do Not Exist", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class StatusRecyclerViewAdapter extends RecyclerView.Adapter<StatusHolder> implements GetPersonalStatusTask.GetPersonalStatusObserver {

        private final List<Status> statuses = new ArrayList<>();

        private String lastStatus;

        private boolean hasMorePages;
        private boolean isLoading = false;

        StatusRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Status> newStatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newStatuses);
            this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
        }

        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoryFragment.this.getContext());
            View view;

            if(isLoading) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new StatusHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StatusHolder followingHolder, int position) {
            if(!isLoading) {
                followingHolder.bindStatus(statuses.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return statuses.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }


        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            User user;
            if (dataCache.getSelectedUser() != null) {
                user = dataCache.getSelectedUser();
            }
            else {
                user = presenter.getCurrentUser();
            }


            GetPersonalStatusTask getPersonalStatusTask = new GetPersonalStatusTask(presenter, this);
            StatusRequest request = new StatusRequest(user, PAGE_SIZE, lastStatus);
            getPersonalStatusTask.execute(request);
        }

        @Override
        public void statusesRetrieved(StatusResponse statusResponse) {
            List<Status> statuses = statusResponse.getStatuses();

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() -1).getTimestamp() : null;
            hasMorePages = statusResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            statusRecyclerViewAdapter.addItems(statuses);
        }

        private void addLoadingFooter() {
            addItem(new Status(new User("Dummy", "User", "",""),"haha",new Timestamp(System.currentTimeMillis()).toString()));
        }

        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!statusRecyclerViewAdapter.isLoading && statusRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    statusRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
