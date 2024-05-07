package com.example.bachhoaonline;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {
    private Spinner spinnerLoai;
    private Spinner spinnerLoaiCon;
    private ArrayList<String> loaiList;
    private ArrayList<String> loaiConList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);



        spinnerLoai = findViewById(R.id.spinnerloai);
        spinnerLoaiCon = findViewById(R.id.spinnerloaicon);

        // Khởi tạo danh sách loai và loaiCon
        loaiList = new ArrayList<>();
        loaiConList = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("loai").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String loai = dataSnapshot.getValue(String.class);
                    loaiList.add(loai);
                }
                // Gắn dữ liệu loai vào Spinner
                ArrayAdapter<String> loaiAdapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item, loaiList);
                spinnerLoai.setAdapter(loaiAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });




    }
}