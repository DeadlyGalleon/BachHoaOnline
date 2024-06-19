package com.example.bachhoaonline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.bachhoaonline.adapter.quanlysanphamAdapter;
import com.example.bachhoaonline.model.sanpham;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLySanPhamActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private ListView sanphamListView;
    private SearchView searchView;
    private List<sanpham> sanPhamList = new ArrayList<>();
    private quanlysanphamAdapter quanlisanphamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanly_sanpham);
        AnhXa();
        Control();

        databaseRef = FirebaseDatabase.getInstance().getReference("sanpham");
        if (databaseRef != null) {
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
                    Intent intentDanhSachSanPham = new Intent(QuanLySanPhamActivity.this, MainActivity.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navdanhsachsanpham) {
                    Intent intentDanhSachSanPham = new Intent(QuanLySanPhamActivity.this, htspact.class);
                    startActivity(intentDanhSachSanPham);
                    return true;
                } else if (item.getItemId() == R.id.navgiohang) {
                    Intent intentGiohang = new Intent(QuanLySanPhamActivity.this, GioHangActivity.class);
                    startActivity(intentGiohang);
                    return true;
                } else if (item.getItemId() == R.id.navcanhan) {
                    Intent intentCaNhan = new Intent(QuanLySanPhamActivity.this, PersonalActivity.class);
                    startActivity(intentCaNhan);
                    return true;
                }
                return false;
            }
        });

        Button buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLySanPhamActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }


    private void AnhXa() {
        sanphamListView = findViewById(R.id.listViewSanPhamxx);
        searchView = findViewById(R.id.searchView);
    }

    private void loadSanPhamData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sanPhamList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        String idString = snapshot.getKey();
                        String tenSanPham = snapshot.child("tensanpham").getValue(String.class);
                        Long giaBan = snapshot.child("giaban").getValue(Long.class);
                        String hinhAnh = snapshot.child("hinhanh").getValue(String.class);
                        Integer idloai = snapshot.child("loai").getValue(Integer.class);
                        Integer idloaicon = snapshot.child("loaicon").getValue(Integer.class);
                        String mota = snapshot.child("mota").getValue(String.class);



                        if (idString != null && tenSanPham != null && giaBan != null ) {
                            sanpham sanPham = new sanpham(idString, tenSanPham, giaBan, idloai, hinhAnh);
                            Log.d("aaaa",sanPham.toString());
                            sanPham.setLoaicon(idloaicon);
                            sanPham.setMota(mota);
                            sanPhamList.add(sanPham);
                            if (hinhAnh != null) {
                                Log.d("Firebase URL", hinhAnh);
                            }
                        } else {
                            Log.d("Firebase Data", "Missing fields in product data");
                        }
                    } catch (Exception e) {
                        Log.e("Firebase Error", "Error parsing product data", e);
                    }
                }

                quanlisanphamAdapter = new quanlysanphamAdapter(QuanLySanPhamActivity.this, sanPhamList);
                sanphamListView.setAdapter(quanlisanphamAdapter);
                quanlisanphamAdapter.setOnItemClickListener(new quanlysanphamAdapter.OnItemClickListener() {

                    @Override
                    public void onEditClick(int position) {
                        sanpham clickedItem = sanPhamList.get(position);
                        Intent intent = new Intent(QuanLySanPhamActivity.this, SuaSanPhamActivity.class);
                        intent.putExtra("idString", clickedItem.getIdsanpham());
                        intent.putExtra("tensanpham", clickedItem.getTensanpham());
                        intent.putExtra("hinhanh", clickedItem.getHinhanh());
                        intent.putExtra("giaban", clickedItem.getGiaban());
                        intent.putExtra("loai", clickedItem.getLoai());
                        intent.putExtra("loaicon", clickedItem.getLoaicon());
                        intent.putExtra("mota", clickedItem.getMota());
                        startActivity(intent);
                    }
                    @Override
                    public void onDeleteClick(int position) {
                        sanpham clickedItem = sanPhamList.get(position);
                        databaseRef.child(clickedItem.getIdsanpham()).removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(QuanLySanPhamActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                loadSanPhamData(); // Reload data after deletion
                            } else {
                                Toast.makeText(QuanLySanPhamActivity.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                });
                // Set up SearchView
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        quanlisanphamAdapter.filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuanLySanPhamActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}