package com.example.bachhoaonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bachhoaonline.model.donhang;
import com.example.bachhoaonline.model.giohang;
import com.example.bachhoaonline.model.sanphamdonhang;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GioHangActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private ValueEventListener valueEventListener;
    private ListView listView;
    List<giohang> gioHangList = new ArrayList<>();
    DatabaseReference databaseReference;

    private LinearLayout productListLayout;
    private Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        productListLayout = findViewById(R.id.product_list);
        orderButton = findViewById(R.id.order_button);
        Control();
        loadCartItems();
    }

    private void Control() {
        orderButton.setOnClickListener(new View.OnClickListener() {
            SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
            String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");
            @Override
            public void onClick(View v) {
                if (!isUserLoggedIn()) {
                    Toast.makeText(GioHangActivity.this, "Please log in!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(GioHangActivity.this, DatHangActivity.class);
                intent.putExtra("gioHangList", new ArrayList<>(gioHangList));
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbottomtrangchu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    Toast.makeText(getApplicationContext(), "Bạn đang ở trong Trang Chủ", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.navdanhsachsanpham) {
                    Intent intentDanhSachSanPham = new Intent(getApplicationContext(), htspact.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                    Intent intentGiohang = new Intent(getApplicationContext(), GioHangActivity.class);
                    startActivity(intentGiohang);
                    return true;
                } else if (item.getItemId() == R.id.navcanhan) {
                    Intent intentCaNhan = new Intent(getApplicationContext(), PersonalActivity.class);
                    startActivity(intentCaNhan);
                    return true;
                }
                return false;
            }
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        return sharedPreferences.contains("idtaikhoan");
    }

    private void loadCartItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");

        if (!idtaikhoan.isEmpty()) {
            DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference("giohang").child(idtaikhoan);
            gioHangRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot idsanpham : dataSnapshot.getChildren()) {
                        String idSanPham = idsanpham.getKey();
                        if (idSanPham == null) {
                            Log.e("GioHangActivity", "Product idSanPham is null in Firebase snapshot");
                            continue;
                        }

                        DatabaseReference sanphamRef = FirebaseDatabase.getInstance().getReference("giohang").child(idtaikhoan).child(idSanPham);
                        sanphamRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                giohang giohang = new giohang();
                                giohang.setIdSanPham(idSanPham); // Ensure idSanPham is set

                                for (DataSnapshot sanPham : snapshot.getChildren()) {
                                    String key = sanPham.getKey();
                                    Object value = sanPham.getValue();

                                    if (key.equals("tensanpham")) {
                                        String tensanpham = (String) value;
                                        giohang.setTenSanPham(tensanpham);
                                    }

                                    if (key.equals("hinhanh")) {
                                        String hinhanh = (String) value;
                                        giohang.setHinhAnh(hinhanh);
                                    }

                                    if (key.equals("soluong")) {
                                        Long soluong = sanPham.getValue(Long.class);
                                        if (soluong != null) {
                                            giohang.setSoLuong(soluong.intValue());
                                        }
                                    }

                                    if (key.equals("giaban")) {
                                        Long giaBanLong = sanPham.getValue(Long.class);
                                        if (giaBanLong != null) {
                                            giohang.setGiaBan(giaBanLong.intValue());
                                        }
                                    }
                                }

                                addProductToCart(giohang);
                                gioHangList.add(giohang);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("abcabc2", error.getMessage());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("XemLoi", databaseError.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Bạn Chưa Đăng Nhập", Toast.LENGTH_SHORT).show();
        }
        Log.d("abcabcabcabc", gioHangList.toString());
    }


    private void addProductToCart(giohang gioHang) {
        // Ensure idSanPham is not null
        if (gioHang.getIdSanPham() == null) {
            Log.e("GioHangActivity", gioHang.getTenSanPham());
            return;
        }

        View productLayout = LayoutInflater.from(this).inflate(R.layout.cart_item, productListLayout, false);

        ImageView productImage = productLayout.findViewById(R.id.product_image);
        TextView productName = productLayout.findViewById(R.id.product_name);
        TextView productPrice = productLayout.findViewById(R.id.product_price);
        TextView productQuantity = productLayout.findViewById(R.id.product_quantity);
        Button buttonDecrease = productLayout.findViewById(R.id.button_decrease);
        Button buttonIncrease = productLayout.findViewById(R.id.button_increase);
        Button buttonRemove = productLayout.findViewById(R.id.button_remove);

        productName.setText(gioHang.getTenSanPham());
        int tongGiaBan = gioHang.getSoLuong() * gioHang.getGiaBan();
        productPrice.setText(String.format("%,d VNĐ", tongGiaBan));
        productQuantity.setText(String.valueOf(gioHang.getSoLuong()));
        Glide.with(this).load(gioHang.getHinhAnh()).into(productImage);

        buttonDecrease.setOnClickListener(v -> {
            int quantity = gioHang.getSoLuong();
            if (quantity > 1) {
                gioHang.setSoLuong(--quantity);
                productQuantity.setText(String.valueOf(quantity));
                productPrice.setText(String.format("%,d VNĐ", gioHang.getSoLuong() * gioHang.getGiaBan()));
                updateQuantityInFirebase(gioHang);
            }
        });

        buttonIncrease.setOnClickListener(v -> {
            int quantity = gioHang.getSoLuong();
            gioHang.setSoLuong(++quantity);
            productQuantity.setText(String.valueOf(quantity));
            productPrice.setText(String.format("%,d VNĐ", gioHang.getSoLuong() * gioHang.getGiaBan()));
            updateQuantityInFirebase(gioHang);
        });

        buttonRemove.setOnClickListener(v -> {
            removeProductFromCart(gioHang);
            productListLayout.removeView(productLayout);
        });

        productListLayout.addView(productLayout);
    }


    private void updateQuantityInFirebase(giohang gioHang) {
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");
        if (!idtaikhoan.isEmpty()) {
            DatabaseReference sanphamRef = FirebaseDatabase.getInstance().getReference("giohang").child(idtaikhoan).child(gioHang.getIdSanPham());
            sanphamRef.child("soluong").setValue(gioHang.getSoLuong());
        }
    }

    private void removeProductFromCart(giohang gioHang) {
        String idSanPham = gioHang.getIdSanPham();
        if (idSanPham == null) {
            Log.e("GioHangActivity", "Cannot remove product with null idSanPham");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");
        if (!idtaikhoan.isEmpty()) {
            DatabaseReference sanphamRef = FirebaseDatabase.getInstance().getReference("giohang").child(idtaikhoan).child(idSanPham);
            sanphamRef.removeValue();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseRef != null && valueEventListener != null) {
            databaseRef.removeEventListener(valueEventListener);
        }
    }
}
