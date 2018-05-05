package com.tour_log.tourlog.nearby;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tour_log.tourlog.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;

    private FusedLocationProviderClient client;
    private Location lastLocation;

    private GeoDataClient geoDataClient;
    private PlaceDetectionClient placeDetectionClient;


    private ViewPager mapViewPager;
    private TabLayout mapTabLayout;
    private TabPageAdapter mapTabPageAdapter;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_nearby, container, false);


        mapViewPager = view.findViewById(R.id.mapViewPager);
        mapTabLayout = view.findViewById(R.id.mapTabLayout);

        mapTabLayout.addTab(mapTabLayout.newTab().setText("Map view"));
        mapTabLayout.addTab(mapTabLayout.newTab().setText("Near by"));
        mapTabPageAdapter = new TabPageAdapter(getChildFragmentManager(),mapTabLayout.getTabCount());
        mapViewPager.setAdapter(mapTabPageAdapter);

        mapTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mapViewPager.setCurrentItem(tab.getPosition(),true);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mapViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mapTabLayout));

        return view;
    }
    public class TabPageAdapter  extends FragmentPagerAdapter {

        private int tabCount;
        public TabPageAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new NearbyFragmentOne();
                case 1:
                    return new NearbyFragmentTwo();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

    private void getLastLocation() {

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }
        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    lastLocation = task.getResult();
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 17));


                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

    }


}
