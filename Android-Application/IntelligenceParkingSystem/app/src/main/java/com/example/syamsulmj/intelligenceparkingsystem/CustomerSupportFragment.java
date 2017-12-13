package com.example.syamsulmj.intelligenceparkingsystem;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerSupportFragment extends Fragment {
    Button dialUpBtn;

    public CustomerSupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:123456789"));
//        startActivity(callIntent);

        View view = inflater.inflate(R.layout.fragment_customer_support, container, false);
        dialUpBtn = view.findViewById(R.id.dialUpBtn);

        dialUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:1300882525"));
                startActivity(callIntent);
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

}
