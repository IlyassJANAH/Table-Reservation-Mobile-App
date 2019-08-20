package com.example.lenovo.pfa_project.Activities;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.pfa_project.Adapters.MyAdapterMesReservations;
import com.example.lenovo.pfa_project.Adapters.MyAdapterRestaurant;
import com.example.lenovo.pfa_project.Models.Reservation;
import com.example.lenovo.pfa_project.Models.Restaurant;
import com.example.lenovo.pfa_project.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class ReservationsActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static final String TAG = "ReservationsActivity";

    public static final String PREFS_NAME = "Client_Settings";
    private String idClient,img="null",NomRest="null";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinkedList<Reservation> mesReservations;
    private FirebaseFirestore db;
    private ShimmerFrameLayout mShimmerViewContainer;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent a = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(a);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_reservations:

                    return true;
                case R.id.navigation_profile:
                    Intent b = new Intent(getBaseContext(),ProfileActivity.class);
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
        setContentView(R.layout.activity_reservations);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);





        // Reading from SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        idClient = settings.getString("idClient", "");
        System.out.println("********* Reservations du client : "+idClient);

        recyclerView = findViewById(R.id.mes_res_recyler);
        mesReservations = new LinkedList<Reservation>();
        db = FirebaseFirestore.getInstance();

        getReservations();


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_reservations);


    }


    private void getReservations() {

        db.collection("reservations")
                .whereEqualTo("idClient", idClient)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //recuperer img

                                Reservation r= new Reservation(document.getId(),
                                        document.get("idRestaurant").toString(),
                                        document.get("idClient").toString(),
                                        document.get("nbPersonnes").toString(),
                                        document.get("dateReserv").toString(),
                                        document.get("heureReserv").toString(),
                                        document.get("numPhone").toString(),
                                        document.get("etatReservation").toString());
                                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+r);

                                mesReservations.add(r);
                                System.out.println(mesReservations);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        System.out.println("***********conf adapter1");
                        recyclerView.setHasFixedSize(true);
                        System.out.println("***********conf adapter2");

                        layoutManager = new LinearLayoutManager(ReservationsActivity.this);
                        System.out.println("***********conf adapter3**********");

                        recyclerView.setLayoutManager(layoutManager);
                        // specify an adapter (see also next example)
                        System.out.println("***********conf adapter4**********");

                        mAdapter = new MyAdapterMesReservations(mesReservations, ReservationsActivity.this);
                        System.out.println("***********conf adapter5**********");

                        recyclerView.setAdapter(mAdapter);
                        System.out.println("***********conf adapter6**********");
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                });


    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

}
