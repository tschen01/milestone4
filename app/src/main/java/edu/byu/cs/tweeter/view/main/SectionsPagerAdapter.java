package edu.byu.cs.tweeter.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.services.LoginServiceProxy;
import edu.byu.cs.tweeter.view.cache.DataCache;
import edu.byu.cs.tweeter.view.main.followingFragments.FolloweeFragment;
import edu.byu.cs.tweeter.view.main.followingFragments.FollowingFragment;
import edu.byu.cs.tweeter.view.main.statusFragments.FeedFragment;
import edu.byu.cs.tweeter.view.main.statusFragments.StoryFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int FEED_FRAGMENT_POSITION = 0;
    private static final int STORY_FRAGMENT_POSITION = 1;
    private static final int FOLLOWING_FRAGMENT_POSITION = 2;
    private static final int FOLLOWER_FRAGMENT_POSITION = 3;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.feedTabTitle, R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == FEED_FRAGMENT_POSITION && loggedIn()) {
            return new FeedFragment();
        }
        else if (position == STORY_FRAGMENT_POSITION) {
            return new StoryFragment();
        }
        else if (position == FOLLOWING_FRAGMENT_POSITION) {
            return new FollowingFragment();
        }
        else if (position == FOLLOWER_FRAGMENT_POSITION) {
                return new FolloweeFragment();
        } else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }

    private Boolean loggedIn() {
        if (LoginServiceProxy.getInstance().getCurrentUser().equals(DataCache.getInstance().getSelectedUser()) || DataCache.getInstance().getSelectedUser() == null) {
            return true;
        }
        return false;
    }
}