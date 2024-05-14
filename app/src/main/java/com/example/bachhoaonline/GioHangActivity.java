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
import com.example.bachhoaonline.model.sanpham;
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
    List<giohang> gioHangList = new ArrayList<>();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbottomtrangchu);



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
loadCartItems();


//                        loadCartItems();
                    } else {
                        // Hiển thị thông báo yêu cầu đăng nhập

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
//                        final String[] tenSanPham = {null};
//                        final long[] giaBan = {0};
//                        final String[] linkHinhAnh = {null};
//                        final long[] soLuong = {0};


                        DatabaseReference sanphamRef = FirebaseDatabase.getInstance().getReference("giohang").child(idtaikhoan).child(idSanPham);
                        sanphamRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                giohang giohang=new giohang();
                                    Log.d("abcabc1", snapshot.toString());

                                    for (DataSnapshot sanPham : snapshot.getChildren()) {
                                        String key = sanPham.getKey();
                                        Object value = sanPham.getValue();

                                        if (key != null && value != null) {
                                            switch (key) {
                                                case "tensanpham":
                                                    giohang.setTenSanPham((String) value);
                                                    break;
                                                case "giaban":
                                                    giohang.setGiaBan((Integer) value);
                                                    break;
                                                case "hinhanh":
                                                    giohang.
g
                                                    break;
                                                case "soluong":
                                                  giohang.setSoLuong((Integer) value);
                                                    break;


                                                default:
                                                    break;
                                            }
                                        }

                                        giohang giohang =new giohang();
                                        giohang.getTenSanPham(tenSanPham);


                                    }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("abcabc2",error.getMessage());
                            }
                        });
//                        Log.d("hahaa1", idSanPham.toString());// Lấy id của sản phẩm
//                        String tensanpham = idsanpham.child("tensanpham").getValue(String.class);
//                        Log.d("hahaa1", tensanpham.toString());
//                        String hinhanh = idsanpham.child("hinhanh").getValue(String.class);
//                        Log.d("hahaa1", hinhanh.toString());
//                        Integer giaban = idsanpham.child("giaban").getValue(Integer.class);
//                        Log.d("hahaa1", giaban.toString());
//                        Integer soluong = idsanpham.child("soluong").getValue(Integer.class);
//                        Log.d("hahaa1", soluong.toString());

//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            String idString = snapshot.getKey();
//                            String tenSanPham = snapshot.child("tensanpham").getValue(String.class);
//                            Long giaBan = snapshot.child("giaban").getValue(Long.class);
//                            String loaiSanPham = snapshot.child("loai").getValue(String.class);
//                            String hinhAnh = snapshot.child("hinhanh").getValue(String.class);
//                            sanpham sanPham = new sanpham(idString, tenSanPham, giaBan, loaiSanPham, hinhAnh);
//                            sanPhamList.add(sanPham);
//                            if (hinhAnh != null) {
//                                Log.d("Firebase URL", hinhAnh);
//                            }
//                        }
//                        if (tensanpham != null && hinhanh != null && giaban != null && soluong != null) {
//                            // Tạo đối tượng GioHang từ dữ liệu đã lấy được
//                            giohang gioHang = new giohang(idSanPham, tensanpham, giaban, hinhanh, soluong);
//                            gioHangList.add(gioHang);
//                            Log.d("abcabc", gioHangList.toString());
//                        }
                    }
                    // Sau khi load xong dữ liệu, bạn có thể cập nhật giao diện ở đây
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






    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên
        if (databaseRef != null && valueEventListener != null) {
            databaseRef.removeEventListener(valueEventListener);
        }
    }
}
