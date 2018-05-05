package com.tour_log.tourlog.weather;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.squareup.picasso.Picasso;
import com.tour_log.tourlog.MainActivity;
import com.tour_log.tourlog.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragmentOne extends Fragment {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private String units = "metric";
    private WeatherService weatherService;
    private WeatherForecastService weatherForecastService;

    private List<WeatherForecastResponse.Mylist> forecastDatalist;
    private WeatherForecastAdapter adapter;
    private Context context;
    private RecyclerView listView;

    private TextView currentWeatherTV;
    private TextView currentWeatherLocationTV;
    private TextView currentTempTV;
    private TextView currentHumidityTV;
    private TextView currentVisibilityTV;
    private TextView currentPressureTV;
    private TextView currentTemp1TV;
    private TextView currentTempMinTV;
    private TextView currentTempMaxTV;
    private TextView currentSunriseTV;
    private TextView currentSunsetTV;
    private TextView currentWindTV;
    private ImageView currentWeatherIcon;

    public static String lat,longi;

    public WeatherFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_weather_fragment_one, container, false);

        currentWeatherTV = view.findViewById(R.id.currentWeather);
        currentWeatherLocationTV = view.findViewById(R.id.currentWeatherLocation);
        currentTempTV = view.findViewById(R.id.currentTemp);
        currentWeatherIcon = view.findViewById(R.id.currentWeatherIcon);
        currentHumidityTV = view.findViewById(R.id.currentHumidity);
        //currentVisibilityTV = view.findViewById(R.id.currentVisibility);
        currentPressureTV = view.findViewById(R.id.currentPressure);
        currentTemp1TV = view.findViewById(R.id.currentTemp1);
        currentTempMinTV = view.findViewById(R.id.currentTempMin);
        currentTempMaxTV = view.findViewById(R.id.currentTempMax);
        currentSunriseTV = view.findViewById(R.id.currentSunrise);
        currentSunsetTV = view.findViewById(R.id.currentSunset);
        currentWindTV = view.findViewById(R.id.currentWind);


        lat=String.valueOf(MainActivity.lastLocation.getLatitude());
        longi=String.valueOf(MainActivity.lastLocation.getLongitude());



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        weatherService = retrofit.create(WeatherService.class);
        weatherForecastService = retrofit.create(WeatherForecastService.class);

        final FloatingSearchView floatingSearchView = view.findViewById(R.id.floating_search_view_weather);
        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                if(currentQuery != null && currentQuery != ""){
                    Geocoder geocoderSerach = new Geocoder(getContext());
                    List<Address> addresslist = null;
                    try {
                        addresslist = geocoderSerach.getFromLocationName(currentQuery,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(addresslist.size()!=0){
                        Address address = addresslist.get(0) ;
                        lat=String.valueOf(address.getLatitude());
                        longi=String.valueOf(address.getLongitude());
                        callWeatherResponse();
                        floatingSearchView.clearQuery();
                    }

                    else {
                        Toast.makeText(getContext(),"Enter Valid Location",Toast.LENGTH_LONG).show();
                        floatingSearchView.clearQuery();
                    }

                }
            }
        });

        callWeatherResponse();

        return  view;
    }

    private void callWeatherResponse() {
        Call<WeatherResponse> call = weatherService.getCurrentWeatherData(
                String.valueOf(lat),
                String.valueOf(longi),
                units,
                getString(R.string.weather_API_Key)
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.code() == 200){
                    WeatherResponse weatherResponse = response.body();
                    String iconString = weatherResponse.getWeather().get(0).getIcon();

                    Picasso.with(getContext())
                            .load(Uri.parse("http://openweathermap.org/img/w/"+iconString+".png"))
                            .into(currentWeatherIcon);
                    currentWeatherTV.setText(weatherResponse.getWeather().get(0).getMain());
                    currentWeatherLocationTV.setText(weatherResponse.getName());
                    currentTempTV.setText(String.valueOf(weatherResponse.getMain().getTemp()) + (char) 0x00B0 +"C");
                    currentHumidityTV.setText(String.valueOf(weatherResponse.getMain().getHumidity())+" %");
                    //  currentVisibilityTV.setText(String.valueOf(weatherResponse.getVisibility()/1000)+" km");
                    currentPressureTV.setText(String.valueOf(weatherResponse.getMain().getPressure())+" hPa");
                    currentTemp1TV.setText(String.valueOf(weatherResponse.getMain().getTemp()) + (char) 0x00B0 +"C");
                    currentTempMinTV.setText(String.valueOf(weatherResponse.getMain().getTempMin()) + (char) 0x00B0 +"C");
                    currentTempMaxTV.setText(String.valueOf(weatherResponse.getMain().getTempMax()) + (char) 0x00B0 +"C");

                    Date date1 = new Date(weatherResponse.getSys().getSunrise() * 1000L);
                    DateFormat format1 = new SimpleDateFormat("hh:mm:ss a");
                    format1.setTimeZone(TimeZone.getDefault());
                    String formatted1 = format1.format(date1);

                    Date date2 = new Date(weatherResponse.getSys().getSunset() * 1000L);
                    DateFormat format2 = new SimpleDateFormat("hh:mm:ss a");
                    format2.setTimeZone(TimeZone.getDefault());
                    String formatted2 = format2.format(date2);

                    currentSunriseTV.setText(formatted1);
                    currentSunsetTV.setText(formatted2);
                    currentWindTV.setText(String.valueOf(weatherResponse.getWind().getSpeed()+" m/s"));

                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }
        });
    }

}
