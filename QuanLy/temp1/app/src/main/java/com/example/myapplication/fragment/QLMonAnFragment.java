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
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.NguoiDung;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class QLMonAnFragment extends Fragment {
    private EditText edtIdMonAn, edtTenMonAn, edtLoaiMonAn, edtHuongDanMonAn, edtNguyenLieuMonAn, edtThoiGianMonAn, edtDungCuMonAn;

    private Button btninsertMonAn, btnqueryMonAn, btn_themHinhAnhMonAn;

    private ImageView img_anhMonAnDuocChon;


    // khai báo ListView
    private ListView lv;
    private List<MonAn> monAnList;
    private ArrayAdapter<MonAn> monAnArrayAdapter;

    private DatabaseHanler databaseHanler;

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_IMAGE_PICK = 2;
    private Uri imageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == MainActivity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            img_anhMonAnDuocChon.setImageURI(imageUri);
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

    public String layImg() {
        if (imageUri == null) {
            img_anhMonAnDuocChon.setImageResource(R.mipmap.img_dl_userr111);
            return convertImageViewToString(img_anhMonAnDuocChon);
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

    public void xl_themMonAn(){
        btninsertMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idMonAn = edtIdMonAn.getText().toString().trim();
                String idUser = null;
                String tenMonAn = edtTenMonAn.getText().toString();
                String loai = edtLoaiMonAn.getText().toString().trim();
                String huonDan = edtHuongDanMonAn.getText().toString().trim();
                String nguyenLieu = edtNguyenLieuMonAn.getText().toString().trim();
                String thoiGianNau = edtThoiGianMonAn.getText().toString().trim();
                String dungCu = edtDungCuMonAn.getText().toString().trim();

                if(!ktra_thongTinDauVao(idMonAn, tenMonAn, loai, huonDan, nguyenLieu, thoiGianNau, dungCu)){
                    Toast.makeText(getActivity(), "yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    String stateInsert = "";

                    MonAn monAn = new MonAn(idMonAn, idUser, tenMonAn, layImg(), loai, huonDan, nguyenLieu, thoiGianNau, dungCu);

                    if(!databaseHanler.them_MonAn(monAn)){
                        stateInsert = "thêm thất bại(do trùng ID)";
                        Toast.makeText(getActivity(), stateInsert, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        stateInsert = "thêm thành công";
                        Toast.makeText(getActivity(), stateInsert, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void xl_queryMonAn(){
        btnqueryMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MonAn> monAns = databaseHanler.getAllMonAns();

                monAnArrayAdapter.clear();

                monAnArrayAdapter.addAll(monAns);
            }
        });
    }

    public boolean ktra_thongTinDauVao(String id, String ten, String loai, String huongDan, String nguyenLieu, String thoiGianNau, String dungCu){
        // neu rong -> tra ve false
        // khong rong -> tra ve true
        if(id.isEmpty() ||
                ten.isEmpty() ||
                loai.isEmpty() ||
                huongDan.isEmpty() ||
                nguyenLieu.isEmpty() ||
                thoiGianNau.isEmpty() ||
                dungCu.isEmpty())
            return false;
        return true;
    }

    public void xl_click_chonHinhAnhMonAn(){
        btn_themHinhAnhMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    public void anhXa(View view){
        edtIdMonAn = view.findViewById(R.id.edt_idMonAn);
        edtTenMonAn = view.findViewById(R.id.edt_tenMonAn);
        edtLoaiMonAn = view.findViewById(R.id.edt_loaiMonAn);
        edtHuongDanMonAn = view.findViewById(R.id.edt_huongDanNauMonAn);
        edtNguyenLieuMonAn = view.findViewById(R.id.edt_nguyenLieuMonAn);
        edtThoiGianMonAn = view.findViewById(R.id.edt_thoiGianNauMonAn);
        edtDungCuMonAn = view.findViewById(R.id.edt_dungCuMonAn);

        img_anhMonAnDuocChon = view.findViewById(R.id.img_hinhAnhMonAnDuocChon);

        btninsertMonAn = view.findViewById(R.id.btn_themMonAn);
        btnqueryMonAn = view.findViewById(R.id.btn_queryMonAn);
        btn_themHinhAnhMonAn = view.findViewById(R.id.btn_chonHinhAnhMonAn);
    }

    public QLMonAnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_mon_an, container, false);

        anhXa(view);

        databaseHanler = new DatabaseHanler(getContext());

        lv = view.findViewById(R.id.lv_dsMonAn);
        monAnList = new ArrayList<>();

        monAnArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        lv.setAdapter(monAnArrayAdapter);

        xl_click_chonHinhAnhMonAn();

        xl_themMonAn();
        xl_queryMonAn();

        return view;
    }
}