package com.example.passwordmanagerproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class PasswordInterface extends AppCompatActivity {

    private ArrayList<String> NameAppli; // Déplacez la déclaration de la liste à un niveau supérieur pour la rendre accessible partout.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_interface);


        Button dec = findViewById(R.id.dec);
        Button ajout = findViewById(R.id.ajout);
        Intent i = getIntent();
        String name = i.getStringExtra("name");

        ListView l = findViewById(R.id.list);
        NameAppli = new ArrayList<String>();
        NameAppli.add("nom application"); // Ajoutez le nom par défaut ici
        ArrayAdapter<String> adt = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, NameAppli);
        l.setAdapter(adt);


        /* recuperer automatiquement lorsque l app est ouvert  et afficher dans la liste*/

        ParseQuery<ParseObject> query = ParseQuery.getQuery(name);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    NameAppli.clear();
                    for (ParseObject parseObject : objects) {
                        // Récupérer la valeur de la colonne "NomApp"
                        String nom = parseObject.getString("NomAPP");
                        // Faire quelque chose avec la valeur
                        Log.d("NomAPP", nom);
                        /* recupere from back*/

                        NameAppli.add(nom);
                        adt.notifyDataSetChanged();
                    }
                } else {
                    e.printStackTrace();
                }
                /* recuperer automatiquement lorsque l app est ouvert  et afficher dans la liste*/





                dec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(PasswordInterface.this);
                        alert.setTitle("déconnexion");
                        alert.setMessage("Voulez-vous vraiment vous déconnecter?")
                                .setCancelable(false)
                                .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("Déconnecter", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(PasswordInterface.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    }
                });

                ajout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert2 = new AlertDialog.Builder(PasswordInterface.this);
                        alert2.setTitle("Ajouter nouvelle Mot de Passe");
                        LayoutInflater inflater = LayoutInflater.from(PasswordInterface.this);
                        View view = inflater.inflate(R.layout.dialog_alert,null);
                        alert2.setView(view);

                        final EditText app = view.findViewById(R.id.app);

                        final EditText pass = view.findViewById(R.id.pass);

                        alert2.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alert2.setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nom = app.getText().toString();
                                String passw = pass.getText().toString();






















                                // Création d'un objet de données
                                ParseObject parseObject = new ParseObject(name);
                                parseObject.put("NomAPP", nom);
                                parseObject.put("Mot_de_passe", passw);

                                /* Associer l'objet à l'utilisateur*/
                                parseObject.put("Utilisateur", ParseUser.getCurrentUser());

                                // Enregistrement de l'objet dans le backend
                                parseObject.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {


                                            /* recuperer donne from back*/
                                            ParseQuery<ParseObject> query = ParseQuery.getQuery(name);
                                            query.findInBackground(new FindCallback<ParseObject>() {
                                                @Override
                                                public void done(List<ParseObject> objects, ParseException e) {
                                                    if (e == null) {
                                                        NameAppli.clear();
                                                        for (ParseObject parseObject : objects) {
                                                            // Récupérer la valeur de la colonne "NomApp"
                                                            String nom = parseObject.getString("NomAPP");
                                                            // Faire quelque chose avec la valeur
                                                            Log.d("NomAPP", nom);
                                                            /* recupere from back*/

                                                            NameAppli.add(nom);
                                                            adt.notifyDataSetChanged(); // Mettez à jour l'adaptateur
                                                        }
                                                    } else {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });





                                        } else {
                                        }
                                    }
                                });
                            }
                        });

                        AlertDialog dialog = alert2.create();
                        dialog.show();
                    }
                });
            }
        });
        /* afficher le mot de passe de l app selectionne */
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAppName = NameAppli.get(position);

                AlertDialog.Builder alert3 = new AlertDialog.Builder(PasswordInterface.this);
                alert3.setTitle("Mot de Passe");
                LayoutInflater inflater = LayoutInflater.from(PasswordInterface.this);
                View view3 = inflater.inflate(R.layout.app_mdp, null);
                alert3.setView(view3);
                TextView t = view3.findViewById(R.id.p);
                Button Modifier = view3.findViewById(R.id.Modifier);
                Button Supprimer = view3.findViewById(R.id.Supprimer);

                // Récupérer le mot de passe associé au nom d'application sélectionné
                ParseQuery<ParseObject> query = ParseQuery.getQuery(name);
                query.whereEqualTo("NomAPP", selectedAppName); // Filtrer par le nom d'application sélectionné
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null && objects.size() > 0) {
                            ParseObject selectedObject = objects.get(0);
                            String selectedPassword = selectedObject.getString("Mot_de_passe");
                            t.setText(selectedPassword);
                        } else {
                            // La requête a échoué ou aucun objet trouvé
                            e.printStackTrace();
                        }
                    }
                });


                AlertDialog dialog = alert3.create();
                dialog.show();

                /* Modifier item selectionne*/
                Modifier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(PasswordInterface.this,ModificationPassWord.class);
                        i.putExtra("Classname",name);
                        i.putExtra("selectionné",selectedAppName);
                        startActivity(i);

                    }
                });
                /* Modifier item selectionne*/






                /*supprimer item selectionné*/

                Supprimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery(name);
                        query.whereEqualTo("NomAPP", selectedAppName); // Filtrer par le nom d'application sélectionné
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null && objects.size() > 0) {
                                    ParseObject selectedObject = objects.get(0);

                                    // Supprimer l'objet de Back4App
                                    selectedObject.deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Toast.makeText(PasswordInterface.this, "APP bien supprimer ", Toast.LENGTH_SHORT).show();
                                                recreate();

                                            } else {
                                                // La suppression a échoué
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });





            }
        });






    }}