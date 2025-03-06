package com.example.projectprm392.java;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.R;

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

        kq = findViewById(R.id.text_mess);

        btnRegister_signup = findViewById(R.id.btnRegister_signup);


        btnRegister_signup.setOnClickListener(v->{
            insertUser();
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
                .baseUrl("http://192.168.1.6/0api8/register.php/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);

        Call<UserResponse> call = interfaceUser.register(u.getUsername()
                ,u.getPassword(), u.getName(), u.getEmail(), u.getPhone(),u.getPassword());

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

//    private boolean validateInput() {
//        String email = ema.getText().toString().trim();
//        String password = passwordEditText.getText().toString().trim();
//        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
//        String phone = phoneEditText.getText().toString().trim();
//
//        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            emailEditText.setError("Email không hợp lệ");
//            return false;
//        }
//
//        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*+=?-].*")) {
//            passwordEditText.setError("Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, số và ký tự đặc biệt");
//            return false;
//        }
//
//        if (!password.equals(confirmPassword)) {
//            confirmPasswordEditText.setError("Mật khẩu xác nhận không khớp");
//            return false;
//        }
//
//        if (!phone.matches("\\d{10,11}")) {
//            phoneEditText.setError("Số điện thoại không hợp lệ (10-11 số)");
//            return false;
//        }
//
//        return true;
//    }


}