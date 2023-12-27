package com.example.myapplication.model;

import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

public class NguoiDung  implements Serializable {
    String id_user;
    String ten_user;
    String hinhAnh_user;
    String taiKhoan_user;
    String matKhau_user;
    String email_user;
    int maQuyen_user;

    public NguoiDung(String id_user, String ten_user, String hinhAnh_user, String taiKhoan_user, String matKhau_user, String email_user, int maQuyen_user) {
        super();
        this.id_user = id_user;
        this.ten_user = ten_user;
        this.hinhAnh_user = hinhAnh_user;
        this.taiKhoan_user = taiKhoan_user;
        this.matKhau_user = matKhau_user;
        this.email_user = email_user;
        this.maQuyen_user = maQuyen_user;
    }

//    public void hashPassUser(){
//        String password = getMatKhau_user();
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//
//        System.out.println("Mật khẩu gốc: " + password);
//        System.out.println("Mật khẩu băm: " + hashedPassword);
//    }
    public String hashPassUser(){
        String password = getMatKhau_user();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        return hashedPassword;
    }

    public String getHinhAnh_user() {
        return hinhAnh_user;
    }

    public void setHinhAnh_user(String hinhAnh_user) {
        this.hinhAnh_user = hinhAnh_user;
    }

    @Override
    public String toString() {
        return "ID: " + id_user +
//                "\n Tên: " + ten_user +
                "\n Tài khoản: " + taiKhoan_user +
//                "\n Hình ảnh: " + hinhAnh_user +
//                "\n Mật khẩu: " + matKhau_user +
//                "\n Hash pass: " + hashPassUser() +
//                "\n Email: " + email_user +
//                "\n Số dư tài khoản: " + soDu_user +
                "\n Mã quyền: " + maQuyen_user;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTen_user() {
        return ten_user;
    }

    public void setTen_user(String ten_user) {
        this.ten_user = ten_user;
    }

    public String getTaiKhoan_user() {
        return taiKhoan_user;
    }

    public void setTaiKhoan_user(String taiKhoan_user) {
        this.taiKhoan_user = taiKhoan_user;
    }

    public String getMatKhau_user() {
        return matKhau_user;
    }

    public void setMatKhau_user(String matKhau_user) {
        this.matKhau_user = matKhau_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }
    public int getMaQuyen_user() {
        return maQuyen_user;
    }

    public void setMaQuyen_user(int maQuyen_user) {
        this.maQuyen_user = maQuyen_user;
    }
}
