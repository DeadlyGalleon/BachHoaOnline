package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bachhoaonline.model.sanpham;
import com.example.bachhoaonline.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class sanphamadapter extends ArrayAdapter<sanpham> {

    private Context context;
    private List<sanpham> sanPhamList;

    public sanphamadapter(Context context, List<sanpham> sanPhamList) {
        super(context, R.layout.item_sanpham, sanPhamList);
        this.context = context;
        this.sanPhamList = sanPhamList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_sanpham, null);
            viewHolder = new ViewHolder();
            viewHolder.tenTextView = view.findViewById(R.id.tenspTextView);
            viewHolder.giaBanTextView = view.findViewById(R.id.giaBanTextView);
            viewHolder.loaiTextView = view.findViewById(R.id.loaiTextView);
            viewHolder.imageView = view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        sanpham sanPham = sanPhamList.get(position);

        viewHolder.tenTextView.setText(sanPham.getTensanpham());
        viewHolder.giaBanTextView.setText(String.format("%d VND", sanPham.getGiaban()));
        viewHolder.loaiTextView.setText(sanPham.getLoai());
        Picasso.get().load(sanPham.getHinhanh()).into(viewHolder.imageView);

        return view;
    }

    private static class ViewHolder {
        TextView tenTextView;
        TextView giaBanTextView;
        TextView loaiTextView;
        ImageView imageView;
    }
}
