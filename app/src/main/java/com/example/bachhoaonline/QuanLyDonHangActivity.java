package com.example.bachhoaonline;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bachhoaonline.adapter.quanlydonhangAdapter;
import com.example.bachhoaonline.model.donhang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLyDonHangActivity extends AppCompatActivity {

    private RecyclerView rvOrderList;
    private quanlydonhangAdapter orderAdapter;
    private List<donhang> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlydonhang);

        rvOrderList = findViewById(R.id.rv_order_list);
        rvOrderList.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new quanlydonhangAdapter(orderList);
        rvOrderList.setAdapter(orderAdapter);

        loadOrderData1();
    }

    private void loadOrderData() {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("donhang");
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot donHangSnapshot : snapshot.getChildren()) {
                    donhang donHang = donHangSnapshot.getValue(donhang.class);
                    if (donHang != null) {
                        orderList.add(donHang);
                    }
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuanLyDonHangActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadOrderData1() {
        DatabaseReference donHangRef = FirebaseDatabase.getInstance().getReference().child("donhang");
        donHangRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    List<donhang> reverseOrderList = new ArrayList<>();
                    List<DataSnapshot> dataSnapshotList = new ArrayList<>();

                    // Convert the Iterable to a list for random access
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        dataSnapshotList.add(snapshot);
                    }

                    for (int i = dataSnapshotList.size() - 1; i >= 0; i--) {
                        DataSnapshot snapshot = dataSnapshotList.get(i);
                        donhang donHang = snapshot.getValue(donhang.class);
                        if (donHang != null) {
                            reverseOrderList.add(donHang);
                        }
                    }

                    // Clear the existing list and add elements from reverseOrderList
                    orderList.clear();
                    orderList.addAll(reverseOrderList);
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
