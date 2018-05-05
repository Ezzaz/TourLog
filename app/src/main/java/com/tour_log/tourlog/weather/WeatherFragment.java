package com.tour_log.tourlog.weather;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tour_log.tourlog.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private ViewPager weatherViewPager;
    private TabLayout weatherTabLayout;
    private WeatherTabPageAdapter tabPageAdapter;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_weather, container, false);

        weatherViewPager = view.findViewById(R.id.weatherViewPager);
        weatherTabLayout = view.findViewById(R.id.weatherTabLayout);

        weatherTabLayout.addTab(weatherTabLayout.newTab().setText("Current "));
        weatherTabLayout.addTab(weatherTabLayout.newTab().setText("Forecast "));

        tabPageAdapter = new WeatherTabPageAdapter(getChildFragmentManager(),weatherTabLayout.getTabCount());

        weatherViewPager.setAdapter(tabPageAdapter);

        weatherTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                weatherViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        weatherViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(weatherTabLayout));



        return view;
    }

    public class WeatherTabPageAdapter extends FragmentPagerAdapter {

        private int totalTab;
        public WeatherTabPageAdapter(FragmentManager fm , int totalTab) {
            super(fm);
            this.totalTab = totalTab;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new WeatherFragmentOne();
                case 1:
                    return new WeatherFragmentTwo();
            }

            return null;
        }

        @Override
        public int getCount() {
            return totalTab;
        }
    }


}
