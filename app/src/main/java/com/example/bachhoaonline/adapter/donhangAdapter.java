package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bachhoaonline.R;

import com.example.bachhoaonline.model.donhang;

import java.util.List;

public class donhangAdapter extends RecyclerView.Adapter<donhangAdapter.OrderViewHolder> {
    private Context context;
    private List<donhang> orderList;

    public donhangAdapter(Context context, List<donhang> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donhang_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        donhang order = orderList.get(position);
        StringBuilder sb = new StringBuilder();
        sb.append("ID Đơn hàng: ").append(order.getIdDonHang());
        holder.orderId.setText(sb.toString());

        sb.setLength(0);  // Reset the StringBuilder
        sb.append("Ngày đặt: ").append(order.getNgaydat());
        holder.orderDate.setText(sb.toString());

        sb.setLength(0);  // Reset the StringBuilder
        sb.append("Ngày giao: ").append(order.getNgaygiao());
        holder.deliveryDate.setText(sb.toString());

        sb.setLength(0);  // Reset the StringBuilder
        sb.append("Tình trạng: ").append(order.getTrangThai());
        holder.orderStatus.setText(sb.toString());

        sb.setLength(0);  // Reset the StringBuilder
        sb.append("Tổng tiền: ").append(String.format("%,.VNĐ", order.getTongTien()));
        holder.orderTotal.setText(sb.toString());

        // Set up RecyclerView cho danh sách sản phẩm
        sanphamdonhangAdapter productAdapter = new sanphamdonhangAdapter(context, order.getListSanPhamDonHang());
        holder.recyclerViewProducts.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewProducts.setAdapter(productAdapter);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, orderDate, deliveryDate, orderStatus, orderTotal;
        RecyclerView recyclerViewProducts;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderDate = itemView.findViewById(R.id.order_date);
            deliveryDate = itemView.findViewById(R.id.delivery_date);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderTotal = itemView.findViewById(R.id.order_total);
            recyclerViewProducts = itemView.findViewById(R.id.recycler_view_products);
        }
    }
}
