package com.rpl.kelompok7.sportification.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
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
import com.rpl.kelompok7.sportification.LoginActivity;
import com.rpl.kelompok7.sportification.Models.*;
import com.rpl.kelompok7.sportification.R;
import com.rpl.kelompok7.sportification.RegisterActivity;


/**
 * Created by zakwan on 22/10/2017.
 */

public class FirebaseMethods {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    private static final String TAG = "HomeActivity";
    private Context mContext;


    public FirebaseMethods(Context context){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;

    }

    public Task<AuthResult> registerNewEmail(final String email , final String username , final String password ,final String role){
        return  mAuth.createUserWithEmailAndPassword(email, password);
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT).show();
//                        } else if(task.isSuccessful()){
//                            Toast.makeText(mContext, "register success", Toast.LENGTH_SHORT).show();
//                            addNewUser(email, username,role).addOnCompleteListener(RegisterActivity.this ,new OnCompleteListener<Void>(){
//
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Intent intent = new Intent(mContext , LoginActivity.class);
//                                    startActivity(intent);
//                                }
//                            } );
//                        }
//                    }
//                });
    }

    public Task<Void> addNewUser(String email, String username ,String role ){
        User user = new User(
                email,
                role,
                mAuth.getCurrentUser().getUid(),
                username
        );

        return myRef.child(mContext.getString(R.string.db_users))
                .child(mAuth.getCurrentUser().getUid())
                .setValue(user);
    }

}
