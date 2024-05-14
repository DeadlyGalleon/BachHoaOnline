package com.example.bachhoaonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PersonalActivity extends AppCompatActivity {
    Button dangnhap;
    Button dangxuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal);
        anhxa();
        Control();

        // Check login state
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", Context.MODE_PRIVATE);
        String isLoggedIn = sharedPreferences.getString("idtaikhoan", "");
        if (isLoggedIn.length()>0) {
            // User is logged in, hide login button and show logout button
            dangnhap.setVisibility(View.GONE);
            dangxuat.setVisibility(View.VISIBLE);
        } else {
            // User is not logged in, hide logout button and show login button
            dangnhap.setVisibility(View.VISIBLE);
            dangxuat.setVisibility(View.GONE);
        }

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);

            }
        });

        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout button click
                // Clear "taikhoan" SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Hide logout button and show login button
                dangxuat.setVisibility(View.GONE);
                dangnhap.setVisibility(View.VISIBLE);
                Toast.makeText(PersonalActivity.this, "Đăng Xuất Thành Công!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void anhxa(){
        dangnhap=findViewById(R.id.loginButton);
        dangxuat=findViewById(R.id.logoutButton);
    }

    public void Control(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.navbottomcanhan);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    // Chuyển đến MainActivity
                    Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentHome);
                    return true;
                } else if (item.getItemId() == R.id.navdanhsachsanpham) {
                    // Chuyển đến DanhSachSanPhamActivity (hoặc Fragment)
                    Intent intentDanhSachSanPham = new Intent(getApplicationContext(), htspact.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                    Intent intentDanhSachSanPham = new Intent(getApplicationContext(), GioHangActivity.class);
                    startActivity(intentDanhSachSanPham);

                } else if (item.getItemId() == R.id.navcanhan) {

                }
                return false;
            }

        });
    }
}
