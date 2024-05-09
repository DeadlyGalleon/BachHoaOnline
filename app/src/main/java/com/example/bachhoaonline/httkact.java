package com.example.bachhoaonline;

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

import com.example.bachhoaonline.adapter.taikhoanadapter;

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

public class httkact extends AppCompatActivity {
    Toolbar toolbartrangchu;
    ViewFlipper ViewFlipperTrangchu;
    RecyclerView RecyclerViewTrangChu;
    ListView listViewtrangchu;
    BottomNavigationView bottomNavigationViewTrangChu;
    NavigationView NagiNavigationViewTrangChu;

    private DatabaseReference databaseRef;
    private ListView taikhoanListView;
    APIbachhoa apibachhoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_taikhoan); // Sửa thành tên đúng của tệp XML
        AnhXa();
        databaseRef = FirebaseDatabase.getInstance().getReference("taikhoan"); // Gán giá trị cho databaseRef
        loadTaiKhoanData();
    }
    private void AnhXa() {
//
        taikhoanListView = findViewById(R.id.listViewTaiKhoan); // Đã sửa lại tên đúng
    }
    private void loadTaiKhoanData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<taikhoan> taiKhoanList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idString = snapshot.getKey(); // Lấy giá trị id dưới dạng chuỗi từ key của snapshot
                    String tenTaiKhoan = snapshot.child("tentaikhoan").getValue(String.class);
                    String soDienThoai = snapshot.child("sodienthoai").getValue(String.class);
                    String matKhau = snapshot.child("matkhau").getValue(String.class);
                    int id = Integer.parseInt(idString); // Chuyển đổi id từ chuỗi sang số nguyên
                    taikhoan taiKhoan = new taikhoan(id, tenTaiKhoan, soDienThoai, matKhau);
                    taiKhoanList.add(taiKhoan);
                }
                // Truyền giá trị boolean vào Adapter khi tạo Adapter
                taikhoanadapter taiKhoanAdapter = new taikhoanadapter(httkact.this, taiKhoanList, true); // Đặt giá trị là true nếu button có trong listview.xml
                taikhoanListView.setAdapter(taiKhoanAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
            }
        });
    }


}