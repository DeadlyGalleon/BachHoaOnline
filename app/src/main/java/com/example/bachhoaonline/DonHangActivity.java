package com.example.bachhoaonline;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bachhoaonline.adapter.donhangAdapter;
import com.example.bachhoaonline.model.donhang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonHangActivity extends AppCompatActivity {

    private RecyclerView rvOrderList;
    private donhangAdapter orderAdapter;
    private List<donhang> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donhang);

        rvOrderList = findViewById(R.id.rv_order_list);
        rvOrderList.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new donhangAdapter(orderList);
        rvOrderList.setAdapter(orderAdapter);

        // Get the user's account ID
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");

        loadOrderData(idtaikhoan);
    }

    private void loadOrderData(String idTaiKhoan) {

        DatabaseReference donHangRef = FirebaseDatabase.getInstance().getReference().child("donhang");
        donHangRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        donhang donHang = snapshot.getValue(donhang.class);
                        if (donHang != null && idTaiKhoan.equals(donHang.getIdTaiKhoan())) {
                            orderList.add(donHang);
                        }
                    }


                    orderAdapter.notifyDataSetChanged();
                } else {
                    // Handle case where dataSnapshot doesn't have any children
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
            }
        });

    }
}
