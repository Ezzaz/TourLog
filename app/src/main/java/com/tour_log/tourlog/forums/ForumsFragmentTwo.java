package com.tour_log.tourlog.forums;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tour_log.tourlog.R;
import com.tour_log.tourlog.events.PopularEvent;
import com.tour_log.tourlog.events.PopularEventAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForumsFragmentTwo extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference rootRef;

    private EditText reviewET;
    private Button postbtn;
    private String review;

    private RecyclerView RV;
    private ArrayList<ForumsPost> forumsPosts;
    private ForumsPostAdapter forumsPostAdapter;

    public ForumsFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forums_fragment_two, container, false);

        reviewET = view.findViewById(R.id.forums_Post);
        postbtn = view.findViewById(R.id.postbtn);

        RV =view.findViewById(R.id.reviewPostRV);
        forumsPosts =ForumsPost.getAllPopularEventList();
        forumsPostAdapter =new ForumsPostAdapter(getContext(),forumsPosts);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RV.setLayoutManager(llm);
        RV.setAdapter(forumsPostAdapter);



        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 review = reviewET.getText().toString();
                if(review != ""){
                    popularEventAdd();
                    Snackbar.make(view, "Sucessfully save your post ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    Snackbar.make(view, "Please write something to post your review ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return view;
    }

    private void popularEventAdd(){

        user = FirebaseAuth.getInstance().getCurrentUser();

        ForumsPost ev = new ForumsPost(review,0,0,user.getDisplayName(),user.getEmail());
        reviewET.setText("");
        rootRef = FirebaseDatabase.getInstance().getReference().child("TourLog").child("ReviewPost");
        rootRef.keepSynced(true);
        String eventID = rootRef.push().getKey();
        rootRef.child(eventID).setValue(ev);


    }

}
