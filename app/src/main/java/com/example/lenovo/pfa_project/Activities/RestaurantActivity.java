package com.example.lenovo.pfa_project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.lenovo.pfa_project.Adapters.SectionsPageAdapter;
import com.example.lenovo.pfa_project.R;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantActivity";
    private String idRestaurant;



    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Dar naji");


        //###### Get id du MainActivity ######
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idRestaurant = extras.getString("idRes");
            System.out.println("*****************************************************"+idRestaurant);
            //The key argument here must match that used in the other activity
        }
        Bundle bundle = new Bundle();
        bundle.putString("idRes", idRestaurant);
        Intent i = new Intent(RestaurantActivity.this, TabResFragment.class);
        i.putExtra("idRes",bundle);

        Bundle bund = new Bundle();
        bundle.putString("idRes", idRestaurant);
        Intent in = new Intent(RestaurantActivity.this, TabMenuFragment.class);
        in.putExtra("idRes",bund);








        //##### fin get id #######

        //******** get restaurants du bd && affichage ***********
        //getDetails();
        //*************************
        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nest_scrollview);
        scrollView.setFillViewport (true);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabResFragment(), "Réserver");
        adapter.addFragment(new TabMenuFragment(), "Menu");
        viewPager.setAdapter(adapter);
    }

}
