package ru.mirea.volegovaa.mireaproject.ui.hobbys;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HobbyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HobbyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}