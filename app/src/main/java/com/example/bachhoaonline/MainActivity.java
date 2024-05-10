package com.example.bachhoaonline;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbartrangchu;
    ViewFlipper ViewFlipperTrangchu;
    RecyclerView RecyclerViewTrangChu;
    ListView listViewtrangchu;
    BottomNavigationView bottomNavigationViewTrangChu;
    NavigationView NagiNavigationViewTrangChu;


//thiendeptrai
DatabaseReference rootdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Write a message to the database

Control();

        Toolbar toolbar = findViewById(R.id.toolbartrangchu);
        setSupportActionBar(toolbar);


        if(!isConnected(this)){
            Toast.makeText(this, "Kết Nối Internet thất bại", Toast.LENGTH_SHORT).show();
        }



        // Lắng nghe sự kiện khi người dùng nhấn vào nút đăng nhập trên Toolbar
        ImageButton loginButton = toolbar.findViewById(R.id.action_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang màn hình đăng nhập (formlogin.xml)
                Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });
        ImageButton themsanphambtn = toolbar.findViewById(R.id.addproduct);
        themsanphambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang màn hình đăng nhập (formlogin.xml)
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        // Tiếp tục với các xử lý khác của MainActivity
    }


    private void AnhXa(){
toolbartrangchu =findViewById(R.id.toolbartrangchu);
ViewFlipperTrangchu=findViewById(R.id.quangcaotrangchu);
        RecyclerViewTrangChu = findViewById(R.id.sanphammoitrangchu);
        listViewtrangchu   = findViewById(R.id.listviewtrangchu);
        bottomNavigationViewTrangChu = findViewById(R.id.navbottomtrangchu);
        NagiNavigationViewTrangChu=findViewById(R.id.navtrangchu);

    }


    public void Control(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.navbottomtrangchu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {

                    Toast.makeText(MainActivity.this, "Bạn đang ở trong Trang Chủ", Toast.LENGTH_SHORT).show();
                    return true;
                    // Chuyển đến MainActivity


                } else if (item.getItemId() == R.id.navdanhsachsanpham) {
                    // Chuyển đến DanhSachSanPhamActivity (hoặc Fragment)
                    Intent intentDanhSachSanPham = new Intent(MainActivity.this, htspact.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                    Intent intentGiohang = new Intent(MainActivity.this, GioHangActivity.class);
                    startActivity(intentGiohang);
                    return true;

                } else if (item.getItemId() == R.id.navcanhan) {
                    // Chuyển đến CaNhanActivity (hoặc Fragment)
                    Intent intentCaNhan = new Intent(MainActivity.this, PersonalActivity.class);
                    startActivity(intentCaNhan);
                    return true;
                }
                return false;
            }

        });
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isConnected = false;
        if (wifi != null && wifi.isConnectedOrConnecting()) {
            isConnected = true;
        } else if (mobile != null && mobile.isConnectedOrConnecting()) {
            isConnected = true;
        }

        return isConnected;
    }



}