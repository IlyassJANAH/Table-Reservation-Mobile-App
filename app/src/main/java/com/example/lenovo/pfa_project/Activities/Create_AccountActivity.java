package com.example.lenovo.pfa_project.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.pfa_project.Models.Users;
import com.example.lenovo.pfa_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Create_AccountActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "EmailPassword";
    public static final String PREFS_NAME = "Client_Settings";

    private TextView inputNom,inputPrenom,inputEmail,inputPassword;
    private Button btnSignup;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    final Context context = this;
    public ProgressDialog mProgressDialog;
    private Toolbar mTopToolbar;

    private String idUser,nom,prenom;

    private FirebaseFirestore db;
    private SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        inputNom = findViewById(R.id.input_nom);
        inputPrenom = findViewById(R.id.input_prenom);
        inputEmail =findViewById(R.id.input_email);
        inputPassword=findViewById(R.id.input_password);
        btnSignup=findViewById(R.id.btn_signup);

        db = FirebaseFirestore.getInstance();

        // Buttons
        btnSignup.setOnClickListener(this);
        //findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    private void createAccount(String email, String password) {

        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        //Remplace showprogressdialog()


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            idUser=user.getUid();
                            System.out.println("*******************"+idUser);
                            CreateUser(idUser,nom,prenom);

                            settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("idClient", idUser);
                            editor.apply();
                            //Intent intent= new Intent(context, catalog.class);
                            //startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Create_AccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
        // [END create_user_with_email]
    }


    private void CreateUser(String id,String nom_,String prenom_){
        if (!validateForm()) {
            return;
        }
        Users users=new Users(nom_,prenom_);

        db.collection("users").document(id)
                .set(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Intent i= new Intent(context, MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }


    //****** valider formulaire***************
    private boolean validateForm() {
        boolean valid = true;

        String email = inputEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Required.");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        String password = inputPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Required.");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        String prenom = inputPrenom.getText().toString();
        if (TextUtils.isEmpty(password)) {
            inputPrenom.setError("Required.");
            valid = false;
        } else {
            inputPrenom.setError(null);
        }

        String nom = inputNom.getText().toString();
        if (TextUtils.isEmpty(password)) {
            inputNom.setError("Required.");
            valid = false;
        } else {
            inputNom.setError(null);
        }



        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
         if (i == R.id.btn_signup) {
             nom=inputNom.getText().toString();
             prenom=inputPrenom.getText().toString();
            createAccount(inputEmail.getText().toString(), inputPassword.getText().toString());




        }
    }







    //********** valider ******
}
