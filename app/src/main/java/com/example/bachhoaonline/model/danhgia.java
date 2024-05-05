package com.example.bachhoaonline.model;

public class danhgia {
    public int idtaikhoan;
    public int idsanpham;
    public int soSao;
    public String binhLuan;
    public String ngayGio;

    public int getidtaikhoan() {
        return idtaikhoan;
    }



    @Override
    public String toString() {
        return "danhgia{" +
                "idtaikhoan=" + idtaikhoan +
                ", idsanpham=" + idsanpham +
                ", soSao=" + soSao +
                ", binhLuan='" + binhLuan + '\'' +
                ", ngayGio='" + ngayGio + '\'' +
                '}';
    }

    public void setidtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public int getIdSanPham() {
        return idsanpham;
    }

    public void setIdSanPham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public int getSoSao() {
        return soSao;
    }

    public void setSoSao(int soSao) {
        this.soSao = soSao;
    }

    public String getBinhLuan() {
        return binhLuan;
    }

    public void setBinhLuan(String binhLuan) {
        this.binhLuan = binhLuan;
    }

    public String getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(String ngayGio) {
        this.ngayGio = ngayGio;
    }

    public int getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public danhgia(int idtaikhoan, int idsanpham, int soSao, String binhLuan, String ngayGio) {



        this.idtaikhoan = idtaikhoan;
        this.idsanpham = idsanpham;
        this.soSao = soSao;
        this.binhLuan = binhLuan;
        this.ngayGio = ngayGio;
    }
}
