package com.example.myapplication.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.database.DatabaseHanler;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.NguoiDung;

import java.io.ByteArrayOutputStream;

public class SuaMonAnFragment extends Fragment {
    private EditText edt_id, edt_ten, edt_loai, edt_huongDan, edt_nguyenLieu, edt_thoiGianNau, edt_dungCu;

    private ImageView img_hinhMonAn;

    private String food_Id;
    private String food_ten;
    private Uri food_hinhAnhMonAn;
    private String food_loai;
    private String food_huongDan;
    private String food_nguyenLieu;
    private String food_thoiGianNau;
    private String food_dungCu;

    private Button btn_capNhatfood, btn_thoatfood, btn_themHinhAnhfood;
    private DatabaseHanler databaseHanler;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_IMAGE_PICK = 2;
    private Uri imageUrifood;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == MainActivity.RESULT_OK && data != null && data.getData() != null) {
            imageUrifood = data.getData();
            img_hinhMonAn.setImageURI(imageUrifood);
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
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK);
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
        if (imageUrifood == null) {
            img_hinhMonAn.setImageResource(R.mipmap.img_dl_userr111);
            return convertImageViewToString(img_hinhMonAn);
        }
        return imageUrifood.toString();
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

    public void xl_click_chonHinhAnhMonAn(){
        btn_themHinhAnhfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    public void xl_capNhat(){
        btn_capNhatfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edt_id.getText().toString().trim();
                String ten = edt_ten.getText().toString();
                String loai = edt_loai.getText().toString();
                String huongDan = edt_huongDan.getText().toString();
                String nguyenLieu = edt_nguyenLieu.getText().toString();
                String thoiGianNau = edt_thoiGianNau.getText().toString();
                String dungCu = edt_dungCu.getText().toString();

                if(!ktra_thongTinDauVao(id, ten, loai, huongDan, nguyenLieu, thoiGianNau, dungCu)){
                    Toast.makeText(getActivity(), "yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    String stateInsert = "";


                    MonAn monAn = new MonAn(id, null, ten, layImg(), loai, huongDan, nguyenLieu, thoiGianNau, dungCu);

                    int capNhatMonAn = databaseHanler.updateMonAn(monAn);

                    if(capNhatMonAn > 0){
                        stateInsert = "Cập nhật thành công";
                        Toast.makeText(getActivity(), stateInsert, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        stateInsert = "Cập nhật thất bại";
                        Toast.makeText(getActivity(), stateInsert, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void anhXa(View view){
        edt_id = view.findViewById(R.id.SuaMonAnFragment_edt_idMonAn);
        edt_ten = view.findViewById(R.id.SuaMonAnFragment_edt_tenMonAn);
        edt_loai = view.findViewById(R.id.SuaMonAnFragment_edt_loaiMonAn);
        edt_huongDan = view.findViewById(R.id.SuaMonAnFragment_edt_huongDanMonAn);
        edt_nguyenLieu = view.findViewById(R.id.SuaMonAnFragment_edt_nguyenLieuMonAn);
        edt_thoiGianNau = view.findViewById(R.id.SuaMonAnFragment_edt_thoiGianNauMonAn);
        edt_dungCu = view.findViewById(R.id.SuaMonAnFragment_edt_dungCuMonAn);

        img_hinhMonAn = view.findViewById(R.id.SuaMonAnFragment_img_hinhAnhMonAnDuocChon);

        btn_capNhatfood = view.findViewById(R.id.SuaMonAnFragment_btn_capNhatMonAn);
        btn_thoatfood = view.findViewById(R.id.SuaMonAnFragment_btn_thoatCapNhatMonAn);

        btn_themHinhAnhfood = view.findViewById(R.id.SuaMonAnFragment_btn_chonHinhAnhMonAn);
    }

    public boolean ktra_thongTinDauVao(String id, String ten, String loai, String huongDan, String nguyenLieu, String tgNau, String dungCu){
        // neu rong -> tra ve false
        // khong rong -> tra ve true
        if(id.isEmpty() ||
                ten.isEmpty() ||
                loai.isEmpty() ||
                huongDan.isEmpty() ||
                nguyenLieu.isEmpty() ||
                tgNau.isEmpty() ||
                dungCu.isEmpty())
            return false;
        return true;
    }


    public SuaMonAnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lấy thông tin món ăn từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            food_Id = bundle.getString("idMATuFragChiTiet");
            food_ten = bundle.getString("tenMAFragChiTiet");
            food_hinhAnhMonAn = Uri.parse(bundle.getString("hinhAnhMAFragChiTiet"));
            food_loai = bundle.getString("loaiMAFragChiTiet");
            food_huongDan = bundle.getString("huongDanMAFragChiTiet");
            food_nguyenLieu = bundle.getString("nguyenLieuMAFragChiTiet");
            food_thoiGianNau = bundle.getString("thoiGianNauTuFragChiTiet");
            food_dungCu = bundle.getString("dungCuTuFragChiTiet");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sua_mon_an, container, false);

        databaseHanler = new DatabaseHanler(getContext());

        anhXa(view);

        edt_id.setText("" + food_Id);
        edt_ten.setText("" + food_ten);
        img_hinhMonAn.setImageURI(food_hinhAnhMonAn);
        edt_loai.setText("" + food_loai);
        edt_huongDan.setText("" + food_huongDan);
        edt_nguyenLieu.setText("" + food_nguyenLieu);
        edt_thoiGianNau.setText("" + food_thoiGianNau);
        edt_dungCu.setText("" + food_dungCu);

        xl_click_chonHinhAnhMonAn();

        xl_capNhat();

        return view;
    }
}