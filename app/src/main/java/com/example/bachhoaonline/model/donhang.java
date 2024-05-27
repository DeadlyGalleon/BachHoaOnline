package com.example.bachhoaonline.model;

import java.util.ArrayList;

public class donhang {
    private String DiaChi;
    private String idDonHang;
    private String idTaiKhoan;
    private ArrayList<sanphamdonhang> listSanPhamDonHang;
    private String ngaydat;
    private String ngaygiao;
    private int trangThai;
    private double tongTien;

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public donhang(String idDonHang, String idTaiKhoan, String ngaydat, String ngaygiao, int trangThai, double tongTien) {
        this.idDonHang = idDonHang;
        this.idTaiKhoan = idTaiKhoan;
        this.ngaydat = ngaydat;
        this.ngaygiao = ngaygiao;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public donhang() {
    }

    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public ArrayList<sanphamdonhang> getListSanPhamDonHang() {
        return listSanPhamDonHang;
    }

    public void setListSanPhamDonHang(ArrayList<sanphamdonhang> listSanPhamDonHang) {
        this.listSanPhamDonHang = listSanPhamDonHang;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getNgaygiao() {
        return ngaygiao;
    }

    public void setNgaygiao(String ngaygiao) {
        this.ngaygiao = ngaygiao;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
