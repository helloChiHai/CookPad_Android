package com.example.myapplication.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.CaiDatActivity;
import com.example.myapplication.activity.DangNhapActivity;
import com.example.myapplication.adapter.MonAnAdapter_2;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.MonAn_1;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private TextView tv_tenNguoiDung, tv_idNguoiDung;

    private ImageView img_nguoiDung;

    private Uri ImageUri;

    private DatabaseHelper databaseHelper;

    private void anhXa(View view){
        tv_tenNguoiDung = view.findViewById(R.id.fragment_user_txt_userName);
        tv_idNguoiDung = view.findViewById(R.id.fragment_user_txt_userID);
        img_nguoiDung = view.findViewById(R.id.fragment_user_img_nguoiDung);
    }

    private void anhXaThongTinNguoiDung(){

        String taiKhoan = DangNhapActivity.anhXaTaiKhoan();

        String ten = databaseHelper.layTenTheoTaiKhoan(taiKhoan);
        String id = databaseHelper.layIDTheoTaiKhoan(taiKhoan);

        ImageUri = Uri.parse(databaseHelper.layHinhAnhTheoTaiKhoan(taiKhoan));

        tv_tenNguoiDung.setText(ten);
        tv_idNguoiDung.setText(id);

//        img_nguoiDung.setImageURI(ImageUri);
//        Picasso.get().load(ImageUri).into(img_nguoiDung);

        loadImageFromString(ImageUri.toString(), img_nguoiDung);
    }

    public void loadImageFromString(String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).into(imageView);
    }


    private void DSMonAnDaLuu(View view){

        recyclerView = view.findViewById(R.id.recycler_view_DSMonDaLuu);

        List<MonAn> monans =  databaseHelper.getCacMonAnDaLuu(DangNhapActivity.anhXaTaiKhoan());

        recyclerView.setAdapter(new MonAnAdapter_2(getContext(), monans, new MonAnAdapter_2.ItemClickMonAnListener() {
            @Override
            public void onItemClickFood(MonAn food) {
                // Các hành động khi nhấn vào mục trong RecyclerView
                Toast.makeText(getContext(), "Bạn đã nhấn vào: " + food.getTen_MA(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Bạn đã nhấn vào: " + food.getId_User(), Toast.LENGTH_SHORT).show();

                ChiTietMonAnFragment chiTietMonAnFragment = new ChiTietMonAnFragment();
                Bundle bundle = new Bundle();
                bundle.putString("hinhAnh_Mon", food.getHinhAnh_MA());
                bundle.putString("ten_Mon", food.getTen_MA());
                bundle.putString("nguyen_lieu", food.getNguyenLieu_MA());
                bundle.putString("loai_Mon", food.getLoai_MA());
                bundle.putString("huongDan_Mon", food.getHuongDanNau_MA());

                String tenNguoiDung = databaseHelper.timTenNguoiDungCuaMon(food.getTen_MA());
                bundle.putString("ten_nguoi_dung", tenNguoiDung);

                String IDNguoiDung = databaseHelper.timIDNguoiDungCuaMon(food.getTen_MA());
                bundle.putString("id_nguoi_dung", IDNguoiDung);

                String imgNguoiDung;

                if(databaseHelper.timHinhAnhNguoiDungCuaMon(food.getTen_MA()).isEmpty()){
                    imgNguoiDung = imageToString(R.mipmap.img_default);
                }
                else{
                    imgNguoiDung = databaseHelper.timHinhAnhNguoiDungCuaMon(food.getTen_MA());
                }
                bundle.putString("hinh_nguoiDung", imgNguoiDung);

                chiTietMonAnFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, chiTietMonAnFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }));


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private String imageToString(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        anhXa(view);

        anhXaThongTinNguoiDung();


        DSMonAnDaLuu(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.user_setting){
            Intent intent = new Intent(getActivity(), CaiDatActivity.class);
            startActivity(intent);
            return true;
        }

        if(item.getItemId() == R.id.user_setting_xemThongTinCaNhan){
            XemThongTinCaNhanFragment suaThongTinNguoiDungFragment = new XemThongTinCaNhanFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, suaThongTinNguoiDungFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return  super.onOptionsItemSelected(item);
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