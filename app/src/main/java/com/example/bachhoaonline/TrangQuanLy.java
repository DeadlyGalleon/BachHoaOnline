package com.example.bachhoaonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TrangQuanLy extends AppCompatActivity {
    Button trangsanpham,trangdanhmuc,trangdonhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.trangquanly);
        AnhXa();
        Control();

    }

    public void AnhXa(){
        trangdanhmuc=findViewById(R.id.button_quan_ly_danh_muc);
        trangsanpham=findViewById(R.id.button_quan_ly_san_pham);
        trangdonhang=findViewById(R.id.button_quan_ly_don_hang);


    }
    public void Control(){
       trangsanpham.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),QuanLySanPhamActivity.class);
               startActivity(intent);

           }
       });
        trangdanhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),QuanLyDanhMucActivity.class);
                startActivity(intent);

            }
        });
        trangdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),QuanLyDonHangActivity.class);
                startActivity(intent);

            }
        });


    }

}