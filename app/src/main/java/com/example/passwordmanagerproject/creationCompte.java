package com.example.passwordmanagerproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class creationCompte extends AppCompatActivity {
    public static final int RESULT_CODE_CANCEL = 2;
    public static final int RESULT_OK = 3;
    EditText UserName;
    EditText UserPass;
    EditText Email;
    Button Sauvegarder;
    ImageButton retour;

    private static final String TAG = "creationCompte"; // Corrected typo in the tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);

        // Initialize views here
        UserName = findViewById(R.id.UserName);
        UserPass = findViewById(R.id.UserPass);
        Email = findViewById(R.id.email);
        Sauvegarder = findViewById(R.id.Sauvegarder);
        retour = findViewById(R.id.retour);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CODE_CANCEL);
                finish();
            }
        });

        Sauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (champsSontValides()) {
                    /*creation de user */
                    ParseUser user = new ParseUser();
                    user.setUsername(UserName.getText().toString());
                    user.setPassword(UserPass.getText().toString());
                    user.put("Email", Email.getText().toString());
                    user.signUpInBackground(e ->  {
                            if (e == null) {
                                Log.d(TAG, "User account created successfully");
                            } else {
                                Log.e(TAG, "Error when creating user account");
                            }

                    });

                    /*creation de user */
                    Intent i = new Intent();
                    String nom = UserName.getText().toString();
                    i.putExtra("nom", nom);
                    setResult(RESULT_OK, i);
                    finish();
                } else {
                    Toast.makeText(creationCompte.this, "Vous devez remplir tous les champs !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean champsSontValides() {
        /* Vérifiez si les champs requis sont remplis*/
        String nom = UserName.getText().toString().trim();
        String email = UserPass.getText().toString().trim();
        String phone = Email.getText().toString().trim();

        return !TextUtils.isEmpty(nom) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone);
    }
}
