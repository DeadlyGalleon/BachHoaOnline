package com.example.bachhoaonline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SuaSanPhamActivity extends AppCompatActivity {

    private EditText textTenSanPham, textGiaBan;
    private ImageView imageViewProduct;
    private Button buttonUpdateProduct, buttonDeleteProduct;
    int REQUEST_CODE_IMAGE = 1;

    // Khai báo biến imageUrl ở mức độ toàn cục
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_sanpham);
        Log.d("sss", "loiii");

        // Ánh xạ các view
        textTenSanPham = findViewById(R.id.texttensanpham);
        textGiaBan = findViewById(R.id.textgiaban);
        imageViewProduct = findViewById(R.id.imageViewProduct);
        buttonUpdateProduct = findViewById(R.id.buttonUpdateProduct);


        // Lấy URL hình ảnh từ Intent
        imageUrl = getIntent().getStringExtra("imageUrl");

        // Load hình ảnh từ URL và hiển thị lên ImageView
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

    // Phương thức onActivityResult sẽ được gọi khi kết quả trả về từ Activity chọn hình ảnh
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

    // Phương thức cập nhật sản phẩm
    private void updateProduct() {
        String idSanPham = getIntent().getStringExtra("idString");
        String tenSanPham = textTenSanPham.getText().toString().trim();
        Long giaBan = Long.parseLong(textGiaBan.getText().toString().trim());

        // Cập nhật thông tin sản phẩm trong Firebase
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("sanpham").child(idSanPham);
        productRef.child("tensanpham").setValue(tenSanPham);
        productRef.child("giaban").setValue(giaBan);
        productRef.child("hinhanh").setValue(imageUrl); // Sử dụng imageUrl ở đây

        // Hiển thị thông báo thành công
        Toast.makeText(this, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();

        // Chuyển đến màn hình danh sách sản phẩm
        Intent intent = new Intent(SuaSanPhamActivity.this, QuanLySanPhamActivity.class);
        startActivity(intent);
    }

    // Phương thức loadImageFromUrl để load và hiển thị hình ảnh từ URL lên ImageView
    private void loadImageFromUrl(String imageUrl) {
        Picasso.get().load(imageUrl).into(imageViewProduct);
    }
}