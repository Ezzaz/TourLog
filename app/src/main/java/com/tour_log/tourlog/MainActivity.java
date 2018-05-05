package com.tour_log.tourlog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.tour_log.tourlog.events.EventCostFragment;
import com.tour_log.tourlog.events.EventDetails;
import com.tour_log.tourlog.events.PopularEvent;
import com.tour_log.tourlog.forums.ForumsFragment;
import com.tour_log.tourlog.login.LoginActivity;
import com.tour_log.tourlog.nearby.NearbyFragment;
import com.tour_log.tourlog.profile.ProfileFragment;
import com.tour_log.tourlog.weather.WeatherFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    NavigationView navigationView = null;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference rootRef;

    private FusedLocationProviderClient client;
    public static Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        customToolbar();

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        client = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if(user.isEmailVerified()==false){
            checkVerification();
        }

        String userName = user.getDisplayName();
        String userEmail = user.getEmail();
        Uri photoUri = user.getPhotoUrl();

        ImageView userPhotoIV = (ImageView) headerView.findViewById(R.id.profileImage);
        if (photoUri != null) {
            Picasso.with(this).load(photoUri).into(userPhotoIV);
        }

        TextView userNameTV = (TextView) headerView.findViewById(R.id.userName);
        userNameTV.setText(userName);
        TextView userEmailTV = (TextView) headerView.findViewById(R.id.userEmail);
        userEmailTV.setText(userEmail);


    }

    @Override
    public void onBackPressed() {
        EventDetails.getAllEventCostList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to exit ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trip) {
            // Handle the camera action
            //  eventsDetailsAdd();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_weaather) {
            WeatherFragment fragment = new WeatherFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_event) {
            EventCostFragment fragment = new EventCostFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_nearby) {
            NearbyFragment fragment = new NearbyFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_forums) {
            ForumsFragment fragment = new ForumsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_profile) {
            ProfileFragment fragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragmentContainer,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_sign_out) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startLoginActivity();
                        }
                    });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void customToolbar(){
        toolbar = (Toolbar) findViewById(com.tour_log.tourlog.R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("TourLog");
        for(int i = 0; i < toolbar.getChildCount(); i++)
        { View view = toolbar.getChildAt(i);

            if(view instanceof TextView) {
                TextView textView = (TextView) view;
                Typeface myCustomFont=Typeface.createFromAsset(getAssets(),"fonts/JMHBelicosa.otf");
                textView.setTypeface(myCustomFont); }

        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

    private void checkVerification(){
        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Email Verification");
        dialog.setMessage("You need to verify that email "+user.getEmail()+" belongs to you. After Clinking Verify Button an email will send to verify your address.");
        dialog.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });
        dialog.setNegativeButton("Verify Email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendEmailVerification();
                checkVerification();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void sendEmailVerification(){
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Verification link sent to your email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eventsDetailsAdd(){
        EventDetails ev = new EventDetails(" Cox’s Bazar ",800,200,450,350,800,00);

        user = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference().child("TourLog").child("EventDetailsCost");
        rootRef.keepSynced(true);
        String eventID = rootRef.push().getKey();
        rootRef.child(eventID).setValue(ev);

    }
    private void popularEventAdd(){
        PopularEvent ev = new PopularEvent("s","s","s","s");

        user = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference().child("TourLog").child("PopularEvent");
        rootRef.keepSynced(true);
        String eventID = rootRef.push().getKey();
        rootRef.child(eventID).setValue(ev);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},21);
            return;
        }
        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful() && task != null) {
                    lastLocation = task.getResult();
                }
            }
        });
    }
}
