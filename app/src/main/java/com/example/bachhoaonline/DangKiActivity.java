package com.example.bachhoaonline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AndroidException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import com.example.bachhoaonline.model.taikhoan;
import com.example.bachhoaonline.retrofit.APIbachhoa;
import com.example.bachhoaonline.retrofit.RetrofitClient;

import com.example.bachhoaonline.utils.ApiUtils;
import com.example.bachhoaonline.utils.ApiUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.Until;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKiActivity extends AppCompatActivity {

    EditText tentaikhoan,sodienthoai,matkhau,matkhaunhaplai;
    AppCompatButton button,quayveloginbutton;
        APIbachhoa apibachhoa;
        CompositeDisposable compositeDisposable;



    @SuppressLint("WrongViewCast")
    private void initView(){



        tentaikhoan=findViewById(R.id.tentaikhoan);
        sodienthoai=findViewById(R.id.sodienthoai);
        matkhau=findViewById(R.id.matkhau);
        matkhaunhaplai=findViewById(R.id.nhaplaimatkhau);
        button=findViewById(R.id.buttondangky);
quayveloginbutton=findViewById(R.id.quayvelogin);



    }




    private void initControl(){


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangki();
            }
        });

        quayveloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKiActivity.this, DangNhapActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formdangki);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!6969696969esfsdsdfdsfsdfdsfsdfd");

        if(isConnected(this)){
            Toast.makeText(this, "Đã Kết nối Internet", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Kết Nối Internet thất bại", Toast.LENGTH_SHORT).show();

        initView();
        initControl();





    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isConnected = false;
        if (wifi != null && wifi.isConnectedOrConnecting()) {
            isConnected = true;
        } else if (mobile != null && mobile.isConnectedOrConnecting()) {
            isConnected = true;
        }

        return isConnected;
    }
    private void dangki() {
        if (!isConnected(this)) {
            Toast.makeText(this, "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin từ các trường nhập liệu
        String str_tentaikhoan = tentaikhoan.getText().toString().trim();
        String str_sodienthoai = sodienthoai.getText().toString().trim();
        String str_matkhau = matkhau.getText().toString().trim();
        String str_nhaplaimaukhau = matkhaunhaplai.getText().toString().trim();

        // Kiểm tra xem có trường nào trống không
        if (TextUtils.isEmpty(str_tentaikhoan)) {
            Toast.makeText(this, "Bạn chưa nhập tên tài khoản", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(str_sodienthoai)) {
            Toast.makeText(this, "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(str_matkhau)) {
            Toast.makeText(this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(str_nhaplaimaukhau)) {
            Toast.makeText(this, "Bạn chưa nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem mật khẩu và nhập lại mật khẩu có khớp nhau không
        if (!str_nhaplaimaukhau.equals(str_matkhau)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tham chiếu đến nút "taikhoan" trong Firebase Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("taikhoan");

        // Lấy ra số lớn nhất hiện có
        databaseReference.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long maxId = 0; // Mặc định nếu không có dữ liệu

                // Kiểm tra xem có dữ liệu hay không
                if (dataSnapshot.exists()) {
                    // Lấy key của dòng cuối cùng, chuyển đổi sang kiểu số
                    String lastKey = dataSnapshot.getChildren().iterator().next().getKey();
                    maxId = Long.parseLong(lastKey);
                }

                // Tăng số lớn nhất lên 1 để tạo key mới
                long newId = maxId + 1;

                // Tạo một đối tượng Tài khoản
                taikhoan taikhoan = new taikhoan();
                taikhoan.setTentaikhoan(str_tentaikhoan);
                taikhoan.setMatkhau(str_matkhau);
                taikhoan.setSodienthoai(str_sodienthoai);

                // Tạo key mới từ số lớn nhất tăng thêm 1
                String newKey = String.valueOf(newId);

                // Đẩy dữ liệu tài khoản vào Firebase Database với key mới
                databaseReference.child(newKey).setValue(taikhoan)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Đăng ký thành công
                                Toast.makeText(DangKiActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(DangKiActivity.this, DangNhapActivity.class);
                                startActivity(intent);


                            } else {
                                // Đăng ký thất bại
                                Toast.makeText(DangKiActivity.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }






}
