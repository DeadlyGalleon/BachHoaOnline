package com.example.bachhoaonline.model;

public class giohang {
    private Integer idtaikhoan;
    private Integer idsanpham;
    private  String tensanpham;

    private String hinhanhsanphamgiohang;
    private Integer giabanra;

    public giohang(Integer idtaikhoan, Integer idsanpham, String tensanpham, String hinhanhsanphamgiohang, Integer giabanra) {
        this.idtaikhoan = idtaikhoan;
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.hinhanhsanphamgiohang = hinhanhsanphamgiohang;
        this.giabanra = giabanra;
    }

    @Override
    public String toString() {
        return "giohang{" +
                "idtaikhoan=" + idtaikhoan +
                ", idsanpham=" + idsanpham +
                ", tensanpham='" + tensanpham + '\'' +
                ", hinhanhsanphamgiohang='" + hinhanhsanphamgiohang + '\'' +
                ", giabanra=" + giabanra +
                '}';
    }

    public Integer getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(Integer idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public Integer getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(Integer idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhanhsanphamgiohang() {
        return hinhanhsanphamgiohang;
    }

    public void setHinhanhsanphamgiohang(String hinhanhsanphamgiohang) {
        this.hinhanhsanphamgiohang = hinhanhsanphamgiohang;
    }

    public Integer getGiabanra() {
        return giabanra;
    }

    public void setGiabanra(Integer giabanra) {
        this.giabanra = giabanra;
    }
}
