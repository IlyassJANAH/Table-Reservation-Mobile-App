package com.example.lenovo.pfa_project.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lenovo.pfa_project.Adapters.MyAdapterMesReservations;
import com.example.lenovo.pfa_project.Adapters.MyAdapterRestaurant;
import com.example.lenovo.pfa_project.Adapters.ReservationsAdapter;
import com.example.lenovo.pfa_project.Models.Reservation;
import com.example.lenovo.pfa_project.Models.Restaurant;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class MainGerantActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinkedList<Reservation> mesReservations;
    private FirebaseFirestore db;
    private static final String TAG = "MainActivity";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_reservations_gerant:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_plats:
                    Intent a = new Intent(MainGerantActivity.this,PlatsActivity.class);
                    startActivity(a);
                    overridePendingTransition(0, 0);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Réservations");

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.reservation_recyclerview);
        mesReservations = new LinkedList<Reservation>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        System.out.println("******************begin get **************************************");
        getReservations();

        setupFirebaseListener();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_gerant);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_reservations_gerant);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gerant_menu, menu);
        return true;
    }

    private void getReservations() {

        db.collection("reservations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //recuperer img

                                Reservation r= new Reservation( document.getId(),
                                        document.get("idRestaurant").toString(),
                                        document.get("idClient").toString(),
                                        document.get("nbPersonnes").toString(),
                                        document.get("dateReserv").toString(),
                                        document.get("heureReserv").toString(),
                                        document.get("numPhone").toString(),
                                        document.get("etatReservation").toString());
                                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+r.getEtatReservation());

                                mesReservations.add(r);
                                System.out.println(mesReservations);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        System.out.println("***********conf adapter1");
                        recyclerView.setHasFixedSize(true);
                        System.out.println("***********conf adapter2");

                        layoutManager = new LinearLayoutManager(MainGerantActivity.this);
                        System.out.println("***********conf adapter3**********");

                        recyclerView.setLayoutManager(layoutManager);
                        // specify an adapter (see also next example)
                        System.out.println("***********conf adapter4**********");

                mAdapter = new ReservationsAdapter(mesReservations, MainGerantActivity.this);
                        System.out.println("***********conf adapter5**********");

                        recyclerView.setAdapter(mAdapter);
                        System.out.println("***********conf adapter6**********");

                    }
                });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Toast.makeText(MainGerantActivity.this, "logout clicked", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void setupFirebaseListener(){
        Log.d(TAG, "setupFirebaseListener: setting up the auth state listener.");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }else{
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                    Toast.makeText(MainGerantActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainGerantActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

}
