package com.example.myapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.DangNhapActivity;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.NguoiDung;

import java.util.ArrayList;


public class XemThongTinCaNhanFragment extends Fragment {
    private TextView tv_id, tv_ten, tv_tk, tv_mk, tv_email, tv_maQuyen;

    private ImageView img_hinhAnhDuocChon;

    private ImageButton imageButtonBack_fragment_hsNguoiDung;

    private DatabaseHelper databaseHelper;

    private Button btn_dangXuat;

    private Toolbar toolbar_fragment_hsNguoiDung;

    private void XLNutBack(View view){
        imageButtonBack_fragment_hsNguoiDung = view.findViewById(R.id.fragment_ctMon_imgBtn_quayLaiHSoSo);
        imageButtonBack_fragment_hsNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, homeFragment);
                transaction.commit();
            }
        });
    }

    private void xl_dangXuat(){
        btn_dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void anhXa(View view){
        tv_id = view.findViewById(R.id.tv_idNguoiDung);
        tv_ten = view.findViewById(R.id.tv_tenNguoiDung);
        tv_tk = view.findViewById(R.id.tv_taiKhoanNguoiDung);
        tv_mk = view.findViewById(R.id.tv_matKhauNguoiDung);
        tv_email = view.findViewById(R.id.tv_emailNguoiDung);
        tv_maQuyen = view.findViewById(R.id.edt_maQuyenNguoiDung);
        img_hinhAnhDuocChon = view.findViewById(R.id.img_hinhUser);

        btn_dangXuat = view.findViewById(R.id.fgXemTTCN_btn_dangXuat);

        toolbar_fragment_hsNguoiDung = view.findViewById(R.id.toolbar_fragment_hoSoNguoiDung);
    }

    private void getAllUserInfoById(String userId) {
        ArrayList<NguoiDung> userInfoList = databaseHelper.getAllUserInfoById(userId);

        if (!userInfoList.isEmpty()) {
            NguoiDung userInfo = userInfoList.get(0);

            tv_id.setText(userInfo.getId_user());
            tv_ten.setText(userInfo.getTen_user());
            tv_tk.setText(userInfo.getTaiKhoan_user());
            tv_mk.setText(userInfo.getMatKhau_user());
            tv_email.setText(userInfo.getEmail_user());
            tv_maQuyen.setText(String.valueOf(userInfo.getMaQuyen_user()));

            img_hinhAnhDuocChon.setImageURI(Uri.parse(userInfo.getHinhAnh_user()));
        }
    }

    public XemThongTinCaNhanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xem_thong_tin_ca_nhan, container, false);

        anhXa(view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_fragment_hsNguoiDung);
        setHasOptionsMenu(true);

        // Xóa title của Toolbar
        toolbar_fragment_hsNguoiDung.setTitle("");

        databaseHelper = new DatabaseHelper(getContext());


        getAllUserInfoById(DangNhapActivity.anhXaTaiKhoan());

        XLNutBack(view);

        xl_dangXuat();

        return view;
    }
}