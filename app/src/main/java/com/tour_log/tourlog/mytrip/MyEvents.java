package com.tour_log.tourlog.mytrip;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tour_log.tourlog.R;

import java.util.ArrayList;

public class MyEvents {
    private  int eventImg;
    private String eventName;
    private String eventDetails;
    private String eventPlace;
    private LatLng eventPlaceLatlng;
    private String eventDate;
    private double eventBudget;
    private double eventExpense;
    private ArrayList<String> eventExpenceList;

    private String userId;
    private String eventID;

    public static MyEvents myEvents;
    public static ArrayList<MyEvents> events = new ArrayList<>();

    public MyEvents() {
    }

    public MyEvents(String eventName, String eventDetails, String eventPlace, String eventDate, double eventBudget,
                    String userId, String eventID) {
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventPlace = eventPlace;
        this.eventDate = eventDate;
        this.eventBudget = eventBudget;
        this.userId = userId;
        this.eventID = eventID;
    }

    public MyEvents(String eventName, String eventDetails, String eventPlace, String eventDate, double eventBudget,
                    double eventExpense, String userId, String eventID) {
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventPlace = eventPlace;
        this.eventDate = eventDate;
        this.eventBudget = eventBudget;
        this.eventExpense = eventExpense;
        this.userId = userId;
        this.eventID = eventID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public MyEvents(int eventImg, String eventName, String eventDetails, String eventPlace, LatLng eventPlaceLatlng, String eventDate, double eventBudget, double eventExpense, ArrayList<String> eventExpenceList) {
        this.eventImg = eventImg;
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventPlace = eventPlace;
        this.eventPlaceLatlng = eventPlaceLatlng;
        this.eventDate = eventDate;
        this.eventBudget = eventBudget;
        this.eventExpense = eventExpense;
        this.eventExpenceList = eventExpenceList;
    }

    public ArrayList<String> getEventExpenceList() {
        return eventExpenceList;
    }

    public void setEventExpenceList(ArrayList<String> eventExpenceList) {
        this.eventExpenceList = eventExpenceList;
    }

    public MyEvents(int eventImg, String eventName, String eventDetails, String eventPlace, LatLng eventPlaceLatlng, String eventDate, double eventBudget, double eventExpense) {
        this.eventImg = eventImg;
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventPlace = eventPlace;
        this.eventPlaceLatlng = eventPlaceLatlng;
        this.eventDate = eventDate;
        this.eventBudget = eventBudget;
        this.eventExpense = eventExpense;
    }

    public MyEvents(int eventImg, String eventName, String eventDate) {
        this.eventImg = eventImg;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public int getEventImg() {
        return eventImg;
    }

    public void setEventImg(int eventImg) {
        this.eventImg = eventImg;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public LatLng getEventPlaceLatlng() {
        return eventPlaceLatlng;
    }

    public void setEventPlaceLatlng(LatLng eventPlaceLatlng) {
        this.eventPlaceLatlng = eventPlaceLatlng;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public double getEventBudget() {
        return eventBudget;
    }

    public void setEventBudget(double eventBudget) {
        this.eventBudget = eventBudget;
    }

    public double getEventExpense() {
        return eventExpense;
    }

    public void setEventExpense(double eventExpense) {
        this.eventExpense = eventExpense;
    }

    public static ArrayList<MyEvents> getAllEventList(){

        DatabaseReference rootReference;
        FirebaseUser user;

        //ArrayList<MyEvents> events = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        rootReference = FirebaseDatabase.getInstance().getReference("Events");
        final long countev =  events.size();

        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    myEvents = snapshot.getValue(MyEvents.class);


                    long countdt =  dataSnapshot.getChildrenCount();

                    if (countdt > countev){
                        events.add(new MyEvents(R.drawable.event_card_img,myEvents.getEventName(),myEvents.getEventDate()));
                    }
                    else {

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Log.e("ttt", "getAllEventList: "+events.size());

        return  events;
    }

}
