package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.ChiTietMonAnDuocLuu;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHanler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "databaseUngDungNauAn_10diem.db";
    private static final int DATABASE_VERSION = 1;

                // BẢNG NGƯỜI NGƯỜI DÙNG

    private static final String TABLE_NAME_NGUOIDUNG = "nguoiDungs";
    private static final String KEY_ID_NGUOIDUNG = "idNguoiDung";
    private static final String KEY_TEN_NGUOIDUNG = "tenNguoiDung";
    private static final String KEY_HINHANH_NGUOIDUNG = "hinhAnhNguoiDung";
    private static final String KEY_TAIKHOAN_NGUOIDUNG = "taiKhoanNguoiDung";
    private static final String KEY_MATKHAU_NGUOIDUNG = "matKhauNguoiDung";
    private static final String KEY_EMAIL_NGUOIDUNG = "emailNguoiDung";
    private static final String KEY_MAQUYEN_NGUOIDUNG = "maQuyenNguoiDung";

                // BẢNG MÓN ĂN
    private static final String TABLE_NAME_MONAN = "monAns";
    private static final String KEY_ID_MONAN = "id_monAn";
    private static final String KEY_ID_FK_NGUOIDUNG = "id_nguoiDung";
    private static final String KEY_TEN_MONAN = "tenMonAn";
    private static final String KEY_HINHANH_MONAN = "hinhAnh";
    private static final String KEY_LOAI_MONAN = "loai";
    private static final String KEY_HUONDAN_MONAN = "huonDan";
    private static final String KEY_NGUYENLIEU_MONAN = "nguyenLieu";
    private static final String KEY_THOIGIANNAU_MONAN = "thoiGianNau";
    private static final String KEY_DUNGCU_MONAN = "dungCu";

                // BẢNG CHI TIẾT MÓN ĂN ĐƯỢC LƯU

    private static final String TABLE_NAME_CHITIETMONLUU = "chiTietMonLuus";

    private static final String KEY_ID_USER_CHITIETMONLUU = "id_nguoiDung_chiTiet";
    private static final String KEY_ID_MON_CHITIETMONLUU = "id_monAn_chiTiet";

    public DatabaseHanler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create_table_chiTietMonLuus = "CREATE TABLE chiTietMonLuus(id_nguoiDung_chiTiet TEXT, id_monAn_chiTiet TEXT, PRIMARY KEY(id_nguoiDung_chiTiet, id_monAn_chiTiet), FOREIGN KEY (id_nguoiDung_chiTiet) REFERENCES nguoiDungs(id_nguoiDung), FOREIGN KEY (id_monAn_chiTiet) REFERENCES monAns(id_monAn))";

        String sql_create_table_monAns = "CREATE TABLE monAns(id_monAn TEXT primary key, id_nguoiDung TEXT, tenMonAn TEXT, hinhAnh TEXT, loai TEXT, huonDan TEXT, nguyenLieu TEXT, thoiGianNau TEXT, dungCu TEXT, FOREIGN KEY (id_nguoiDung) REFERENCES nguoiDungs(id_nguoiDung))";

        String sql_create_table_nguoiDungs = "CREATE TABLE nguoiDungs(idNguoiDung TEXT primary key, tenNguoiDung TEXT, hinhAnhNguoiDung TEXT, taiKhoanNguoiDung TEXT, matKhauNguoiDung TEXT, emailNguoiDung TEXT, maQuyenNguoiDung INTEGER)";

        db.execSQL(sql_create_table_chiTietMonLuus);
        db.execSQL(sql_create_table_monAns);
        db.execSQL(sql_create_table_nguoiDungs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nguoiDungs");
        db.execSQL("DROP TABLE IF EXISTS monAns");
        db.execSQL("DROP TABLE IF EXISTS chiTietMonLuus");
        onCreate(db);

        onCreate(db);
    }

    public boolean them_NguoiDung(NguoiDung nguoiDung){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_NGUOIDUNG, nguoiDung.getId_user());
        values.put(KEY_TEN_NGUOIDUNG, nguoiDung.getTen_user());
        values.put(KEY_HINHANH_NGUOIDUNG, nguoiDung.getHinhAnh_user());
        values.put(KEY_TAIKHOAN_NGUOIDUNG, nguoiDung.getTaiKhoan_user());
        values.put(KEY_MATKHAU_NGUOIDUNG, nguoiDung.getMatKhau_user());
        values.put(KEY_EMAIL_NGUOIDUNG, nguoiDung.getEmail_user());
        values.put(KEY_MAQUYEN_NGUOIDUNG, nguoiDung.getMaQuyen_user());

        if(db.insert(TABLE_NAME_NGUOIDUNG, null, values) == -1){
            return false;
        }
        db.close();
        return true;
    }
    public boolean them_MonAn(MonAn monAn){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_MONAN, monAn.getId_MA());
        values.put(KEY_ID_FK_NGUOIDUNG, monAn.getId_User());
        values.put(KEY_TEN_MONAN, monAn.getTen_MA());
        values.put(KEY_HINHANH_MONAN, monAn.getHinhAnh_MA());
        values.put(KEY_LOAI_MONAN, monAn.getLoai_MA());
        values.put(KEY_HUONDAN_MONAN, monAn.getHuongDanNau_MA());
        values.put(KEY_NGUYENLIEU_MONAN, monAn.getNguyenLieu_MA());
        values.put(KEY_THOIGIANNAU_MONAN, monAn.getThoiGianNau_MA());
        values.put(KEY_DUNGCU_MONAN, monAn.getDungCu_MA());

        if(db.insert(TABLE_NAME_MONAN, null, values) == -1){
            return false;
        }
        db.close();
        return true;
    }
    public boolean them_MonAnVaoChiTietBoiNguoiDung(ChiTietMonAnDuocLuu chiTietMonAnDuocLuu){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER_CHITIETMONLUU, chiTietMonAnDuocLuu.getId_User());
        values.put(KEY_ID_MON_CHITIETMONLUU, chiTietMonAnDuocLuu.getId_mon());

        if (db.insert(TABLE_NAME_CHITIETMONLUU, null, values) == -1) {
            return false;
        }
        db.close();
        return true;
    }

    public List<MonAn> getCacMonAnDaLuu(String idNguoiDung) {
        List<MonAn> monAnList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Tạo truy vấn SELECT để lấy các món ăn đã lưu của người dùng
        String query = "SELECT * FROM " + TABLE_NAME_MONAN + " INNER JOIN " +
                TABLE_NAME_CHITIETMONLUU + " ON " + TABLE_NAME_MONAN + "." + KEY_ID_MONAN +
                " = " + TABLE_NAME_CHITIETMONLUU + "." + KEY_ID_MON_CHITIETMONLUU +
                " WHERE " + TABLE_NAME_CHITIETMONLUU + "." + KEY_ID_USER_CHITIETMONLUU +
                " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{idNguoiDung});
        cursor.moveToFirst();

        // Duyệt qua các dòng kết quả và tạo đối tượng MonAn tương ứng
        while (!cursor.isAfterLast()) {
            MonAn monAn = new MonAn(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8));
            monAnList.add(monAn);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return monAnList;
    }

    public boolean checkUserExists(String idNguoiDung) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Tạo truy vấn SELECT để kiểm tra sự tồn tại của người dùng trong CSDL
        String query = "SELECT * FROM " + TABLE_NAME_NGUOIDUNG +
                " WHERE " + KEY_ID_NGUOIDUNG + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{idNguoiDung});
        boolean userExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return userExists;
    }

    public boolean checkMonAnExists(String idMonAn) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Tạo truy vấn SELECT để kiểm tra sự tồn tại của món ăn trong CSDL
        String query = "SELECT * FROM " + TABLE_NAME_MONAN +
                " WHERE " + KEY_ID_MONAN + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{idMonAn});
        boolean monAnExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return monAnExists;
    }


    // tìm kiếm người dùng theo id
    public NguoiDung getNguoiDung(String id_user){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_NGUOIDUNG, null, KEY_ID_NGUOIDUNG + " = ?", new String[] { id_user },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        NguoiDung nguoiDung = new NguoiDung(cursor.getString(0), cursor.getString(1),cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
        return nguoiDung;
    }

    // tìm kiếm món ăn theo id
    public MonAn getMonAn(String id_food){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_MONAN, null, KEY_ID_MONAN + " = ?", new String[] { id_food },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        MonAn monAn = new MonAn(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
        return monAn;
    }

    public List<NguoiDung> getAllNguoiDungs() {
        List<NguoiDung> nguoiDungList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_NGUOIDUNG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            NguoiDung nguoiDung = new NguoiDung(cursor.getString(0), cursor.getString(1),cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
            nguoiDungList.add(nguoiDung);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return nguoiDungList;
    }

    public List<MonAn> getAllMonAns() {
        List<MonAn> monAnList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_MONAN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            MonAn monAn = new MonAn(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
            monAnList.add(monAn);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return monAnList;
    }

//    Đối số 1: tên bảng
//    Đối số 2: đối tượng muốn chỉnh sửa (với giá trị mới)
//    Đối số 3: tập các điều kiện lọc (dùng dấu ? để tạo điều kiện lọc)
//    Đối số 4: tập các giá trị ủa điều kiện lọc (lấy theo đúng thứ tự)

    public int updateNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_NGUOIDUNG, nguoiDung.getId_user());
        values.put(KEY_TEN_NGUOIDUNG, nguoiDung.getTen_user());
        values.put(KEY_HINHANH_NGUOIDUNG, nguoiDung.getTen_user());
        values.put(KEY_TAIKHOAN_NGUOIDUNG, nguoiDung.getTaiKhoan_user());
        values.put(KEY_MATKHAU_NGUOIDUNG, nguoiDung.getMatKhau_user());
        values.put(KEY_EMAIL_NGUOIDUNG, nguoiDung.getEmail_user());
        values.put(KEY_MAQUYEN_NGUOIDUNG, nguoiDung.getMaQuyen_user());

        int result = db.update(TABLE_NAME_NGUOIDUNG, values, KEY_ID_NGUOIDUNG + " = ?", new String[] { nguoiDung.getId_user() });
        db.close();

        return result;
    }
    public int updateMonAn(MonAn monAn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_MONAN, monAn.getId_MA());
        values.put(KEY_ID_FK_NGUOIDUNG, monAn.getId_User());
        values.put(KEY_TEN_MONAN, monAn.getTen_MA());
        values.put(KEY_HINHANH_MONAN, monAn.getHinhAnh_MA());
        values.put(KEY_LOAI_MONAN, monAn.getLoai_MA());
        values.put(KEY_HUONDAN_MONAN, monAn.getHuongDanNau_MA());
        values.put(KEY_NGUYENLIEU_MONAN, monAn.getNguyenLieu_MA());
        values.put(KEY_THOIGIANNAU_MONAN, monAn.getThoiGianNau_MA());
        values.put(KEY_DUNGCU_MONAN, monAn.getDungCu_MA());

        int result = db.update(TABLE_NAME_MONAN, values, KEY_ID_MONAN + " = ?", new String[] { monAn.getId_MA() });
        db.close();

        return result;
    }

    public int deleteNguoiDung(String id_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME_NGUOIDUNG, KEY_ID_NGUOIDUNG + " = ?", new String[] { id_user });

        db.close();
        return result;
    }
    public int deleteMonAn(String id_food) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME_MONAN, KEY_ID_MONAN + " = ?", new String[] { id_food });

        db.close();
        return result;
    }
}
