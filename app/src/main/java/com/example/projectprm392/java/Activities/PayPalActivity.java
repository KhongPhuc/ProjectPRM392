package com.example.projectprm392.java.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class PayPalActivity extends AppCompatActivity {
    EditText edtAmount;
    Button btnPay;

    String clientId = "AS2rcTBksQX9dJrezRkNRXoQyBwXN5xUpUUpEzq-Vs_8hHxk-caoqvs5DKiss8yhf5FSJxRoqvOFT7_F";

    int PAYPAY_REQUEST_CODE = 123;

    public  static PayPalConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay_pal);

        edtAmount = findViewById(R.id.edtAmount);
        btnPay = findViewById(R.id.btnPay);

        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayment();
            }
        });
    }

    private void getPayment() {

        String amounts = edtAmount.getText().toString();
        PayPalPayment palPayment = new PayPalPayment(new BigDecimal(String.valueOf(amounts)),
                "USD", "TVStore", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,palPayment);

        startActivityForResult(intent, PAYPAY_REQUEST_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PAYPAY_REQUEST_CODE){
            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if(paymentConfirmation != null){

                try {

                    String paymentDetail = paymentConfirmation.toJSONObject().toString();

                    JSONObject object = new JSONObject(paymentDetail);


                } catch (JSONException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }

            } else if (requestCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

            } else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                Toast.makeText(this, "RESULT_EXTRAS_INVALID", Toast.LENGTH_SHORT).show();


            }

        }
    }
}