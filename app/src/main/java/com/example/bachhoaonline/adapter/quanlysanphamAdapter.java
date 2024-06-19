package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.sanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class quanlysanphamAdapter extends ArrayAdapter<sanpham> {
    private Context context;
    private OnItemClickListener mListener;
    private List<sanpham> sanPhamList;
    private List<sanpham> originalSanPhamList;

    public quanlysanphamAdapter(Context context, List<sanpham> sanPhamList) {
        super(context, R.layout.quanliitem_sanpham, sanPhamList);
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.originalSanPhamList = new ArrayList<>(sanPhamList);
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.quanliitem_sanpham, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tenTextView = view.findViewById(R.id.tenTextView);
            viewHolder.giaTextView = view.findViewById(R.id.giaTextView);

            viewHolder.imageView = view.findViewById(R.id.imageView);
            viewHolder.buttonEdit = view.findViewById(R.id.buttonEdit);
            viewHolder.buttonDelete = view.findViewById(R.id.buttonDelete);
            viewHolder.productLayout = view.findViewById(R.id.productLayout);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        sanpham sanPham = sanPhamList.get(position);
        viewHolder.tenTextView.setText(sanPham.getTensanpham());
        viewHolder.giaTextView.setText(String.format("%d VND", sanPham.getGiaban()));
        Picasso.get().load(sanPham.getHinhanh()).fit().into(viewHolder.imageView);

        final int pos = position;
        viewHolder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onEditClick(pos);
                }
            }
        });

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onDeleteClick(pos);
                }
            }
        });

        return view;
    }

    public void filter(String query) {
        query = query.toLowerCase();
        sanPhamList.clear();

        if (query.isEmpty()) {
            sanPhamList.addAll(originalSanPhamList);
        } else {
            for (sanpham product : originalSanPhamList) {
                if (product.getTensanpham().toLowerCase().contains(query)) {
                    sanPhamList.add(product);
                }
            }
        }

        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView tenTextView;
        TextView giaTextView;

        TextView loaiTextView;
        ImageView imageView;
        Button buttonEdit;
        Button buttonDelete;
        LinearLayout productLayout;
    }

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}