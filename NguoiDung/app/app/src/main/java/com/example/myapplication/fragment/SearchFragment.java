package com.example.myapplication.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.DangNhapActivity;
import com.example.myapplication.adapter.MonAnAdapter_5;
import com.example.myapplication.adapter.MonAnAdapter_6;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.ChiTietMonAnDuocLuu;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.MonAn_1;

import java.io.ByteArrayOutputStream;
import java.util.List;

//public class SearchFragment extends Fragment implements MonAnAdapter_4.ItemClickListener {
public class SearchFragment extends Fragment{

    private RecyclerView recyclerView;

    private Button btn_chuyenFG;

    private DatabaseHelper databaseHelper;

    private void anhXa(View view){
        btn_chuyenFG = view.findViewById(R.id.btn_chuyenFgSearch2);
    }

    private void xl_chuyenFGSearch2(){
        btn_chuyenFG.setOnClickListener(new View.OnClickListener() {
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

//    public void monAnPhoBien(View view){
//        recyclerView = view.findViewById(R.id.fg_search_recycler_view_DSMonPhoBien);
//        databaseHelper = new DatabaseHelper(getContext());
//
//        List<MonAn> monans = databaseHelper.layTatCaMonAn();
//
//        MonAnAdapter_5 monAnAdapter = new MonAnAdapter_5(getContext(), monans, new MonAnAdapter_5.ItemClickFoodListener() {
//            @Override
//            public void onItemClickFood(MonAn food) {
//                // Các hành động khi nhấn vào mục trong RecyclerView
//                Toast.makeText(getContext(), "Bạn đã nhấn vào: " + food.getTen_MA(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "Bạn đã nhấn vào: " + food.getId_User(), Toast.LENGTH_SHORT).show();
//
//                ChiTietMonAnFragment chiTietMonAnFragment = new ChiTietMonAnFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("hinhAnh_Mon", food.getHinhAnh_MA());
//                bundle.putString("ten_Mon", food.getTen_MA());
//                bundle.putString("nguyen_lieu", food.getNguyenLieu_MA());
//                bundle.putString("loai_Mon", food.getLoai_MA());
//                bundle.putString("huongDan_Mon", food.getHuongDanNau_MA());
//
//                String tenNguoiDung = databaseHelper.timTenNguoiDungCuaMon(food.getTen_MA());
//                bundle.putString("ten_nguoi_dung", tenNguoiDung);
//
//                String IDNguoiDung = databaseHelper.timIDNguoiDungCuaMon(food.getTen_MA());
//                bundle.putString("id_nguoi_dung", IDNguoiDung);
//
//                String imgNguoiDung;
//
//                if(databaseHelper.timHinhAnhNguoiDungCuaMon(food.getTen_MA()).isEmpty()){
//                    imgNguoiDung = imageToString(R.mipmap.img_default);
//                }
//                else{
//                    imgNguoiDung = databaseHelper.timHinhAnhNguoiDungCuaMon(food.getTen_MA());
//                }
//                bundle.putString("hinh_nguoiDung", imgNguoiDung);
//
//                chiTietMonAnFragment.setArguments(bundle);
//                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, chiTietMonAnFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
//
//        recyclerView.setAdapter(monAnAdapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//    }

    public void monAnPhoBien(View view){
        recyclerView = view.findViewById(R.id.fg_search_recycler_view_DSMonPhoBien);
        databaseHelper = new DatabaseHelper(getContext());


        List<MonAn> monans = databaseHelper.layTatCaMonAn();

        MonAnAdapter_6 monAnAdapter = new MonAnAdapter_6(getContext(), monans);

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

    private String imageToString(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

//        monAnPhoBien(view);

        anhXa(view);

        monAnPhoBien(view);

        xl_chuyenFGSearch2();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_timkiem, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        super.onCreateOptionsMenu(menu, inflater);
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