/* too bee continuuue */




package com.example.passwordmanagerproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class Reset extends AppCompatActivity {

    EditText email;
    Button envoyer;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        // Initialize your variables after setContentView
        email = findViewById(R.id.champEmail);
        envoyer = findViewById(R.id.b2);

        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the email from the EditText
                String userEmail = email.getText().toString().trim();

                // Request password reset
                ParseUser.requestPasswordResetInBackground(userEmail, e -> {
                    if (e == null) {
                        // Password reset successful
                        Toast.makeText(Reset.this, "Password reset success", Toast.LENGTH_SHORT).show();
                    } else {
                        // Password reset failed, show error message
                        Toast.makeText(Reset.this, "Password reset failure: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
