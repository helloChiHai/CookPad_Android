package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.ChiTietMonAnDuocLuu;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.MonAn_1;
import com.example.myapplication.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_PATH = "/data/data/com.example.myapplication/databases/databaseUngDungNauAn_10diem.db";


    // BẢNG NGƯỜI DÙNG
    private static final String TABLE_NAME_NGUOIDUNG = "nguoiDungs";

    private static final String COLUMN_ID_NGUOIDUNG = "idNguoiDung";
    private static final String KEY_TEN_NGUOIDUNG = "tenNguoiDung";
    private static final String COLUMN_HINHANH_NGUOIDUNG = "hinhAnhNguoiDung";
    private static final String COLUMN_TAIKHOAN_NGUOIDUNG = "taiKhoanNguoiDung";
    private static final String COLUMN_MATKHAU_NGUOIDUNG = "matKhauNguoiDung";
    private static final String COLUMN_EMAIL_NGUOIDUNG = "emailNguoiDung";
    private static final String COLUMN_MAQUYEN_NGUOIDUNG = "maQuyenNguoiDung";


    // BẢNG MÓN ĂN

    private static final String TABLE_NAME_MONAN = "monAns";
    private static final String KEY_ID_MONAN = "id_monAn";
    private static final String KEY_ID_FK_NGUOIDUNG = "id_nguoiDung";
    private static final String KEY_TEN_MONAN = "tenMonAn";
    private static final String KEY_HINHANH_MONAN = "hinhAnh";
    private static final String KEY_LOAI_MONAN = "loai";
    private static final String KEY_HUONGDAN_MONAN = "huonDan";
    private static final String KEY_NGUYENLIEU_MONAN = "nguyenLieu";
    private static final String KEY_THOIGIANNAU_MONAN = "thoiGianNau";
    private static final String KEY_DUNGCU_MONAN = "dungCu";


    // BẢNG CHI TIẾT MÓN ĂN ĐƯỢC LƯU

    private static final String TABLE_NAME_CHITIETMONLUU = "chiTietMonLuus";

    private static final String KEY_ID_USER_CHITIETMONLUU = "id_nguoiDung_chiTiet";
    private static final String KEY_ID_MON_CHITIETMONLUU = "id_monAn_chiTiet";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_PATH, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không cần viết logic tạo bảng vì đã có cơ sở dữ liệu từ project A
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Không cần viết logic nâng cấp phiên bản vì không có thay đổi cấu trúc cơ sở dữ liệu
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_NGUOIDUNG;
        return db.rawQuery(query, null);
    }

    public ArrayList<NguoiDung> getUsersFromDatabaseByAccountPass(String taiKhoan, String matKhau){
        ArrayList<NguoiDung> userList = new ArrayList<>();

        // Mở cơ sở dữ liệu
        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (database != null) {
            String query = "SELECT * FROM nguoiDungs WHERE taiKhoanNguoiDung = ? AND matKhauNguoiDung = ?";
            String[] selectionArgs = {taiKhoan, matKhau};
            Cursor cursor = database.rawQuery(query, selectionArgs);

//
//            // Truy vấn dữ liệu từ bảng người dùng
//            Cursor cursor = database.rawQuery("SELECT * FROM nguoiDungs", null);

            if (cursor.moveToFirst()) {
                do {
                    // Đọc dữ liệu từ con trỏ và tạo đối tượng User
                    String id = cursor.getString(cursor.getColumnIndex("idNguoiDung"));
                    String name = cursor.getString(cursor.getColumnIndex("tenNguoiDung"));
                    String image = cursor.getString(cursor.getColumnIndex("hinhAnhNguoiDung"));
                    String username = cursor.getString(cursor.getColumnIndex("taiKhoanNguoiDung"));
                    String password = cursor.getString(cursor.getColumnIndex("matKhauNguoiDung"));
                    String email = cursor.getString(cursor.getColumnIndex("emailNguoiDung"));
                    int maQuyen = cursor.getInt(cursor.getColumnIndex("maQuyenNguoiDung"));

                    NguoiDung user = new NguoiDung(id, name, image, username, password, email, maQuyen);
                    userList.add(user);
                } while (cursor.moveToNext());
            }

            // Đóng con trỏ và cơ sở dữ liệu
            cursor.close();
            database.close();
        }

        return userList;
    }

    public ArrayList<NguoiDung> getUsersFromDatabase() {
        ArrayList<NguoiDung> userList = new ArrayList<>();

        // Mở cơ sở dữ liệu
        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (database != null) {
            // Truy vấn dữ liệu từ bảng người dùng
            Cursor cursor = database.rawQuery("SELECT * FROM nguoiDungs", null);

            if (cursor.moveToFirst()) {
                do {
                    // Đọc dữ liệu từ con trỏ và tạo đối tượng User
                    String id = cursor.getString(cursor.getColumnIndex("idNguoiDung"));
                    String name = cursor.getString(cursor.getColumnIndex("tenNguoiDung"));
                    String image = cursor.getString(cursor.getColumnIndex("hinhAnhNguoiDung"));
                    String username = cursor.getString(cursor.getColumnIndex("taiKhoanNguoiDung"));
                    String password = cursor.getString(cursor.getColumnIndex("matKhauNguoiDung"));
                    String email = cursor.getString(cursor.getColumnIndex("emailNguoiDung"));
                    int maQuyen = cursor.getInt(cursor.getColumnIndex("maQuyenNguoiDung"));

                    NguoiDung user = new NguoiDung(id, name, image, username, password, email, maQuyen);
                    userList.add(user);
                } while (cursor.moveToNext());
            }

            // Đóng con trỏ và cơ sở dữ liệu
            cursor.close();
            database.close();
        }

        return userList;
    }

    public ArrayList<MonAn> getFoodsFromDatabase() {
        ArrayList<MonAn> userList = new ArrayList<>();

        // Mở cơ sở dữ liệu
        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (database != null) {
            // Truy vấn dữ liệu từ bảng người dùng
            Cursor cursor = database.rawQuery("SELECT * FROM monAns", null);

            if (cursor.moveToFirst()) {
                do {
                    String idMA = cursor.getString(cursor.getColumnIndex("id_monAn"));
                    String idNguoiDung = cursor.getString(cursor.getColumnIndex("id_nguoiDung"));
                    String tenMA = cursor.getString(cursor.getColumnIndex("tenMonAn"));
                    String hinhMA = cursor.getString(cursor.getColumnIndex("hinhAnh"));
                    String loaiMA = cursor.getString(cursor.getColumnIndex("loai"));
                    String huongDanMA = cursor.getString(cursor.getColumnIndex("huonDan"));
                    String nguyenLieuMA = cursor.getString(cursor.getColumnIndex("nguyenLieu"));
                    String thoiGianNauMA = cursor.getString(cursor.getColumnIndex("thoiGianNau"));
                    String dungCuMA = cursor.getString(cursor.getColumnIndex("dungCu"));

                    MonAn monAn = new MonAn(idMA, idNguoiDung, tenMA, hinhMA, loaiMA, huongDanMA, nguyenLieuMA, thoiGianNauMA, dungCuMA);
                    userList.add(monAn);
                } while (cursor.moveToNext());
            }

            // Đóng con trỏ và cơ sở dữ liệu
            cursor.close();
            database.close();
        }

        return userList;
    }

    public String layTenTheoTaiKhoan(String taiKhoan) {
        String userName = "";

        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (database != null) {
            // Truy vấn dữ liệu từ bảng người dùng
            String query = "SELECT tenNguoiDung FROM nguoiDungs WHERE taiKhoanNguoiDung = ?";
            String[] selectionArgs = {taiKhoan};
            Cursor cursor = database.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                userName = cursor.getString(cursor.getColumnIndex("tenNguoiDung"));
            }

            // Đóng con trỏ và cơ sở dữ liệu
            cursor.close();
            database.close();
        }
        return userName;
    }

    public String timTenNguoiDungCuaMon(String foodName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_NAME_NGUOIDUNG + "." + KEY_TEN_NGUOIDUNG
                + " FROM " + TABLE_NAME_MONAN
                + " INNER JOIN " + TABLE_NAME_NGUOIDUNG + " ON " + TABLE_NAME_MONAN + "." + KEY_ID_FK_NGUOIDUNG + " = " + TABLE_NAME_NGUOIDUNG + "." + COLUMN_ID_NGUOIDUNG
                + " WHERE " + TABLE_NAME_MONAN + "." + KEY_TEN_MONAN + " = ?";

        String[] selectionArgs = { foodName };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        String userName = "cookpad";

        if (cursor.moveToFirst()) {
            userName = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return userName;
    }

    public String timIDNguoiDungCuaMon(String foodName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_NAME_NGUOIDUNG + "." + COLUMN_ID_NGUOIDUNG
                + " FROM " + TABLE_NAME_MONAN
                + " INNER JOIN " + TABLE_NAME_NGUOIDUNG + " ON " + TABLE_NAME_MONAN + "." + KEY_ID_FK_NGUOIDUNG + " = " + TABLE_NAME_NGUOIDUNG + "." + COLUMN_ID_NGUOIDUNG
                + " WHERE " + TABLE_NAME_MONAN + "." + KEY_TEN_MONAN + " = ?";

        String[] selectionArgs = { foodName };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        String userId = "cook123"; // Giá trị mặc định nếu không tìm thấy

        if (cursor.moveToFirst()) {
            userId = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return userId;
    }

    public String timHinhAnhNguoiDungCuaMon(String foodName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_NAME_NGUOIDUNG + "." + COLUMN_HINHANH_NGUOIDUNG
                + " FROM " + TABLE_NAME_MONAN
                + " INNER JOIN " + TABLE_NAME_NGUOIDUNG + " ON " + TABLE_NAME_MONAN + "." + KEY_ID_FK_NGUOIDUNG + " = " + TABLE_NAME_NGUOIDUNG + "." + COLUMN_ID_NGUOIDUNG
                + " WHERE " + TABLE_NAME_MONAN + "." + KEY_TEN_MONAN + " = ?";

        String[] selectionArgs = { foodName };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        String userImg = "null"; // Giá trị mặc định nếu không tìm thấy

        if (cursor.moveToFirst()) {
            userImg = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return userImg;
    }

    public String layIDTheoTaiKhoan(String taiKhoan) {
        String layID = "";

        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (database != null) {
            // Truy vấn dữ liệu từ bảng người dùng
            String query = "SELECT taiKhoanNguoiDung FROM nguoiDungs WHERE taiKhoanNguoiDung = ?";
            String[] selectionArgs = {taiKhoan};
            Cursor cursor = database.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                layID = cursor.getString(cursor.getColumnIndex("taiKhoanNguoiDung"));
            }

            // Đóng con trỏ và cơ sở dữ liệu
            cursor.close();
            database.close();
        }
        return layID;
    }

    public String layHinhAnhTheoTaiKhoan(String idTaiKhoan) {
        String layImg = "";

        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (database != null) {
            // Truy vấn dữ liệu từ bảng người dùng
            String query = "SELECT hinhAnhNguoiDung FROM nguoiDungs WHERE idNguoiDung = ?";
            String[] selectionArgs = {idTaiKhoan};
            Cursor cursor = database.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                layImg = cursor.getString(cursor.getColumnIndex("hinhAnhNguoiDung"));
            }

            // Đóng con trỏ và cơ sở dữ liệu
            cursor.close();
            database.close();
        }
        return layImg;
    }



    public List<MonAn> layTatCaMonAn() {
        List<MonAn> userList = new ArrayList<>();

        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (database != null) {
            String query = "SELECT * FROM monAns";
            Cursor cursor = database.rawQuery(query, null);

            while (cursor.moveToNext()) {
                String idMA = cursor.getString(cursor.getColumnIndex("id_monAn"));
                String idNguoiDung = cursor.getString(cursor.getColumnIndex("id_nguoiDung"));
                String tenMA = cursor.getString(cursor.getColumnIndex("tenMonAn"));
                String hinhMA = cursor.getString(cursor.getColumnIndex("hinhAnh"));
                String loaiMA = cursor.getString(cursor.getColumnIndex("loai"));
                String huongDanMA = cursor.getString(cursor.getColumnIndex("huonDan"));
                String nguyenLieuMA = cursor.getString(cursor.getColumnIndex("nguyenLieu"));
                String thoiGianNauMA = cursor.getString(cursor.getColumnIndex("thoiGianNau"));
                String dungCuMA = cursor.getString(cursor.getColumnIndex("dungCu"));

                MonAn monAn = new MonAn(idMA, idNguoiDung, tenMA, hinhMA, loaiMA, huongDanMA, nguyenLieuMA, thoiGianNauMA, dungCuMA);
                userList.add(monAn);
            }

            cursor.close();
            database.close();
        }
        return userList;
    }

    public List<MonAn> layTatCaMonAnTheoLoai(String loaiMon) {
        List<MonAn> monAnList = new ArrayList<>();

        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (database != null) {
            String query = "SELECT * FROM monAns WHERE loai = ?";
            String[] selectionArgs = {loaiMon};
            Cursor cursor = database.rawQuery(query, selectionArgs);

            while (cursor.moveToNext()) {
                String idMA = cursor.getString(cursor.getColumnIndex("id_monAn"));
                String idNguoiDung = cursor.getString(cursor.getColumnIndex("id_nguoiDung"));
                String tenMA = cursor.getString(cursor.getColumnIndex("tenMonAn"));
                String hinhMA = cursor.getString(cursor.getColumnIndex("hinhAnh"));
                String loaiMA = cursor.getString(cursor.getColumnIndex("loai"));
                String huongDanMA = cursor.getString(cursor.getColumnIndex("huonDan"));
                String nguyenLieuMA = cursor.getString(cursor.getColumnIndex("nguyenLieu"));
                String thoiGianNauMA = cursor.getString(cursor.getColumnIndex("thoiGianNau"));
                String dungCuMA = cursor.getString(cursor.getColumnIndex("dungCu"));

                MonAn monAn = new MonAn(idMA, idNguoiDung, tenMA, hinhMA, loaiMA, huongDanMA, nguyenLieuMA, thoiGianNauMA, dungCuMA);
                monAnList.add(monAn);
            }

            cursor.close();
            database.close();
        }
        return monAnList;
    }

    public List<MonAn> timKiemMonAn(String tenMon) {
        List<MonAn> monAnList = new ArrayList<>();

        SQLiteDatabase database = null;
        try {
            database = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (database != null) {
            String query = "SELECT * FROM monAns WHERE tenMonAn LIKE '%" + tenMon + "%'";
            Cursor cursor = database.rawQuery(query, null);

            while (cursor.moveToNext()) {
                String idMA = cursor.getString(cursor.getColumnIndex("id_monAn"));
                String idNguoiDung = cursor.getString(cursor.getColumnIndex("id_nguoiDung"));
                String tenMA = cursor.getString(cursor.getColumnIndex("tenMonAn"));
                String hinhMA = cursor.getString(cursor.getColumnIndex("hinhAnh"));
                String loaiMA = cursor.getString(cursor.getColumnIndex("loai"));
                String huongDanMA = cursor.getString(cursor.getColumnIndex("huonDan"));
                String nguyenLieuMA = cursor.getString(cursor.getColumnIndex("nguyenLieu"));
                String thoiGianNauMA = cursor.getString(cursor.getColumnIndex("thoiGianNau"));
                String dungCuMA = cursor.getString(cursor.getColumnIndex("dungCu"));

                MonAn monAn = new MonAn(idMA, idNguoiDung, tenMA, hinhMA, loaiMA, huongDanMA, nguyenLieuMA, thoiGianNauMA, dungCuMA);
                monAnList.add(monAn);
            }
            cursor.close();
            database.close();
        }
        return monAnList;
    }

    public boolean checkAccountAndPassword(String taiKhoan, String matKhau) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_NGUOIDUNG;
        Cursor cursor = db.rawQuery(query, null);

        boolean exists = false;

        while (cursor.moveToNext()) {
            String dbTaiKhoan = cursor.getString(cursor.getColumnIndex(COLUMN_ID_NGUOIDUNG));
            String dbMatKhau = cursor.getString(cursor.getColumnIndex(COLUMN_MATKHAU_NGUOIDUNG));

            if (dbTaiKhoan.equals(taiKhoan) && dbMatKhau.equals(matKhau)) {
                exists = true;
                break;
            }
        }

        cursor.close();
        return exists;
    }

    public boolean them_MonAnVaoDanhSachLuuBoiNguoiDung(ChiTietMonAnDuocLuu chiTietMonAnDuocLuu){
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

    public ArrayList<NguoiDung> getAllUserInfoById(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NguoiDung> userInfoList = new ArrayList<>();

        String[] columns = {"idNguoiDung", "tenNguoiDung", "hinhAnhNguoiDung", "taiKhoanNguoiDung", "matKhauNguoiDung", "emailNguoiDung", "maQuyenNguoiDung"};
        String selection = "idNguoiDung = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("nguoiDungs", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("idNguoiDung"));
            String ten = cursor.getString(cursor.getColumnIndex("tenNguoiDung"));
            String img = cursor.getString(cursor.getColumnIndex("hinhAnhNguoiDung"));
            String tk = cursor.getString(cursor.getColumnIndex("taiKhoanNguoiDung"));
            String mk = cursor.getString(cursor.getColumnIndex("matKhauNguoiDung"));
            String email = cursor.getString(cursor.getColumnIndex("emailNguoiDung"));
            int maQuyen = cursor.getInt(cursor.getColumnIndex("maQuyenNguoiDung"));

            NguoiDung userInfo = new NguoiDung(id, ten, img, tk, mk, email, maQuyen);
            userInfoList.add(userInfo);
        }

        cursor.close();
        db.close();

        return userInfoList;
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
                " WHERE " + COLUMN_ID_NGUOIDUNG + " = ?";

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

}