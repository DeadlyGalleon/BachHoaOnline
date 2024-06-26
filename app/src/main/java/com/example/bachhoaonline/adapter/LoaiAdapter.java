package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bachhoaonline.model.Loai;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.Loai;

import java.util.List;

public class LoaiAdapter extends ArrayAdapter<Loai> {

    private Context context;
    private List<Loai> loaiList;

    public LoaiAdapter(@NonNull Context context, @NonNull List<Loai> objects) {
        super(context, 0, objects);
        this.context = context;
        this.loaiList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        Loai currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getTenloai());
        }

        return convertView;
    }
}