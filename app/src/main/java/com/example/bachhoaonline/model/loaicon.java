package com.example.bachhoaonline.model;

public class loaicon
{
    private Integer idloaicha;
    private Integer idloaicon;
    private Integer tenloaicon;
    private  String hinhanhloaicon;

    public String getHinhanhloaicon() {
        return hinhanhloaicon;
    }

    public loaicon(Integer idloaicha, Integer idloaicon, Integer tenloaicon, String hinhanhloaicon) {
        this.idloaicha = idloaicha;
        this.idloaicon = idloaicon;
        this.tenloaicon = tenloaicon;
        this.hinhanhloaicon = hinhanhloaicon;
    }


    public loaicon(Integer idloaicha, Integer idloaicon, Integer tenloaicon) {
        this.idloaicha = idloaicha;
        this.idloaicon = idloaicon;
        this.tenloaicon = tenloaicon;
    }

    public void setHinhanhloaicon(String hinhanhloaicon) {
        this.hinhanhloaicon = hinhanhloaicon;
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

    public Integer getTenloaicon() {
        return tenloaicon;
    }

    public void setTenloaicon(Integer tenloaicon) {
        this.tenloaicon = tenloaicon;
    }
}
