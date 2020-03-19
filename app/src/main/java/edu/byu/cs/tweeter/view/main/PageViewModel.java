package edu.byu.cs.tweeter.view.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class PageViewModel extends ViewModel {

    private final MutableLiveData<Integer> index = new MutableLiveData<>();
    private final LiveData<String> text = Transformations.map(index, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Not your account";
        }
    });

    public void setIndex(int index) {
        this.index.setValue(index);
    }

    public LiveData<String> getText() {
        return text;
    }
}