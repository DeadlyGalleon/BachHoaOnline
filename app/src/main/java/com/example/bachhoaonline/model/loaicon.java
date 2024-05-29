package com.example.bachhoaonline.model;

public class loaicon {
    private Integer idloaicha;
    private Integer idloaicon;
    private String tenloaicon;
    private String hinhanhloaicon;

    public loaicon() {
    }

    public loaicon(Integer idloaicha, Integer idloaicon, String tenloaicon) {
        this.idloaicha = idloaicha;
        this.idloaicon = idloaicon;
        this.tenloaicon = tenloaicon;
    }

    public Integer getIdloaicha() {
        return idloaicha;
    }

    public void setIdloaicha(Integer idloaicha) {
        this.idloaicha = idloaicha;
    }

    public Integer getIdloaicon() {
        return idloaicon;
    }

    public void setIdloaicon(Integer idloaicon) {
        this.idloaicon = idloaicon;
    }

    public String getTenloaicon() {
        return tenloaicon;
    }

    public void setTenloaicon(String tenloaicon) {
        this.tenloaicon = tenloaicon;
    }

    public String getHinhanhloaicon() {
        return hinhanhloaicon;
    }

    public void setHinhanhloaicon(String hinhanhloaicon) {
        this.hinhanhloaicon = hinhanhloaicon;
    }
}

