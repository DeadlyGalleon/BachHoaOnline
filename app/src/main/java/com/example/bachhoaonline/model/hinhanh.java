package com.example.bachhoaonline.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class hinhanh {
    private String idsanpham;
    private ArrayList<String> hinhanh;

    public hinhanh(String idsanpham, ArrayList<String> hinhanh) {
        this.idsanpham = idsanpham;
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return "hinhanh{" +
                "idsanpham='" + idsanpham + '\'' +
                ", hinhanh=" + hinhanh +
                '}';
    }

    public String getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(String idsanpham) {
        this.idsanpham = idsanpham;
    }

    public ArrayList<String> getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(ArrayList<String> hinhanh) {
        this.hinhanh = hinhanh;
    }
}
