package com.rpl.kelompok7.sportification.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpl.kelompok7.sportification.DashboardActivity;
import com.rpl.kelompok7.sportification.DashboardAdapter;
import com.rpl.kelompok7.sportification.Models.*;
import com.rpl.kelompok7.sportification.R;


/**
 * Created by zakwan on 22/10/2017.
 */

public class FirebaseMethods {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    private static final String TAG = "HomeActivity";
    private String userID;
    private Context mContext;


    public FirebaseMethods(Context context){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    public void updateEmail(String email){
        myRef.child(mContext.getString(R.string.db_users))
                .child(userID)
                .child(mContext.getString(R.string.db_field_email))
                .setValue(email);
    }

//    public User getUser(final String id){
//        final User[] user = new User[1];
//
////        Log.d("=======================", user[0].toString());
//
//        return user[0];
//    }


    public void registerNewEmail(final String email , final String username , final String password ,final String role){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT).show();
                        } else if(task.isSuccessful()){
                            userID = mAuth.getCurrentUser().getUid();
                            Toast.makeText(mContext, "register success", Toast.LENGTH_SHORT).show();
                            addNewUser(email, username,role);
                        }
                    }
                });
    }

    public void addNewUser(String email, String username ,String role){

        Toast.makeText(mContext , role + email + username + userID , Toast.LENGTH_SHORT).show();
        User user = new User(
                email,
                role,
                userID,
                username
        );

        myRef.child(mContext.getString(R.string.db_users))
                .child(userID)
                .setValue(user);

    }


}
