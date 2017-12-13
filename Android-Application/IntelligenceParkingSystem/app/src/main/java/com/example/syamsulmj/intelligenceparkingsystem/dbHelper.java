package com.example.syamsulmj.intelligenceparkingsystem;

import android.content.Context;

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

/**
 * Created by syamsulmj on 14/11/2017.
 */

public abstract class dbHelper extends Context {

    String PARKING_STATUS_URL = "http://intelligenceparkingsystem.000webhostapp.com/parking_status.php";
    String UPDATE_PARKING_URL;

    // Using volley to get the parking status color
    public int getLedColor(final String grid){
        final int[] ledStatus = {0};

        StringRequest stringRequest = new StringRequest(Request.Method.POST, PARKING_STATUS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);

                    switch (json.getString("led_color")){
                        case "green":
                            ledStatus[0] = R.color.green;
                            break;
                        case "yellow":
                            ledStatus[0] = R.color.yellow;
                            break;
                        case "red":
                            ledStatus[0] = R.color.red;
                            break;
                        case "blue":
                            ledStatus[0] = R.color.blue;
                            break;
                        default:
                            ledStatus[0] = 0;
                            break;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    ledStatus[0] = 0;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ledStatus[0] = 0;
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("grid", grid);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        return ledStatus[0];
    }

    public void updateParkingStatus(String available, String booked, String notPaid, String paid){


    }
}
