package com.tour_log.tourlog.nearby;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.squareup.picasso.Picasso;
import com.tour_log.tourlog.R;
import com.tour_log.tourlog.animation.AnimationUtil;

import java.util.List;

/**
 * Created by biplab on 19-Mar-18.
 */

public class MapNearbyAdapter extends RecyclerView.Adapter<MapNearbyAdapter.nearbyViewHolder> implements OnMapReadyCallback {
    private Context context;
    private List<MapNearbyResponse.Result> nearbyDataList;
    private GoogleMap map;

    int  previousposition=0;



    public MapNearbyAdapter(Context context, List<MapNearbyResponse.Result> nearbyDataList) {
        this.context = context;
        this.nearbyDataList = nearbyDataList;
    }

    @Override
    public nearbyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.single_nearby_row,parent,false);
        return new nearbyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(nearbyViewHolder holder, int position) {
        holder.nearbyNameTV.setText(nearbyDataList.get(position).getName().toString());
        holder.nearbyDisTV.setText(nearbyDataList.get(position).getVicinity().toString());

        String iconString = nearbyDataList.get(position).getIcon().toString();
        Uri iconUri = Uri.parse(iconString);
        Picasso.with(context)
                .load(iconUri)
                .into(holder.nearbyImg);

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
        return nearbyDataList.size();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public class nearbyViewHolder extends RecyclerView.ViewHolder{

        TextView nearbyNameTV;
        TextView nearbyDisTV;
        ImageView nearbyImg;

        public nearbyViewHolder(View itemView) {
            super(itemView);
            nearbyNameTV = itemView.findViewById(R.id.nearbyName);
            nearbyDisTV = itemView.findViewById(R.id.nearbyDis);
            nearbyImg = itemView.findViewById(R.id.nearbyImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    Toast.makeText (context, "" + nearbyDataList.get (position).getName ()+ " LOCATION : " + nearbyDataList.get (position).getVicinity () , Toast.LENGTH_SHORT).show ( );
                }
            });


        }
    }
}
