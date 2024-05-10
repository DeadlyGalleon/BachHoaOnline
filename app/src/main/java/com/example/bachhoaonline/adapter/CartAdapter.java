package com.example.bachhoaonline.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.giohang;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<giohang> giohangList;

    public CartAdapter(Context context, List<giohang> giohangList) {
        this.context = context;
        this.giohangList = giohangList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        giohang giohang = giohangList.get(position);

        holder.textViewProductName.setText(giohang.getTenSanPham());
        holder.textViewProductPrice.setText(String.valueOf(giohang.getGiaBan()));
        // Set other views accordingly
    }

    @Override
    public int getItemCount() {
        return giohangList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProduct;
        TextView textViewProductName;
        TextView textViewProductPrice;
        Button buttonDecreaseQuantity;
        TextView textViewQuantity;
        Button buttonIncreaseQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            buttonDecreaseQuantity = itemView.findViewById(R.id.buttonDecreaseQuantity);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            buttonIncreaseQuantity = itemView.findViewById(R.id.buttonIncreaseQuantity);
        }
    }
}
