package com.example.lenovo.pfa_project.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.pfa_project.Models.Users;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "EmailPassword";
    public static final String PREFS_NAME = "Client_Settings";
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    final Context context = this;
    public ProgressDialog mProgressDialog;

    private String hour,date,nb,idClient,idRestaurant;
    SharedPreferences settings;

    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        mProgressDialog=new ProgressDialog(LoginActivity.this);
        mProgressDialog.setTitle("Authentification");
        mProgressDialog.setMessage("En cours...");

        //*************** get value intent

        /*hour = getIntent().getStringExtra("heure");
        nb = getIntent().getStringExtra("nombre");
        date = getIntent().getStringExtra("date");
        idRestaurant = getIntent().getStringExtra("idRest");*/

        db = FirebaseFirestore.getInstance();

        mEmailField = findViewById(R.id.input_email);
        mPasswordField = findViewById(R.id.input_password);
        mAuth = FirebaseAuth.getInstance();
        // Buttons
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        // [START initialize_auth]
        // Initialize Firebase Auth


    }


    /* Verifer si user est deja connecte */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            findViewById(R.id.auth).setVisibility(View.INVISIBLE);
            // Writing data to SharedPreferences

            idClient=currentUser.getUid();

            getType(idClient);
        }

        //finish();
    }
    /* Fiiin verif */


//++++++++ Creer nv user  +++++++++++++


    private void getType(String id){

        DocumentReference docRef = db.collection("users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Users users=new Users(document.get("nom").toString(),document.get("prenom").toString(),document.get("type").toString());
                        String type=users.getType();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("idClient", idClient);


                        editor.apply();

                        if (type.equals("client")){
                            mProgressDialog.hide();
                            Intent i= new Intent(context, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            mProgressDialog.hide();
                            Intent i= new Intent(context, MainGerantActivity.class);
                            startActivity(i);
                            finish();
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }


        });

    }


    private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mProgressDialog.show();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Success",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            idClient=currentUser.getUid();

                            getType(idClient);




                            //Intent intent= new Intent(context, catalog.class);
                            //startActivity(intent);
                        } else {
                            mProgressDialog.hide();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }


                    }

                });


    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_signup) {
                Intent in= new Intent(context, Create_AccountActivity.class);
           /* in.putExtra("date",date );
            in.putExtra("nombre",nb );
            in.putExtra("heure",hour );
            in.putExtra("idClient",idClient );
            in.putExtra("idRest",idRestaurant );*/

            startActivity(in);

        } else if (i == R.id.btn_login) {

            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }


    //****** valider formulaire***************
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }



}
