package com.example.myapplication.model;

public class MonAn {
    String id_MA;
    String id_User;
    String ten_MA;
    String hinhAnh_MA;
    String loai_MA;
    String huongDanNau_MA;
    String nguyenLieu_MA;
    String thoiGianNau_MA;
    String dungCu_MA;

    public MonAn(String id_MA, String id_User, String ten_MA, String hinhAnh_MA, String loai_MA, String huongDanNau_MA, String nguyenLieu_MA, String thoiGianNau_MA, String dungCu_MA) {
        this.id_MA = id_MA;
        this.id_User = id_User;
        this.ten_MA = ten_MA;
        this.hinhAnh_MA = hinhAnh_MA;
        this.loai_MA = loai_MA;
        this.huongDanNau_MA = huongDanNau_MA;
        this.nguyenLieu_MA = nguyenLieu_MA;
        this.thoiGianNau_MA = thoiGianNau_MA;
        this.dungCu_MA = dungCu_MA;
    }

    public String getId_MA() {
        return id_MA;
    }

    public void setId_MA(String id_MA) {
        this.id_MA = id_MA;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getTen_MA() {
        return ten_MA;
    }

    public void setTen_MA(String ten_MA) {
        this.ten_MA = ten_MA;
    }

    public String getHinhAnh_MA() {
        return hinhAnh_MA;
    }

    public void setHinhAnh_MA(String hinhAnh_MA) {
        this.hinhAnh_MA = hinhAnh_MA;
    }

    public String getLoai_MA() {
        return loai_MA;
    }

    public void setLoai_MA(String loai_MA) {
        this.loai_MA = loai_MA;
    }

    public String getHuongDanNau_MA() {
        return huongDanNau_MA;
    }

    public void setHuongDanNau_MA(String huongDanNau_MA) {
        this.huongDanNau_MA = huongDanNau_MA;
    }

    public String getNguyenLieu_MA() {
        return nguyenLieu_MA;
    }

    public void setNguyenLieu_MA(String nguyenLieu_MA) {
        this.nguyenLieu_MA = nguyenLieu_MA;
    }

    public String getThoiGianNau_MA() {
        return thoiGianNau_MA;
    }

    public void setThoiGianNau_MA(String thoiGianNau_MA) {
        this.thoiGianNau_MA = thoiGianNau_MA;
    }

    public String getDungCu_MA() {
        return dungCu_MA;
    }

    public void setDungCu_MA(String dungCu_MA) {
        this.dungCu_MA = dungCu_MA;
    }

    @Override
    public String toString() {
        return "id_MA: " + id_MA +
                "\n id_User: " + id_User +
                "\n ten_MA='" + ten_MA;
    }
//    @Override
//    public String toString() {
//        return "MonAn{" +
//                "id_MA='" + id_MA + '\'' +
//                ", id_User='" + id_User + '\'' +
//                ", ten_MA='" + ten_MA + '\'' +
//                ", hinhAnh_MA='" + hinhAnh_MA + '\'' +
//                ", moTa_MA='" + moTa_MA + '\'' +
//                ", nguyenLieu_MA='" + nguyenLieu_MA + '\'' +
//                ", thoiGianNau_MA='" + thoiGianNau_MA + '\'' +
//                ", dungCu_MA='" + dungCu_MA + '\'' +
//                '}';
//    }
}
