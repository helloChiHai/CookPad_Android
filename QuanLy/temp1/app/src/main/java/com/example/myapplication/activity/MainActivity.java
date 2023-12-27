package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHanler;
import com.example.myapplication.fragment.QLDanhSachMonAnFragment;
import com.example.myapplication.fragment.QLDanhSachNguoiDungFragment;
import com.example.myapplication.fragment.QLMonAnDuocNguoiDungLuuFragment;
import com.example.myapplication.fragment.QLMonAnFragment;
import com.example.myapplication.fragment.QLNguoiDungFragment;
import com.example.myapplication.model.NguoiDung;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new QLNguoiDungFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    if (item.getItemId() == R.id.menu_qlNguoiDung) {
                        selectedFragment = new QLNguoiDungFragment();
                    }else if (item.getItemId() == R.id.menu_qlMonAn) {
                        selectedFragment = new QLMonAnFragment();
                    }else if (item.getItemId() == R.id.menu_list_monDuocLuu) {
                        selectedFragment = new QLMonAnDuocNguoiDungLuuFragment();
                    }else if (item.getItemId() == R.id.menu_list_NguoiDung) {
                        selectedFragment = new QLDanhSachNguoiDungFragment();
                    }else if (item.getItemId() == R.id.menu_list_MonAn) {
                        selectedFragment = new QLDanhSachMonAnFragment();
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment).commit();
                    }
                    return true;
                }
            };
}