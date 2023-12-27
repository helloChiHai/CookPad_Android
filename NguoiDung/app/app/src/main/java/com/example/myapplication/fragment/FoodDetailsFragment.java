package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class FoodDetailsFragment extends Fragment {
    private ImageView imageView;
    private TextView textView;

    public FoodDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_details2, container, false);

        imageView = view.findViewById(R.id.details_imageView);
        textView = view.findViewById(R.id.details_textView);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String tenMonAn = bundle.getString("tenMonAn");
            String hinhAnhMonAn = bundle.getString("hinhAnhMonAn");

            textView.setText(tenMonAn);
            Picasso.get().load(hinhAnhMonAn).into(imageView);
        }

        return view;
    }
}