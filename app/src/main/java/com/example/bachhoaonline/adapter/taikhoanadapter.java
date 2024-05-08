package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.taikhoan;

import java.util.List;

public class taikhoanadapter extends ArrayAdapter<taikhoan> {
    private Context context;
    private List<taikhoan> taikhoanList;
    private boolean isButtonInListView; // Biến để kiểm tra xem button có trong listview.xml hay không

    public taikhoanadapter(Context context, List<taikhoan> taikhoanList, boolean isButtonInListView) {
        super(context, 0, taikhoanList);
        this.context = context;
        this.taikhoanList = taikhoanList;
        this.isButtonInListView = isButtonInListView; // Gán giá trị của biến khi tạo Adapter
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_taikhoan, parent, false);
        }
        taikhoan currentTaikhoan = taikhoanList.get(position);
        TextView idTextView = view.findViewById(R.id.idtk);
        TextView tenTextView = view.findViewById(R.id.tenTextView);
        TextView sdtTextView = view.findViewById(R.id.sdtTextView);
        TextView matkhauTextView = view.findViewById(R.id.matkhauTextView);
        idTextView.setText(String.valueOf(currentTaikhoan.getIdtaikhoan()));
        tenTextView.setText(currentTaikhoan.getTentaikhoan());
        sdtTextView.setText(currentTaikhoan.getSodienthoai());
        matkhauTextView.setText(currentTaikhoan.getMatkhau());
        return view;
    }
}
