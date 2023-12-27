package com.example.myapplication.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
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
import com.example.myapplication.model.NguoiDung;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.ByteArrayOutputStream;

public class SuaNGuoiDungFragmentFragment extends Fragment {

    private EditText edt_id, edt_ten, edt_taiKhoan, edt_matKhau, edt_email, edt_quyen;

    private ImageView img_hinhNguoiDung;

    private String user_Id;
    private String user_ten;
    private Uri user_hinhAnh;
    private String user_taiKhoan;
    private String user_matKhau;
    private String user_email;
    private int user_maQuyen;

    private Button btn_capNhat, btn_thoat, btn_themHinhAnh;
    private DatabaseHanler databaseHanler;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_IMAGE_PICK = 2;
    private Uri imageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == MainActivity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            img_hinhNguoiDung.setImageURI(imageUri);
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
            img_hinhNguoiDung.setImageResource(R.mipmap.img_dl_userr111);
            return convertImageViewToString(img_hinhNguoiDung);
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

    public void anhXa(View view){
        edt_id = view.findViewById(R.id.SuaNguoiDungFragment_edt_idNguoiDung);
        edt_ten = view.findViewById(R.id.SuaNguoiDungFragment_edt_tenNguoiDung);
        edt_taiKhoan = view.findViewById(R.id.SuaNguoiDungFragment_edt_taiKhoanNguoiDung);
        edt_matKhau = view.findViewById(R.id.SuaNguoiDungFragment_edt_matKhauNguoiDung);
        edt_email = view.findViewById(R.id.SuaNguoiDungFragment_edt_emailNguoiDung);
        edt_quyen = view.findViewById(R.id.SuaNguoiDungFragment_edt_maQuyenNguoiDung);

        img_hinhNguoiDung = view.findViewById(R.id.SuaNguoiDungFragment_img_hinhAnhDuocChon);

        btn_capNhat = view.findViewById(R.id.SuaNguoiDungFragment_btn_capNhat);
        btn_thoat = view.findViewById(R.id.SuaNguoiDungFragment_btn_thoatCapNhat);

        btn_themHinhAnh = view.findViewById(R.id.SuaNguoiDungFragment_btn_chonHinhAnh);
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

    public void xl_capNhat(){
        btn_capNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edt_id.getText().toString().trim();
                String ten = edt_ten.getText().toString();
                String taiKhoan = edt_taiKhoan.getText().toString().trim();
                String matKhau = edt_matKhau.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String maQuyen = edt_quyen.getText().toString().trim();

                if(!ktra_thongTinDauVao(id, ten, taiKhoan, matKhau, email, maQuyen)){
                    Toast.makeText(getActivity(), "yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    String stateInsert = "";

                    NguoiDung nguoiDung = new NguoiDung(id, ten, layImg(), taiKhoan, matKhau, email, Integer.parseInt(maQuyen));

                    int capNhatNguoiDung = databaseHanler.updateNguoiDung(nguoiDung);

                    if(capNhatNguoiDung > 0){
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

    public void xl_click_chonHinhAnh(){
        btn_themHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }


    public SuaNGuoiDungFragmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lấy thông tin người dùng từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            user_Id = bundle.getString("idTuFragChiTiet");
            user_ten = bundle.getString("tenFragChiTiet");
            user_hinhAnh = Uri.parse(bundle.getString("hinhAnhFragChiTiet"));
            user_taiKhoan = bundle.getString("taiKhoanFragChiTiet");
            user_matKhau = bundle.getString("matKhauFragChiTiet");
            user_email = bundle.getString("emailFragChiTiet");
            user_maQuyen = bundle.getInt("maQuyenFragChiTiet");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sua_n_guoi_dung_fragment, container, false);

        databaseHanler = new DatabaseHanler(getContext());

        anhXa(view);

        edt_id.setText("" + user_Id);
        edt_ten.setText("" + user_ten);
        img_hinhNguoiDung.setImageURI(user_hinhAnh);
        edt_taiKhoan.setText("" + user_taiKhoan);
        edt_matKhau.setText("" + user_matKhau);
        edt_email.setText("" + user_email);
        edt_quyen.setText("" + user_maQuyen);

        xl_click_chonHinhAnh();

        xl_capNhat();

        return view;
    }
}