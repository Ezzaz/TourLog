package com.tour_log.tourlog.mytrip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tour_log.tourlog.R;

import java.util.ArrayList;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.MyEventView> {
    private Context context;
    private ArrayList<MyEvents> events;

    public MyEventAdapter(Context context, ArrayList<MyEvents> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public MyEventView onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.event_single_row,parent,false);
        return new MyEventView(v);
    }

    @Override
    public void onBindViewHolder(MyEventView holder, int position) {
        holder.eventImg.setImageResource(events.get(position).getEventImg());
        holder.eventName.setText(events.get(position).getEventName());
        holder.eventDate.setText(events.get(position).getEventDate());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyEventView extends RecyclerView.ViewHolder{

        ImageView eventImg;
        TextView eventName;
        TextView eventDate;

        public MyEventView(View itemView) {
            super(itemView);
            eventImg = itemView.findViewById(R.id.event_row_img);
            eventName = itemView.findViewById(R.id.event_row_name);
            eventDate = itemView.findViewById(R.id.event_row_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    final AlertDialog.Builder dialog;
                    dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Details");

                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    final LinearLayout ll = (LinearLayout) layoutInflater.inflate(R.layout.event_details_row,null,false);

                    final TextView EventDetailsNameTV = ll.findViewById(R.id.eventDetailsName);
                    final TextView EventDetailsDisTV = ll.findViewById(R.id.eventDetailsDis);
                    final TextView EventDetailsBudgetTV = ll.findViewById(R.id.eventDetailsBudget);
                    final EditText EventDetailsBudgetET = ll.findViewById(R.id.eventDetailsBudgetET);
                    final EditText EventDetailsExpET = ll.findViewById(R.id.eventDetailsExpET);

                    final DatabaseReference rootReference;
                    rootReference = FirebaseDatabase.getInstance().getReference("Events");
                    // final MyEvents[] e = new MyEvents[1];
                    final ArrayList<MyEvents> e = new ArrayList<>();

                    double eD_budget =0;
                    double eD_exp =0;
                    final String[] eD_name = {" "};
                    String eD_dis ;
                    String eD_place ;
                    String eD_date ;
                    String eD_uid ;
                    String eD_eid ;

                    rootReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsp : dataSnapshot.getChildren()){
                                String userKey = dsp.getKey();
                                e.add(dsp.getValue(MyEvents.class)) ;
                                Log.e("fffffff", "onDataChange: "+ e.get(position).getEventBudget() );

                            }
                            Log.e("eeeee", "onDataChange: "+ e.get(position).getEventBudget() );


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    EventDetailsNameTV.setText(events.get(position).getEventName().toString());
                    // EventDetailsDisTV.setText(" "+e[position].getEventDetails());
                    EventDetailsBudgetTV.setText("Total Budget : "+events.get(0).getEventBudget());
                    EventDetailsBudgetET.setText(String.valueOf(events.get(0).getEventBudget()));
                    EventDetailsExpET.setText("0");

                    dialog.setIcon(R.drawable.events);
                    dialog.setView(ll);

                    dialog.setNegativeButton("ADD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyEvents myEvents = new MyEvents(EventDetailsNameTV.getText().toString(),
                                    events.get(position).getEventDetails(),
                                    events.get(position).getEventPlace(),
                                    events.get(position).getEventDate(),
                                    Double.parseDouble(EventDetailsBudgetET.getText().toString()+events.get(position).getEventBudget()),
                                    Double.parseDouble(EventDetailsExpET.getText().toString()+events.get(position).getEventExpense()),
                                    events.get(position).getUserId(),
                                    events.get(position).getEventID());


                            rootReference.child(events.get(position).getEventID()).setValue(myEvents);

                        }
                    });

                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                        }
                    });


                    dialog.setCancelable(false);

                    dialog.show();

                }
            });

        }
    }

}
