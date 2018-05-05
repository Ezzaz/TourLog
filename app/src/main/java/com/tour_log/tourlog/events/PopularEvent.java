package com.tour_log.tourlog.events;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by biplab on 19-Mar-18.
 */

public class PopularEvent {
    private String imgURL;
    private String Title;
    private String subTitle;
    private String details;

    public static ArrayList<PopularEvent> eventsPopular = new ArrayList<>();
    public static PopularEvent myEventsPopular;

    public PopularEvent() {
    }

    public PopularEvent(String imgURL, String title, String subTitle, String details) {
        this.imgURL = imgURL;
        Title = title;
        this.subTitle = subTitle;
        this.details = details;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    public static ArrayList<PopularEvent> getAllPopularEventList(){
        DatabaseReference rootReference;
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootReference = FirebaseDatabase.getInstance().getReference().child("TourLog").child("PopularEvent");
        final long countev =  eventsPopular.size();

        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    myEventsPopular = snapshot.getValue(PopularEvent.class);
                    if(dataSnapshot.getChildrenCount()>countev){
                        eventsPopular.add(new PopularEvent(myEventsPopular.getImgURL(),myEventsPopular.getTitle(),
                                myEventsPopular.getSubTitle(),myEventsPopular.getDetails()));
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return  eventsPopular;
    }

}
