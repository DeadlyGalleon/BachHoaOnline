package com.example.bachhoaonline.model;

public class sanpham {
    private String idsanpham;
    private String tensanpham;
    private  Long giaban;
    private Integer loai;
    private String hinhanh;
    private String mota;



    public sanpham(String idsanpham, String tensanpham, Long giaban, Integer loai, String hinhanh) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
        this.loai = loai;
        this.hinhanh = hinhanh;
    }
    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public sanpham() {
    }

    public Integer getLoai() {
        return loai;
    }

    public void setLoai(Integer loai) {
        this.loai = loai;
    }


    public sanpham(String idsanpham, String tensanpham, Long giaban, String hinhanh) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
        this.hinhanh = hinhanh;
    }
    public sanpham(String idsanpham, String tensanpham, Long giaban, String hinhanh,Integer loai) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
        this.hinhanh = hinhanh;
        this.loai=loai;

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
