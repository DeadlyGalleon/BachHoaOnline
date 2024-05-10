package com.example.bachhoaonline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bachhoaonline.model.sanpham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Chitietsp extends AppCompatActivity {
    private DatabaseReference databaseRef;
    private ValueEventListener valueEventListener; // Di chuyển khai báo ra khỏi phương thức onCreate

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtinsanpham);
        Button themVaoGioHangButton = findViewById(R.id.add_to_cart_button);
        themVaoGioHangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ID sản phẩm từ Intent
                String productId = getIntent().getStringExtra("idString");

                // Tạo Intent để chuyển đến Activity ThemVaoGioHang
                Intent intent = new Intent(Chitietsp.this, GioHangActivity.class);
                // Chuyển dữ liệu idString qua Activity ThemVaoGioHang
                intent.putExtra("idString", productId);
                // Khởi động Activity ThemVaoGioHang
                startActivity(intent);
            }
        });

        // Lấy ID sản phẩm từ Intent
        String productId = getIntent().getStringExtra("idString");

        // Kiểm tra xem ID có tồn tại hay không
        if (productId != null) {
            // In ra Log để kiểm tra xem ID đã được lấy thành công hay không
            Log.d("Product ID", productId);

            // Thực hiện truy vấn để lấy thông tin chi tiết của sản phẩm từ cơ sở dữ liệu
            databaseRef = FirebaseDatabase.getInstance().getReference("sanpham").child(productId);
            valueEventListener = new ValueEventListener() { // Di chuyển khai báo vào đây
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Xử lý dữ liệu
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi truy vấn bị hủy bỏ
                }
            };

            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Lấy thông tin sản phẩm từ DataSnapshot
                        String tenSanPham = dataSnapshot.child("tensanpham").getValue(String.class);
                        Long giaBan = dataSnapshot.child("giaban").getValue(Long.class);
                        String loaiSanPham = dataSnapshot.child("loai").getValue(String.class);
                        String hinhAnh = dataSnapshot.child("hinhanh").getValue(String.class);

                        // Hiển thị thông tin sản phẩm lên giao diện
                        StringBuilder stringBuilder = new StringBuilder();

// Hiển thị thông tin sản phẩm lên giao diện
                        TextView tenSanPhamTextView = findViewById(R.id.product_name);
                        stringBuilder.setLength(0);
                        stringBuilder.append("Tên sản phẩm: ").append(tenSanPham);
                        tenSanPhamTextView.setText(stringBuilder.toString());

                        TextView giaBanTextView = findViewById(R.id.product_price_value);
                        stringBuilder.setLength(0);
                        stringBuilder.append("Giá bán: ").append(giaBan).append(" VND");
                        giaBanTextView.setText(stringBuilder.toString());

                        TextView loaiSanPhamTextView = findViewById(R.id.product_type);
                        stringBuilder.setLength(0);
                        stringBuilder.append("Loại sản phẩm: ").append(loaiSanPham);
                        loaiSanPhamTextView.setText(stringBuilder.toString());

                        ImageView productImageView = findViewById(R.id.product_image);
                        Picasso.get().load(hinhAnh).fit().into(productImageView);

                    } else {
                        // Xử lý khi không tìm thấy sản phẩm với ID tương ứng
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi truy vấn bị hủy bỏ
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên
        if (databaseRef != null) {
            databaseRef.removeEventListener(valueEventListener);
        }
    }
}
