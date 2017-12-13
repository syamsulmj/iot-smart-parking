package com.example.syamsulmj.intelligenceparkingsystem;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    String PARKING_STATUS_URL = "http://ipscapstone.tech/parking_status.php";
    String CHANGE_PARKING_STATUS_URL = "http://ipscapstone.tech/android_change_parking_status.php";
    int led11, led12, led21, led22;
    Button parking1, parking2, parking3, parking4;
    Handler handler = new Handler();
    Timer timer = new Timer();
    Session session;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize session
        session = new Session(getActivity());

        // Inflate the layout for this fragment
        handler = new Handler();
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        parking1 = view.findViewById(R.id.grid11);
        parking2 = view.findViewById(R.id.grid12);
        parking3 = view.findViewById(R.id.grid21);
        parking4 = view.findViewById(R.id.grid22);

        final Context checkingFragment = getContext();


        getLedColor("1-1", "1-2", "2-1", "2-2");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        if(java.util.Objects.equals(getContext(), checkingFragment)){
                            getLedColor("1-1", "1-2", "2-1", "2-2");
                            //Toast.makeText(getActivity().getApplicationContext(), getContext()+"Updated! boh!!!", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            timer.cancel();
                            //handler.removeCallbacks((Runnable) getActivity().getApplicationContext());
                        }
                    }
                });
            }
        }, 0, 5000);

        //Toast.makeText(getActivity().getApplicationContext(), test1 + " " + test2 + " " + test3 + " " + test4, Toast.LENGTH_SHORT).show();

        parking1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonInteraction("1-1", led11, v, inflater, container);
            }
        });

        parking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonInteraction("1-2", led12, v, inflater, container);
            }
        });

        parking3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonInteraction("2-1", led21, v, inflater, container);
            }
        });

        parking4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonInteraction("2-2", led22, v, inflater, container);
            }
        });

        return view;
    }


    public void buttonInteraction(final String btnGrid, int status, View v, LayoutInflater inflater, ViewGroup container){

        View mView = inflater.inflate(R.layout.hours_edittext, container, false);
        final EditText et = mView.findViewById(R.id.hoursInput);

        if(status == R.drawable.parking_button_red) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Confirmation")
                    .setView(mView)
                    .setMessage("Pay for this parking? (Parking can only be pay in hours increment) (1 hours = RM 0.40)")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changeParkingStatus("blue", btnGrid, et.getText().toString(), "paying");
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
        else if (status == R.drawable.parking_button_green) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Confirmation")
                    .setMessage("Do you wanna book this parking?")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getActivity().getApplicationContext(), et.getText()+" hours", Toast.LENGTH_SHORT).show();
                            changeParkingStatus("yellow", btnGrid, "none", "booking");
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

        else if (status == R.drawable.parking_button_yellow) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Confirmation")
                    .setView(mView)
                    .setMessage("Pay for this booked parking? (This will only works if you're the one who book this parking) (1 hours = RM 0.40)")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changeParkingStatus("blue", btnGrid, et.getText().toString(), "paying_booking");
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
    }


    private void getLedColor(final String grid11, final String grid12, final String grid21, final String grid22){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PARKING_STATUS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    updateLedColor(json.getString("grid11"), json.getString("grid12"), json.getString("grid21"), json.getString("grid22"));

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grid11", grid11);
                params.put("grid12", grid12);
                params.put("grid21", grid21);
                params.put("grid22", grid22);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void updateLedColor(String grid11, String grid12, String grid21, String grid22) {
        switch (grid11){
            case "green":
                led11 = R.drawable.parking_button_green;
                break;
            case "yellow":
                led11 = R.drawable.parking_button_yellow;
                break;
            case "red":
                led11 = R.drawable.parking_button_red;
                break;
            case "blue":
                led11 = R.drawable.parking_button_blue;
                break;
            default:
                led11 = R.drawable.parking_button_corner;
        }
        switch (grid12){
            case "green":
                led12 = R.drawable.parking_button_green;
                break;
            case "yellow":
                led12 = R.drawable.parking_button_yellow;
                break;
            case "red":
                led12 = R.drawable.parking_button_red;
                break;
            case "blue":
                led12 = R.drawable.parking_button_blue;
                break;
            default:
                led12 = R.drawable.parking_button_corner;
        }
        switch (grid21){
            case "green":
                led21 = R.drawable.parking_button_green;
                break;
            case "yellow":
                led21 = R.drawable.parking_button_yellow;
                break;
            case "red":
                led21 = R.drawable.parking_button_red;
                break;
            case "blue":
                led21 = R.drawable.parking_button_blue;
                break;
            default:
                led21 = R.drawable.parking_button_corner;
        }
        switch (grid22){
            case "green":
                led22 = R.drawable.parking_button_green;
                break;
            case "yellow":
                led22 = R.drawable.parking_button_yellow;
                break;
            case "red":
                led22 = R.drawable.parking_button_red;
                break;
            case "blue":
                led22 = R.drawable.parking_button_blue;
                break;
            default:
                led22 = R.drawable.parking_button_corner;
        }
        parking1.setBackgroundResource(led11);
        parking2.setBackgroundResource(led12);
        parking3.setBackgroundResource(led21);
        parking4.setBackgroundResource(led22);
    }

    public void changeParkingStatus(final String change, final String grid, final String hoursParking, final String bookStatus) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHANGE_PARKING_STATUS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    if(json.getString("response").equals("success")) {
                        changeSingleLedColor(grid, change);
                        Toast.makeText(getActivity().getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    else if(json.getString("response").equals("failed")) {
                        Toast.makeText(getActivity().getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Toast.makeText(getActivity().getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("change", change);
                params.put("grid", grid);
                params.put("hoursParking", hoursParking);
                params.put("bookStatus", bookStatus);
                params.put("email", session.getSessionData("email"));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void changeSingleLedColor(String grid, String color) {
        int led;

        switch (color){
            case "green":
                led = R.drawable.parking_button_green;
                break;
            case "yellow":
                led = R.drawable.parking_button_yellow;
                break;
            case "red":
                led = R.drawable.parking_button_red;
                break;
            case "blue":
                led = R.drawable.parking_button_blue;
                break;
            default:
                led = R.drawable.parking_button_corner;
        }

        switch (grid) {
            case "1-1":
                led11 = led;
                parking1.setBackgroundResource(led11);
                break;
            case "1-2":
                led12 = led;
                parking2.setBackgroundResource(led12);
                break;
            case "2-1":
                led21 = led;
                parking3.setBackgroundResource(led21);
                break;
            case "2-2":
                led22 = led;
                parking4.setBackgroundResource(led22);
                break;
            default:
                // do nothing..
        }

    }

}
