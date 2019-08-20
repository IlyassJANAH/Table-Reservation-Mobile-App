package com.example.lenovo.pfa_project.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.pfa_project.Activities.MainActivity;
import com.example.lenovo.pfa_project.Activities.RestaurantActivity;
import com.example.lenovo.pfa_project.Models.Restaurant;
import com.example.lenovo.pfa_project.R;

import java.util.LinkedList;

public class MyAdapterRestaurant extends RecyclerView.Adapter<MyAdapterRestaurant.MyViewHolder>{

    private LinkedList<Restaurant> myRestaurant;
    private Context context;

    public MyAdapterRestaurant(LinkedList<Restaurant> myRestaurant,Context context) {
        this.myRestaurant = new LinkedList<Restaurant>() ;
        this.myRestaurant.addAll(myRestaurant);
        this.context=context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.restaurant_home_items,viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterRestaurant.MyViewHolder holder, int position) {
        String urlImg;
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.id.setText(myRestaurant.get(position).getId());
        holder.nom.setText(myRestaurant.get(position).getNom());
        holder.type.setText(myRestaurant.get(position).getTypeCuisine());

        urlImg=myRestaurant.get(position).getImage();
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)

        Glide.with(context /* context */)
                .load(urlImg)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return myRestaurant.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nom;
        public TextView type;
        public ImageView image;
        public TextView id;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            nom=itemLayoutView.findViewById(R.id.nomRestaurant);
            type=itemLayoutView.findViewById(R.id.typeRestaurant);
            image=  itemLayoutView.findViewById(R.id.imageRest);
            id=  itemLayoutView.findViewById(R.id.idRestaurant);

            itemLayoutView.setOnClickListener(this);

        }
        //###### Envoie de l'id du restaurant selectione to restaurantActivity #####
        @Override
        public void onClick(View v) {

            Intent i = new Intent(v.getContext(), RestaurantActivity.class);
            i.putExtra("idRes",id.getText().toString());
            context.startActivity(i);

        }
        //##### Envoie #####





    }

}
