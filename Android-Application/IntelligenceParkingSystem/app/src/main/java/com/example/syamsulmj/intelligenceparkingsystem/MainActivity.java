package com.example.syamsulmj.intelligenceparkingsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.design.widget.NavigationView.*;

public class MainActivity extends AppCompatActivity {
    private Session session;
    DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    TextView pName, pEmail;
    CircleImageView pPicture;
    dbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check either user already login or not
        session = new Session(this);
        if(!session.loggedIn()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        //Toast.makeText(MainActivity.this, helper.getLedColor("1-1"), Toast.LENGTH_SHORT).show();


        // To make starting fragment opened
        Fragment startFragment = null;
        Class startFragmentClass = MainFragment.class;
        try {
            startFragment =(Fragment) startFragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager1st = getSupportFragmentManager();
        fragmentManager1st.beginTransaction().replace(R.id.flcontent, startFragment).commit();

        // All declaration of the component that are being used
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout =  navigationView.inflateHeaderView(R.layout.header);
        pPicture = (CircleImageView) headerLayout.findViewById(R.id.profile_picture);
        pName = (TextView) headerLayout.findViewById(R.id.profile_name);
        pEmail = (TextView) headerLayout.findViewById(R.id.profile_email);

        pName.setText(session.getSessionData("name"));
        pEmail.setText(session.getSessionData("email"));

        if(session.getSessionData("email").equals("syamsulmj94@gmail.com")) {
            pPicture.setImageResource(R.drawable.sam);
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem item){
        Fragment myFragment = null;
        Class fragmentClass;
        Boolean check;

        switch (item.getItemId()){
            case R.id.home:
                fragmentClass = MainFragment.class;
                check = true;
                break;
            case R.id.profile:
                fragmentClass = ProfileFragment.class;
                check = true;
                break;
            case R.id.balance:
                fragmentClass = BalanceFragment.class;
                check = true;
                break;
            case R.id.customerSupport:
                fragmentClass = CustomerSupportFragment.class;
                check = true;
                break;
            case R.id.logout:
                session.logOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                fragmentClass = null;
                check = false;
                break;
            default:
                fragmentClass = MainFragment.class;
                check = true;
                break;
        }
        try {
            if(check){
                myFragment =(Fragment) fragmentClass.newInstance();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(check){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
            item.setCheckable(true);
            if(fragmentClass == MainFragment.class){
                setTitle(R.string.app_name);
            }
            else {
                setTitle(item.getTitle());
            }
            drawer.closeDrawers();
        }
    }

    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }
}
