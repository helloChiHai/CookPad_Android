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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.database.DatabaseHanler;
import com.example.myapplication.model.NguoiDung;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class QLNguoiDungFragment extends Fragment {

    private EditText edtIdNguoiDung, edtTenNguoiDung, edtTaiKhoanNguoiDung, edtMatKhauNguoiDung, edtEmailNguoiDung, edtMaQuyenNguoiDung;

    private Button btninsert, btndelete, btnupdate, btnquery, btn_themHinhAnh;

    private ImageView img_anhDuocChon;


    // khai báo ListView
    ListView lv;
    List<NguoiDung> mylist;
    ArrayAdapter<NguoiDung> myadapter;

    DatabaseHanler mydatabase;

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_IMAGE_PICK = 2;
    private Uri imageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == MainActivity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            img_anhDuocChon.setImageURI(imageUri);
        }
    }

    private void openFileChooser() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        } else {
            performImagePick();
        }
    }

    private void performImagePick() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE_IMAGE_PICK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performImagePick();
            } else {
                Toast.makeText(getContext(), "bị từ chối truy cập", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public QLNguoiDungFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void anhXa(View view){
        edtIdNguoiDung = view.findViewById(R.id.edt_idNguoiDung);
        edtTenNguoiDung = view.findViewById(R.id.edt_tenNguoiDung);
        edtTaiKhoanNguoiDung = view.findViewById(R.id.edt_taiKhoanNguoiDung);
        edtMatKhauNguoiDung = view.findViewById(R.id.edt_matKhauNguoiDung);
        edtEmailNguoiDung = view.findViewById(R.id.edt_emailNguoiDung);
        edtMaQuyenNguoiDung = view.findViewById(R.id.edt_maQuyenNguoiDung);

        img_anhDuocChon = view.findViewById(R.id.img_hinhAnhDuocChon);

        btninsert = view.findViewById(R.id.btn_them);
        btndelete = view.findViewById(R.id.btn_xoa);
        btnupdate = view.findViewById(R.id.btn_sua);
        btnquery = view.findViewById(R.id.btn_query);
        btn_themHinhAnh = view.findViewById(R.id.btn_chonHinhAnh);
    }

    public String layImg() {
        if (imageUri == null) {
            img_anhDuocChon.setImageResource(R.mipmap.img_dl_userr111);
            return convertImageViewToString(img_anhDuocChon);
        }
        return imageUri.toString();
    }

    private String convertImageViewToString(ImageView imageView) {
        Bitmap bitmap = getBitmapFromDrawable(imageView.getDrawable());

        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        return null;
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }


    public void xl_themND(){
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtIdNguoiDung.getText().toString().trim();
                String ten = edtTenNguoiDung.getText().toString();
                String taiKhoan = edtTaiKhoanNguoiDung.getText().toString().trim();
                String matKhau = edtMatKhauNguoiDung.getText().toString().trim();
                String email = edtEmailNguoiDung.getText().toString().trim();
                String maQuyen = edtMaQuyenNguoiDung.getText().toString().trim();

                if(!ktra_thongTinDauVao(id, ten, taiKhoan, matKhau, email, maQuyen)){
                    Toast.makeText(getActivity(), "yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    String stateInsert = "";

                    NguoiDung nguoiDung = new NguoiDung(id, ten, layImg(), taiKhoan, matKhau, email, Integer.parseInt(maQuyen));

                    if(!mydatabase.them_NguoiDung(nguoiDung)){
                        stateInsert = "thêm thất bại(do trùng ID)";
                        Toast.makeText(getContext(), stateInsert, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        stateInsert = "thêm thành công";
                        Toast.makeText(getContext(), stateInsert, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void xl_queryND(){
        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<NguoiDung> nguoiDungList = mydatabase.getAllNguoiDungs();

                myadapter.clear();

                myadapter.addAll(nguoiDungList);

                for (NguoiDung nguoiDung : mylist) {
                    Log.d("Từ quản lý người dùng fragment: ", "Tên: " + nguoiDung.getTen_user());
                }
            }
        });
    }

    public boolean ktra_thongTinDauVao(String id, String ten, String tk, String mk, String email, String maQuyen){
        // neu rong -> tra ve false
        // khong rong -> tra ve true
        if(id.isEmpty() ||
                ten.isEmpty() ||
                tk.isEmpty() ||
                mk.isEmpty() ||
                email.isEmpty() ||
                maQuyen.isEmpty())
            return false;
        return true;
    }

    public void xl_click_chonHinhAnh(){
        btn_themHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_nguoi_dung, container, false);

        anhXa(view);

        mydatabase = new DatabaseHanler(getContext());

        // Tạo ListView
        lv = view.findViewById(R.id.lv_dsLop);
        mylist = new ArrayList<>();

        myadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        lv.setAdapter(myadapter);

        xl_click_chonHinhAnh();

        xl_themND();
        xl_queryND();

        return view;
    }

}