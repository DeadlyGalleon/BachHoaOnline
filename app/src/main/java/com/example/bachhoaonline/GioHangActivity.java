package com.example.bachhoaonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GioHangActivity extends AppCompatActivity {

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
                    Intent intentDanhSachSanPham = new Intent(GioHangActivity.this, DanhSachSanPhamActivity.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                    // Không cần thực hiện gì vì bạn đã ở trong màn hình Giỏ hàng rồi
                    Toast.makeText(GioHangActivity.this, "Bạn đang ở trong Giỏ hàng", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.navcanhan) {
                    // Chuyển đến CaNhanActivity (hoặc Fragment)
                    Intent intentCaNhan = new Intent(GioHangActivity.this, CaNhanActivity.class);
                    startActivity(intentCaNhan);
                    return true;
                }
                return false;
            }

        });
    }
}
