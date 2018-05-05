package com.tour_log.tourlog.events;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by biplab on 18-Mar-18.
 */

public class EventDetails {

    private String placesName;
    private double departureCost;
    private double miniTransport;
    private double hotelCost;
    private double mealCost;
    private double arrivalCost;
    private double guidCost;

    public static ArrayList<EventDetails> eventsCost = new ArrayList<>();
    public static EventDetails myEventsCost;

    public EventDetails() {
    }

    public EventDetails(String placesName, double departureCost, double miniTransport, double hotelCost, double mealCost, double arrivalCost, double guidCost) {
        this.placesName = placesName;
        this.departureCost = departureCost;
        this.miniTransport = miniTransport;
        this.hotelCost = hotelCost;
        this.mealCost = mealCost;
        this.arrivalCost = arrivalCost;
        this.guidCost = guidCost;
    }

    public String getPlacesName() {
        return placesName;
    }

    public void setPlacesName(String placesName) {
        this.placesName = placesName;
    }

    public double getDepartureCost() {
        return departureCost;
    }

    public void setDepartureCost(double departureCost) {
        this.departureCost = departureCost;
    }

    public double getMiniTransport() {
        return miniTransport;
    }

    public void setMiniTransport(double miniTransport) {
        this.miniTransport = miniTransport;
    }

    public double getHotelCost() {
        return hotelCost;
    }

    public void setHotelCost(double hotelCost) {
        this.hotelCost = hotelCost;
    }

    public double getMealCost() {
        return mealCost;
    }

    public void setMealCost(double mealCost) {
        this.mealCost = mealCost;
    }

    public double getArrivalCost() {
        return arrivalCost;
    }

    public void setArrivalCost(double arrivalCost) {
        this.arrivalCost = arrivalCost;
    }

    public double getGuidCost() {
        return guidCost;
    }

    public void setGuidCost(double guidCost) {
        this.guidCost = guidCost;
    }

    public static ArrayList<EventDetails> getAllEventCostList(){
        DatabaseReference rootReference;
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootReference = FirebaseDatabase.getInstance().getReference().child("TourLog").child("EventDetailsCost");
        final long countev =  eventsCost.size();

        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    myEventsCost = snapshot.getValue(EventDetails.class);
                    if(dataSnapshot.getChildrenCount()>countev){
                        eventsCost.add(new EventDetails(myEventsCost.getPlacesName(),myEventsCost.getDepartureCost(),
                                myEventsCost.getMiniTransport(),myEventsCost.getHotelCost(),myEventsCost.getMealCost(),
                                myEventsCost.getArrivalCost(),myEventsCost.getGuidCost()));
                    }

                }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return  eventsCost;
    }

    }
