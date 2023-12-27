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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class Search2Fragment extends Fragment {

    private Button btn_timMonAn;

    private EditText edt_inputTenMon;

    private ImageButton imageButtonBack_fragment_Search;

    private RecyclerView recyclerView;

    private TextView tv_thongBaoKhongTimThay;

    DatabaseHelper databaseHelper;

    private String imageToString(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void anhXa(View view){
        btn_timMonAn = view.findViewById(R.id.btn_timKiem);
        edt_inputTenMon = view.findViewById(R.id.edt_nhapTenMon);
        imageButtonBack_fragment_Search = view.findViewById(R.id.fragment_ctSearch2_imgBtn_quayLaiSearch);
        recyclerView = view.findViewById(R.id.fgsearch2_recycler_view_DSMonTimKiem);
        tv_thongBaoKhongTimThay = view.findViewById(R.id.txt_TH_KhongTimThay);
    }

    private void XLNutBack(){
        imageButtonBack_fragment_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, searchFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void xl_timKiem(){
        btn_timMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(getContext());
                String tenMon = edt_inputTenMon.getText().toString();

                List<MonAn> monAns = databaseHelper.timKiemMonAn(tenMon);

                if(!edt_inputTenMon.getText().toString().isEmpty()){
                    if(!monAns.isEmpty()){
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
                    else{
                        tv_thongBaoKhongTimThay.setText("Không tìm thấy món ăn nào cả :(((");
                    }
                }
                else{
                    Toast toast = Toast.makeText(getContext(), "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }


    public Search2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search2, container, false);

        anhXa(view);

        xl_timKiem();

        XLNutBack();

        return view;
    }
}