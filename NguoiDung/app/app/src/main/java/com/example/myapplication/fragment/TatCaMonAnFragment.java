package com.example.myapplication.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.DangNhapActivity;
import com.example.myapplication.adapter.MonAnAdapter_5;
import com.example.myapplication.adapter.MonAnAdapter_6;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.ChiTietMonAnDuocLuu;
import com.example.myapplication.model.MonAn;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class TatCaMonAnFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;

    private void anhXa(View view){
        recyclerView = view.findViewById(R.id.fgTatCaMonAn_recycler_view_DSTatCaMonAn);
    }

    private String imageToString(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void tatCaMonAn(){
        List<MonAn> monAns = databaseHelper.layTatCaMonAn();

        MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monAns);

        monAnAdapter.setOnItemClickListener(new MonAnAdapter_6.OnItemClickListener() {
            @Override
            public void onItemClick(MonAn food) {
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

            @Override
            public void onLuuButtonClick(MonAn food) {
                // Xử lý khi nhấn vào nút "Lưu"
                Toast.makeText(getContext(), food.getId_MA(), Toast.LENGTH_SHORT).show();

                ChiTietMonAnDuocLuu chiTietMonAnDuocLuu = new ChiTietMonAnDuocLuu(food.getId_MA(), DangNhapActivity.anhXaTaiKhoan());

                if(!databaseHelper.them_MonAnVaoDanhSachLuuBoiNguoiDung(chiTietMonAnDuocLuu)){
                    Toast.makeText(getActivity(), "thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(monAnAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    public TatCaMonAnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tat_ca_mon_an, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        anhXa(view);

        tatCaMonAn();

        return view;
    }
}