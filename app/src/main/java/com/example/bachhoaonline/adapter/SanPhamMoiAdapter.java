package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bachhoaonline.Chitietsp;
import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.sanpham;

import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.SanPhamViewHolder> {
    private Context mContext;
    private List<sanpham> sanPhamList;

    public SanPhamMoiAdapter(Context context, List<sanpham> sanPhamList) {
        this.mContext = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sanphammoi, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        sanpham sanPham1 = sanPhamList.get(position * 2); // Sản phẩm 1
        sanpham sanPham2 = null;
        if ((position * 2) + 1 < sanPhamList.size()) {
            sanPham2 = sanPhamList.get((position * 2) + 1); // Sản phẩm 2
        }

        // Hiển thị sản phẩm 1
        holder.bindSanPham(sanPham1, holder.itemView.findViewById(R.id.imageView1),
                holder.itemView.findViewById(R.id.tenTextView1),
                holder.itemView.findViewById(R.id.giaTextView1));

        // Hiển thị sản phẩm 2 nếu có
        if (sanPham2 != null) {
            holder.bindSanPham(sanPham2, holder.itemView.findViewById(R.id.imageView2),
                    holder.itemView.findViewById(R.id.tenTextView2),
                    holder.itemView.findViewById(R.id.giaTextView2));
        } else {
            // Ẩn view sản phẩm 2 nếu không có
            holder.itemView.findViewById(R.id.layoutProduct2).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        // Số lượng items = số lượng sản phẩm / 2, làm tròn lên nếu số lẻ
        return (int) Math.ceil(sanPhamList.size() / 2.0);
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindSanPham(sanpham sanPham, ImageView imageView, TextView tenTextView, TextView giaTextView) {
            // Hiển thị thông tin sản phẩm
            tenTextView.setText(sanPham.getTensanpham());
            giaTextView.setText(String.format("%d VND", sanPham.getGiaban()));
            Glide.with(mContext).load(sanPham.getHinhanh()).into(imageView);

            // Thiết lập sự kiện khi người dùng nhấn vào sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Chitietsp.class);
                    intent.putExtra("idString", sanPham.getIdsanpham());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
