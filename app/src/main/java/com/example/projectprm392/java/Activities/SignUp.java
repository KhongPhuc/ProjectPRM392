package com.example.projectprm392.java.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Entities.User;
import com.example.projectprm392.java.Interfaces.InterfaceUser;
import com.example.projectprm392.java.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    String result;
    User user;
    EditText name, username, password, phone, address, repassword, email;
    Button btnRegister_signup;

    TextView kq;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name_signup);
        username = findViewById(R.id.username_signup);
        password = findViewById(R.id.password_signup);
        email = findViewById(R.id.email_signup);
        phone = findViewById(R.id.phone_signup);
        address = findViewById(R.id.address_signup);
        repassword = findViewById(R.id.repassword_signup);
        kq = findViewById(R.id.text_mess);

        btnRegister_signup = findViewById(R.id.btnRegister_signup);


        btnRegister_signup.setOnClickListener(v -> {
            if (validateInput()) {
                insertUser();
            }
        });


    }



    public void insertUser(){

        User u = new User();
        u.setUsername(username.getText().toString());
        u.setPassword(password.getText().toString());
        u.setName(name.getText().toString());
        u.setEmail(email.getText().toString());
        u.setPhone(phone.getText().toString());
        u.setAddress(address.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4/0api8/register.php/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);

        Call<UserResponse> call = interfaceUser.register(u.getUsername()
                ,u.getPassword(), u.getName(), u.getEmail(), u.getPhone(),u.getAddress());

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //thanh cong

                UserResponse userResponse = response.body();
                kq.setText(userResponse.getMessage());

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                //that bai
                kq.setText(t.getMessage());

            }
        });
    }

    private boolean validateInput() {
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        String confirmPassword = repassword.getText().toString().trim();
        String phone1 = phone.getText().toString().trim();
        String address1 = address.getText().toString().trim();
        String username1 = username.getText().toString().trim();
        String fullname = name.getText().toString().trim();


        if (email1.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Email Invalid ");
            return false;
        }

        if (address1.isEmpty() ) {
            address.setError("Address Invalid ");
            return false;
        }
        if (username1.isEmpty() ) {
            username.setError("UserName Invalid ");
            return false;
        }
        if (fullname.isEmpty() ) {
            name.setError("Fullname Invalid ");
            return false;
        }

        if (password1.length() < 8 || !password1.matches(".*[A-Z].*") || !password1.matches(".*\\d.*") || !password1.matches(".*[!@#$%^&*+=?-].*")) {
            password.setError("Password must be at least 8 characters, including uppercase letters, numbers and special characters");
            return false;
        }

        if (!password1.equals(confirmPassword)) {
            repassword.setError("Confirmation password does not match");
            return false;
        }

        if (!phone1.trim().matches("^0[0-9]{9,10}$")) {
            phone.setError("Phone number must start with 0 and have 10-11 digits");
            return false;
        }


        return true;
    }


}