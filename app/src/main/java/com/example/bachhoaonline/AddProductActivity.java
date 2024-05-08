package com.example.bachhoaonline;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bachhoaonline.R;
import com.example.bachhoaonline.adapter.LoaiSpinnerAdapter;
import com.example.bachhoaonline.model.Loai;
import com.example.bachhoaonline.model.sanpham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private List<Loai> loaiList;
    private Spinner spinnerLoai;
    private Spinner spinnerLoaiCon;
    private EditText textTenSanPham;
    private EditText textGiaBan;
    private Button buttonAddProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        AnhXa();
        loadLoaiData();
        Control(); // Di chuyển gọi phương thức Control() xuống đây
    }


    private void loadLoaiData() {
        FirebaseDatabase.getInstance().getReference().child("loai").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loaiList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    String tenLoai = snapshot.child("tenloai").getValue(String.class);

                    // Tạo một đối tượng Loai từ dữ liệu
                    Loai loai = new Loai();
                    loai.setIdloai(key);
                    loai.setTenloai(tenLoai);

                    loaiList.add(loai);
                }

                // Sử dụng Custom Adapter mới
                LoaiSpinnerAdapter loaiAdapter = new LoaiSpinnerAdapter(AddProductActivity.this, loaiList);
                spinnerLoai.setAdapter(loaiAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }


    @SuppressLint("WrongViewCast")
    public void AnhXa() {
        spinnerLoai = findViewById(R.id.spinnerloai);
        spinnerLoaiCon = findViewById(R.id.spinnerloaicon);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        textTenSanPham = findViewById(R.id.texttensanpham);
        textGiaBan = findViewById(R.id.textgiaban);
    }

    public void Control() {
buttonAddProduct.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        themSanPham();
    }
});

    }
    private void themSanPham() {
        // Lấy dữ liệu từ các trường nhập liệu
        String tenSanPham = textTenSanPham.getText().toString();
        String giaBanStr = textGiaBan.getText().toString();
        Toast.makeText(this,"raucantay" , Toast.LENGTH_SHORT).show();
        // Kiểm tra xem các trường có dữ liệu không
        if (tenSanPham.isEmpty() || giaBanStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double giaBan = Double.parseDouble(giaBanStr);
        String idLoai = ((Loai) spinnerLoai.getSelectedItem()).getIdloai(); // Lấy id loại từ Spinner

        // Thực hiện lưu thông tin sản phẩm vào cơ sở dữ liệu Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference sanPhamRef = database.getReference("sanpham").push();
        String sanPhamKey = sanPhamRef.getKey(); // Lấy key mới tạo
        sanpham sanPham = new sanpham(sanPhamKey, tenSanPham, giaBan, idLoai, ""); // Tạo đối tượng SanPham mới
        sanPhamRef.setValue(sanPham)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        // Xử lý thành công, có thể thực hiện các hành động khác ở đây nếu cần
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProductActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                        // Xử lý khi thêm sản phẩm thất bại
                    }
                });
    }

}
