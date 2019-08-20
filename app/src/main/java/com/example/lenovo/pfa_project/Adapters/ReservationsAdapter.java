package com.example.lenovo.pfa_project.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.pfa_project.Activities.DetailReservActivity;
import com.example.lenovo.pfa_project.Activities.MainGerantActivity;
import com.example.lenovo.pfa_project.Models.Reservation;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.MyViewHolder>{

    private LinkedList<Reservation> myReservation;
    private Context context;
    private FirebaseFirestore db;
    private static final String TAG = "ReservationsActivity";


    public ReservationsAdapter(LinkedList<Reservation> myReservation, Context context) {
        this.myReservation = new LinkedList<>() ;
        this.myReservation.addAll(myReservation);
        this.context=context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_item,viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ReservationsAdapter.MyViewHolder holder, int position) {
        String urlImg;
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.TxtNom.setText(myReservation.get(position).getNomRest());
        String nb=myReservation.get(position).getNbPersonnes()+"personne(s)";
        holder.TxtNb.setText(nb);
        holder.TxtHeure.setText(myReservation.get(position).getHeureReserv());
        holder.TxtDate.setText(myReservation.get(position).getDateReserv());
        String id="ID Réservation :"+myReservation.get(position).getIdReservation();
        holder.TxtId.setText(id);
        holder.id_=myReservation.get(position).getIdReservation();
        String etat=myReservation.get(position).getEtatReservation();
        holder.Txtstatus.setText(myReservation.get(position).getEtatReservation());
        System.out.println("***************************"+etat);
        if (etat.equals("En cours")){

            holder.Txtstatus.setTextColor(Color.rgb(255,165,0));

        }
        else if (etat.equals("Confirmer")){
            holder.Txtstatus.setTextColor(Color.rgb(0,100,0));
            holder.actionButtonBar.setVisibility(View.GONE);
        }
        else {
            holder.Txtstatus.setTextColor(Color.rgb(100,0,0));
            holder.actionButtonBar.setVisibility(View.GONE);
        }


        db = FirebaseFirestore.getInstance();
        //connexion avec FireBase
        DocumentReference docRef = db.collection("users").document(myReservation.get(position).getIdClient());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        holder.TxtNom.setText(document.getString("nom"));
                        //String urlImg=document.getString("Image");
                        //Glide.with(context /* context */)
                                //.load(urlImg)
                                //.into(holder.TxtImg);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }


            }
        });

        //urlImg=myReservation.get(position).getImageRest();
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)

    }


    @Override
    public int getItemCount() {
        return myReservation.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView TxtNom;
        public TextView TxtNb;
       // public ImageView TxtImg;
        public TextView TxtHeure;
        public TextView TxtDate;
        public TextView TxtId;
        public TextView Txtstatus;
        String id_;
        LinearLayout acceptButton, declineButton, actionButtonBar;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            context = itemView.getContext();
            TxtNom=itemLayoutView.findViewById(R.id.user_name_resact);
            TxtNb=itemLayoutView.findViewById(R.id.peep_count_resact);
            TxtHeure=  itemLayoutView.findViewById(R.id.time_resact);
            //TxtImg=  itemLayoutView.findViewById(R.id.mes_res_img);
            TxtDate=  itemLayoutView.findViewById(R.id.date_resact);
            TxtId=  itemLayoutView.findViewById(R.id.reservation_id_text);
            Txtstatus=  itemLayoutView.findViewById(R.id.reservation_status);
            acceptButton = (LinearLayout) itemLayoutView.findViewById(R.id.accept_res_layout);
            declineButton = (LinearLayout) itemLayoutView.findViewById(R.id.decline_res_layout);
            actionButtonBar = (LinearLayout) itemLayoutView.findViewById(R.id.action_section);

            itemLayoutView.findViewById(R.id.main_content).setOnClickListener(this);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    DocumentReference ReservationRef = db.collection("reservations")
                            .document(id_);

// Set the "isCapital" field of the city 'DC'
                    ReservationRef
                            .update("etatReservation", "Confirmer")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });

                    Intent i = new Intent(context, MainGerantActivity.class);
                    context.startActivity(i);

                }
            });



            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    DocumentReference ReservationRef = db.collection("reservations")
                            .document(id_);

// Set the "isCapital" field of the city 'DC'
                    ReservationRef
                            .update("etatReservation", "Refuser")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });

                    Intent i = new Intent(context, MainGerantActivity.class);
                    context.startActivity(i);
                    

                }
            });


        }


        @Override
        public void onClick(View v) {

            Intent in= new Intent(context, DetailReservActivity.class);

            in.putExtra("idReservation",TxtId.getText().toString() );

            context.startActivity(in);

        }
        //###### Envoie de l'id du restaurant selectione to restaurantActivity #####

        //##### Envoie #####




    }

}
