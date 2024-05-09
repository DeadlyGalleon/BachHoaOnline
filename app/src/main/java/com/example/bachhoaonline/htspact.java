package com.example.bachhoaonline;

import com.example.bachhoaonline.adapter.taikhoanadapter;
import com.example.bachhoaonline.model.sanpham;
import com.example.bachhoaonline.model.taikhoan;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bachhoaonline.adapter.sanphamadapter;

import com.example.bachhoaonline.retrofit.APIbachhoa;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
public class htspact extends AppCompatActivity  {
    private DatabaseReference databaseRef;
    private ListView sanphamListView;
    private List<sanpham> sanPhamList = new ArrayList<>();

    APIbachhoa apibachhoa;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_sanpham);
        AnhXa();
        databaseRef = FirebaseDatabase.getInstance().getReference("sanpham");
        if (databaseRef != null) {
            loadSanPhamData();
        } else {
            // Xử lý trường hợp databaseRef là null
            Toast.makeText(this, "Database reference is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void AnhXa() {
        sanphamListView = findViewById(R.id.listViewSanPham); // Đã sửa lại tên đúng
    }

    private void loadSanPhamData() {

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<taikhoan> taiKhoanList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idString = snapshot.getKey(); // Lấy giá trị id dưới dạng chuỗi từ key của snapshot
                    String tenSanPham = snapshot.child("tentaikhoan").getValue(String.class);
                    String giaBan = snapshot.child("sodienthoai").getValue(String.class);
                    String loaiSanPham = snapshot.child("matkhau").getValue(String.class);
                    int id = Integer.parseInt(idString); // Chuyển đổi id từ chuỗi sang số nguyên
                    taikhoan taiKhoan = new taikhoan(id, tenSanPham, giaBan, loaiSanPham);
                    taiKhoanList.add(taiKhoan);
                }

                // Truyền giá trị boolean vào Adapter khi tạo Adapter
                sanphamadapter sanphamAdapter = new sanphamadapter(htspact.this, sanPhamList);
                sanphamListView.setAdapter(sanphamAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
            }
        });
    }

    }
