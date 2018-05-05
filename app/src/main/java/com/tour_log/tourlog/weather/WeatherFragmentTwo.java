package com.tour_log.tourlog.weather;


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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragmentTwo extends Fragment {


    private RecyclerView listview;
    private List<WeatherForecastResponse.Mylist> forecastDatalist;
    private WeatherForecastAdapter adapter;
    private Context context;

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private String units = "metric";
    private WeatherForecastService weatherForecastService;

    public WeatherFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_fragment_two, container, false);

        listview = (RecyclerView) view.findViewById(R.id.forecast_listView);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherForecastService = retrofit.create(WeatherForecastService.class);

        callForecastWeather();
        return view;
    }

    private void callForecastWeather() {
        Call<WeatherForecastResponse> responseCall = weatherForecastService.getCurrentForecastWeatherData(
                String.valueOf(WeatherFragmentOne.lat),
                String.valueOf(WeatherFragmentOne.longi),
                units,
                getString(R.string.weather_API_Key)
        );

        responseCall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                if(response.code()==200){

                    WeatherForecastResponse forecastData = response.body();
                    forecastDatalist = forecastData.getList();
                    context = getContext();

                    adapter = new WeatherForecastAdapter(context,forecastDatalist);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    listview.setLayoutManager(llm);
                    listview.setAdapter(adapter);
                    Log.e("forecast", "setForecastDataShow: "+forecastDatalist.size() );
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
            }
        });

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible){
            callForecastWeather();
        }
    }



}
