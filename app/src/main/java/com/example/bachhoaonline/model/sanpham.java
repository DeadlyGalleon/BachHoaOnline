package com.example.bachhoaonline.model;

public class sanpham {
    private String idsanpham;
    private String tensanpham;
    private  Long giaban;
    private String loai;
    private String hinhanh;
    public sanpham(String idsanpham, String tensanpham, Long giaban, String loai, String hinhanh) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
        this.loai = loai;
        this.hinhanh = hinhanh;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }


    public sanpham(String idsanpham, String tensanpham, Long giaban, String hinhanh) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
        this.hinhanh = hinhanh;
    }

    public sanpham(String idsanpham, String tensanpham, Long giaban) {
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

    public Long getGiaban() {
        return giaban;
    }

    public void setGiaban(Long giaban) {
        this.giaban = giaban;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
