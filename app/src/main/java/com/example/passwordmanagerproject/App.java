package com.example.passwordmanagerproject;


import android.app.Application;

import com.parse.Parse;


public class App extends Application{
    public void onCreate(){
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("IUTjdP5WkZDI9r6h2eV2gqaivFl1pG56n7rWrh1o")
                .clientKey("sz6CZAQT4x2Fwe5cDQUnU1A7p5l2Hv4cWNGzkCnW")
                .server("https://parseapi.back4app.com/")
                .build());
    }
}



