package com.example.bachhoaonline;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bachhoaonline.model.donhang;
import com.example.bachhoaonline.model.sanphamdonhang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonHangActivity extends AppCompatActivity {

    private TextView orderId, orderDate, deliveryDate, orderStatus, orderTotal;
    private LinearLayout productListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donhang);

        orderId = findViewById(R.id.tv_order_id);
        orderDate = findViewById(R.id.tv_order_date);
        deliveryDate = findViewById(R.id.tv_delivery_date);
        orderStatus = findViewById(R.id.tv_order_status);
        orderTotal = findViewById(R.id.tv_order_total);
        productListLayout = findViewById(R.id.product_list);

        // Giả sử bạn đã có id đơn hàng
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");

        loadOrderData(idtaikhoan);
    }

    private void loadOrderData(String idTaiKhoan) {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("donhang").child(idTaiKhoan);
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot donHangSnapshot : snapshot.getChildren()) {
                    donhang donHang = donHangSnapshot.getValue(donhang.class);

                    if (donHang != null) {
                        orderId.setText(new StringBuilder().append("ID Đơn hàng: ").append(donHang.getIdDonHang()).toString());
                        orderDate.setText(new StringBuilder().append("Ngày đặt: ").append(donHang.getNgaydat()).toString());
                        deliveryDate.setText(new StringBuilder().append("Ngày giao: ").append(donHang.getNgaygiao()).toString());
                        orderStatus.setText(new StringBuilder().append("Tình trạng: ").append(donHang.getTrangThai()).toString());
                        orderTotal.setText(new StringBuilder().append("Tổng tiền: ").append(String.format("%,.0f VNĐ", donHang.getTongTien())).toString());

                        for (sanphamdonhang sp : donHang.getListSanPhamDonHang()) {
                            addProductToView(sp);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DonHangActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addProductToView(sanphamdonhang sp) {
        View productLayout = LayoutInflater.from(this).inflate(R.layout.sanpham_donhang_item, productListLayout, false);

        ImageView productImage = productLayout.findViewById(R.id.product_image);
        TextView productName = productLayout.findViewById(R.id.product_name);
        TextView productPrice = productLayout.findViewById(R.id.product_price);
        TextView productQuantity = productLayout.findViewById(R.id.product_quantity);

        productName.setText(sp.getTensanpham());
        productPrice.setText(new StringBuilder().append(String.format("%,.0f VNĐ", sp.getGiaCu())).toString());
        productQuantity.setText(new StringBuilder().append("Số lượng: ").append(sp.getSoLuong()).toString());




        Glide.with(this).load(sp.getHinhanh()).into(productImage);

        productListLayout.addView(productLayout);
    }
}
