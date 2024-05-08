package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.taikhoan;

import java.util.List;

public class taikhoanadapter extends ArrayAdapter<taikhoan> {
    private Context context;
    private List<taikhoan> taikhoanList;

    public taikhoanadapter(Context context, List<taikhoan> taikhoanList) {
        super(context, 0, taikhoanList);
        this.context = context;
        this.taikhoanList = taikhoanList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_taikhoan, parent, false);
        }

        taikhoan currentTaikhoan = taikhoanList.get(position);

        TextView idTextView = view.findViewById(R.id.idtk);
        TextView tenTextView = view.findViewById(R.id.tenTextView);
        TextView sdtTextView = view.findViewById(R.id.sdtTextView);
        TextView matkhauTextView = view.findViewById(R.id.matkhauTextView);
        Button button = view.findViewById(R.id.button);

        idTextView.setText(String.valueOf(currentTaikhoan.getIdtaikhoan()));
        tenTextView.setText(currentTaikhoan.getTentaikhoan());
        sdtTextView.setText(currentTaikhoan.getSodienthoai());
        matkhauTextView.setText(currentTaikhoan.getMatkhau());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi Button được nhấn
                // Ví dụ: Hiển thị thông báo, thực hiện một hành động, vv.
            }
        });

        return view;
    }
}