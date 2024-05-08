package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bachhoaonline.model.Loai;

import java.util.List;

public class LoaiAdapter extends ArrayAdapter<Loai> {
    private List<Loai> loaiList;

    public LoaiAdapter(Context context, List<Loai> loaiList) {
        super(context, android.R.layout.simple_spinner_dropdown_item);
        this.loaiList = loaiList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);

        textView.setText(loaiList.get(position).getTenloai());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setText(loaiList.get(position).getTenloai());
        return textView;
    }
}
