package com.example.myapplication.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.DangNhapActivity;
import com.example.myapplication.adapter.MonAnAdapter;
import com.example.myapplication.adapter.MonAnAdapter_5;
import com.example.myapplication.adapter.MonAnAdapter_6;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.ChiTietMonAnDuocLuu;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.MonAn_1;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    private DatabaseHelper databaseHelper;

    private Button btn_chuyenFGSearch, btn_chuyenFGTatCaMonAn;

    private TextView tv_tatCaMon, tv_monTrung, tv_monNhoiThit, tv_monKho, tv_monAnVat, tv_monLauCa, tv_monMiCay, tv_monSotCay;


    private void xl_clickTV_MonSotCay(View view){
        tv_monSotCay = view.findViewById(R.id.fgHome_tv_monSotCay);
        tv_monSotCay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
                databaseHelper = new DatabaseHelper(getContext());

                List<MonAn> monans = databaseHelper.timKiemMonAn("Cay");

                MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void xl_clickTV_MonCay(View view){
        tv_monMiCay = view.findViewById(R.id.fgHome_tv_monMiCay);
        tv_monMiCay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
                databaseHelper = new DatabaseHelper(getContext());

                List<MonAn> monans = databaseHelper.timKiemMonAn("Cay");

                MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void xl_clickTV_MonLau(View view){
        tv_monLauCa = view.findViewById(R.id.fgHome_tv_monAnVat);
        tv_monLauCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
                databaseHelper = new DatabaseHelper(getContext());

                List<MonAn> monans = databaseHelper.timKiemMonAn("Lau");

                MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void xl_clickTV_MonAnVat(View view){
        tv_monAnVat = view.findViewById(R.id.fgHome_tv_monAnVat);
        tv_monAnVat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
                databaseHelper = new DatabaseHelper(getContext());

                List<MonAn> monans = databaseHelper.timKiemMonAn("AnVat");

                MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void xl_clickTV_MonKho(View view){
        tv_monKho = view.findViewById(R.id.fgHome_tv_monThitKho);
        tv_monKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
                databaseHelper = new DatabaseHelper(getContext());

                List<MonAn> monans = databaseHelper.timKiemMonAn("Kho");

                MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void xl_clickTV_MonNhoiThit(View view){
        tv_monNhoiThit = view.findViewById(R.id.fgHome_tv_monNhoiThit);
        tv_monNhoiThit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
                databaseHelper = new DatabaseHelper(getContext());

                List<MonAn> monans = databaseHelper.timKiemMonAn("Thit");

                MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }


    private void xl_clickTV_tatCaMon(View view){
        tv_tatCaMon = view.findViewById(R.id.fgHome_tv_tatCa);
        tv_tatCaMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
                databaseHelper = new DatabaseHelper(getContext());

                List<MonAn> monans = databaseHelper.layTatCaMonAn();

                MonAnAdapter_5 monAnAdapter = new MonAnAdapter_5(getContext(), monans, new MonAnAdapter_5.ItemClickFoodListener() {
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
                });

                recyclerView.setAdapter(monAnAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void xl_clickTV_monrung(View view){
        tv_monTrung = view.findViewById(R.id.fgHome_tv_monTrung);
        tv_monTrung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
                databaseHelper = new DatabaseHelper(getContext());

                List<MonAn> monans = databaseHelper.timKiemMonAn("Trung");

                MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private String imageToString(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void xl_chuyenFGSearch2(View view){
        btn_chuyenFGSearch = view.findViewById(R.id.fgHome_btn_chuyenFgSearch2);
        btn_chuyenFGSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search2Fragment search2Fragment = new Search2Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, search2Fragment);
                fragmentTransaction.addToBackStack("Search2Fragment");
                fragmentTransaction.commit();
            }
        });
    }

    private void xl_chuyenFGTatCaMonAn(View view){
        btn_chuyenFGTatCaMonAn = view.findViewById(R.id.fgHome_btn_chuyenFgTatCaMonAn);
        btn_chuyenFGTatCaMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TatCaMonAnFragment tatCaMonAnFragment = new TatCaMonAnFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, tatCaMonAnFragment);
                fragmentTransaction.addToBackStack("TatCaMonAnFragment");
                fragmentTransaction.commit();
            }
        });
    }

    public void monAnMoiNhat(View view){
        recyclerView = view.findViewById(R.id.recycler_view_MonMoiNhat);
        databaseHelper = new DatabaseHelper(getContext());

        List<MonAn> monans = databaseHelper.layTatCaMonAn();

        MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    public void monAnCuoiTuan(View view){
        recyclerView = view.findViewById(R.id.recycler_view_MonCuoiTuan);
        databaseHelper = new DatabaseHelper(getContext());

        List<MonAn> monans = databaseHelper.layTatCaMonAnTheoLoai("Canh");

        MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void monAnSotMe(View view){
        recyclerView = view.findViewById(R.id.recycler_view_MonSotMe);
        databaseHelper = new DatabaseHelper(getContext());

        List<MonAn> monans = databaseHelper.layTatCaMonAnTheoLoai("Cay");

        MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void monAnTheoLoai(View view){
        recyclerView = view.findViewById(R.id.recycler_view_MonAnTheoLoai);
        databaseHelper = new DatabaseHelper(getContext());

        List<MonAn> monans = databaseHelper.layTatCaMonAn();

        MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext() ,monans);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        monAnMoiNhat(view);
        monAnCuoiTuan(view);
        monAnSotMe(view);
        monAnTheoLoai(view);
        xl_chuyenFGSearch2(view);
        xl_chuyenFGTatCaMonAn(view);
        xl_clickTV_tatCaMon(view);
        xl_clickTV_monrung(view);
        xl_clickTV_MonSotCay(view);
        xl_clickTV_MonCay(view);
        xl_clickTV_MonLau(view);
        xl_clickTV_MonAnVat(view);
        xl_clickTV_MonKho(view);
        xl_clickTV_MonNhoiThit(view);
        return view;
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