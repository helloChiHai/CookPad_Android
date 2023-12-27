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
import com.example.myapplication.model.NguoiDung;

public class ChiTietNguoiDungFragment extends Fragment {

    private TextView tv_id, tv_ten, tv_tk, tv_mk, tv_email, tv_maQuyen;

    private ImageView img_hinhAnhDuocChon;

    private String user_Id;
    private String user_ten;
    private Uri user_hinhAnh;
    private String user_taiKhoan;
    private String user_matKhau;
    private String user_email;
    private int user_maQuyen;

    private Button btn_xoa, btn_sua;

    DatabaseHanler databaseHanler;
    public ChiTietNguoiDungFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lấy thông tin người dùng từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            user_Id = bundle.getString("id");
            user_ten = bundle.getString("ten");
            user_hinhAnh = Uri.parse(bundle.getString("hinhAnh"));
            user_taiKhoan = bundle.getString("taiKhoan");
            user_matKhau = bundle.getString("matKhau");
            user_email = bundle.getString("email");
            user_maQuyen = bundle.getInt("maQuyen");
        }
    }

    public void anhXa(View view){
        tv_id = view.findViewById(R.id.tv_idNguoiDung);
        tv_ten = view.findViewById(R.id.tv_tenNguoiDung);
        tv_tk = view.findViewById(R.id.tv_taiKhoanNguoiDung);
        tv_mk = view.findViewById(R.id.tv_matKhauNguoiDung);
        tv_email = view.findViewById(R.id.tv_emailNguoiDung);
        tv_maQuyen = view.findViewById(R.id.edt_maQuyenNguoiDung);
        img_hinhAnhDuocChon = view.findViewById(R.id.img_hinhUser);

        btn_sua = view.findViewById(R.id.btn_sua);
        btn_xoa = view.findViewById(R.id.btn_xoa);
    }

    public void xl_sua(){
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaNGuoiDungFragmentFragment suaNguoiDungFragment = new SuaNGuoiDungFragmentFragment();

                Bundle bundle = new Bundle();
                bundle.putString("idTuFragChiTiet", user_Id);
                bundle.putString("tenFragChiTiet", user_ten);
                bundle.putString("hinhAnhFragChiTiet", user_hinhAnh.toString());
                bundle.putString("taiKhoanFragChiTiet", user_taiKhoan);
                bundle.putString("matKhauFragChiTiet", user_matKhau);
                bundle.putString("emailFragChiTiet", user_email);
                bundle.putInt("maQuyenFragChiTiet", user_maQuyen);

                suaNguoiDungFragment.setArguments(bundle);

                FragmentTransaction transactionSuaNguoi = getParentFragmentManager().beginTransaction();
                transactionSuaNguoi.replace(R.id.fragment_container, suaNguoiDungFragment);
                transactionSuaNguoi.addToBackStack("SuaNGuoiDungFragmentFragment");
                transactionSuaNguoi.commit();
            }
        });
    }

    public void xl_xoa(){
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng chọn "Yes"
                        String id = tv_id.getText().toString();
//
                        int deleteCount = databaseHanler.deleteNguoiDung(id);

                        if(deleteCount > 0){
                            Toast.makeText(getContext(), "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
                            requireActivity().getSupportFragmentManager().popBackStack();
                        }
                        else{
                            Toast.makeText(getContext(), "Xóa người dùng thất bại", Toast.LENGTH_SHORT).show();
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
        View view =  inflater.inflate(R.layout.fragment_chi_tiet_nguoi_dung, container, false);

        databaseHanler = new DatabaseHanler(getContext());

        anhXa(view);

        tv_id.setText("" + user_Id);
        tv_ten.setText("" + user_ten);
        img_hinhAnhDuocChon.setImageURI(user_hinhAnh);
        tv_tk.setText("" + user_taiKhoan);
        tv_mk.setText("" + user_matKhau);
        tv_email.setText("" + user_email);
        tv_maQuyen.setText("" + user_maQuyen);

        xl_xoa();

        xl_sua();

        return view;
    }
}