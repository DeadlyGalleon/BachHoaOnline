package com.example.bachhoaonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bachhoaonline.model.sanpham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Chitietsp extends AppCompatActivity {
    String tenSanPham;
    Long giaBan;
    String loaiSanPham;
    String hinhAnh;
    private DatabaseReference databaseRef;
    private ValueEventListener valueEventListener; // Di chuyển khai báo ra khỏi phương thức onCreate

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietsp);
        Button themVaoGioHangButton = findViewById(R.id.themVaoGioHangButton);
        themVaoGioHangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ID sản phẩm từ Intent
                String productId = getIntent().getStringExtra("idString");
                SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", Context.MODE_PRIVATE);
                String idTaiKhoan = sharedPreferences.getString("idtaikhoan", null);
                if (idTaiKhoan != null) {
                    DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference("giohang");

                    gioHangRef.child(idTaiKhoan).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                // If "idtaikhoan" doesn't exist under "giohang", add it
                                gioHangRef.child(idTaiKhoan).setValue(true);

                            }

                            // Navigate to the "idsanpham" under "idtaikhoan"
                            DatabaseReference sanPhamRef = gioHangRef.child(idTaiKhoan).child(productId);
                            sanPhamRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        // If "idsanpham" exists under "idtaikhoan", update its quantity
                                        Integer quantity = dataSnapshot.child("soluong").getValue(Integer.class);
                                        sanPhamRef.child("soluong").setValue(quantity + 1);
                                    } else {
                                        // If "idsanpham" doesn't exist under "idtaikhoan", add it with details and quantity 1

                                        sanPhamRef.child("tensanpham").setValue(tenSanPham); // Replace tenSanPham with actual value
                                        sanPhamRef.child("hinhanh").setValue(hinhAnh); // Replace hinhAnh with actual value
                                        sanPhamRef.child("giaban").setValue(giaBan); // Replace giaBan with actual value
                                        sanPhamRef.child("soluong").setValue(1); // Initial quantity is 1
                                    }
                                    Toast.makeText(Chitietsp.this, "Thêm Sản Phẩm Vào Giỏ Hàng Thành Công!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle error
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                } else {
                    Toast.makeText(Chitietsp.this, "Vui Lòng Đăng Nhập!", Toast.LENGTH_SHORT).show();
                }

            }


        });
        String productId="chua co id";

        // Lấy ID sản phẩm từ Intent
         productId = getIntent().getStringExtra("idString");

        Toast.makeText(this, productId, Toast.LENGTH_SHORT).show();

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
                         tenSanPham = dataSnapshot.child("tensanpham").getValue(String.class);
                         giaBan = dataSnapshot.child("giaban").getValue(Long.class);
                         loaiSanPham = dataSnapshot.child("loai").getValue(String.class);
                         hinhAnh = dataSnapshot.child("hinhanh").getValue(String.class);

                        // Hiển thị thông tin sản phẩm lên giao diện
                        StringBuilder stringBuilder = new StringBuilder();

                        // Hiển thị thông tin sản phẩm lên giao diện
                        TextView tenSanPhamTextView = findViewById(R.id.tenSanPhamTextView);
                        stringBuilder.setLength(0);
                        stringBuilder.append("Tên sản phẩm: ").append(tenSanPham);
                        tenSanPhamTextView.setText(stringBuilder.toString());

                        TextView giaBanTextView = findViewById(R.id.giaBanTextView);
                        stringBuilder.setLength(0);
                        stringBuilder.append("Giá bán: ").append(giaBan).append(" VND");
                        giaBanTextView.setText(stringBuilder.toString());

                        TextView loaiSanPhamTextView = findViewById(R.id.loaiSanPhamTextView);
                        stringBuilder.setLength(0);
                        stringBuilder.append("Loại sản phẩm: ").append(loaiSanPham);
                        loaiSanPhamTextView.setText(stringBuilder.toString());

                        ImageView productImageView = findViewById(R.id.imageView);
                        Glide.with(Chitietsp.this)
                                .load(hinhAnh)
                                .into(productImageView);

                        // Load image into ImageView using Glide

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
