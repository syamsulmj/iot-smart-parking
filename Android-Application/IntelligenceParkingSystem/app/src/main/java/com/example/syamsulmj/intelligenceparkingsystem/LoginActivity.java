package com.example.syamsulmj.intelligenceparkingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login, register;
    EditText username, password;
    ProgressBar progressBar;
    private Session session;
    static String CHECK_LOGIN_URL = "http://ipscapstone.tech/check_login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(this);

        if(session.loggedIn()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        login = (Button)findViewById(R.id.loginBtn);
        register = (Button)findViewById(R.id.regBtn);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginCheck();
                }
                progressBar.setVisibility(View.GONE);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void loginCheck(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);

                    if(json.getString("check").equals("201")){
                        Toast.makeText(getApplication(), "Successfully login.", Toast.LENGTH_SHORT).show();
                        session.setLoggedIn(true, json.getString("name"), json.getString("email"), json.getString("username"), json.getString("password"));
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplication(), "Wrong username or password. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
