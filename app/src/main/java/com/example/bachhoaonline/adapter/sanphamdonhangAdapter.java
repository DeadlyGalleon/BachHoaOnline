package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.sanphamdonhang;

import java.util.List;

public class sanphamdonhangAdapter extends RecyclerView.Adapter<sanphamdonhangAdapter.ProductViewHolder> {
    private Context context;
    private List<sanphamdonhang> productList;

    public sanphamdonhangAdapter(Context context, List<sanphamdonhang> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanpham_donhang_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        sanphamdonhang product = productList.get(position);
        holder.productName.setText(product.getTensanpham());
        holder.productPrice.setText(new StringBuilder().append(String.format("%,.0f VNĐ", product.getGiaCu())).toString());
        holder.productQuantity.setText(new StringBuilder().append("Số lượng: ").append(product.getSoLuong()).toString());
        Glide.with(context).load(product.getHinhanh()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
        }
    }
}

