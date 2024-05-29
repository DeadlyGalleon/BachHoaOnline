package com.example.bachhoaonline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bachhoaonline.QuanLyDanhMucActivity;
import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.loaicon;

import java.util.List;

public class QuanLyLoaiConAdapter extends ArrayAdapter<loaicon> {
    private Context context;
    private List<loaicon> loaiConList;
    private String loaiId;

    public QuanLyLoaiConAdapter(@NonNull Context context, @NonNull List<loaicon> loaiConList, String loaiId) {
        super(context, 0, loaiConList);
        this.context = context;
        this.loaiConList = loaiConList;
        this.loaiId = loaiId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.quanlydanhmuc_loaicon, parent, false);
        }

        loaicon loaiCon = loaiConList.get(position);

        TextView textViewLoaiCon = convertView.findViewById(R.id.textViewLoaiCon);
        Button buttonSuaLoaiCon = convertView.findViewById(R.id.buttonSuaLoaiCon);
        Button buttonXoaLoaiCon = convertView.findViewById(R.id.buttonXoaLoaiCon);
        textViewLoaiCon.setText(loaiCon.getTenloaicon());


        buttonSuaLoaiCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditLoaiConDialog(loaiId, String.valueOf(loaiCon.getIdloaicon()), loaiCon.getTenloaicon());
            }
        });

        buttonXoaLoaiCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyDanhMucActivity) context).deleteLoaiCon(loaiId, String.valueOf(loaiCon.getIdloaicon()));
            }
        });

        return convertView;
    }

    private void showEditLoaiConDialog(String loaiId, String loaiConId, String currentTenLoaiCon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sửa Loại Con");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(currentTenLoaiCon);
        builder.setView(input);

        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTenLoaiCon = input.getText().toString();
                ((QuanLyDanhMucActivity) context).editLoaiCon(loaiId, loaiConId, newTenLoaiCon);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void showAddLLoaiConDialog(String loaiId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thêm Loại Con");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String TenLoaiCon = input.getText().toString();
                ((QuanLyDanhMucActivity) context).addLoaiCon(loaiId, TenLoaiCon);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
