package com.example.projectprm392.java.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Entities.User;
import com.example.projectprm392.java.Interfaces.InterfaceUser;
import com.example.projectprm392.java.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeInfoActivity extends AppCompatActivity {


    EditText edtFullName, edtEmail, edtPhone, edtAddress;

    Button btnSaveProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "userID");

        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        btnSaveProfile.setOnClickListener(v->{
            if(validateInput()){
                updateUser(userName);
            }
        });




    }

    public void updateUser(String username){

        edtFullName = findViewById(R.id.edtFullName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4/0api8/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);

        Call<UserResponse> call = interfaceUser.updateUser(username,edtFullName.getText().toString(),
                edtEmail.getText().toString(), edtPhone.getText().toString(), edtAddress.getText().toString());

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //thanh cong
                UserResponse userResponse = response.body();
                Intent intent = new Intent(ChangeInfoActivity.this, HomePage.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                //that bai

            }
        });
    }

    private boolean validateInput() {
        String email1 = edtEmail.getText().toString().trim();
        String phone1 = edtPhone.getText().toString().trim();
        String address1 = edtAddress.getText().toString().trim();
        String fullname = edtFullName.getText().toString().trim();



        if (email1.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            edtEmail.setError("Invalid email");
            return false;
        }
        if (address1.isEmpty() ) {
            edtAddress.setError("Invalid address");
            return false;
        }
        if (fullname.isEmpty() ) {
            edtFullName.setError("Fullname is invalid");
            return false;
        }
        if (!phone1.trim().matches("^0[0-9]{9,10}$")) {
            edtPhone.setError("Phone number must start with 0 and have 10-11 digits");
            return false;
        }


        return true;
    }
}