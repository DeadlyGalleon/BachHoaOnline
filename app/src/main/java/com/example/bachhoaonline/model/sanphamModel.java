package com.example.bachhoaonline.model;

import java.util.List;

public class sanphamModel {
    boolean success;
    String message;
    List<sanpham> sanphamList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<sanpham> getSanphamList() {
        return sanphamList;
    }

    public void setSanphamList(List<sanpham> sanphamList) {
        this.sanphamList = sanphamList;
    }
}
