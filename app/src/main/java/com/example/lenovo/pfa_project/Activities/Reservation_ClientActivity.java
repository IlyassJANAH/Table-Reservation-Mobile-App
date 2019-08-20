package com.example.lenovo.pfa_project.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Reservation_ClientActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_list_item);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        TextView nom=findViewById(R.id.restaurant_name_resact);
        nom.setText(intent.getStringExtra("nom"));


        TextView date=findViewById(R.id.date_resact);
        date.setText(intent.getStringExtra("date"));
        TextView heure=findViewById(R.id.time_resact);
        heure.setText(intent.getStringExtra("heure"));

        TextView nb=findViewById(R.id.peep_count_resact);
        nb.setText(intent.getStringExtra("nb"));
findViewById(R.id.delet).setOnClickListener(this);
        db.collection("reservations").document(intent.getStringExtra("idRes"))
                .delete();



    }

    @Override
    public void onClick(View v) {

        Intent in= new Intent(Reservation_ClientActivity.this, ReservationsActivity.class);



        startActivity(in);

    }
}
