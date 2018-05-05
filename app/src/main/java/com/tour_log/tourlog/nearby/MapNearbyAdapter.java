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


                    final AlertDialog.Builder dialog;
                    dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Details");

                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    LinearLayout ll = (LinearLayout) layoutInflater.inflate(R.layout.single_nearby_details_row,null,false);

                    final ImageView nearbyDetailsImg = ll.findViewById(R.id.nearbyDetailsImg);
                    final TextView nearbyDetailsName = ll.findViewById(R.id.nearbyDetailsName);
                    final TextView nearbyDetailsDis = ll.findViewById(R.id.nearbyDetailsDis);
                    final TextView nearbyDetailsOpen = ll.findViewById(R.id.nearbyDetailsOpen);
                    final TextView nearbyDetailsRating = ll.findViewById(R.id.nearbyDetailsRating);

                    nearbyDetailsName.setText(nearbyDataList.get(position).getName().toString());
                    nearbyDetailsDis.setText(nearbyDataList.get(position).getVicinity().toString());
                    nearbyDetailsOpen.setText("Open Now :"+String.valueOf(nearbyDataList.get(position).getOpeningHours().isOpenNow()));
                    nearbyDetailsRating.setText("Rating :"+String.valueOf(nearbyDataList.get(position).getRating()));

                    String iconString = nearbyDataList.get(position).getIcon().toString();
                    Uri iconUri = Uri.parse(iconString);
                    Picasso.with(context)
                            .load(iconUri)
                            .into(nearbyDetailsImg);

                    dialog.setIcon(R.drawable.location);
                    dialog.setView(ll);

                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.setCancelable(false);

                    dialog.show();

                }
            });


        }
    }
}
