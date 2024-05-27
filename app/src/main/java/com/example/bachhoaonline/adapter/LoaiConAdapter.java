package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.loaicon;

import java.util.List;

public class LoaiConAdapter extends ArrayAdapter<loaicon> {

    private Context context;
    private List<loaicon> loaiConList;

    public LoaiConAdapter(@NonNull Context context, @NonNull List<loaicon> objects) {
        super(context, 0, objects);
        this.context = context;
        this.loaiConList = objects;
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
        loaicon currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getTenloaicon());
        }

        return convertView;
    }
}
