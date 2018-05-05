package com.tour_log.tourlog.forums;


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
public class ForumsFragment extends Fragment {

    private ViewPager forumsViewPager;
    private TabLayout forumsTabLayout;

    private ForumsTabPageAdapter tabPageAdapter;

    public ForumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forums, container, false);

        forumsViewPager = view.findViewById(R.id.forumsViewPager);
        forumsTabLayout = view.findViewById(R.id.forumsTabLayout);

        forumsTabLayout.addTab(forumsTabLayout.newTab().setText("public review"));
        forumsTabLayout.addTab(forumsTabLayout.newTab().setText("my review"));


        tabPageAdapter = new ForumsTabPageAdapter(getChildFragmentManager(),forumsTabLayout.getTabCount());

        forumsViewPager.setAdapter(tabPageAdapter);

        forumsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                forumsViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        forumsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(forumsTabLayout));


        return view;

    }

    public class ForumsTabPageAdapter extends FragmentPagerAdapter {

        private int totalTab;
        public ForumsTabPageAdapter(FragmentManager fm , int totalTab) {
            super(fm);
            this.totalTab = totalTab;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ForumsFragmentOne();
                case 1:
                    return new ForumsFragmentTwo();
            }

            return null;
        }

        @Override
        public int getCount() {
            return totalTab;
        }
    }


}
