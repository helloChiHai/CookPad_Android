package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class CaiDatActivity extends AppCompatActivity {

    private Button btn_quayVeFg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);

        btn_quayVeFg = findViewById(R.id.acCaiDat_btn_quayVeFg);

        btn_quayVeFg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaiDatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}