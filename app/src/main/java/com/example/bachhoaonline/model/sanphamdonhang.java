package com.example.bachhoaonline.model;

public class sanphamdonhang {
    private String idDonHang;
    private String idSanPham;
    private double giaban;
    private int soluong;
    private double thanhTien;
private  String tensanpham;
    private  String hinhanh;

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public double getGiaCu() {
        return giaban;
    }

    public void setGiaCu(double giaCu) {
        this.giaban = giaCu;
    }

    public int getSoLuong() {
        return soluong;
    }

    public void setSoLuong(int soLuong) {
        this.soluong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
