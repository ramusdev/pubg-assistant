package com.rb.pubgassistant;

import android.text.Html;
import android.text.Spanned;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DonateViewModel extends ViewModel {

    private MutableLiveData<Spanned> mText;

    public DonateViewModel() {
        mText = new MutableLiveData<>();

        String text = MyApplicationContext.getAppContext().getResources().getString(R.string.donate_text);
        mText.setValue(Html.fromHtml(text));
    }

    public MutableLiveData<Spanned> getText() {
        return mText;
    }
}