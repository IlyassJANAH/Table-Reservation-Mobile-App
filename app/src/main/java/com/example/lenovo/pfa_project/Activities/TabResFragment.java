package com.example.lenovo.pfa_project.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.pfa_project.R;
import com.example.lenovo.pfa_project.Utils.ReserverInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.support.constraint.Constraints.TAG;

public class TabResFragment extends Fragment implements View.OnClickListener {
    private TextView type;
    private TextView DescView,Adresse,Ville,phone;
    private String id;
    private String Desc;
    private FirebaseFirestore db;
    private String TempCuisine;
    private String TempDesc;
    private Button btnReserver;
    private Context context;
    public ProgressDialog mProgressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        btnReserver=view.findViewById(R.id.Reserver);
        db = FirebaseFirestore.getInstance();
        //Bundle args = getArguments();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("idRes");
            System.out.println("**********#######*************************"+id);
            //The key argument here must match that used in the other activity
        }
        mProgressDialog=new ProgressDialog(getActivity());

        mProgressDialog.show();

        //id = args.getString("idRes");
            getDetails();
            type=(TextView) view.findViewById(R.id.typeCui);
            DescView=(TextView) view.findViewById(R.id.Desc);
        Adresse=(TextView) view.findViewById(R.id.adresse);
        Ville=(TextView) view.findViewById(R.id.ville);
        phone=view.findViewById(R.id.Phone);


        System.out.println("###########"+TempCuisine);

        btnReserver.setOnClickListener(this);


        return view;
    }

    private void getDetails(){
        DocumentReference docRef = db.collection("restaurants").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                        TempCuisine = document.get("TypeCuisine").toString();
                        TempDesc = document.get("Description").toString();
                        type.setText(TempCuisine);
                        DescView.setText(TempDesc);
                        Adresse.setText(document.get("Adresse").toString());
                        Ville.setText(document.get("Ville").toString());
                        phone.setText(document.get("Phone").toString());
                        mProgressDialog.hide();


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }



    @Override
    public void onClick(View v) {
        Activity activity = (Activity)this.context;
        //FragmentActivity myContext=(FragmentActivity) activity;
        ReserverInfo ri= new ReserverInfo();
        ri.setContext(context);
        ri.setIdRestaurant(id);
        ri.show(getActivity().getSupportFragmentManager(),"hhh");

    }
}