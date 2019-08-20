package com.example.lenovo.pfa_project.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.lenovo.pfa_project.Models.Reservation;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReserverActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Add reserv";
    public static final String PREFS_NAME = "Client_Settings";


    private String hour,date,nb,idClient,idRestaurant,Num_Phone;
    private TextView Txtnb,Txtheure,Txtdate,TxtPhone;
    private Button btnReserv;

    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserver);


        db = FirebaseFirestore.getInstance();

        hour = getIntent().getStringExtra("heure");
        nb = getIntent().getStringExtra("nombre");
        date = getIntent().getStringExtra("date");

        idRestaurant= getIntent().getStringExtra("idRest");

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        idClient = settings.getString("idClient", "");

        //System.out.println("++++++++date :"+date+"*******nb : "+nb+"**********heure : "+hour);
        System.out.println("++++++++idClient :"+idClient+"*******idRest : "+idRestaurant);
        Txtnb=findViewById(R.id.nb_pers);
        Txtdate=findViewById(R.id.date);
        Txtheure=findViewById(R.id.heure_res);
        btnReserv=findViewById(R.id.btn_Reserver);
        TxtPhone=findViewById(R.id.input_phone);
        btnReserv.setOnClickListener(this);

        Txtnb.setText(nb);
        Txtdate.setText(date);
        Txtheure.setText(hour);


    }

    @Override
    public void onClick(View v) {
        Num_Phone=TxtPhone.getText().toString();
        Reservation reserv=new Reservation(idRestaurant,idClient,nb,date,hour,Num_Phone);

        db.collection("reservations")
                .add(reserv)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Intent i= new Intent(ReserverActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}
