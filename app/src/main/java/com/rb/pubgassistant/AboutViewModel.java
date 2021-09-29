package com.rb.pubgassistant;

import android.text.Html;
import android.text.Spanned;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private MutableLiveData<Spanned> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();

        String text = MyApplicationContext.getAppContext().getResources().getString(R.string.about_text);
        mText.setValue(Html.fromHtml(text));
    }

    public MutableLiveData<Spanned> getText() {
        return mText;
    }
}