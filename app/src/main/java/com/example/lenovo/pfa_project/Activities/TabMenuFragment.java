package com.example.lenovo.pfa_project.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lenovo.pfa_project.Models.Plats;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class TabMenuFragment extends Fragment {

    private String id;
    private FirebaseFirestore db;
    private ArrayList<Plats> plats=new ArrayList<>();
    ListView listView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        db = FirebaseFirestore.getInstance();
        Bundle extras = getActivity().getIntent().getExtras();
        System.out.println("******************** menuFRag********************");
        if (extras != null) {
            id = extras.getString("idRes");
            System.out.println("**********#######*************************"+id);
            //The key argument here must match that used in the other activity
        }




        db.collection("menus")
                .whereEqualTo("idRestaurant", "/restaurants/"+id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Plats tempplats= new Plats(document.get("intitule").toString(),document.get("prix").toString());
                                plats.add(tempplats);
                                System.out.println("**********#######*************************"+
                                        document.get("intitule").toString()+document.get("prix").toString());


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        System.out.println("************************#############"+plats);

                    }


                });
        return view;
    }




}