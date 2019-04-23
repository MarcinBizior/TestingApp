package com.example.kanum.testingapp;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class JsonSecondFragment extends Fragment {

    public String text;
    public String image;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            text = bundle.getString("text");
            image = bundle.getString("image");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.item_view, container, false);
        TextView textView = view.findViewById(R.id.textViewItem);
        ImageView imageView = view.findViewById(R.id.imageViewItem);

        textView.setText(text);
        Glide.with(this)
                .load(image)
                .into(imageView);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && !(getActivity().getSupportFragmentManager().findFragmentByTag("jsonFragment").isVisible())) {
            getActivity().getSupportFragmentManager().popBackStack();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }
}
