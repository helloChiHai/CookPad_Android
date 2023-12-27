package com.example.myapplication.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;

public class ChiTietMonAnFragment extends Fragment {

    private Toolbar toolbar_fragment_ctMon;
    private ImageButton imageButtonBack_fragment_ctMon;

    private ImageView img_hinhMonAn, img_hinhNguoiDung;

    private TextView txt_tenMon, txt_nguyenLieu, txt_cachLam, txt_tenNguoiDung, txt_idNguoiDung;

    private String tenMonAn;
    private String nguyenLieuLamMon;
    private String huongDanLamMonAn;
    private String tenNguoiDung;
    private String idNguoiDung;
    private Uri uri_hinhMon, uri_hinhNguoiDung;

    private DatabaseHelper databaseHanler;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_IMAGE_PICK = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lấy thông tin món ăn từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            tenMonAn = bundle.getString("ten_Mon");
            nguyenLieuLamMon = bundle.getString("nguyen_lieu");
            huongDanLamMonAn = bundle.getString("huongDan_Mon");
            tenNguoiDung = bundle.getString("ten_nguoi_dung");
            idNguoiDung = bundle.getString("id_nguoi_dung");
            uri_hinhMon = Uri.parse(bundle.getString("hinhAnh_Mon"));
            uri_hinhNguoiDung = Uri.parse(bundle.getString("hinh_nguoiDung"));
        }
    }

    public void anhXa(View view){
        toolbar_fragment_ctMon = view.findViewById(R.id.toolbar_fragment_chiTietMonAn);
        txt_tenMon = view.findViewById(R.id.fragment_ctMon_tenMon);
        txt_nguyenLieu = view.findViewById(R.id.fragment_ctMon_nguyenLieu);
        txt_cachLam = view.findViewById(R.id.fragment_ctMon_cachLam);
        img_hinhMonAn = view.findViewById(R.id.fragment_ctMon_hinhMon);
        img_hinhNguoiDung = view.findViewById(R.id.fgChiTiet_img_nguoiDung);
        txt_tenNguoiDung = view.findViewById(R.id.fragment_ctMon_userName);
        txt_idNguoiDung = view.findViewById(R.id.fragment_ctMon_userID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_mon_an, container, false);

        anhXa(view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_fragment_ctMon);
        setHasOptionsMenu(true);

        // Xóa title của Toolbar
        toolbar_fragment_ctMon.setTitle("");

        txt_tenMon.setText("" + tenMonAn);
        txt_nguyenLieu.setText("" + nguyenLieuLamMon);
        txt_cachLam.setText("" + huongDanLamMonAn);
        txt_tenNguoiDung.setText("" + tenNguoiDung);
        txt_idNguoiDung.setText("" + idNguoiDung);
        img_hinhMonAn.setImageURI(uri_hinhMon);
        img_hinhNguoiDung.setImageURI(uri_hinhNguoiDung);

        XLNutBack(view);

        return view;
    }

    private void XLNutBack(View view){
        imageButtonBack_fragment_ctMon = view.findViewById(R.id.fragment_ctMon_imgBtn_quayLai);
        imageButtonBack_fragment_ctMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, homeFragment);
                transaction.commit();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        // Xử lý khi Fragment được resumed
    }

    @Override
    public void onPause() {
        super.onPause();
        // Xử lý khi Fragment bị paused
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Xử lý khi Fragment bị huỷ View
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Xử lý khi Fragment bị huỷ
    }
}