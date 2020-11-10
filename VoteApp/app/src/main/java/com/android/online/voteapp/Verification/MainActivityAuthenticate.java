package com.android.online.voteapp.Verification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.online.voteapp.R;
import com.android.online.voteapp.Session.Prevalent;
import com.android.online.voteapp.navhome;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityAuthenticate extends AppCompatActivity {

    TextInputEditText editTextCountryCode, editTextPhone;
    AppCompatButton buttonContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCountryCode = findViewById(R.id.editTextCountryCode);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPhone.setEnabled(false);
        editTextCountryCode.setEnabled(false);
        editTextPhone.setText(Prevalent.currentOnlineUser.getPhone());
        buttonContinue = findViewById(R.id.buttonContinue);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCountryCode.getText().toString().trim();
                String number = editTextPhone.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editTextPhone.setError("Valid number is required");
                    editTextPhone.requestFocus();
                    return;
                }

                String phoneNumber = code + number;

                Intent intent = new Intent(MainActivityAuthenticate.this, VerifyPhoneActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);

            }
        });
    }


}
