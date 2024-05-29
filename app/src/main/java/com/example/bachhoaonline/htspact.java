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

import com.example.bachhoaonline.adapter.LoaiAdapter;
import com.example.bachhoaonline.adapter.LoaiConSpinnerAdapter;
import com.example.bachhoaonline.adapter.sanphamadapter;
import com.example.bachhoaonline.model.Loai;
import com.example.bachhoaonline.model.loaicon;
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
import androidx.appcompat.widget.SearchView;

public class htspact extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private ListView sanphamListView;
    private List<sanpham> sanPhamList = new ArrayList<>();
    private Spinner spinnerLoaiSanPham, spinnerLoaiConSanPham;
    private List<Loai> loaiSanPhamList = new ArrayList<>();
    private List<loaicon> loaiConSanPhamList = new ArrayList<>();
    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_sanpham);
        AnhXa();

        Control();
        databaseRef = FirebaseDatabase.getInstance().getReference("sanpham");

        if (databaseRef != null) {
            loadLoaiSanPham();
            loadSanPhamData();

        } else {
            Toast.makeText(this, "Database reference is null", Toast.LENGTH_SHORT).show();
        }

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

        spinnerLoaiSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                loadLoaiConSanPham(selectedLoai.getIdloai());
                Loai selectedLoai = loaiSanPhamList.get(position);
                loadSanPhamTheoIdLoai( selectedLoai.getIdloai().toString());
                loadLoaiConSanPham(selectedLoai.getIdloai().toString() );

                Log.d("abcabc1",selectedLoai.getTenloai().toString());
                Log.d("abcabc1",selectedLoai.getIdloai().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        spinnerLoaiConSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Loai selectedLoai = (Loai) spinnerLoaiSanPham.getSelectedItem(); // Lấy loại được chọn từ spinnerLoaiSanPham
                Log.d("aaaa",  selectedLoai.getIdloai());

                loaicon selectedLoaiCon = loaiConSanPhamList.get(position);
                Log.d("aaaa",  selectedLoaiCon.getIdloaicon().toString());

                loadSanPhamTheoIdLoaiCon(selectedLoai.getIdloai(), selectedLoaiCon.getIdloaicon().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

    }

    private void AnhXa() {
        sanphamListView = findViewById(R.id.listViewSanPham);
        spinnerLoaiSanPham = findViewById(R.id.spinnerLoaiSanPham);
        spinnerLoaiConSanPham = findViewById(R.id.spinnerLoaiConSanPham);
        sanphamListView = findViewById(R.id.listViewSanPham);
        searchView = findViewById(R.id.searchView);
    }

    private void loadLoaiSanPham() {
        DatabaseReference loaiSanPhamRef = FirebaseDatabase.getInstance().getReference().child("loai");
        loaiSanPhamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loaiSanPhamList.clear();
                Loai tatca=new Loai();
                tatca.setTenloai("Tất Cả");
                tatca.setIdloai("0");
                loaiSanPhamList.add(tatca);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenLoai = snapshot.child("tenloai").getValue(String.class);
                    String idLoai = snapshot.getKey();
                    Log.d("abcabc",tenLoai);
                    Loai loaiSanPham = new Loai(idLoai, tenLoai);
                    loaiSanPhamList.add(loaiSanPham);
                }
                LoaiAdapter adapter = new LoaiAdapter(htspact.this, loaiSanPhamList);
                spinnerLoaiSanPham.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
            }
        });
    }

    private void loadSanPhamTheoIdLoai(String idLoai) {
        if (idLoai.equals("0")) {
            loadSanPhamData();
            return;
        } else {

            DatabaseReference sanPhamRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
            Query query = sanPhamRef.orderByChild("loai").equalTo(Integer.parseInt(idLoai));
            query.addValueEventListener(new ValueEventListener() {
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
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        sanphamAdapter.filter(newText);
                        return false;
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
            }
        });



    }
    private void loadLoaiConSanPham(String idLoai) {
        // Kiểm tra nếu idloai là 0 thì không tải loại con
        if (idLoai.equals("0") || idLoai.isEmpty()) {
            // Xóa danh sách loại con và không cần tải dữ liệu
            loaiConSanPhamList.clear();
            // Create adapter with the updated list (which is now empty)
            LoaiConSpinnerAdapter adapter = new LoaiConSpinnerAdapter(htspact.this, loaiConSanPhamList);
            // Set adapter to spinner
            spinnerLoaiConSanPham.setAdapter(adapter);
            return;
        }
        Log.d("idloai",idLoai);

        DatabaseReference loaiConSanPhamRef = FirebaseDatabase.getInstance().getReference().child("loai").child(idLoai).child("loaicon");
        loaiConSanPhamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loaiConSanPhamList.clear();
                loaicon tatCaLoaiCon = new loaicon();
                tatCaLoaiCon.setIdloaicon(0); // Gán id cho loaicon "Tất Cả"
                tatCaLoaiCon.setTenloaicon("Tất Cả"); // Gán tên cho loaicon "Tất Cả"
                loaiConSanPhamList.add(tatCaLoaiCon); // Thêm loaicon "Tất Cả" vào danh sách loaicon
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idLoaiCon = snapshot.getKey();
                    String tenLoaiCon = snapshot.getValue(String.class);
                    loaicon loaiCon = new loaicon();
                    loaiCon.setIdloaicon(Integer.parseInt(idLoaiCon));
                    loaiCon.setTenloaicon(tenLoaiCon);
                    loaiConSanPhamList.add(loaiCon);
                }
                // Tạo adapter mới từ danh sách loaicon đã tải
                LoaiConSpinnerAdapter adapter = new LoaiConSpinnerAdapter(htspact.this, loaiConSanPhamList);
                // Đặt adapter cho spinnerLoaiConSanPham
                spinnerLoaiConSanPham.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
            }
        });
    }






    private void loadSanPhamTheoIdLoaiCon(String idloai,String idLoaiCon) {
        Log.d("idloaicon",idloai);
        Log.d("idloaicon",idLoaiCon);

        if (idLoaiCon.equals("0") || idLoaiCon.isEmpty()) {
            loadSanPhamTheoIdLoai(idloai);
            return;
        } else {


            DatabaseReference sanPhamRef = FirebaseDatabase.getInstance().getReference().child("sanpham");
            Query query = sanPhamRef.orderByChild("loai").equalTo(Integer.parseInt(idloai));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    sanPhamList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String idString = snapshot.getKey();
                        String tenSanPham = snapshot.child("tensanpham").getValue(String.class);
                        Long giaBan = snapshot.child("giaban").getValue(Long.class);
                        String hinhAnh = snapshot.child("hinhanh").getValue(String.class);
                        sanpham sanPham = new sanpham(idString, tenSanPham, giaBan, hinhAnh);
                        int loaicon=snapshot.child("loaicon").getValue(Integer.class);
                        if(loaicon==Integer.parseInt(idLoaiCon)) {
                            sanPhamList.add(sanPham);
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
    }

}

