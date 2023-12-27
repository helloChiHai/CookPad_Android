package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHanler;

public class ChiTietMonAnFragment extends Fragment {
    private TextView tv_idMA, tv_idUser, tv_tenMA, tv_loai, tv_huongDan, tv_nguyenLieu, tv_thoiGianNau, tv_dungCu;

    private ImageView img_hinhAnhMonAnDuocChon;

    private String food_Id;
    private String user_Id;
    private String food_ten;
    private Uri food_hinhAnh;
    private String food_loai;
    private String food_huongDan;
    private String food_nguyenLieu;
    private String food_thoiGianNau;
    private String food_dungCu;

    private Button btn_xoaMonAn, btn_suaMonAn;

    private DatabaseHanler databaseHanler;


    public ChiTietMonAnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lấy thông tin món ăn từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            food_Id = bundle.getString("idMA");
            user_Id = bundle.getString("idUS");
            food_ten = bundle.getString("tenMA");
            food_hinhAnh = Uri.parse(bundle.getString("hinhAnhMA"));
            food_loai = bundle.getString("loaiMA");
            food_huongDan = bundle.getString("huongDanMA");
            food_nguyenLieu = bundle.getString("nguyenLieuMA");
            food_thoiGianNau = bundle.getString("thoiGianNauMA");
            food_dungCu = bundle.getString("dungCuMA");
        }
    }

    public void anhXa(View view){
        tv_idMA = view.findViewById(R.id.chiTietFragment_tv_idMonAn);
        tv_idUser = view.findViewById(R.id.chiTietFragment_tv_idNguoiDung);
        tv_tenMA = view.findViewById(R.id.chiTietFragment_tv_tenMonAn);
        tv_loai = view.findViewById(R.id.chiTietFragment_tv_loaiMonAn);
        tv_huongDan = view.findViewById(R.id.chiTietFragment_tv_huongDanMonAn);
        tv_nguyenLieu = view.findViewById(R.id.chiTietFragment_tv_nguyenLieuMonAn);
        tv_thoiGianNau = view.findViewById(R.id.chiTietFragment_tv_thoiGianMonAn);
        tv_dungCu = view.findViewById(R.id.chiTietFragment_tv_dungCuMonAn);
        img_hinhAnhMonAnDuocChon = view.findViewById(R.id.chiTietFragment_img_hinhMonAn);

        btn_suaMonAn = view.findViewById(R.id.chiTietFragment_btn_suaMonAn);
        btn_xoaMonAn = view.findViewById(R.id.chiTietFragment_btn_xoaMonAn);
    }

    public void xl_sua(){
        btn_suaMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaMonAnFragment suaMonAnFragment = new SuaMonAnFragment();

                Bundle bundle = new Bundle();
                bundle.putString("idMATuFragChiTiet", food_Id);
                bundle.putString("idUSTuFragChiTiet", user_Id);
                bundle.putString("tenMAFragChiTiet", food_ten);
                bundle.putString("hinhAnhMAFragChiTiet", food_hinhAnh.toString());
                bundle.putString("loaiMAFragChiTiet", food_loai);
                bundle.putString("huongDanMAFragChiTiet", food_huongDan);
                bundle.putString("nguyenLieuMAFragChiTiet", food_nguyenLieu);
                bundle.putString("thoiGianNauTuFragChiTiet", food_thoiGianNau);
                bundle.putString("dungCuTuFragChiTiet", food_dungCu);

                suaMonAnFragment.setArguments(bundle);


                FragmentTransaction transactionSuaMon = getParentFragmentManager().beginTransaction();
                transactionSuaMon.replace(R.id.fragment_container, suaMonAnFragment);
                transactionSuaMon.addToBackStack("SuaMonAnFragment");
                transactionSuaMon.commit();
            }
        });
    }

    public void xl_xoa(){
        btn_xoaMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa món ăn?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng chọn "Yes"
                        String id = tv_idMA.getText().toString();
//
                        int deleteCount = databaseHanler.deleteMonAn(id);

                        if(deleteCount > 0){
                            Toast.makeText(getContext(), "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
                            requireActivity().getSupportFragmentManager().popBackStack();
                        }
                        else{
                            Toast.makeText(getContext(), "Xóa món ăn thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng chọn "No"
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_mon_an, container, false);

        databaseHanler = new DatabaseHanler(getContext());

        anhXa(view);

        tv_idMA.setText("" + food_Id);
        tv_idUser.setText("" + user_Id);
        tv_tenMA.setText("" + food_ten);
        img_hinhAnhMonAnDuocChon.setImageURI(food_hinhAnh);
        tv_loai.setText("" + food_loai);
        tv_huongDan.setText("" + food_huongDan);
        tv_nguyenLieu.setText("" + food_nguyenLieu);
        tv_thoiGianNau.setText("" + food_thoiGianNau);
        tv_dungCu.setText("" + food_dungCu);

        xl_xoa();

        xl_sua();

        return view;
    }
}