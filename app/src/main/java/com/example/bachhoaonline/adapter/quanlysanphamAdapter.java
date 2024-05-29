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

public class quanlysanphamAdapter extends ArrayAdapter<sanpham>

{

    private Context context;
    private quanlysanphamAdapter.OnItemClickListener mListener;
    private List<sanpham> sanPhamList;
    private List<sanpham> originalSanPhamList;

    public quanlysanphamAdapter(Context context, List<sanpham> sanPhamList) {
        super(context, R.layout.item_sanpham, sanPhamList);
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.originalSanPhamList = new ArrayList<>(sanPhamList);

    }

    @Override
    public int getCount() {
        return (sanPhamList.size() + 1) / 2; // mỗi hàng có hai sản phẩm
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        quanlysanphamAdapter.ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.quanliitem_sanpham, parent, false);
            viewHolder = new quanlysanphamAdapter.ViewHolder();
            viewHolder.tenTextView1 = view.findViewById(R.id.tenTextView1);
            viewHolder.giaTextView1 = view.findViewById(R.id.giaTextView1);
            viewHolder.imageView1 = view.findViewById(R.id.imageView1);
            viewHolder.buttonEdit1 = view.findViewById(R.id.buttonEdit1);
            viewHolder.buttonDelete1 = view.findViewById(R.id.buttonDelete1);
            viewHolder.productLayout1 = view.findViewById(R.id.productLayout1);

            viewHolder.tenTextView2 = view.findViewById(R.id.tenTextView2);
            viewHolder.giaTextView2 = view.findViewById(R.id.giaTextView2);
            viewHolder.imageView2 = view.findViewById(R.id.imageView2);
            viewHolder.buttonEdit2 = view.findViewById(R.id.buttonEdit2);
            viewHolder.buttonDelete2 = view.findViewById(R.id.buttonDelete2);
            viewHolder.productLayout2 = view.findViewById(R.id.productLayout2);

            view.setTag(viewHolder);
        } else {
            viewHolder = (quanlysanphamAdapter.ViewHolder) view.getTag();
        }

        // Lấy sản phẩm thứ nhất
        sanpham sanPham1 = sanPhamList.get(position * 2);
        viewHolder.tenTextView1.setText(sanPham1.getTensanpham());
        viewHolder.giaTextView1.setText(String.format("%d VND", sanPham1.getGiaban()));
        Picasso.get()
                .load(sanPham1.getHinhanh())
                .fit()
                .into(viewHolder.imageView1);
        viewHolder.buttonEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onEditClick(position * 2);
                }
            }
        });
        viewHolder.buttonDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onDeleteClick(position * 2);
                }
            }
        });

        // Kiểm tra xem có sản phẩm thứ hai không
        if (position * 2 + 1 < sanPhamList.size()) {
            sanpham sanPham2 = sanPhamList.get(position * 2 + 1);
            viewHolder.productLayout2.setVisibility(android.view.View.VISIBLE);
            viewHolder.tenTextView2.setText(sanPham2.getTensanpham());
            viewHolder.giaTextView2.setText(String.format("%d VND", sanPham2.getGiaban()));
            Picasso.get()
                    .load(sanPham2.getHinhanh())
                    .fit()
                    .into(viewHolder.imageView2);
            viewHolder.buttonDelete2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onEditClick(position * 2);
                    }
                }
            });
            viewHolder.buttonEdit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onDeleteClick(position * 2);
                    }
                }
            });
        } else {
            // Ẩn layout của sản phẩm thứ hai nếu không có
            viewHolder.productLayout2.setVisibility(android.view.View.INVISIBLE);
        }

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
        TextView tenTextView1;
        TextView giaTextView1;
        ImageView imageView1;
        Button buttonEdit1;
        Button buttonDelete1;
        LinearLayout productLayout1;


        TextView tenTextView2;
        TextView giaTextView2;
        ImageView imageView2;
        Button buttonEdit2;
        Button buttonDelete2;
        LinearLayout productLayout2;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(quanlysanphamAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}