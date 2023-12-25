package com.example.passwordmanagerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ModificationPassWord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_pass_word);
        Intent i = getIntent();
        String name = i.getStringExtra("Classname");
        String selected = i.getStringExtra("selectionné");
        EditText nouveau = findViewById(R.id.nouveau);
        Button valider = findViewById(R.id.Valider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = nouveau.getText().toString();

                ParseQuery<ParseObject> query = ParseQuery.getQuery(name);
                query.whereEqualTo("NomAPP", selected); // Filtrer par le nom d'application sélectionné
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null && objects.size() > 0) {
                            ParseObject selectedObject = objects.get(0);
                            selectedObject.put("Mot_de_passe", newPass);
                            selectedObject.saveInBackground();
                            Toast.makeText(ModificationPassWord.this, "votre mot de passe est bien  changé", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(ModificationPassWord.this,PasswordInterface.class);
                            startActivity(i);





                        } else {
                            // La requête a échoué ou aucun objet trouvé
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}

