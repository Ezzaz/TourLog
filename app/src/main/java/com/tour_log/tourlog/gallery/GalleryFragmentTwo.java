package com.tour_log.tourlog.gallery;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tour_log.tourlog.R;
import com.tour_log.tourlog.weather.WeatherForecastAdapter;
import com.tour_log.tourlog.weather.WeatherForecastResponse;
import com.tour_log.tourlog.weather.WeatherForecastService;
import com.tour_log.tourlog.weather.WeatherFragmentOne;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragmentTwo extends Fragment {


    public GalleryFragmentTwo () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_fragment_two, container, false);

        return view;
    }

}
