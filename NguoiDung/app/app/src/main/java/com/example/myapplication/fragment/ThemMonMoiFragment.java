package com.example.myapplication.fragment;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.DangNhapActivity;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.model.MonAn;

import java.io.ByteArrayOutputStream;

public class ThemMonMoiFragment extends Fragment {

    private Button btn_chonHinhAnh, btn_saveMon;
    private Toolbar toolbar;
    private ImageButton imageButtonBack;

    private ImageView img_anhDuocChon;

    private EditText edt_tenMonAn, edt_loaiMon, edt_thoiGianNau, edt_nguyenLieu, edt_cachLam, edt_dungCu;

    private Uri uri_hinhMonAn;

    private DatabaseHelper databaseHelper;

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_IMAGE_PICK = 2;

    private void anhXa(View view){
        edt_tenMonAn = view.findViewById(R.id.edt_tenMon);
        edt_loaiMon = view.findViewById(R.id.edt_loaiMonAn);
        edt_thoiGianNau = view.findViewById(R.id.edt_thoiGianNau);
        edt_nguyenLieu = view.findViewById(R.id.edt_nguyenLieu);
        edt_cachLam = view.findViewById(R.id.edt_cachLam);
        edt_dungCu = view.findViewById(R.id.edt_dungCu);

        toolbar = view.findViewById(R.id.toolbar_fragment_themMonAn);

        btn_chonHinhAnh = view.findViewById(R.id.btn_moThuVienChonHinhMonAn);
        btn_saveMon = view.findViewById(R.id.btn_luuMonAn);

        imageButtonBack = view.findViewById(R.id.imgBtn_quayLai);
        img_anhDuocChon = view.findViewById(R.id.img_hinhMonDuocChon);
    }

    public void xl_chonHinhAnhMonAn(){
        btn_chonHinhAnh.setOnClickListener(new View.OnClickListener() {
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
            uri_hinhMonAn = data.getData();
            img_anhDuocChon.setImageURI(uri_hinhMonAn);
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
                Toast.makeText(getContext(), "Bị từ chối truy cập", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String layImg() {
        if (uri_hinhMonAn == null) {
            img_anhDuocChon.setImageResource(R.mipmap.img_montom_foreground);
            return convertImageViewToString(img_anhDuocChon);
        }
        return uri_hinhMonAn.toString();
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

    private void xl_luuMonAn(){
        btn_saveMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idMonAn = edt_tenMonAn.getText().toString().trim();
                String idUser = DangNhapActivity.anhXaTaiKhoan();
                String tenMonAn = edt_tenMonAn.getText().toString();
                String loai = edt_loaiMon.getText().toString().trim();
                String huonDan = edt_cachLam.getText().toString().trim();
                String nguyenLieu = edt_nguyenLieu.getText().toString().trim();
                String thoiGianNau = edt_thoiGianNau.getText().toString().trim();
                String dungCu = edt_dungCu.getText().toString().trim();

                if(!ktra_thongTinDauVao(idMonAn, tenMonAn, loai, huonDan, nguyenLieu, thoiGianNau, dungCu)){
                    Toast.makeText(getActivity(), "yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Lưu dữ liệu vào cơ sở dữ liệu của project A
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("id_monAn", idMonAn);
                    values.put("id_nguoiDung", idUser);
                    values.put("tenMonAn", tenMonAn);
                    values.put("hinhAnh", layImg());
                    values.put("loai", loai);
                    values.put("huonDan", huonDan);
                    values.put("nguyenLieu", nguyenLieu);
                    values.put("thoiGianNau", thoiGianNau);
                    values.put("dungCu", dungCu);
                    long result = db.insert("monAns", null, values);
                    db.close();

                    if (result == -1) {
                        Toast.makeText(getActivity(), "Thêm món ăn không thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                    }
                }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_mon_moi, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        anhXa(view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // Xóa title của Toolbar
        toolbar.setTitle("");

        xl_luuMonAn();

        xl_chonHinhAnhMonAn();

        XLNutBack(view);

        return view;
    }

    private void XLNutBack(View view){
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFragment addFragment = new AddFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, addFragment);
                transaction.commit();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_addmonan, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.xoaMonNay){
            Intent intent = new Intent(getActivity(), DangNhapActivity.class);
            startActivity(intent);
            return true;
        }
        return  super.onOptionsItemSelected(item);
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