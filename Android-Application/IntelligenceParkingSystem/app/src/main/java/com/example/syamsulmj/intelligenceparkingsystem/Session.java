package com.example.syamsulmj.intelligenceparkingsystem;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by syamsulmj on 03/11/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("com.example.syamsulmj.intelligenceparkingsystem", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedIn(boolean loggedIn, String name, String email, String username, String password) {
        editor.putBoolean("loggedInmode", loggedIn);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public void logOut(){
        editor.clear();
        editor.commit();
    }

    public boolean loggedIn() {
        return prefs.getBoolean("loggedInmode", false);
    }

    public String getSessionData(String choice) {

        switch (choice){
            case "username":
                return prefs.getString("username", null);
            case "password":
                return prefs.getString("password", null);
            case "name":
                return prefs.getString("name", null);
            case "email":
                return prefs.getString("email", null);
            default:
                return null;
        }
    }

}
