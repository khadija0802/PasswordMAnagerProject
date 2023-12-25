package com.example.passwordmanagerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATION_COMPTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nom = findViewById(R.id.nom);
        EditText MotDePasse = findViewById(R.id.MotDePasse);
        Button Connecter = findViewById(R.id.button);
        Button creation = findViewById(R.id.creation);

        creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, creationCompte.class);
                startActivityForResult(i, REQUEST_CODE_CREATION_COMPTE);
            }
        });

        Connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nom.getText().toString();
                String Pass = MotDePasse.getText().toString();
                authentifierUtilisateur(name, Pass);
            }

            private void authentifierUtilisateur(String nom, String Pass) {
                ParseUser.logInInBackground(nom, Pass, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null) {
                            Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                            // Passer à l'activité suivante (par exemple, PassWordInterface)
                            Intent intent = new Intent(MainActivity.this, PasswordInterface.class);
                            intent.putExtra("name",nom);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Échec !! Vérifiez vos coordonnées", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Button resetPass = findViewById(R.id.forget);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Reset.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CREATION_COMPTE) {
            if (resultCode == creationCompte.RESULT_CODE_CANCEL) {
                Toast.makeText(this, "La création de votre compte est annulée !", Toast.LENGTH_SHORT).show();
            } else if (resultCode == creationCompte.RESULT_OK && data != null) {
                String nomUser = data.getStringExtra("nom");
                Toast.makeText(this, "Merci " + nomUser + " votre compte a été bien créé", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
