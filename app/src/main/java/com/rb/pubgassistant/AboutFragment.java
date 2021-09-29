package com.rb.pubgassistant;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {

    private AboutViewModel mViewModel;
    View view;

    public static DonateFragment newInstance() {
        return new DonateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_about));

        view = inflater.inflate(R.layout.fragment_about, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView textView = view.findViewById(R.id.about_text);
        final TextView textViewTwo = view.findViewById(R.id.about_text_2);
        final TextView textViewThree = view.findViewById(R.id.about_text_3);

        textView.setText(Html.fromHtml(getResources().getString(R.string.about_text), Html.FROM_HTML_MODE_COMPACT));
        textViewTwo.setText(Html.fromHtml(getResources().getString(R.string.about_text_2), Html.FROM_HTML_MODE_COMPACT));
        textViewThree.setText(Html.fromHtml(getResources().getString(R.string.about_text_3), Html.FROM_HTML_MODE_COMPACT));
    }

}