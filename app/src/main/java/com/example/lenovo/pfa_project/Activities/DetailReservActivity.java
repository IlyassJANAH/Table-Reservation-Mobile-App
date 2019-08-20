package com.example.lenovo.pfa_project.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.pfa_project.Adapters.ReservationsAdapter;
import com.example.lenovo.pfa_project.Models.Reservation;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailReservActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore db;
    TextView topResId;
    TextView mainResId;
    TextView nameTextView;
    TextView emailTextView;
    TextView phoneTextView;
    TextView dateTextView;
    TextView timeTextView;
    TextView peepCountTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        topResId = findViewById(R.id.res_id_top_rec);
        mainResId = findViewById(R.id.reservation_id_textview);
        nameTextView = findViewById(R.id.your_name_rec);
        emailTextView = findViewById(R.id.your_email_rec);
        phoneTextView = findViewById(R.id.your_phone_rec);
        dateTextView = findViewById(R.id.res_date_rec);
        timeTextView = findViewById(R.id.res_time_rec);
        peepCountTextView = findViewById(R.id.res_people_rec);

        ImageView closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailReservActivity.super.onBackPressed();
            }
        });

        db = FirebaseFirestore.getInstance();
        getReservations();

    }


    private void getReservations() {

        DocumentReference docRef = db.collection("reservations")
                .document(getIntent().getStringExtra("idReservation"));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                        topResId.setText(document.getId());
                        mainResId.setText(document.getId());
                        //nameTextView.setText(currentReservation.getName());
                        //emailTextView.setText(currentReservation.getEmail());
                        //phoneTextView.setText(currentReservation.getPhone());
                        dateTextView.setText(document.getString("dateReserv"));
                        timeTextView.setText(document.getString("heureReserv"));
                        String nb=document.getString("nbPersonnes")+" personne(s)";
                        peepCountTextView.setText(nb);
                        phoneTextView.setText(document.getString("numPhone"));


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }


            }
        });

    }

}
