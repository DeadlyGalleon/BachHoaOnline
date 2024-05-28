package com.example.bachhoaonline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.bachhoaonline.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bachhoaonline.model.donhang;

import java.util.List;

public class donhangAdapter extends RecyclerView.Adapter<donhangAdapter.OrderViewHolder> {

    private List<donhang> orderList;

    public donhangAdapter(List<donhang> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donhang_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        donhang order = orderList.get(position);
        StringBuilder stringBuilder = new StringBuilder();

// ID Đơn hàng
        stringBuilder.append("ID Đơn hàng: ").append(order.getIdDonHang());
        holder.orderId.setText(stringBuilder.toString());
        stringBuilder.setLength(0);

// Ngày đặt
        stringBuilder.append("Ngày đặt: ").append(order.getNgaydat());
        holder.orderDate.setText(stringBuilder.toString());
        stringBuilder.setLength(0);

// Ngày giao
        stringBuilder.append("Ngày giao: ").append(order.getNgaygiao());
        holder.deliveryDate.setText(stringBuilder.toString());
        stringBuilder.setLength(0);

// Tình trạng
        stringBuilder.append("Tình trạng: ").append(order.getTrangThai());
        holder.orderStatus.setText(stringBuilder.toString());
        stringBuilder.setLength(0);

// Tổng tiền
        stringBuilder.append("Tổng tiền: ").append(String.format("%,.0f VNĐ", order.getTongTien()));
        holder.orderTotal.setText(stringBuilder.toString());
        stringBuilder.setLength(0);


        holder.productList.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.productList.setAdapter(new sanphamdonhangadapter(order.getListSanPhamDonHang()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, orderDate, deliveryDate, orderStatus, orderTotal;
        RecyclerView productList;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.tv_order_id);
            orderDate = itemView.findViewById(R.id.tv_order_date);
            deliveryDate = itemView.findViewById(R.id.tv_delivery_date);
            orderStatus = itemView.findViewById(R.id.tv_order_status);
            orderTotal = itemView.findViewById(R.id.tv_order_total);
            productList = itemView.findViewById(R.id.rv_product_list);
        }
    }
}
