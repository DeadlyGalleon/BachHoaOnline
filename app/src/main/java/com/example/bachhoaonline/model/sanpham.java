package com.example.bachhoaonline.model;

public class sanpham {
    private String idsanpham;
    private String tensanpham;
    private  Integer giaban;

    private String hinhanh;



    public sanpham(String idsanpham, String tensanpham, Integer giaban, String hinhanh) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
        this.hinhanh = hinhanh;
    }

    public sanpham(String idsanpham, String tensanpham, Integer giaban) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
    }



    public String getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(String idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public Integer getGiaban() {
        return giaban;
    }

    public void setGiaban(Integer giaban) {
        this.giaban = giaban;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
