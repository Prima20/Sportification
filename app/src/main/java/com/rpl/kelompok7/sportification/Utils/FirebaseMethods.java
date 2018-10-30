package com.rpl.kelompok7.sportification.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.root.project.instagramclone.Home.HomeActivity;
import com.root.project.instagramclone.Models.Photo;
import com.root.project.instagramclone.Models.User;
import com.root.project.instagramclone.Models.UserAccountSettings;
import com.root.project.instagramclone.Models.UserSettings;
import com.root.project.instagramclone.Profile.AccountSettingActivity;
import com.root.project.instagramclone.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by zakwan on 22/10/2017.
 */

public class FirebaseMethods {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;


    private static final String TAG = "HomeActivity";
    private String userID;
    private Context mContext;

    private double mPhotoUploadProgress = 0;

    public FirebaseMethods(Context context){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;

        for(DataSnapshot ds: dataSnapshot
                .child(mContext.getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    public void updateEmail(String email){
        myRef.child(mContext.getString(R.string.db_users))
                .child(userID)
                .child(mContext.getString(R.string.db_field_email))
                .setValue(email);
    }


    public void registerNewEmail(final String email , final String username , final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT).show();
                        } else if(task.isSuccessful()){
                            userID = mAuth.getCurrentUser().getUid();
                            Toast.makeText(mContext, "register success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addNewUser(String email, String username , String description, String website , String profile_photo){
        User user = new User(
                email,
                1,
                userID,
                username.replace(" ",".")
        );

        myRef.child(mContext.getString(R.string.db_users))
                .child(userID)
                .setValue(user);

        UserAccountSettings userAccountSettings = new UserAccountSettings(
                description,
                username,
                1,
                1,
                1,
                profile_photo,
                username.replace(" ","."),
                website
        );

        myRef.child(mContext.getString(R.string.db_user_account_settings))
                .child(userID)
                .setValue(userAccountSettings);
    }

    public UserSettings getUserSetting(DataSnapshot dataSnapshot){
        UserAccountSettings settings = new UserAccountSettings();
        User user = new User();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.db_user_account_settings))) {
                try {
                    settings.setDisplay_name(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDisplay_name()
                    );
                    settings.setUsername(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUsername()
                    );
                    settings.setWebsite(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getWebsite()
                    );
                    settings.setDescription(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDescription()
                    );
                    settings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProfile_photo()
                    );
                    settings.setPosts(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getPosts()
                    );
                    settings.setFollowing(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFollowing()
                    );
                    settings.setFollowers(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFollowers()
                    );
                    Log.d(TAG , "retrieved user accout setting" + settings.toString());
                }
                catch (NullPointerException e){
                    Log.e(TAG,"NullPointerException" + e.getMessage());
                }
            }
            if(ds.getKey().equals(mContext.getString(R.string.db_users))) {
                try {
                    user.setUsername(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUsername()
                    );
                    user.setEmail(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getEmail()
                    );
                    user.setPhone_number(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getPhone_number()
                    );
                    user.setUser_id(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUser_id()
                    );
                    Log.d(TAG , "retrieved user information" + user.toString());
                }
                catch (NullPointerException e){
                    Log.e(TAG,"NullPointerException" + e.getMessage());
                }
            }
        }
        return new UserSettings(user , settings);
    }

    private void setProfilePhoto(String url){
        Log.d(TAG, "setProfilePhoto: setting new profile image: " + url);

        myRef.child(mContext.getString(R.string.db_user_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
    }

}
