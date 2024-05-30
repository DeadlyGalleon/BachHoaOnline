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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bachhoaonline.QuanLyDanhMucActivity;
import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.Loai;

import java.util.List;

public class QuanLyLoaiAdapter extends ArrayAdapter<Loai> {
    private Context context;
    private List<Loai> loaiList;

    public QuanLyLoaiAdapter(@NonNull Context context, @NonNull List<Loai> loaiList) {
        super(context, 0, loaiList);
        this.context = context;
        this.loaiList = loaiList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.quanlydanhmuc_loai, parent, false);
        }

        Loai loai = loaiList.get(position);

        TextView textViewLoai = convertView.findViewById(R.id.textViewLoai);
        Button buttonSuaLoai = convertView.findViewById(R.id.buttonSuaLoai);
        Button buttonXoaLoai = convertView.findViewById(R.id.buttonXoaLoai);
        ListView listViewLoaiCon = convertView.findViewById(R.id.listViewLoaiCon);

        textViewLoai.setText(loai.getTenloai());

        QuanLyLoaiConAdapter loaiConAdapter = new QuanLyLoaiConAdapter(context, loai.getListloaicon(), loai.getIdloai());
        listViewLoaiCon.setAdapter(loaiConAdapter);

        setListViewHeightBasedOnChildren(listViewLoaiCon);
        Button themloaicon=convertView.findViewById(R.id.buttonThemLoaiCon);


        themloaicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddLLoaiConDialog(loai.getIdloai());

            }
        });
        buttonSuaLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditLoaiDialog(loai.getIdloai(), loai.getTenloai());
            }
        });

        buttonXoaLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyDanhMucActivity) context).deleteLoai(loai.getIdloai());
            }
        });

        return convertView;
    }


    private void showAddLLoaiConDialog(String loaiId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thêm Loại Con");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
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

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void showEditLoaiDialog(String loaiId, String currentTenLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sửa Loại");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(currentTenLoai);
        builder.setView(input);

        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTenLoai = input.getText().toString();
                ((QuanLyDanhMucActivity) context).editLoai(loaiId, newTenLoai);
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
