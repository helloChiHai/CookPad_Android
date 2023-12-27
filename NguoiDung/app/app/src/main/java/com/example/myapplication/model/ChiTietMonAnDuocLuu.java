
package com.example.myapplication.model;

public class ChiTietMonAnDuocLuu {
    String id_mon;
    String id_User;

    public String getId_mon() {
        return id_mon;
    }

    public void setId_mon(String id_mon) {
        this.id_mon = id_mon;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public ChiTietMonAnDuocLuu(String id_mon, String id_User) {
        this.id_mon = id_mon;
        this.id_User = id_User;
    }

    @Override
    public String toString() {
        return "id_mon='" + id_mon + '\'' +
                ", id_User='" + id_User + '\''
               ;
    }
}
