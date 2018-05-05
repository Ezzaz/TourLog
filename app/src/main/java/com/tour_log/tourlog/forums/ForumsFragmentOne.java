package com.tour_log.tourlog.forums;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tour_log.tourlog.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForumsFragmentOne extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView RV;
    private ArrayList<ForumsPost> forumsPosts;
    private ForumsPostAdapter forumsPostAdapter;

    public ForumsFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forums_fragment_one, container, false);
        RV =view.findViewById(R.id.reviewPostpublicRV);
        progressBar=view.findViewById (R.id.pb_forum);
        RV.setVisibility (View.GONE);
        forumsPosts =ForumsPost.getAllPopularEventList();
        forumsPostAdapter =new ForumsPostAdapter(getContext(),forumsPosts);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RV.setLayoutManager(llm);
        RV.setAdapter(forumsPostAdapter);
        progressBar.setVisibility (View.GONE);
        RV.setVisibility (View.VISIBLE);
        return view;
    }

}
