package com.example.syamsulmj.intelligenceparkingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class BalanceFragment extends Fragment {
    TextView balance;
    Button topupBtn;
    String GET_BALANCE_URL = "http://ipscapstone.tech/get_balance.php";
    String TOPUP_BALANCE_URL = "http://ipscapstone.tech/add_balance.php";
    Session session;

    public BalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        topupBtn = view.findViewById(R.id.topupBtn);
        balance = view.findViewById(R.id.balance);
        session = new Session(getActivity());

        updateBalance(session.getSessionData("email"));

        topupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonInteraction(v, inflater, container);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void buttonInteraction(View v, LayoutInflater inflater, ViewGroup container){

        View mView = inflater.inflate(R.layout.topup_edittext, container, false);
        final EditText et = mView.findViewById(R.id.topupInput);

        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Topup Confirmation")
                .setMessage("How much do you want to topup?")
                .setView(mView)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        topupBalance(session.getSessionData("email"), et.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public void updateBalance(final String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BALANCE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    if(json.getString("response").equals("success")) {

                        balance.setText(json.getString("balance"));
                    }

                    else if(json.getString("response").equals("failed")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "json exception", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error+"  error listener", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void topupBalance(final String email, final String topup) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TOPUP_BALANCE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    if(json.getString("response").equals("success")) {

                        Uri uri = Uri.parse("https://www.maybank2u.com.my/mbb/m2u/common/mbbPortalAccess.do?action=Login#1");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                        updateBalance(email);
                        Toast.makeText(getActivity().getApplicationContext(), "Successfully topup new balance!", Toast.LENGTH_SHORT).show();
                    }

                    else if(json.getString("response").equals("failed")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "json exception", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error+"  error listener", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("topup", topup);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
