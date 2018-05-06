package com.tour_log.tourlog.mytrip;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tour_log.tourlog.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MyTripFragment extends Fragment {

    private RecyclerView eventRV;
    private ArrayList<MyEvents> events = new ArrayList<>();
    private MyEventAdapter eventAdapter;

    public ImageButton date_pick;
    private Calendar calendar;
    private int year,month,day;
    public String finalDate;

    private DatabaseReference rootReference;
    private FirebaseUser user;

    public MyTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_trip, container, false);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.isNoEvent);
        layout.setVisibility(View.VISIBLE);

        date_pick =(ImageButton) view.findViewById(R.id.date_btn);


        eventRV = view.findViewById(R.id.eventRV);
        events = MyEvents.getAllEventList();
        eventAdapter= new MyEventAdapter(getContext(),events);

        GridLayoutManager glm = new GridLayoutManager(getContext(),2);
        eventRV.setLayoutManager(glm);
        eventRV.setAdapter(eventAdapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        rootReference = FirebaseDatabase.getInstance().getReference("Events");


        FloatingActionButton event_add_btn = view.findViewById(R.id.add_event_btn);

        event_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addEventDialog();
            }
        });

        return view;
    }



    DatePickerDialog.OnDateSetListener dateListener   = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
            calendar.set(year,month,day_of_month);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            finalDate = sdf.format(calendar.getTime());
            // Toast.makeText(getContext(),"date"+finalDate,Toast.LENGTH_LONG).show();
            // eventDateTV.setText(finalDate);
        }
    };

    public void addEventDialog(){

        final AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Add new Event");

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        LinearLayout ll = (LinearLayout) layoutInflater.inflate(R.layout.event_add_dialog,null,false);

        final EditText eventNameET = ll.findViewById(R.id.event_Name);
        final EditText eventPlaceET = ll.findViewById(R.id.event_place);
        final EditText eventDisET = ll.findViewById(R.id.event_Dis);
        final EditText eventBudgetET = ll.findViewById(R.id.event_Budget);
        final TextView date1TV = ll.findViewById(R.id.date_select);
        final TextView eventDateTV = ll.findViewById(R.id.event_Date);


        // final Button dateSelectBtn = (Button) ll.findViewById(R.id.date_btn);

        dialog.setIcon(R.drawable.events);


        dialog.setView(ll);

        dialog.setPositiveButton("Add Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                String eventName;
                String eventPlace;
                String eventDis;
                String eventBudget;

                eventName = eventNameET.getText().toString();
                eventPlace = eventPlaceET.getText().toString();
                eventDis = eventDisET.getText().toString();
                eventBudget = eventBudgetET.getText().toString();

                String userID = user.getUid();
                String eventID = rootReference.push().getKey();

                MyEvents myEvents = new MyEvents(eventName,eventDis,eventPlace,finalDate,
                        Double.parseDouble(eventBudget),userID,eventID);

                rootReference.child(eventID).setValue(myEvents);

            }
        });

        dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dialogInterface.dismiss();

            }
        });
        dialog.setCancelable(false);

        dialog.show();


        calendar = Calendar.getInstance();
        year= calendar.get(Calendar.YEAR);
        month= calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);


        eventDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),dateListener,year,month,day);
                datePickerDialog.show();
            }
        });




    }



}
