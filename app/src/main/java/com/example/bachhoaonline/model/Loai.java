package com.example.bachhoaonline.model;

import java.util.ArrayList;
import java.util.List;

public class Loai {
    private String idloai;
    private String tenloai;
    private String hinhanh;
    private List<loaicon> listloaicon;

    public Loai(String id, String tenLoai, ArrayList<loaicon> loaicons) {
    }

    public List<loaicon> getListloaicon() {
        return listloaicon;
    }

    public void setListloaicon(List<loaicon> listloaicon) {
        this.listloaicon = listloaicon;
    }

    public Loai(String idloai, String tenloai, String hinhanh) {
        this.idloai = idloai;
        this.tenloai = tenloai;
        this.hinhanh = hinhanh;
    }

    public Loai() {
    }

    public Loai(String idloai, String tenloai) {
        this.idloai = idloai;
        this.tenloai = tenloai;
    }

    public String getIdloai() {
        return idloai;
    }

    public void setIdloai(String idloai) {
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
