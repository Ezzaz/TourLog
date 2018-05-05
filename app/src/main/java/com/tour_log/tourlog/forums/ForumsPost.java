package com.tour_log.tourlog.forums;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tour_log.tourlog.events.PopularEvent;

import java.util.ArrayList;

/**
 * Created by biplab on 19-Mar-18.
 */

public class ForumsPost {
    private String reviewPost;
    private int like;
    private int dislike;
    private String username;
    private String useremail;

    public static ArrayList<ForumsPost> postsReview = new ArrayList<>();
    public static ForumsPost myForumsPost;

    public ForumsPost() {
    }

    public ForumsPost(String reviewPost, int like, int dislike,String userid,String useremail) {
        this.reviewPost = reviewPost;
        this.like = like;
        this.dislike = dislike;
        this.username = userid;
        this.useremail = useremail;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserid() {
        return username;
    }

    public void setUserid(String userid) {
        this.username = userid;
    }

    public String getReviewPost() {
        return reviewPost;
    }

    public void setReviewPost(String reviewPost) {
        this.reviewPost = reviewPost;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public static ArrayList<ForumsPost> getAllPopularEventList(){
        DatabaseReference rootReference;
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootReference = FirebaseDatabase.getInstance().getReference().child("TourLog").child("ReviewPost");
        final long countev =  postsReview.size();

        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    myForumsPost = snapshot.getValue(ForumsPost.class);
                    if(dataSnapshot.getChildrenCount()>countev){
                        postsReview.add(new ForumsPost(myForumsPost.getReviewPost(),myForumsPost.getLike(),
                                myForumsPost.getDislike(),myForumsPost.getUserid(),myForumsPost.getUseremail()));
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return  postsReview;
    }

}
