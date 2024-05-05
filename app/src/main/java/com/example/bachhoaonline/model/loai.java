package com.example.bachhoaonline.model;

public class loai {
    private int idloai;
    private String tenloai;
    private String hinhanh;

    public loai(int idloai, String tenloai, String hinhanh) {
        this.idloai = idloai;
        this.tenloai = tenloai;
        this.hinhanh = hinhanh;
    }



    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }



}
