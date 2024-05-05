package com.example.bachhoaonline.model;

public class donhang {
    private int idDonHang;
    private int idTaiKhoan;
    private String listSanPhamDonHang;
    private String ngayGio;
    private String trangThai;
    private double tongTien;

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getListSanPhamDonHang() {
        return listSanPhamDonHang;
    }

    public void setListSanPhamDonHang(String listSanPhamDonHang) {
        this.listSanPhamDonHang = listSanPhamDonHang;
    }

    public String getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(String ngayGio) {
        this.ngayGio = ngayGio;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
