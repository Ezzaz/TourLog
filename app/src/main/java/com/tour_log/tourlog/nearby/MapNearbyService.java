package com.tour_log.tourlog.nearby;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by biplab on 19-Mar-18.
 */

public interface MapNearbyService {
    @GET("json?")
    Call<MapNearbyResponse> getNearbyPlacesesData(@Query("location")String latlon,
                                                  @Query("radius")String radious,
                                                  @Query("type")String type,
                                                  @Query("key")String appkey);
}
