package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;

public class AddFragment extends Fragment {
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button btn_themMonMoi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        clickBtnThemMonMoi(view);
        return view;
    }



    private void clickBtnThemMonMoi(View view){
        btn_themMonMoi = (Button) view.findViewById(R.id.fragment_add_btn_themMonMoi);
        btn_themMonMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemMonMoiFragment themMonMoiFragment = new ThemMonMoiFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, themMonMoiFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

}