package com.tour_log.tourlog.nearby;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tour_log.tourlog.MainActivity;
import com.tour_log.tourlog.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragmentTwo extends Fragment {

    private Spinner typeSP;
    private Spinner distSP;

    private RecyclerView listview;
    private List<MapNearbyResponse.Result> nearbyDatalist;
    private MapNearbyAdapter adapter;
    private Context context;

    public static final String NEARBY_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private String type ;
    private String dist ;
    private MapNearbyService mapNearbyService;

    private Button search_btn;


    public NearbyFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_nearby_fragment_two, container, false);

        typeSP = (Spinner) v.findViewById(R.id.typeSP);
        distSP = (Spinner) v.findViewById(R.id.distSP);

        search_btn = (Button) v.findViewById(R.id.nearbySearch);


        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,ContainGenerator.getTypes());
        final ArrayAdapter<String> distAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,ContainGenerator.getDist());
        typeSP.setAdapter(typeAdapter);
        distSP.setAdapter(distAdapter);


        typeSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(),adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                switch (i){
                    case 0:
                        type="restaurant";
                        break;
                    case 1:
                        type="bank";
                        break;
                    case 2:
                        type="atm";
                        break;
                    case 3:
                        type="hospital";
                        break;
                    case 4:
                        type="shopping_mall";
                        break;
                    case 5:
                        type="mosque";
                        break;
                    case 6:
                        type="bus_station";
                        break;
                    case 7:
                        type="police";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type="restaurant";
            }
        });

        distSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        dist = String.valueOf(500);
                        break;
                    case 1:
                        dist = String.valueOf(1000);
                        break;
                    case 2:
                        dist = String.valueOf(1500);
                        break;
                    case 3:
                        dist = String.valueOf(2000);
                        break;
                    case 4:
                        dist = String.valueOf(3000);
                        break;
                    case 5:
                        dist = String.valueOf(4000);
                        break;
                    case 6:
                        dist = String.valueOf(5000);
                        break;
                    case 7:
                        dist = String.valueOf(10000);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dist = String.valueOf(2000);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listview = (RecyclerView) v.findViewById(R.id.nearbyList);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(NEARBY_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                mapNearbyService = retrofit.create(MapNearbyService.class);

                Call<MapNearbyResponse> nearbyResponseCall = mapNearbyService.getNearbyPlacesesData(
                        String.valueOf(MainActivity.lastLocation.getLatitude()+","+MainActivity.lastLocation.getLongitude()),
                        dist,
                        type,
                        getString(R.string.nearby_API_Key)
                );

                nearbyResponseCall.enqueue(new Callback<MapNearbyResponse>() {
                    @Override
                    public void onResponse(Call<MapNearbyResponse> call, Response<MapNearbyResponse> response) {
                        TextView nodataFoundTV;
                        nodataFoundTV = v.findViewById(R.id.nearbyNotFound);
                        if(response.code()==200){
                            MapNearbyResponse nearbyData = response.body();
                            nearbyDatalist = nearbyData.getResults();
                            context = getContext();

                            adapter = new MapNearbyAdapter(context,nearbyDatalist);
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            listview.setLayoutManager(llm);
                            listview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (nearbyDatalist.size() == 0){
                                nodataFoundTV.setVisibility(v.VISIBLE);
                                nodataFoundTV.setText("No "+type+" found within "+String.valueOf(Float.valueOf(dist)/1000)+"km");
                            }
                            else {
                                nodataFoundTV.setVisibility(v.GONE);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MapNearbyResponse> call, Throwable t) {
                    }
                });
            }
        });


        return v;
    }

    public static final class ContainGenerator{
        public static ArrayList<String> getTypes(){
            ArrayList<String> types = new ArrayList<>();
            types.add(" Restaurant ");
            types.add(" Bank ");
            types.add(" ATM ");
            types.add(" Hospital ");
            types.add(" Shopping Mall ");
            types.add(" Mosque ");
            types.add(" Bus Station ");
            types.add(" Police Station ");
            return types;
        }

        public static ArrayList<String>getDist(){
            ArrayList<String> types = new ArrayList<>();
            types.add(" 0.5 Km ");
            types.add(" 1.0 Km ");
            types.add(" 1.5 Km ");
            types.add(" 2.0 Km ");
            types.add(" 3.0 Km ");
            types.add(" 4.0 Km ");
            types.add(" 5.0 Km ");
            types.add(" 10.0 Km ");
            return types;
        }
    }


}
