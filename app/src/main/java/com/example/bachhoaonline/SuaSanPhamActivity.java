package com.example.bachhoaonline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bachhoaonline.adapter.LoaiConSpinnerAdapter;
import com.example.bachhoaonline.adapter.LoaiSpinnerAdapter;
import com.example.bachhoaonline.model.Loai;
import com.example.bachhoaonline.model.loaicon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SuaSanPhamActivity extends AppCompatActivity {
    private Spinner spinnerLoai, spinnerLoaiCon;
    private List<Loai> loaiList;

    private EditText textTenSanPham, textGiaBan, textMoTa;
    private ImageView imageViewProduct;
    private Button buttonUpdateProduct;
    int REQUEST_CODE_IMAGE = 1;

    // Khai báo biến imageUrl ở mức độ toàn cục
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_sanpham);

        // Ánh xạ các view
        textTenSanPham = findViewById(R.id.texttensanpham);
        textGiaBan = findViewById(R.id.textgiaban);
        textMoTa = findViewById(R.id.textmota);

        imageViewProduct = findViewById(R.id.imageViewProduct);
        buttonUpdateProduct = findViewById(R.id.buttonUpdateProduct);
        spinnerLoai = findViewById(R.id.spinnerLoai);
        spinnerLoaiCon = findViewById(R.id.spinnerLoaiCon);
        loadLoaiData();

        String tenSanPham = getIntent().getStringExtra("tensanpham");
        String moTa = getIntent().getStringExtra("mota");
        long giaBan = getIntent().getLongExtra("giaban", 0);
        imageUrl = getIntent().getStringExtra("hinhanh");

        // Load hình ảnh từ URL và hiển thị lên ImageView
        textTenSanPham.setText(tenSanPham);
        textMoTa.setText(moTa);
        textGiaBan.setText(String.valueOf(giaBan));
        loadImageFromUrl(imageUrl);

        imageViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity để chọn hình ảnh mới từ điện thoại
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        // Đặt sự kiện cho nút cập nhật sản phẩm
        buttonUpdateProduct.setOnClickListener(view -> updateProduct());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                // Lấy URI của hình ảnh mới đã chọn
                Uri selectedImageUri = data.getData();
                // Chuyển URI thành URL của hình ảnh mới
                String newImageUrl = selectedImageUri.toString();
                // Cập nhật imageUrl với ảnh mới
                imageUrl = newImageUrl;
                // Load và hiển thị hình ảnh mới lên ImageView
                loadImageFromUrl(newImageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLoaiData() {
        FirebaseDatabase.getInstance().getReference().child("loai").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loaiList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    String tenLoai = snapshot.child("tenloai").getValue(String.class);

                    List<loaicon> loaiConList = new ArrayList<>();
                    for (DataSnapshot loaiSnapshot : snapshot.child("loaicon").getChildren()) {
                        String idLoaiCon = loaiSnapshot.getKey();
                        String tenLoaiCon = loaiSnapshot.getValue(String.class);
                        loaicon loaiCon = new loaicon();
                        loaiCon.setIdloaicon(Integer.parseInt(idLoaiCon));
                        loaiCon.setTenloaicon(tenLoaiCon);
                        loaiConList.add(loaiCon);
                    }

                    Loai loai = new Loai();
                    loai.setIdloai(key);
                    loai.setTenloai(tenLoai);
                    loai.setListloaicon(loaiConList);

                    loaiList.add(loai);
                }

                LoaiSpinnerAdapter loaiAdapter = new LoaiSpinnerAdapter(SuaSanPhamActivity.this, loaiList);
                spinnerLoai.setAdapter(loaiAdapter);

                spinnerLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Loai selectedLoai = (Loai) parent.getSelectedItem();
                        List<loaicon> loaiConList = selectedLoai.getListloaicon();

                        LoaiConSpinnerAdapter loaiConAdapter = new LoaiConSpinnerAdapter(SuaSanPhamActivity.this, loaiConList);
                        spinnerLoaiCon.setAdapter(loaiConAdapter);

                        // Set the selection for loaiCon after loaiConAdapter is set
                        setLoaiConSelection();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                setInitialSpinnerSelections();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setInitialSpinnerSelections() {
        int idLoaiHienTai = getIntent().getIntExtra("loai", 0);
        int idLoaiConHienTai = getIntent().getIntExtra("loaicon", 0);

        for (int i = 0; i < loaiList.size(); i++) {
            Loai loai = loaiList.get(i);
            if (Integer.parseInt(loai.getIdloai()) == idLoaiHienTai) {
                spinnerLoai.setSelection(i);

                List<loaicon> loaiConList = loai.getListloaicon();
                for (int j = 0; j < loaiConList.size(); j++) {
                    if (loaiConList.get(j).getIdloaicon() == idLoaiConHienTai) {
                        spinnerLoaiCon.setSelection(j);
                        break;
                    }
                }
                break;
            }
        }
    }

    private void setLoaiConSelection() {
        int idLoaiConHienTai = getIntent().getIntExtra("loaicon", 0);
        Loai selectedLoai = (Loai) spinnerLoai.getSelectedItem();
        List<loaicon> loaiConList = selectedLoai.getListloaicon();

        for (int j = 0; j < loaiConList.size(); j++) {
            if (loaiConList.get(j).getIdloaicon() == idLoaiConHienTai) {
                spinnerLoaiCon.setSelection(j);
                break;
            }
        }
    }

    private void updateProduct() {
        String idSanPham = getIntent().getStringExtra("idString");
        String tenSanPham = textTenSanPham.getText().toString().trim();
        Long giaBan = Long.parseLong(textGiaBan.getText().toString().trim());
        String moTa = textMoTa.getText().toString().trim();

        Loai selectedLoai = (Loai) spinnerLoai.getSelectedItem();
        loaicon selectedLoaiCon = (loaicon) spinnerLoaiCon.getSelectedItem();
        String idLoai = selectedLoai.getIdloai();
        int idLoaiCon = selectedLoaiCon != null ? selectedLoaiCon.getIdloaicon() : 0;

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("sanpham").child(idSanPham);
        productRef.child("tensanpham").setValue(tenSanPham);
        productRef.child("giaban").setValue(giaBan);
        productRef.child("mota").setValue(moTa);
        productRef.child("hinhanh").setValue(imageUrl);
        productRef.child("loai").setValue(Integer.parseInt(idLoai));
        productRef.child("loaicon").setValue(idLoaiCon);

        Toast.makeText(this, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SuaSanPhamActivity.this, QuanLySanPhamActivity.class);
        startActivity(intent);
    }

    private void loadImageFromUrl(String imageUrl) {
        Picasso.get().load(imageUrl).into(imageViewProduct);
    }
}
