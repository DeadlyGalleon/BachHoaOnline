package com.example.bachhoaonline;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bachhoaonline.adapter.CartAdapter;
import com.example.bachhoaonline.model.giohang;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    private DatabaseReference databaseRef;
    private ValueEventListener valueEventListener;
    private ListView listView;
    private List<giohang> cartItemList;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbottomtrangchu);
listView=findViewById(R.id.listViewCartItems);
        cartItemList = new ArrayList<>();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    // Chuyển đến MainActivity
                    Intent intentHome = new Intent(GioHangActivity.this, MainActivity.class);
                    startActivity(intentHome);
                    return true;
                } else if (item.getItemId() == R.id.navdanhsachsanpham) {
                    // Chuyển đến DanhSachSanPhamActivity (hoặc Fragment)
                    Intent intentDanhSachSanPham = new Intent(GioHangActivity.this, htspact.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                    // Kiểm tra nếu người dùng đã đăng nhập
                    if (isUserLoggedIn()) {

                        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
                        String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                        // Tham chiếu đến nút "giohang/1/1" trong cơ sở dữ liệu
                        databaseReference = firebaseDatabase.getReference().child("giohang").child("1").child("17");

                        // Thêm một ValueEventListener để lắng nghe sự thay đổi trong dữ liệu
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String value=dataSnapshot.getValue().toString();

                                if (dataSnapshot.exists()) {



                                    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                                } else {
                                    // Dữ liệu không tồn tại
                                    Toast.makeText(getApplicationContext(), "Dữ liệu không tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Xử lý lỗi nếu có
                                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });



//                        loadCartItems();
                    } else {
                        // Hiển thị thông báo yêu cầu đăng nhập
                        Toast.makeText(GioHangActivity.this, "Vui lòng đăng nhập để xem giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } else if (item.getItemId() == R.id.navcanhan) {
                    // Chuyển đến CaNhanActivity (hoặc Fragment)
                    Intent intentCaNhan = new Intent(GioHangActivity.this, PersonalActivity.class);
                    startActivity(intentCaNhan);
                    return true;
                }
                return false;
            }
        });
    }

    private boolean isUserLoggedIn() {
        // Kiểm tra xem đã lưu thông tin tài khoản trong SharedPreferences hay chưa
        // Trả về true nếu đã có thông tin tài khoản, ngược lại trả về false
        // Ở đây, bạn có thể sử dụng một key cụ thể để kiểm tra (ví dụ: "username")
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        return sharedPreferences.contains("idtaikhoan");
    }

    private void loadCartItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        String idtaikhoan = sharedPreferences.getString("idtaikhoan", "");

        // Tham chiếu đến node giohang trong Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().getReference("giohang");

        // Lắng nghe sự thay đổi dữ liệu trên Firebase
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItemList.clear(); // Xóa dữ liệu cũ trước khi nạp dữ liệu mới

                // Kiểm tra xem node giohang/idtaikhoan có tồn tại hay không
                if (dataSnapshot.hasChild(idtaikhoan)) {
                    // Duyệt qua các nút con của node idtaikhoan
                    DataSnapshot idtaikhoanSnapshot = dataSnapshot.child(idtaikhoan);
                    for (DataSnapshot idsanphamSnapshot : idtaikhoanSnapshot.getChildren()) {
                        // Lấy giá trị từ mỗi child (idsanpham)
                        String idsanpham = idsanphamSnapshot.getKey();

                        // Lấy thông tin chi tiết của sản phẩm từ DataSnapshot
                        String tensanpham = idsanphamSnapshot.child("tensanpham").getValue(String.class);
                        String hinhanh = idsanphamSnapshot.child("hinhanh").getValue(String.class);
                        Integer giaban = idsanphamSnapshot.child("giaban").getValue(Integer.class);
                        Integer soluong = idsanphamSnapshot.child("soluong").getValue(Integer.class);
                        Integer thanhtien = idsanphamSnapshot.child("thanhtien").getValue(Integer.class);

                        // Tạo đối tượng giohang từ dữ liệu đã lấy và thêm vào danh sách
                        giohang cartItem = new giohang(idsanpham, tensanpham, giaban, hinhanh, soluong, thanhtien);
                        cartItemList.add(cartItem);
                    }

                    // Hiển thị danh sách sản phẩm trong giỏ hàng
                    displayCartItems();
                } else {
                    // Hiển thị thông báo khi giỏ hàng trống
                    Toast.makeText(GioHangActivity.this, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show();
                }
            }

            private void displayCartItems() {
                // Tạo adapter để hiển thị danh sách sản phẩm trong giỏ hàng
                CartAdapter adapter = new CartAdapter(GioHangActivity.this, cartItemList);

                // Gán adapter vào ListView
                listView.setAdapter((ListAdapter) adapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
                Toast.makeText(GioHangActivity.this, "Lỗi khi tải dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
            }
        };

        // Đăng ký lắng nghe sự thay đổi dữ liệu
        databaseRef.addListenerForSingleValueEvent(valueEventListener);
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
