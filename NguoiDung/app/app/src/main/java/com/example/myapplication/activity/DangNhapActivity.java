package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.fragment.TatCaMonAnFragment;

public class DangNhapActivity extends AppCompatActivity {

    static EditText edt_tk;
    private EditText edt_mk;

    private Button btn_dn;

    private TextView tv_taoTaiKhoan;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        databaseHelper = new DatabaseHelper(this);

        dangNhap();
        xl_taoTaiKhoan();
    }

    private void anhXa(){
        edt_tk = findViewById(R.id.edt_taiKhoan);
        edt_mk = findViewById(R.id.edt_matKhau);
        tv_taoTaiKhoan = findViewById(R.id.fgDangNhap_tv_taoTaiKhoan);
    }

    public static String anhXaTaiKhoan(){
        if(edt_tk.getText().toString().trim().isEmpty()){
            return "admin";
        }
        return edt_tk.getText().toString().trim();
    }

    public void anhXaButton(){
        btn_dn = findViewById(R.id.btn_dangNhap);
    }

    private void xl_taoTaiKhoan(){
        tv_taoTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, TaoTaiKhoanNguoiDungActivity.class);
                startActivity(intent);
            }
        });
    }

    public void dangNhap(){
        anhXa();
        anhXaButton();

        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = edt_tk.getText().toString().trim();
                String mk = edt_mk.getText().toString().trim();

                if(tk.isEmpty() || mk.isEmpty()){
                    Toast.makeText(
                            DangNhapActivity.this, "Nhập thiếu thông tin", Toast.LENGTH_SHORT
                    ).show();
                }
                else{
                    if(databaseHelper.checkAccountAndPassword(tk, mk)){
                        Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(
                                DangNhapActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        });
    }
}