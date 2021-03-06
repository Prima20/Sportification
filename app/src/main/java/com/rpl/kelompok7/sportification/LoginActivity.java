package com.rpl.kelompok7.sportification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.Models.User;
import com.rpl.kelompok7.sportification.Utils.FirebaseMethods;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private static final String TAG = "LoginActivity";
    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail , mPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("=======================","create login activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = (ProgressBar) findViewById(R.id.login_progresbar);
        mProgressBar.setVisibility(View.GONE);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mContext = LoginActivity.this;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


        setupFirebaseAuth();
        register();
    }

    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }else {
            return false;
        }
    }

    /*
    * ==========================================firebase===============================================
    */
    private void register(){
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(isStringNull(email) || isStringNull(password)){
                    Toast.makeText(mContext , "you must fill out all the field" , Toast.LENGTH_SHORT).show();
                }else{
                    mProgressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast.makeText(mContext, "failed" , Toast.LENGTH_SHORT).show();
                                        mProgressBar.setVisibility(View.GONE);
                                    }else{
                                        Log.w(TAG, "signInWithEmail:success", task.getException());
                                        Toast.makeText(mContext, "success" , Toast.LENGTH_SHORT).show();
                                        myRef.child(mContext.getString(R.string.db_users)).addListenerForSingleValueEvent(new ValueEventListener() {
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                    User tmp =  dataSnapshot1.getValue(User.class);
                                                    if(tmp.user_id != null)
                                                    if(tmp.user_id.equals(mAuth.getUid()) && tmp.role != null){
                                                        Log.d("=======================","found match" + tmp.role);
                                                        if(tmp.role.equals("Pemain Futsal")){
                                                            toDashboard();
                                                        } else if (tmp.role.equals("Pemilik Lapangan")){
//                                                            Intent intent = new Intent(mContext , RegisterActivity.class);
                                                            Intent intent = new Intent(mContext , DataLapangan.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.w("Test", "Failed to read value.", databaseError.toException());
                                            }
                                        }
                                        );

                                    }

                                }
                            });
                }
            }
        });

        TextView linkSignUp = (TextView) findViewById(R.id.to_register_activity_button);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , RegisterActivity.class);
                startActivity(intent);
            }
        });


        if(mAuth.getCurrentUser() != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            mengambilDataProfilUserDariFirebase();
        }

    }

    void toDashboard(){
        Intent intent = new Intent(mContext , DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    void mengambilDataProfilUserDariFirebase(){
        Log.d("=======================","mengambil data profil user dari firebase");
        myRef.child(mContext.getString(R.string.db_users)).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("=======================","data didapatkan");
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    User tmp =  dataSnapshot1.getValue(User.class);
                    if(tmp.user_id != null) {
                        Log.d("=======================","current user id " + mAuth.getCurrentUser().getUid());
                        Log.d("=======================","snapshot user id " + tmp.user_id);
                        if (tmp.user_id.equals(mAuth.getUid()) && tmp.role != null) {
                            Log.d("user is not null ", "found match " + tmp.role);
                            if (tmp.role.equals("Pemain Futsal")) {
                                toDashboard();
                            } else if (tmp.role.equals("Pemilik Lapangan")) {
                                Intent intent = new Intent(mContext, DataLapangan.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Test", "Failed to read value.", databaseError.toException());
            }
        });
    }

    /*
    * ==========================================firebase===============================================
    */
    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
