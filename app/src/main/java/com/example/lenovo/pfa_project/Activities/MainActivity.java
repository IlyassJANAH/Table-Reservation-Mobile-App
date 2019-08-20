package com.example.lenovo.pfa_project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.pfa_project.Adapters.MyAdapterRestaurant;
import com.example.lenovo.pfa_project.Models.Restaurant;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinkedList<Restaurant> restaurants;
    private FirebaseFirestore db;
    ProgressBar Pbar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_reservations:
                    Intent a = new Intent(MainActivity.this,ReservationsActivity.class);
                    startActivity(a);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_profile:
                    Intent b = new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(b);
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Pbar = (ProgressBar)findViewById(R.id.progressBar1);
        Pbar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.RestaurantHomeRecycler);
        restaurants = new LinkedList<Restaurant>();

        db = FirebaseFirestore.getInstance();
        System.out.println("******************begin get **********************************************************");

        getRestaurants();// recyclerview must be initialized in this method because fireStore is asynchronous


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }



    private void getRestaurants() {

        //connexion avec FireBase
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println("***********************Transaction successful####################################################");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Restaurant r= new Restaurant(document.getId(), document.get("Nom").toString(),document.get("Ville").toString(),document.get("TypeCuisine").toString(),
                                        document.get("Description").toString(),document.get("Image").toString(),document.get("Adresse").toString());
                                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+r);

                                restaurants.add(r);
                                System.out.println(restaurants);

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            System.out.println("######################error###################################### ");

                        }
                        //hideProgressDialog();

                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView

                        recyclerView.setHasFixedSize(true);

                        layoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
                        recyclerView.setLayoutManager(layoutManager);
                        // specify an adapter (see also next example)
                        mAdapter = new MyAdapterRestaurant(restaurants, MainActivity.this);

                        recyclerView.setAdapter(mAdapter);
                        Pbar.setVisibility(View.GONE);
                        findViewById(R.id.sc).setVisibility(View.VISIBLE);

                    }
                });

    }



}
