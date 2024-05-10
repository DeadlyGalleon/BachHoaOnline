package com.example.bachhoaonline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GioHangActivity extends AppCompatActivity {
    private DatabaseReference databaseRef;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbottomtrangchu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    // Chuyển đến MainActivity
                    Intent intentHome = new Intent(GioHangActivity.this, MainActivity.class);
                    startActivity(intentHome);
                    return true;
                } else if (item.getItemId() == R.id.navdanhsachsanpham) {
                    // Chuyển đến DanhSachSanPhamActivity (hoặc Fragment)
                    Intent intentDanhSachSanPham = new Intent(GioHangActivity.this, htspact.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                    // Không cần thực hiện gì vì bạn đã ở trong màn hình Giỏ hàng rồi
                    Toast.makeText(GioHangActivity.this, "Bạn đang ở trong Giỏ hàng", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.navcanhan) {
                    // Chuyển đến CaNhanActivity (hoặc Fragment)
                    Intent intentCaNhan = new Intent(GioHangActivity.this, PersonalActivity.class);
                    startActivity(intentCaNhan);
                    return true;
                }
                return false;
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
            valueEventListener = new ValueEventListener() {
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

                        TextView tenSanPhamTextView = findViewById(R.id.textViewProductName);
                        stringBuilder.setLength(0);
                        stringBuilder.append("Tên sản phẩm: ").append(tenSanPham);
                        tenSanPhamTextView.setText(stringBuilder.toString());
                    } else {
                        // Xử lý khi không tìm thấy sản phẩm với ID tương ứng
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi truy vấn bị hủy bỏ
                }
            };

            databaseRef.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên
        if (databaseRef != null && valueEventListener != null) {
            databaseRef.removeEventListener(valueEventListener);
        }
    }
}
