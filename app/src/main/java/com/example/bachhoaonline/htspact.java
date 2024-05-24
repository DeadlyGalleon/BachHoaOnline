package com.example.bachhoaonline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bachhoaonline.adapter.sanphamadapter;
import com.example.bachhoaonline.model.Loai;
import com.example.bachhoaonline.model.sanpham;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class htspact extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private ListView sanphamListView;
    private List<sanpham> sanPhamList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_sanpham);
        AnhXa();
        databaseRef = FirebaseDatabase.getInstance().getReference("sanpham");
        if (databaseRef != null) {
            loadSanPhamData();
            loadLoaiSanPham();

        } else {
            // Xử lý trường hợp databaseRef là null
            Toast.makeText(this, "Database reference is null", Toast.LENGTH_SHORT).show();
        }

        Control();
    }

    private void Control() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navbottomtrangchu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    Intent intentDanhSachSanPham = new Intent(htspact.this, MainActivity.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navdanhsachsanpham) {
                    Intent intentDanhSachSanPham = new Intent(htspact.this, htspact.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                     Intent intentGiohang = new Intent(htspact.this, GioHangActivity.class);
                     startActivity(intentGiohang);
                    return true;
                } else if (item.getItemId() == R.id.navcanhan) {
                    Intent intentCaNhan = new Intent(htspact.this, PersonalActivity.class);
                    startActivity(intentCaNhan);
                    return true;
                }
                return false;
            }
        });

        Spinner spinnerLoaiSanPham = findViewById(R.id.spinnerLoaiSanPham);
        Spinner spinnerLoaiConSanPham1 = findViewById(R.id.spinnerLoaiConSanPham);

        spinnerLoaiSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Loai selectedLoai = (Loai) adapterView.getItemAtPosition(position);
                String idLoai = selectedLoai.getIdloai();
                // Tải danh sách sản phẩm từ Firebase dựa trên idloai đã chọn
                loadSanPhamTheoIdLoai(idLoai);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

    }

    private void AnhXa() {
        sanphamListView = findViewById(R.id.listViewSanPham);
    }

    private void loadSanPhamData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sanPhamList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idString = snapshot.getKey();
                    String tenSanPham = snapshot.child("tensanpham").getValue(String.class);
                    Long giaBan = snapshot.child("giaban").getValue(Long.class);
                    String hinhAnh = snapshot.child("hinhanh").getValue(String.class);
                    sanpham sanPham = new sanpham(idString, tenSanPham, giaBan, hinhAnh);
                    sanPhamList.add(sanPham);
                    if (hinhAnh != null) {
                        Log.d("Firebase URL", hinhAnh);
                    }
                }

                sanphamadapter sanphamAdapter = new sanphamadapter(htspact.this, sanPhamList);
                sanphamAdapter.setOnItemClickListener(new sanphamadapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        sanpham clickedItem = sanPhamList.get(position);
                        Intent intent = new Intent(htspact.this, Chitietsp.class);
                        intent.putExtra("idString", clickedItem.getIdsanpham());
                        startActivity(intent);
                    }
                });
                sanphamListView.setAdapter(sanphamAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
            }
        });
    }

    private void loadLoaiSanPham() {
        DatabaseReference loaiSanPhamRef = FirebaseDatabase.getInstance().getReference().child("loai");
        loaiSanPhamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Loai> loaiSanPhamList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenLoai = snapshot.child("tenloai").getValue(String.class);
                    String idLoai = snapshot.getKey();
                    Loai loaiSanPham = new Loai(tenLoai, idLoai);
                    loaiSanPhamList.add(loaiSanPham);
                }
                // Nạp dữ liệu vào Spinner loại sản phẩm
                ArrayAdapter<Loai> adapter = new ArrayAdapter<>(htspact.this, android.R.layout.simple_spinner_item, loaiSanPhamList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner spinnerLoaiSanPham = findViewById(R.id.spinnerLoaiSanPham);
                spinnerLoaiSanPham.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
            }
        });
    }


//    private void loadLoaiConSanPham(String tenLoai, Spinner spinnerLoaiConSanPham) {
//        DatabaseReference loaiConSanPhamRef = FirebaseDatabase.getInstance().getReference().child("loai").child(tenLoai).child("loaicon");
//        loaiConSanPhamRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<String> loaiConSanPhamList = new ArrayList<>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String tenLoaiCon = snapshot.getValue(String.class);
//                    loaiConSanPhamList.add(tenLoaiCon);
//                }
//                // Nạp dữ liệu vào Spinner loại con sản phẩm
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(htspact.this, android.R.layout.simple_spinner_item, loaiConSanPhamList);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerLoaiConSanPham.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Xử lý khi truy vấn bị hủy bỏ
//            }
//        });
//    }
private void loadSanPhamTheoIdLoai(String idLoai) {
    DatabaseReference sanPhamRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
    Query query = sanPhamRef.orderByChild("idloai").equalTo(idLoai);
    query.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<sanpham> sanPhamList = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String idString = snapshot.getKey();
                String tenSanPham = snapshot.child("tensanpham").getValue(String.class);
                Long giaBan = snapshot.child("giaban").getValue(Long.class);
                String hinhAnh = snapshot.child("hinhanh").getValue(String.class);
                sanpham sanPham = new sanpham(idString, tenSanPham, giaBan, hinhAnh);
                sanPhamList.add(sanPham);
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            // Xử lý khi truy vấn bị hủy bỏ
        }
    });
}






}
