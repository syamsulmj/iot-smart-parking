package com.example.syamsulmj.intelligenceparkingsystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    TextView profileName, profileEmail, profileUsername;
    CircleImageView profileImage;
    Session session;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        session = new Session(getActivity());
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileName = view.findViewById(R.id.profile_name);
        profileEmail = view.findViewById(R.id.profile_email);
        profileUsername = view.findViewById(R.id.profile_username);
        profileImage = view.findViewById(R.id.profile_picture);

        if(session.getSessionData("email").equals("syamsulmj94@gmail.com")) {
            profileImage.setImageResource(R.drawable.sam);
        }

        profileName.setText("Name: " + session.getSessionData("name"));
        profileEmail.setText("Email: " + session.getSessionData("email"));
        profileUsername.setText("Username: " + session.getSessionData("username"));



        // Inflate the layout for this fragment
        return view;
    }

}
