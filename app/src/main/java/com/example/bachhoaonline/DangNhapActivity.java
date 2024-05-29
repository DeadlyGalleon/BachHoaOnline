package com.example.bachhoaonline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.bachhoaonline.model.taikhoan;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangNhapActivity extends AppCompatActivity {
    EditText account,matkhau;
    AppCompatButton btndangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formdangnhap);
        initview();
        initcontrol();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Button goToSignUpButton = findViewById(R.id.buttonGoToSignUp);
        goToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKiActivity.class);
                startActivity(intent);

            }
        });

        Button buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Tùy chọn: kết thúc hoạt động hiện tại nếu bạn muốn
            }
        });
    }
public  void initcontrol(){
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangnhap();
            }
        });

}

    private void dangnhap() {
        String taiKhoan = account.getText().toString().trim();
        String matKhau = matkhau.getText().toString().trim();

        // Kiểm tra xem có trường nào trống không
        if (TextUtils.isEmpty(taiKhoan)) {
            Toast.makeText(this, "Bạn chưa nhập tên tài khoản", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(matKhau)) {
            Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện xác thực tài khoản và mật khẩu
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("taikhoan");


        databaseReference.orderByChild("tentaikhoan").equalTo(taiKhoan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        taikhoan taikhoan = snapshot.getValue(taikhoan.class);
                        String key=snapshot.getKey();
                        if (taikhoan.getMatkhau().equals(matKhau)) {
                            SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("idtaikhoan", key);
                            editor.putString("tentaikhoan",taikhoan.getTentaikhoan());
                            editor.putString("sodienthoai",taikhoan.getSodienthoai());
                            String qunly = String.valueOf(taikhoan.getLaquanly());

                            editor.putString("quanly",qunly);

                            editor.apply();

                            String idtaikhoan=sharedPreferences.getString("idtaikhoan","");


                            // Đăng nhập thành công
                            Toast.makeText(getApplicationContext(), idtaikhoan, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                            startActivity(intent);
                            return;
                        }

                    }
                    // Mật khẩu không đúng
                    Toast.makeText(getApplicationContext(), "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    // Tài khoản không tồn tại
                    Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi
            }
        });
    }


    @SuppressLint("WrongViewCast")
    public void initview(){
        account=findViewById(R.id.textsodienthoai);
        matkhau=findViewById(R.id.textmatkhau);
btndangnhap=findViewById(R.id.buttonLogin);



    }
}
