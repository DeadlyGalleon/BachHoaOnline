package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bachhoaonline.model.Loai;

import java.util.List;

public class LoaiSpinnerAdapter extends ArrayAdapter<Loai> {
    private Context context;
    private List<Loai> loaiList;

    public LoaiSpinnerAdapter(Context context, List<Loai> loaiList) {
        super(context, android.R.layout.simple_spinner_item, loaiList);
        this.context = context;
        this.loaiList = loaiList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(loaiList.get(position).getTenloai());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(loaiList.get(position).getTenloai());

        return convertView;
    }
}


