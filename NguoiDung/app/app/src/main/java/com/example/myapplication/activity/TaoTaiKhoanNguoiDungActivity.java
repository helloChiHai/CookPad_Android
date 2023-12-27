package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.NguoiDung;

import java.io.ByteArrayOutputStream;

public class TaoTaiKhoanNguoiDungActivity extends AppCompatActivity {

    private EditText edtIdNguoiDung, edtTenNguoiDung, edtTaiKhoanNguoiDung, edtMatKhauNguoiDung, edtEmailNguoiDung, edtMaQuyenNguoiDung;

    private Button btninsert, btnCanCel, btn_themHinhAnh;

    private ImageView img_anhDuocChon;

    private DatabaseHelper databaseHelper;

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_IMAGE_PICK = 2;
    private Uri imageUri;

    public void anhXa(){
        edtIdNguoiDung = findViewById(R.id.acTaoTK_edt_idNguoiDung);
        edtTenNguoiDung = findViewById(R.id.acTaoTK_edt_tenNguoiDung);
        edtTaiKhoanNguoiDung = findViewById(R.id.acTaoTK_edt_taiKhoanNguoiDung);
        edtMatKhauNguoiDung = findViewById(R.id.acTaoTK_edt_matKhauNguoiDung);
        edtEmailNguoiDung = findViewById(R.id.acTaoTK_edt_emailNguoiDung);

        img_anhDuocChon = findViewById(R.id.acTaoTK_img_hinhAnhDuocChon);

        btninsert = findViewById(R.id.acTaoTK_btn_taoTaiKhoan);
        btnCanCel = findViewById(R.id.acTaoTK_btn_Huy);
        btn_themHinhAnh = findViewById(R.id.acTaoTK_btn_chonHinhAnh);
    }

    private void xl_huyTaoTK(){
        btnCanCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaoTaiKhoanNguoiDungActivity.this, DangNhapActivity.class);
                startActivity(intent);

            }
        });
    }

    private void xl_chonHinhAnhMonAn(){
        btn_themHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == MainActivity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            img_anhDuocChon.setImageURI(imageUri);
        }
    }

    private void openFileChooser() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
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
                Toast.makeText(this, "Bị từ chối truy cập", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String layImg() {
        if (imageUri == null) {
            img_anhDuocChon.setImageResource(R.mipmap.img_montom_foreground);
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

    private boolean ktra_thongTinDauVao(String id, String ten, String tk, String mk, String email, String maQuyen){
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

    private void xl_taoTaiKhoan(){
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtIdNguoiDung.getText().toString().trim();
                String ten = edtTenNguoiDung.getText().toString();
                String taiKhoan = edtTaiKhoanNguoiDung.getText().toString().trim();
                String matKhau = edtMatKhauNguoiDung.getText().toString().trim();
                String email = edtEmailNguoiDung.getText().toString().trim();
                String maQuyen = "0";

                if(!ktra_thongTinDauVao(id, ten, taiKhoan, matKhau, email, maQuyen)){
                    Toast.makeText(TaoTaiKhoanNguoiDungActivity.this, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Lưu dữ liệu vào cơ sở dữ liệu của project A
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("idNguoiDung", id);
                    values.put("tenNguoiDung", ten);
                    values.put("hinhAnhNguoiDung", layImg());
                    values.put("taiKhoanNguoiDung", taiKhoan);
                    values.put("matKhauNguoiDung", matKhau);
                    values.put("emailNguoiDung", email);
                    values.put("maQuyenNguoiDung", Integer.parseInt(maQuyen));

                    long result = db.insert("nguoiDungs", null, values);
                    db.close();

                    if (result == -1) {
                        Toast.makeText(TaoTaiKhoanNguoiDungActivity.this, "Tạo tài khoản không thành công(Do trùng id và tài khoản", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TaoTaiKhoanNguoiDungActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan_nguoi_dung);

        databaseHelper = new DatabaseHelper(this);

        anhXa();

        xl_huyTaoTK();

        xl_taoTaiKhoan();

        xl_chonHinhAnhMonAn();
    }
}