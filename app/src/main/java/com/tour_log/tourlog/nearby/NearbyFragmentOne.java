package com.tour_log.tourlog.nearby;


import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tour_log.tourlog.MainActivity;
import com.tour_log.tourlog.R;

import java.io.IOException;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragmentOne extends Fragment implements OnMapReadyCallback {


    private GoogleMap map;
    private int countMarker = 0;
    private LatLng start,end;

    public NearbyFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearby_fragment_one, container, false);

        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_TERRAIN);
        options.zoomControlsEnabled(true);
        options.compassEnabled(true);
        //options.ambientEnabled(true);
        //options.liteMode(true);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(options);

        FragmentTransaction ft = getFragmentManager().beginTransaction()
                .replace(R.id.map_container, mapFragment);

        ft.commit();
        mapFragment.getMapAsync(this);

       /* map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(HomeActivity.lastLocation.getLatitude(), HomeActivity.lastLocation.getLongitude()), 17));*/

        final FloatingSearchView floatingSearchView = view.findViewById(R.id.floating_search_view);
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
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(address.getLatitude(), address.getLongitude()), 15));
                        floatingSearchView.clearQuery();

                    }

                    else {
                        floatingSearchView.clearQuery();
                        Toast.makeText(getContext(),"Enter Valid Location",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        FloatingActionButton mylocation = view.findViewById(R.id.map_mylocation);

        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(MainActivity.lastLocation.getLatitude(), MainActivity.lastLocation.getLongitude()), 15));
            }
        });



        FloatingTextButton floatingTextButton = view.findViewById(R.id.map_direction);
        floatingTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(MainActivity.lastLocation.getLatitude(), MainActivity.lastLocation.getLongitude()), 15));
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},261);

            return;
        }
        map.setMyLocationEnabled(true);

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if(countMarker == 0){
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Start Point"));
                    countMarker++;
                    start = latLng;
                }
                else if(countMarker == 1){
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("End Point"));
                    countMarker++;
                    end = latLng;
                }

            }
        });


    }

}
