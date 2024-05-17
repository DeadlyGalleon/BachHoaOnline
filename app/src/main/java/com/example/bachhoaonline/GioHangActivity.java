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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.foundation.text2.input.internal.ToCharArray_androidKt;

import com.bumptech.glide.Glide;
import com.example.bachhoaonline.adapter.CartAdapter;
import com.example.bachhoaonline.model.donhang;
import com.example.bachhoaonline.model.giohang;
import com.example.bachhoaonline.model.sanpham;
import com.example.bachhoaonline.model.sanphamdonhang;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;


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
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
                String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");

                if (!idtaikhoan.isEmpty()) {
                    DatabaseReference donHangRef = FirebaseDatabase.getInstance().getReference("donhang").child(idtaikhoan); // Tham chiếu đến nút "donhang" dựa trên idtaikhoan

                    donHangRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long maxId = 0;

                            // Tìm id lớn nhất trong danh sách iddonhang hiện có
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                long id = Long.parseLong(snapshot.getKey());
                                if (id > maxId) {
                                    maxId = id;
                                }
                            }

                            // Tạo một id mới bằng cách thêm 1 vào id lớn nhất
                            long newId = maxId + 1;
                            String idDonHang = String.valueOf(newId);

                            // Tạo một đơn hàng mới
                            donhang donHang = new donhang();
                            donHang.setIdDonHang(idDonHang);
                            donHang.setIdTaiKhoan(idtaikhoan);

                            // Thiết lập ngày đặt hàng là thời điểm hiện tại
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            String currentDateAndTime = sdf.format(new Date());
                            donHang.setNgaydat(currentDateAndTime);

                            // Thiết lập ngày giao hàng là rỗng
                            donHang.setNgaygiao("");

                            // Khởi tạo danh sách sản phẩm
                            donHang.setListSanPhamDonHang(new ArrayList<>());

                            int tongTien = 0;
                            // Tính tổng tiền dựa trên số lượng và giá bán của từng sản phẩm trong giỏ hàng
                            for (giohang gioHang : gioHangList) {
                                int soLuong = gioHang.getSoLuong();
                                int giaBan = gioHang.getGiaBan();
                                tongTien += soLuong * giaBan;

                                // Tạo một đối tượng SanPham và thêm vào danh sách sản phẩm của đơn hàng
                                sanphamdonhang sanPham = new sanphamdonhang();
                                sanPham.setIdSanPham(gioHang.getIdSanPham());
                                sanPham.setTensanpham(gioHang.getTenSanPham());
                                sanPham.setHinhanh(gioHang.getHinhAnh());
                                sanPham.setGiaCu(gioHang.getGiaBan());
                                sanPham.setSoLuong(gioHang.getSoLuong());
                                donHang.getListSanPhamDonHang().add(sanPham);

                            }
                            // Thiết lập tổng tiền cho đơn hàng
                            donHang.setTongTien(tongTien);

                            // Đặt dữ liệu đơn hàng vào Firebase
                            donHangRef.child(idDonHang).setValue(donHang);

                            Toast.makeText(GioHangActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Xử lý khi truy vấn bị hủy bỏ
                        }
                    });
                } else {
                    Toast.makeText(GioHangActivity.this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.navbottomtrangchu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {

                    Toast.makeText(getApplicationContext(), "Bạn đang ở trong Trang Chủ", Toast.LENGTH_SHORT).show();
                    return true;
                    // Chuyển đến MainActivity


                } else if (item.getItemId() == R.id.navdanhsachsanpham) {
                    // Chuyển đến DanhSachSanPhamActivity (hoặc Fragment)
                    Intent intentDanhSachSanPham = new Intent(getApplicationContext(), htspact.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                    Intent intentGiohang = new Intent(getApplicationContext(), GioHangActivity.class);
                    startActivity(intentGiohang);
                    return true;

                } else if (item.getItemId() == R.id.navcanhan) {
                    // Chuyển đến CaNhanActivity (hoặc Fragment)
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
                        Log.d("abcabc123", idSanPham.toString());
                        DatabaseReference sanphamRef = FirebaseDatabase.getInstance().getReference("giohang").child(idtaikhoan).child(idSanPham);
                        sanphamRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                giohang giohang=new giohang();
                                Log.d("abcabc1", snapshot.toString());

                                for (DataSnapshot sanPham : snapshot.getChildren()) {
                                    String key = sanPham.getKey();
                                    Object value = sanPham.getValue();

                                    if (key.equals("tensanpham")) {
                                        String tensanpham = (String) value;
                                        Log.d("abcabc1", tensanpham);
                                        giohang.setTenSanPham(tensanpham);
                                    }
//

                                    if (key.equals("hinhanh")) {
                                        String tensanpham = (String) value;
                                        Log.d("abcabc1", tensanpham);
                                        giohang.setHinhAnh(tensanpham);
                                    }
                                    if (key.equals("soluong")) {
                                        Long soluong = sanPham.getValue(Long.class); // Chuyển đổi sang kiểu Long
                                        if (soluong != null) {
                                            int soLuongInt = soluong.intValue(); // Chuyển đổi sang kiểu int
                                            giohang.setSoLuong(soLuongInt);
                                            Log.d("abcabc1", "Giá trị soluong có tồn tại");
                                        } else {
                                            Log.d("abcabc1", "Giá trị soluong không tồn tại");
                                        }
                                    }
                                    if (key.equals("giaban")) {
                                        Long giaBanLong = sanPham.getValue(Long.class); // Chuyển đổi sang kiểu Long
                                        if (giaBanLong != null) {
                                            int giaBanInt = giaBanLong.intValue(); // Chuyển đổi sang kiểu int
                                            giohang.setGiaBan(giaBanInt); // Gán giá trị cho trường "giaban"
                                            Log.d("abcabc1", "Giá trị giaban có tồn tại");
                                        } else {
                                            Log.d("abcabc1", "Giá trị giaban không tồn tại");
                                        }
                                    }
                                }
                                addProductToCart(giohang);
                                gioHangList.add(giohang);
                        }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("abcabc2",error.getMessage());
                            }
                        });

                    }

                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi truy vấn bị hủy bỏ
                    Log.d("XemLoi", databaseError.getMessage().toString());
                }
            });


        }

        else {
            // Xử lý khi không tìm thấy idtaikhoan
            Toast.makeText(getApplicationContext(), "Bạn Chưa Đăng Nhặp", Toast.LENGTH_SHORT).show();
        }
        Log.d("abcabcabcabc", gioHangList.toString());
    }


    private void addProductToCart(giohang gioHang) {
        View productLayout = LayoutInflater.from(this).inflate(R.layout.cart_item, productListLayout, false);

        ImageView productImage = productLayout.findViewById(R.id.product_image);
        TextView productName = productLayout.findViewById(R.id.product_name);
        TextView productPrice = productLayout.findViewById(R.id.product_price);
        TextView productQuantity = productLayout.findViewById(R.id.product_quantity);
        Button buttonDecrease = productLayout.findViewById(R.id.button_decrease);
        Button buttonIncrease = productLayout.findViewById(R.id.button_increase);

        productName.setText(gioHang.getTenSanPham());
        // Tính tổng giá bán cho sản phẩm
        int tongGiaBan = gioHang.getSoLuong() * gioHang.getGiaBan();

// Đặt văn bản của TextView productPrice là tổng giá bán đã tính toán
        productPrice.setText(String.format("%,d VNĐ", tongGiaBan));

        productQuantity.setText(String.valueOf(gioHang.getSoLuong()));

        Glide.with(this).load(gioHang.getHinhAnh()).into(productImage);

        buttonDecrease.setOnClickListener(v -> {
            int quantity = gioHang.getSoLuong();
            if (quantity > 1) {
                gioHang.setSoLuong(--quantity);
                productQuantity.setText(String.valueOf(quantity));
                // Update quantity in Firebase

            }
        });

        buttonIncrease.setOnClickListener(v -> {
            int quantity = gioHang.getSoLuong();
            gioHang.setSoLuong(++quantity);
            productQuantity.setText(String.valueOf(quantity));
            // Update quantity in Firebase

        });

        productListLayout.addView(productLayout);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên
        if (databaseRef != null && valueEventListener != null) {
            databaseRef.removeEventListener(valueEventListener);
        }
    }
}
