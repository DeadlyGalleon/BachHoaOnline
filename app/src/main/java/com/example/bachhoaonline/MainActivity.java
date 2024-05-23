package com.example.bachhoaonline;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
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

        listViewtrangchu = findViewById(R.id.listviewtrangchu);
// Write a message to the database

Control();

        Toolbar toolbar = findViewById(R.id.toolbartrangchu);
        setSupportActionBar(toolbar);


        if(!isConnected(this)){
            Toast.makeText(this, "Kết Nối Internet thất bại", Toast.LENGTH_SHORT).show();
        }

        ViewFlipper viewFlipper = findViewById(R.id.quangcaotrangchu);

        String[] imageUrls = {
                "https://suckhoedoisong.qltns.mediacdn.vn/324455921873985536/2022/10/21/3b360279-8b43-40f3-9b11-604749128187-thumb-1666321262795-16663212629521126675042.jpg",
                "https://cdn.tgdd.vn/2020/12/CookProduct/thumbcn-1200x676-9.jpg",
                "https://nhomkimanh.com/upload/images/x%C6%B0%E1%BB%9Fng%20s%E1%BB%89%20xoong%20n%E1%BB%93i%20ch%E1%BA%A3o%201.jpg"
        };

        for (String url : imageUrls) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Sử dụng Glide để tải hình ảnh từ URL và hiển thị trong ImageView
            Glide.with(this)
                    .load(url)
                    .into(imageView);

            viewFlipper.addView(imageView);
        }

        // Tùy chỉnh hiệu ứng chuyển đổi nếu muốn
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.setFlipInterval(3000); // 3 giây
        viewFlipper.setAutoStart(true);



        // Lắng nghe sự kiện khi người dùng nhấn vào nút đăng nhập trên Toolbar


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