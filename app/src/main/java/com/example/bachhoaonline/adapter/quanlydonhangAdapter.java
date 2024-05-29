package com.example.bachhoaonline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bachhoaonline.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bachhoaonline.model.donhang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class quanlydonhangAdapter extends RecyclerView.Adapter<quanlydonhangAdapter.OrderViewHolder> {

    private List<donhang> orderList;

    public quanlydonhangAdapter(List<donhang> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quanlydonhang_item, parent, false);
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
        if (order.getTrangThai() == 4) { // Nếu trạng thái là "Đã giao"
            stringBuilder.append("Ngày giao: ").append(order.getNgaygiao());
        } else {
            stringBuilder.append("Ngày giao: Chưa giao"); // Nếu trạng thái không phải "Đã giao"
        }
        holder.deliveryDate.setText(stringBuilder.toString());
        stringBuilder.setLength(0);

        // Tình trạng
        TextView orderStatusTextView = holder.orderStatus;
        Spinner spinner = holder.itemView.findViewById(R.id.spinner_order_status);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.order_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(order.getTrangThai()); // Đặt trạng thái hiện tại cho Spinner

        // Tổng tiền
        stringBuilder.append("Tổng tiền: ").append(String.format("%,.0f VNĐ", order.getTongTien()));
        holder.orderTotal.setText(stringBuilder.toString());
        stringBuilder.setLength(0);

        // Xử lý sự kiện khi người dùng nhấn nút "Xác nhận"
        Button btnConfirm = holder.itemView.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("donhang").child(order.getIdDonHang());
                int newStatus = spinner.getSelectedItemPosition(); // Lấy trạng thái mới từ Spinner
                orderRef.child("trangThai").setValue(newStatus); // Cập nhật trạng thái mới
                if (newStatus == 4) { // Nếu trạng thái là "Đã giao"
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentDateAndTime = sdf.format(new Date());
                    orderRef.child("ngaygiao").setValue(currentDateAndTime);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Ngày giao: ").append(currentDateAndTime);
                    holder.deliveryDate.setText(stringBuilder.toString());
// Cập nhật TextView ngày giao hàng
                }
                String statusText;
                switch (newStatus) {
                    case 0:
                        statusText = "Chưa Xác Nhận";
                        break;
                    case 1:
                        statusText = "Đã Hủy";
                        break;
                    case 2:
                        statusText = "Đã Xác Nhận";
                        break;
                    case 3:
                        statusText = "Đang Giao";
                        break;
                    case 4:
                        statusText = "Đã Giao";
                        break;
                    default:
                        statusText = "";
                        break;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tình trạng: ").append(statusText);
                orderStatusTextView.setText(stringBuilder.toString());
                // Cập nhật TextView tình trạng đơn hàng
                // Cập nhật thành công, thông báo cho người dùng
                Toast.makeText(holder.itemView.getContext(), "Đã cập nhật trạng thái đơn hàng", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged(); // Cập nhật lại giao diện
            }
        });

        // Hiển thị danh sách sản phẩm đơn hàng
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
