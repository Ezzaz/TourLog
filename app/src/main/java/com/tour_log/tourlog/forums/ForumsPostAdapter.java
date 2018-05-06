package com.tour_log.tourlog.forums;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.tour_log.tourlog.R;
import com.tour_log.tourlog.animation.AnimationUtil;
import com.tour_log.tourlog.events.PopularEvent;
import com.tour_log.tourlog.events.PopularEventAdapter;

import java.util.ArrayList;

/**
 * Created by biplab on 19-Mar-18.
 */

public class ForumsPostAdapter extends RecyclerView.Adapter<ForumsPostAdapter.ForumsPostViewHolder> {
    private Context context;
    private ArrayList<ForumsPost> ForumsPosts;
    int  previousposition=0;

    public ForumsPostAdapter(Context context, ArrayList<ForumsPost> forumsPosts) {
        this.context = context;
        this.ForumsPosts = forumsPosts;
    }

    @Override
    public ForumsPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v= layoutInflater.inflate(R.layout.single_row_formus_post,parent,false);

        return new ForumsPostAdapter.ForumsPostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ForumsPostViewHolder holder, int position) {
        holder.titleTV.setText(ForumsPosts.get(position).getReviewPost());
        holder.userTV.setText(ForumsPosts.get(position).getUserid());
        holder.emailTV.setText(ForumsPosts.get(position).getUseremail());
        holder.imgIV.setImageResource(R.drawable.profile);

        if(position > previousposition){

            AnimationUtil.animate(holder,true);
        }
        else{
            AnimationUtil.animate(holder,false);
        }
        previousposition = position;
    }

    @Override
    public int getItemCount() {
        return ForumsPosts.size();
    }

    public class ForumsPostViewHolder extends RecyclerView.ViewHolder{

        ImageView imgIV ;
        TextView titleTV,userTV,emailTV;
        ImageButton likebtn;
        ImageButton dislikebtn;

        public ForumsPostViewHolder(View itemView) {
            super(itemView);
           imgIV = itemView.findViewById(R.id.profileImageForum);
            titleTV = itemView.findViewById(R.id.postText);
            userTV = itemView.findViewById(R.id.forumsUser);
            emailTV = itemView.findViewById(R.id.ForumsEmail);
            likebtn = itemView.findViewById(R.id.like_btn);
            dislikebtn = itemView.findViewById(R.id.dislike_btn);

            likebtn.setOnClickListener (new View.OnClickListener ( ) {
                @Override
                public void onClick ( View view ) {
                    Toast.makeText (context, "Liked", Toast.LENGTH_LONG).show ( );
                }
            });

            dislikebtn.setOnClickListener (new View.OnClickListener ( ) {
                @Override
                public void onClick ( View view ) {
                    Toast.makeText (context, "Disliked", Toast.LENGTH_LONG).show ( );
                }
            });


        }
    }

}
