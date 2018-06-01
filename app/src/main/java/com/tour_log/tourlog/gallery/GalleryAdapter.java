package com.tour_log.tourlog.gallery;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tour_log.tourlog.R;

import java.util.List;

public class GalleryAdapter extends ArrayAdapter<GalleryImg>{

    private Activity context;
    private int resource;
    private List<GalleryImg>listImage;

    public GalleryAdapter( @Nullable Activity context, @LayoutRes int resource, @Nullable List<GalleryImg>objects){
        super(context,resource,objects);
        this.context=context;
        this.resource=resource;
        this.listImage=objects;
    }

    @NonNull
    @Override
    public View getView ( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        LayoutInflater inflater=context.getLayoutInflater ();

        View v = inflater.inflate ( resource,null );

        TextView textView=(TextView) v.findViewById ( R.id.text_view );
        ImageView imageView=(ImageView) v.findViewById ( R.id.imgView );

        textView.setText ( listImage.get ( position ).getName () );
        Glide.with ( context).load ( listImage.get ( position ).getUrl () ).into ( imageView );
        return v;
    }

}
