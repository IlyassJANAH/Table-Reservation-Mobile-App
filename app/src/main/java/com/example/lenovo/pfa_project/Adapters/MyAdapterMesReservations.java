package com.example.lenovo.pfa_project.Adapters;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.pfa_project.Activities.Reservation_ClientActivity;
import com.example.lenovo.pfa_project.Activities.RestaurantActivity;
import com.example.lenovo.pfa_project.Models.Reservation;
import com.example.lenovo.pfa_project.Models.Restaurant;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;

public class MyAdapterMesReservations extends RecyclerView.Adapter<MyAdapterMesReservations.MyViewHolder>{

    private LinkedList<Reservation> myReservation;
    private Context context;
    private FirebaseFirestore db;
    private static final String TAG = "ReservationsActivity";


    public MyAdapterMesReservations(LinkedList<Reservation> myReservation, Context context) {
        this.myReservation = new LinkedList<>() ;
        this.myReservation.addAll(myReservation);
        this.context=context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mes_reservations_item,viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterMesReservations.MyViewHolder holder, int position) {
        String urlImg;
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.TxtNom.setText(myReservation.get(position).getNomRest());
        holder.TxtNb.setText(myReservation.get(position).getNbPersonnes());
        holder.TxtHeure.setText(myReservation.get(position).getHeureReserv());
        holder.TxtEtat.setText(myReservation.get(position).getEtatReservation());
        holder.id=myReservation.get(position).getIdReservation();
        holder.date=myReservation.get(position).getDateReserv();
        String etat=myReservation.get(position).getEtatReservation();

        if (etat.equals("En cours")){
            holder.TxtEtat.setTextColor(Color.rgb(	255,165,0));

        }
        else if(etat.equals("Confirmer")){
            holder.TxtEtat.setTextColor(Color.GREEN);

        }
        else
            holder.TxtEtat.setTextColor(Color.RED);



        db = FirebaseFirestore.getInstance();
        //connexion avec FireBase
        DocumentReference docRef = db.collection("restaurants").document(myReservation.get(position).getIdRestaurant());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        holder.TxtNom.setText(document.getString("Nom"));
                        String urlImg=document.getString("Image");
                        Glide.with(context /* context */)
                                .load(urlImg)
                                .into(holder.TxtImg);
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
        public ImageView TxtImg;
        public TextView TxtHeure,TxtEtat;
        public String id;
        String date;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            TxtNom=itemLayoutView.findViewById(R.id.mes_res_nom);
            TxtNb=itemLayoutView.findViewById(R.id.mes_res_nb);
            TxtHeure=  itemLayoutView.findViewById(R.id.mes_res_time);
            TxtImg=  itemLayoutView.findViewById(R.id.mes_res_img);
            TxtEtat=   itemLayoutView.findViewById(R.id.etat);


            itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), Reservation_ClientActivity.class);
            i.putExtra("idRes",id);
            i.putExtra("nom",TxtNom.getText().toString());
            i.putExtra("nb",TxtNb.getText().toString()+" personne(s)");
            i.putExtra("heure",TxtHeure.getText().toString());
            i.putExtra("date",date);






            context.startActivity(i);
        }
        //###### Envoie de l'id du restaurant selectione to restaurantActivity #####

        //##### Envoie #####


    }

}
