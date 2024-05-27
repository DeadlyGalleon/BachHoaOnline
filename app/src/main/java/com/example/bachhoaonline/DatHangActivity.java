package com.example.bachhoaonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bachhoaonline.model.donhang;
import com.example.bachhoaonline.model.giohang;
import com.example.bachhoaonline.model.sanphamdonhang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatHangActivity extends AppCompatActivity {

    private EditText addressInput;
    private Button placeOrderButton;
    private ArrayList<giohang> gioHangList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        addressInput = findViewById(R.id.address_input);
        placeOrderButton = findViewById(R.id.place_order_button);

        gioHangList = (ArrayList<giohang>) getIntent().getSerializableExtra("gioHangList");

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressInput.getText().toString().trim();
                if (!address.isEmpty()) {
                    placeOrder(address);
                } else {
                    Toast.makeText(DatHangActivity.this, "Please enter an address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void placeOrder(String address) {
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");

        if (!idtaikhoan.isEmpty()) {
            DatabaseReference donHangRef = FirebaseDatabase.getInstance().getReference("donhang").child(idtaikhoan);

            donHangRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long maxId = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        long id = Long.parseLong(snapshot.getKey());
                        if (id > maxId) {
                            maxId = id;
                        }
                    }

                    long newId = maxId + 1;
                    String idDonHang = String.valueOf(newId);

                    donhang donHang = new donhang();
                    donHang.setIdDonHang(idDonHang);
                    donHang.setIdTaiKhoan(idtaikhoan);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentDateAndTime = sdf.format(new Date());
                    donHang.setNgaydat(currentDateAndTime);
                    donHang.setNgaygiao("");
                    donHang.setDiaChi(address);
                    donHang.setListSanPhamDonHang(new ArrayList<>());

                    int tongTien = 0;
                    for (giohang gioHang : gioHangList) {
                        int soLuong = gioHang.getSoLuong();
                        int giaBan = gioHang.getGiaBan();
                        tongTien += soLuong * giaBan;

                        sanphamdonhang sanPham = new sanphamdonhang();
                        sanPham.setIdSanPham(gioHang.getIdSanPham());
                        sanPham.setTensanpham(gioHang.getTenSanPham());
                        sanPham.setHinhanh(gioHang.getHinhAnh());
                        sanPham.setGiaCu(gioHang.getGiaBan());
                        sanPham.setSoLuong(gioHang.getSoLuong());
                        donHang.getListSanPhamDonHang().add(sanPham);
                    }
                    donHang.setTongTien(tongTien);

                    donHangRef.child(idDonHang).setValue(donHang);
                    clearCart(idtaikhoan);

                    Toast.makeText(DatHangActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DatHangActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle cancellation
                }
            });
        } else {
            Toast.makeText(DatHangActivity.this, "Please log in!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearCart(String idtaikhoan) {
        DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference("giohang").child(idtaikhoan);
        gioHangRef.removeValue();
    }
}
