package com.tour_log.tourlog.profile;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.tour_log.tourlog.R;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    EditText userNameET, userEmailET,oldPassET,newPassET;
    ImageView userProfileIV;

    TextView userVerifiedTV, userNotVerifedTV;

    Button verifyBtn , updateProfileBtn;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        String userName = user.getDisplayName();
        final String userEmail = user.getEmail();
        Uri photoUri = user.getPhotoUrl();


        userVerifiedTV = (TextView) view.findViewById(R.id.userVerified);
        userNotVerifedTV = (TextView) view.findViewById(R.id.userNotVerified);

        verifyBtn = (Button) view.findViewById(R.id.verificationBtn);
        updateProfileBtn = (Button) view.findViewById(R.id.updateProfile);

        userVerifiedTV.setVisibility(View.INVISIBLE);
        userNotVerifedTV.setVisibility(View.INVISIBLE);
        verifyBtn.setVisibility(View.INVISIBLE);


        userNameET = (EditText) view.findViewById(R.id.username_input);
        userEmailET =(EditText) view.findViewById(R.id.useremail_input);
        userProfileIV = (ImageView)view.findViewById(R.id.userProfileImage);
        oldPassET = (EditText)view.findViewById(R.id.userPass_input);
        newPassET = (EditText)view.findViewById(R.id.usernewPass_input);

        if(photoUri!= null){
            Picasso.with(getApplicationContext()).load(photoUri).into(userProfileIV);
        }


        userNameET.setText(userName);
        userEmailET.setText(userEmail);

        if(user.isEmailVerified()){
            userVerifiedTV.setVisibility(View.VISIBLE);
        }
        else{
            userNotVerifedTV.setVisibility(View.VISIBLE);
            verifyBtn.setVisibility(View.VISIBLE);
        }

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(userNameET.getText().toString() !=null && oldPassET.getText().toString() != null && newPassET.getText().toString() !=null){

                    firebaseAuth.signInWithEmailAndPassword(userEmailET.getText().toString(), oldPassET.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                firebaseAuth.confirmPasswordReset(userEmailET.getText().toString(),newPassET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Snackbar.make(view, "Your password is successfully reset !", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                        else {
                                            Snackbar.make(view, "Somthing wrong ! Please try again", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Snackbar.make(view, "Please enter your old email or password carefully !", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });
                }
                else{
                    Snackbar.make(view, "Invalid request ! Please try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        return view;
    }

}
