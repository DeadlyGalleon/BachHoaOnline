package com.example.bachhoaonline.model;

public class taikhoan
{

    public taikhoan(Integer idtaikhoan, String tentaikhoan, String matkhau) {
        this.idtaikhoan = idtaikhoan;
        this.tentaikhoan = tentaikhoan;
        this.matkhau = matkhau;
    }


    private Integer idtaikhoan;
    private String tentaikhoan;
    private String matkhau;

    public Integer getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(Integer idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
