package com.example.bachhoaonline.model;

import java.io.Serializable;

public class giohang implements Serializable {
    private String idSanPham; // Mã sản phẩm (tùy chỉnh tên nếu cần)
    private String tenSanPham;
    private Integer giaBan;
    private String hinhAnh;
    private Integer soLuong;
    private Integer thanhTien;

    public giohang(String idSanPham, String tenSanPham, Integer giaBan, String hinhAnh, Integer soLuong, Integer thanhTien) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public giohang(String idSanPham, String tenSanPham, Integer giaBan, String hinhAnh, Integer soLuong) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
    }

    public giohang() {
        // Constructor mặc định được yêu cầu cho Firebase
    }

    public giohang(String idtaikhoan, String idsanpham, String tensanpham, String hinhanh, Integer giaban, Integer soluong, Integer thanhtien) {
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Integer getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Integer giaBan) {
        this.giaBan = giaBan;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Integer thanhTien) {
        this.thanhTien = thanhTien;
    }
}
   