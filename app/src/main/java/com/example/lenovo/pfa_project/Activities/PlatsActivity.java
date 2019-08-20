package com.example.lenovo.pfa_project.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.lenovo.pfa_project.Models.Plats;
import com.example.lenovo.pfa_project.Models.Reservation;
import com.example.lenovo.pfa_project.R;

import java.util.LinkedList;

public class PlatsActivity extends AppCompatActivity {

    private LinkedList<Plats> plats;
    private Plats p;
    private ProgressBar Pbar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_reservations_gerant:
                    Intent a = new Intent(PlatsActivity.this,MainGerantActivity.class);
                    startActivity(a);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_plats:

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plats_2);
        Pbar = (ProgressBar)findViewById(R.id.bar_plats);
        Pbar.setVisibility(View.VISIBLE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_gerant);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_plats);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Pbar.setVisibility(View.GONE);
                findViewById(R.id.sc_plats).setVisibility(View.VISIBLE);
            }
        }, 3000);


    }
}
