package com.example.bachhoaonline;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bachhoaonline.adapter.QuanLyLoaiAdapter;
import com.example.bachhoaonline.model.Loai;
import com.example.bachhoaonline.model.loaicon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLyDanhMucActivity extends AppCompatActivity {
    private ListView listViewLoai;
    private List<Loai> loaiList;
    private QuanLyLoaiAdapter loaiAdapter;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private Button buttonAddLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlydanhmuc);

        listViewLoai = findViewById(R.id.listViewLoai);
        buttonAddLoai = findViewById(R.id.buttonAddLoai);
        loaiList = new ArrayList<>();
        loaiAdapter = new QuanLyLoaiAdapter(this, loaiList);
        listViewLoai.setAdapter(loaiAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("loai");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("idlonnhat");

        loadLoaiFromFirebase();

        buttonAddLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onkoem","koem");
                showAddLoaiDialog();
            }
        });
    }

    private void loadLoaiFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loaiList.clear();
                for (DataSnapshot loaiSnapshot : dataSnapshot.getChildren()) {
                    String idloai = loaiSnapshot.getKey();
                    String tenLoai = loaiSnapshot.child("tenloai").getValue(String.class);

                    List<loaicon> loaiConList = new ArrayList<>();
                    DataSnapshot loaiConSnapshot = loaiSnapshot.child("loaicon");
                    for (DataSnapshot loaiConChildSnapshot : loaiConSnapshot.getChildren()) {
                        String idLoaiCon = loaiConChildSnapshot.getKey();
                        String tenLoaiCon = loaiConChildSnapshot.getValue(String.class);

                        loaicon loaiCon = new loaicon();
                        loaiCon.setIdloaicon(Integer.parseInt(idLoaiCon));
                        loaiCon.setTenloaicon(tenLoaiCon);
                        loaiConList.add(loaiCon);
                    }

                    Loai loai = new Loai();
                    loai.setIdloai(idloai);
                    loai.setTenloai(tenLoai);
                    loai.setListloaicon(loaiConList);
                    loaiList.add(loai);
                }
                loaiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private void showAddLoaiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Loại");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenLoai = input.getText().toString();
                Log.d("onkoem","koem1");
                addLoai(tenLoai);

            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addLoai(String tenLoai) {
        // Get the reference to the node containing the latest idloailonnhat
        DatabaseReference idloaiRef = databaseReference1.child("idloailonnhat");

        // Increment the value of idloailonnhat
        idloaiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the current value of idloailonnhat
                int idloaiLonNhat = dataSnapshot.getValue(Integer.class);

                // Increment the value and set it back
                idloaiRef.setValue(idloaiLonNhat + 1);

                // Use the updated value as the new id for the category
                String id = String.valueOf(idloaiLonNhat + 1);


                // Initialize idloaiconlonnhat with 0
                databaseReference.child(id).child("idloaiconlonnhat").setValue(0);

                // Create a new Loai object with the provided name and an empty list of loaicon
                databaseReference.child(id).child("tenloai").setValue(tenLoai);

                // Set the initial loaicon list with two items: "rau" and "quả"


                // Show a toast message indicating that the category has been added
                Toast.makeText(getApplicationContext(), "Đã thêm loại mới", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors here
            }
        });
    }



    public void editLoai(String id, String newTenLoai) {
        databaseReference.child(id).child("tenloai").setValue(newTenLoai);
        Toast.makeText(this, "Đã sửa loại", Toast.LENGTH_SHORT).show();
    }

    public void deleteLoai(String id) {
        databaseReference.child(id).removeValue();
        Toast.makeText(this, "Đã xóa loại", Toast.LENGTH_SHORT).show();
    }

    public void addLoaiCon(String loaiId, String tenLoaiCon) {
        // Get the current maximum ID of subcategories
        databaseReference.child(loaiId).child("idloaiconlonnhat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the maximum ID and convert it to an integer
                int maxId = dataSnapshot.getValue(Integer.class);

                // Increment the maximum ID to generate a new unique ID
                int newId = maxId + 1;

                // Set the subcategory's name under the 'loaicon' node of the category with the new ID
                databaseReference.child(loaiId).child("loaicon").child(String.valueOf(newId)).setValue(tenLoaiCon);

                // Update the maximum ID to the new value
                databaseReference.child(loaiId).child("idloaiconlonnhat").setValue(newId);

                // Show a toast message indicating that the subcategory has been added
                Toast.makeText(QuanLyDanhMucActivity.this, "Đã thêm loại con", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }



    public void editLoaiCon(String loaiId, String loaiConId, String newTenLoaiCon) {
        databaseReference.child(loaiId).child("loaicon").child(loaiConId).setValue(newTenLoaiCon);
        Toast.makeText(this, "Đã sửa loại con", Toast.LENGTH_SHORT).show();
    }

    public void deleteLoaiCon(String loaiId, String loaiConId) {
        databaseReference.child(loaiId).child("loaicon").child(loaiConId).removeValue();
        Toast.makeText(this, "Đã xóa loại con", Toast.LENGTH_SHORT).show();
    }
}
