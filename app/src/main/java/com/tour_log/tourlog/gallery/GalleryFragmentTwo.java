package com.tour_log.tourlog.gallery;


import android.app.Activity;
import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tour_log.tourlog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragmentTwo extends Fragment {

    private DatabaseReference rootReference;
    private List<GalleryImg>imgList;
    private ListView listView;
    private GalleryAdapter imageListAdapter;
    private ProgressDialog progressDialog;

    public GalleryFragmentTwo () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_fragment_two, container, false);

        imgList=new ArrayList<> ();
        listView=(ListView)view.findViewById (R.id.list_view);
        progressDialog=new ProgressDialog(getContext ());
        progressDialog.setMessage ( "Please wait for loading images..." );
        progressDialog.show ();

        rootReference= FirebaseDatabase.getInstance ().getReference (GalleryFragmentOne.FB_DATABASE_PATH);
        rootReference.addValueEventListener (new ValueEventListener ( ) {
            @Override
            public void onDataChange ( DataSnapshot dataSnapshot ) {

                progressDialog.dismiss ();

                for (DataSnapshot snapshot: dataSnapshot.getChildren ()){
                    GalleryImg imageUpload=snapshot.getValue (GalleryImg.class);
                    imgList.add ( imageUpload);
                }

                imageListAdapter=new GalleryAdapter((Activity) getContext (),R.layout.fragment_gallery_image_item,imgList);
                listView.setAdapter (imageListAdapter);

            }

            @Override
            public void onCancelled ( DatabaseError databaseError ) {

            progressDialog.dismiss ();
            }
        });

        return view;
    }

}
