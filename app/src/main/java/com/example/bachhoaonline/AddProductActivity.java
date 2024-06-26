package com.example.bachhoaonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.example.bachhoaonline.R;
import com.example.bachhoaonline.adapter.LoaiConSpinnerAdapter;
import com.example.bachhoaonline.adapter.LoaiSpinnerAdapter;
import com.example.bachhoaonline.model.Loai;
import com.example.bachhoaonline.model.loaicon;
import com.example.bachhoaonline.model.sanpham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    int REQUEST_CODE_IMAGE = 1;
    private List<Loai> loaiList;
    private Spinner spinnerLoai;
    ImageView hinhanh;
    private Spinner spinnerLoaiCon;
    private EditText textTenSanPham;
    private EditText textGiaBan;
    private EditText textmota;
    private Button buttonAddProduct, btnthemanh;
    private Bitmap selectedImageBitmap;


    FirebaseStorage storage = FirebaseStorage.getInstance("gs://bachhoaonline-de1d0.appspot.com");
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Get a non-default Storage bucket


        AnhXa();
        loadLoaiData();
        Control();
    }

    private void loadLoaiData() {
        FirebaseDatabase.getInstance().getReference().child("loai").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loaiList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    String tenLoai = snapshot.child("tenloai").getValue(String.class);

                    // Load danh sách loại con
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
                    loai.setListloaicon(loaiConList); // Gán danh sách loại con cho mỗi loại

                    loaiList.add(loai);
                }

                // Hiển thị danh sách loại trong spinnerLoai
                LoaiSpinnerAdapter loaiAdapter = new LoaiSpinnerAdapter(AddProductActivity.this, loaiList);
                spinnerLoai.setAdapter(loaiAdapter);

                // Hiển thị loại con tương ứng với loại được chọn trong spinnerLoai
                spinnerLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Loai selectedLoai = (Loai) parent.getSelectedItem();
                        List<loaicon> loaiConList = selectedLoai.getListloaicon();

                        // Hiển thị danh sách loại con trong spinnerLoaiCon
                        LoaiConSpinnerAdapter loaiConAdapter = new LoaiConSpinnerAdapter(AddProductActivity.this, loaiConList);
                        spinnerLoaiCon.setAdapter(loaiConAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
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
        hinhanh = findViewById(R.id.imageViewProduct);
        textmota=findViewById(R.id.textmota);
    }

    public void Control() {
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSanPham();
            }
        });

        hinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                // Get the input stream of the selected image
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                // Decode the input stream into a Bitmap
                selectedImageBitmap = BitmapFactory.decodeStream(inputStream);
                // Set the Bitmap to the ImageView
                hinhanh.setImageBitmap(selectedImageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void themSanPham() {
        // Lấy dữ liệu từ các trường nhập liệu
        String tenSanPham = textTenSanPham.getText().toString();
        String giaBanStr = textGiaBan.getText().toString();

        // Kiểm tra xem các trường có dữ liệu không
        if (tenSanPham.isEmpty() || giaBanStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy key lớn nhất trong nút "sanpham" của cơ sở dữ liệu
        DatabaseReference sanPhamRef = FirebaseDatabase.getInstance().getReference("sanpham");
        sanPhamRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long maxKey = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    long key = Long.parseLong(snapshot.getKey());
                    if (key > maxKey) {
                        maxKey = key;
                    }
                }

                // Tạo key mới bằng cách tăng maxKey lên 1
                long newKey = maxKey + 1;

                // Tiến hành thêm sản phẩm vào cơ sở dữ liệu với key mới
                themSanPhamVoiKeyMoi(newKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });
    }

    private void themSanPhamVoiKeyMoi(long newKey) {
        // Define the default image URL
        final String defaultImageUrl = "https://toppng.com/uploads/preview/and-blank-effect-transparent-11546868080xgtiz6hxid.png";

        // Check if selectedImageBitmap is null
        if (selectedImageBitmap == null) {
            // If no image is selected, use the default image URL
            saveProductWithImageUrl(newKey, defaultImageUrl);
        } else {
            // If an image is selected, upload it to Firebase Storage
            Calendar calendar = Calendar.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("hinhanhsanpham/"); // Đường dẫn của thư mục
            stringBuilder.append("hinhanh");
            stringBuilder.append(calendar.getTimeInMillis());
            stringBuilder.append(".png");

            String imagePath = stringBuilder.toString();
            StorageReference mountainsRef = storageRef.child(imagePath);
            hinhanh.setDrawingCacheEnabled(true);
            hinhanh.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) hinhanh.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the URL of the uploaded image
                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            // Save the product with the image URL
                            saveProductWithImageUrl(newKey, imageUrl);
                        }
                    });
                }
            });
        }
    }

    private void saveProductWithImageUrl(long newKey, String imageUrl) {
        // Save the product information to Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference sanPhamRef = database.getReference("sanpham").child(String.valueOf(newKey));

        // Create a new product object and set its properties
        sanpham sanPham = new sanpham(String.valueOf(newKey), textTenSanPham.getText().toString(), Long.parseLong(textGiaBan.getText().toString()), Integer.parseInt (((Loai) spinnerLoai.getSelectedItem()).getIdloai()), imageUrl);
        sanPham.setMota(textmota.getText().toString());

        // Kiểm tra xem loại có loại con nào không
        if (((Loai) spinnerLoai.getSelectedItem()).getListloaicon().isEmpty()) {
            // Nếu không có loại con, đặt giá trị loaicon thành 0
            sanPham.setLoaicon(0);
        } else {
            // Nếu có loại con, đặt giá trị loaicon bằng idloaicon của loại con được chọn
            int selectedLoaiconId = ((loaicon) spinnerLoaiCon.getSelectedItem()).getIdloaicon();
            sanPham.setLoaicon(selectedLoaiconId);
        }

        sanPhamRef.setValue(sanPham)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),QuanLySanPhamActivity.class);
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        // Handle failure, perform any additional actions if needed
                    }
                });
    }





}
