package com.tour_log.tourlog.events;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tour_log.tourlog.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class EventCostFragment extends Fragment {

    private Spinner placesSearchSP;
    private Button searchBtn;
    private String searchPlaceName;

    private TextView deptCostTV;
    private TextView arivalCostTV;
    private TextView hotelCostTV;
    private TextView mealCostTV;
    private TextView miniTCostTV;
    private TextView guideTCostTV;
    private LinearLayout ll;

    private RecyclerView RV;
    private ArrayList<PopularEvent> popularEvents;
    private PopularEventAdapter popularEventAdapter;

    public EventCostFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_cost, container, false);
        placesSearchSP = (Spinner) view.findViewById(R.id.placesSearchSP);
        searchBtn = (Button) view.findViewById(R.id.onSearch);

        deptCostTV =(TextView) view.findViewById(R.id.departureCost);
        arivalCostTV =(TextView) view.findViewById(R.id.arrivalCost);
        hotelCostTV =(TextView) view.findViewById(R.id.hotelCost);
        mealCostTV =(TextView) view.findViewById(R.id.mealCost);
        miniTCostTV =(TextView) view.findViewById(R.id.miniTCost);
        guideTCostTV =(TextView) view.findViewById(R.id.guideCost);
        ll = (LinearLayout) view.findViewById(R.id.coutLayout) ;

        ArrayAdapter<String> placesAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,ContainGenerator.getPlaces());
        placesSearchSP.setAdapter(placesAdapter);

        placesSearchSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                searchPlaceName = adapterView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(searchPlaceName =="your location"){
                    Snackbar.make(view, "Please selecte an address !!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    ll.setVisibility(View.VISIBLE);
                    showCost();
                }

            }
        });

        RV =view.findViewById(R.id.popularEventRV);
        popularEvents =PopularEvent.getAllPopularEventList();
        popularEventAdapter =new PopularEventAdapter(getContext(),popularEvents);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        RV.setLayoutManager(llm);
        RV.setAdapter(popularEventAdapter);

        return view;
    }
    public static final class ContainGenerator{
        public static ArrayList<String> getPlaces(){
            ArrayList<EventDetails> eventsCost = new ArrayList<>();
            ArrayList<String> places = new ArrayList<>();
            places.add("your location");
            eventsCost=EventDetails.getAllEventCostList();

            if(eventsCost.size()!=0){

                for(int i = 0; i<eventsCost.size();i++){
                    places.add(eventsCost.get(i).getPlacesName());
                }
            }
            else{
                eventsCost=EventDetails.getAllEventCostList();
                for(int i = 0; i<eventsCost.size();i++){
                    places.add(eventsCost.get(i).getPlacesName());
                }
            }
            return places;
        }

    }

    public void showCost(){
        ArrayList<EventDetails> eventsCost = new ArrayList<>();

        eventsCost=EventDetails.getAllEventCostList();

        for(int i=0; i<eventsCost.size();i++){
            if(searchPlaceName==eventsCost.get(i).getPlacesName()){
                deptCostTV.setText(String.valueOf(eventsCost.get(i).getDepartureCost())+" tk");
                arivalCostTV.setText(String.valueOf(eventsCost.get(i).getArrivalCost())+" tk");
                hotelCostTV.setText(String.valueOf(eventsCost.get(i).getHotelCost())+" tk");
                mealCostTV.setText(String.valueOf(eventsCost.get(i).getMealCost())+" tk");
                miniTCostTV.setText(String.valueOf(eventsCost.get(i).getMiniTransport())+" tk");
                guideTCostTV.setText(String.valueOf(eventsCost.get(i).getGuidCost())+" tk");
            }
        }

    }

}
