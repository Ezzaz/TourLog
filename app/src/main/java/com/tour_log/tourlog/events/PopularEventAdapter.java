package com.tour_log.tourlog.events;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tour_log.tourlog.R;
import com.tour_log.tourlog.animation.AnimationUtil;

import java.util.ArrayList;

/**
 * Created by biplab on 19-Mar-18.
 */

public class PopularEventAdapter extends RecyclerView.Adapter<PopularEventAdapter.PopularEventViewHolder> {
    private Context context;
    private ArrayList<PopularEvent> popularEvents;
   int  previousposition=0;

    public PopularEventAdapter(Context context, ArrayList<PopularEvent> popularEvents) {
        this.context = context;
        this.popularEvents = popularEvents;
    }

    @Override
    public PopularEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v= layoutInflater.inflate(R.layout.single_row_popular_event,parent,false);

        return new PopularEventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PopularEventViewHolder holder, int position) {
        holder.titleTV.setText(popularEvents.get(position).getTitle());
        holder.subTitleTV.setText(popularEvents.get(position).getSubTitle());

        String s=popularEvents.get(position).getImgURL();

       // String[] p=s.split("/");
       // String imageLink="https://drive.google.com/uc?export=download&id="+p[4];
        Uri iconUri = Uri.parse(popularEvents.get(position).getImgURL());


        Picasso.with(context)
                .load(iconUri)
                .into(holder.imgIV);

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
        return popularEvents.size();
    }

    public class PopularEventViewHolder extends RecyclerView.ViewHolder{

        ImageView imgIV ;
        TextView titleTV;
        TextView subTitleTV;

        public PopularEventViewHolder(View itemView) {
            super(itemView);
            imgIV = itemView.findViewById(R.id.popularImg);
            titleTV = itemView.findViewById(R.id.popularTitle);
            subTitleTV = itemView.findViewById(R.id.popularSubTitle);
        }
    }
}
