package com.tour_log.tourlog.gallery;


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
import com.tour_log.tourlog.weather.WeatherFragment;
import com.tour_log.tourlog.weather.WeatherFragmentOne;
import com.tour_log.tourlog.weather.WeatherFragmentTwo;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private ViewPager galleryViewPager;
    private TabLayout galleryTabLayout;
    private GalleryTabPageAdapter tabPageAdapter;


    public GalleryFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryViewPager = view.findViewById(R.id.galleryViewPager);
        galleryTabLayout = view.findViewById(R.id.galleryTabLayout);

        galleryTabLayout.addTab(galleryTabLayout.newTab().setText("Camera "));
        galleryTabLayout.addTab(galleryTabLayout.newTab().setText("Gallery "));

        tabPageAdapter = new GalleryTabPageAdapter(getChildFragmentManager(),galleryTabLayout.getTabCount());

        galleryViewPager.setAdapter(tabPageAdapter);

        galleryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                galleryViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        galleryViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(galleryTabLayout));



        return view;
    }

    public class GalleryTabPageAdapter extends FragmentPagerAdapter {

        private int totalTab;
        public GalleryTabPageAdapter(FragmentManager fm , int totalTab) {
            super(fm);
            this.totalTab = totalTab;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new GalleryFragmentOne ();
                case 1:
                    return new GalleryFragmentTwo ();
            }

            return null;
        }

        @Override
        public int getCount() {
            return totalTab;
        }
    }


}
