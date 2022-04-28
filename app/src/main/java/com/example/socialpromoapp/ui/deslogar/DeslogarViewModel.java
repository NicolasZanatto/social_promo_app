package com.example.socialpromoapp.ui.deslogar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeslogarViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DeslogarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}