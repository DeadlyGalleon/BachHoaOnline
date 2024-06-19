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

public class sanphamadapter extends ArrayAdapter<sanpham> {

    private Context context;
    private OnItemClickListener mListener;
    private List<sanpham> sanPhamList;
    private List<sanpham> originalSanPhamList;

    public sanphamadapter(Context context, List<sanpham> sanPhamList) {
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
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_sanpham, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tenTextView1 = view.findViewById(R.id.tenTextView1);
            viewHolder.giaTextView1 = view.findViewById(R.id.giaTextView1);
            viewHolder.imageView1 = view.findViewById(R.id.imageView1);
            viewHolder.button1 = view.findViewById(R.id.button1);
            viewHolder.productLayout1 = view.findViewById(R.id.productLayout1);

            viewHolder.tenTextView2 = view.findViewById(R.id.tenTextView2);
            viewHolder.giaTextView2 = view.findViewById(R.id.giaTextView2);
            viewHolder.imageView2 = view.findViewById(R.id.imageView2);
            viewHolder.button2 = view.findViewById(R.id.button2);
            viewHolder.productLayout2 = view.findViewById(R.id.productLayout2);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Lấy sản phẩm thứ nhất
        sanpham sanPham1 = sanPhamList.get(position * 2);
        viewHolder.tenTextView1.setText(sanPham1.getTensanpham());
        viewHolder.giaTextView1.setText(String.format("%d VND", sanPham1.getGiaban()));
        Picasso.get()
                .load(sanPham1.getHinhanh())
                .fit()
                .into(viewHolder.imageView1);
        viewHolder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position * 2);
                }
            }
        });

        // Kiểm tra xem có sản phẩm thứ hai không
        if (position * 2 + 1 < sanPhamList.size()) {
            sanpham sanPham2 = sanPhamList.get(position * 2 + 1);
            viewHolder.productLayout2.setVisibility(View.VISIBLE);
            viewHolder.tenTextView2.setText(sanPham2.getTensanpham());
            viewHolder.giaTextView2.setText(String.format("%d VND", sanPham2.getGiaban()));
            Picasso.get()
                    .load(sanPham2.getHinhanh())
                    .fit()
                    .into(viewHolder.imageView2);
            viewHolder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(position * 2 + 1);
                    }
                }
            });
        } else {
            // Ẩn layout của sản phẩm thứ hai nếu không có
            viewHolder.productLayout2.setVisibility(View.INVISIBLE);
        }

        return view;
    }
    public void filter(String query,String loai, String loaicon) {
        query = query.toLowerCase();
        sanPhamList.clear();

        if (query.isEmpty()) {
            for (sanpham product : originalSanPhamList) {
                if ( product.getLoai()==Integer.parseInt(loai) && product.getLoaicon()==Integer.parseInt(loaicon) && Integer.parseInt(loaicon)!=0  ) {
                    sanPhamList.add(product);
                }
                else
                if ( product.getLoai()==Integer.parseInt(loai)  ) {
                    sanPhamList.add(product);
                }
            }
        } else {
            for (sanpham product : originalSanPhamList) {
                if (product.getTensanpham().toLowerCase().contains(query) && product.getLoai()==Integer.parseInt(loai) && product.getLoaicon()==Integer.parseInt(loaicon) && Integer.parseInt(loaicon)!=0  ) {
                    sanPhamList.add(product);
                }
                else
                if (product.getTensanpham().toLowerCase().contains(query) && product.getLoai()==Integer.parseInt(loai)  ) {
                    sanPhamList.add(product);
                }
            }
        }

        notifyDataSetChanged();
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
        Button button1;
        LinearLayout productLayout1;

        TextView tenTextView2;
        TextView giaTextView2;
        ImageView imageView2;
        Button button2;
        LinearLayout productLayout2;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
