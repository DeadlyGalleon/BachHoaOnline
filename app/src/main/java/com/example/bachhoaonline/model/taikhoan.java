package com.example.bachhoaonline.model;

public class taikhoan
{


    public taikhoan(Integer idtaikhoan, String tentaikhoan, String sodienthoai, String matkhau) {
        this.idtaikhoan = idtaikhoan;
        this.tentaikhoan = tentaikhoan;
        this.sodienthoai = sodienthoai;
        this.matkhau = matkhau;

    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    private Integer idtaikhoan;
    private String tentaikhoan;
    private String sodienthoai;
    private String matkhau;
    private int laquanly;

    public int getLaquanly() {
        return laquanly;
    }

    public void setLaquanly(int laquanly) {
        this.laquanly = laquanly;
    }

    public taikhoan() {

    }

    public Integer getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(Integer idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
