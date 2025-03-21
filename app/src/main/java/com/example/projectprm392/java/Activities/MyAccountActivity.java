package com.example.projectprm392.java.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprm392.R;

public class MyAccountActivity extends AppCompatActivity {

    Button btnEditProfile, btnChangePassword, btnLogout;

    TextView txtName, txtEmail, txtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_account);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnLogout = findViewById(R.id.btnLogout);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        txtName.setText(sharedPreferences.getString("fullname", "Chưa có tên"));
        txtEmail.setText(sharedPreferences.getString("email", "Chưa có email"));
        txtPhone.setText(sharedPreferences.getString("phone" ,"Chưa có phone" ));


        // chuyen sang trang edit myacount
        btnEditProfile.setOnClickListener(v->{



            Intent i = new Intent(MyAccountActivity.this, ChangeInfoActivity.class);
            startActivity(i);

        });

        // chuyen sang trang changePassWord
        btnChangePassword.setOnClickListener(v->{


            Intent i = new Intent(MyAccountActivity.this, ChangePassActivity.class);
            startActivity(i);
        });

        btnLogout.setOnClickListener(v->{
            showLogoutConfirmation();
        });
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> logout())
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Xóa thông tin đăng nhập
        editor.apply();

        // Quay lại màn hình đăng nhập
        Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}