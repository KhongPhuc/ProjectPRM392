package com.example.projectprm392.java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        txtName.setText("Khong Manh Phuc");
        txtEmail.setText("phuc@gmail.com");
        txtPhone.setText("0987654321");


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





    }
}