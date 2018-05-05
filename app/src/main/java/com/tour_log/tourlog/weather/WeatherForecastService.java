package com.tour_log.tourlog.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by biplab on 19-Mar-18.
 */

public interface WeatherForecastService {

    @GET("forecast?")
    Call<WeatherForecastResponse> getCurrentForecastWeatherData(@Query("lat")String lat,
                                                                @Query("lon")String lon,
                                                                @Query("units")String units,
                                                                @Query("appid")String appid);
}
