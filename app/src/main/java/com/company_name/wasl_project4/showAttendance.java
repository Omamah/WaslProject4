package com.company_name.wasl_project4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.company_name.wasl_project4.activity.Attendance;
import com.company_name.wasl_project4.activity.FirebaseAdapterChecklist;
import com.company_name.wasl_project4.activity.qr_scanner;
import com.company_name.wasl_project4.activity.userAttended;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showAttendance extends AppCompatActivity {
    Button scan;
    String eventID;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAdapterChecklist allDataAdapter;
    private DatabaseReference mDatabase;
    private List<userAttended> mUserList = new ArrayList<>();
    private TextView attended;
    private TextView absent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b1 = getIntent().getExtras();
        eventID = b1.getString("eventIDscanned");
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_show_attendance);


        //init
        recyclerView = findViewById(R.id.list);
        scan= (Button)findViewById(R.id.scanAttendance);
        attended= (TextView)findViewById(R.id.attendCount);
        absent=(TextView)findViewById(R.id.absentCount);




        FirebaseDatabase.getInstance()
                .getReference("attendance")
                 .child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Attendance attendanceTemp =dataSnapshot.getValue(Attendance.class);
                // we get the values from snapshot b y using getValue in the
                // format of User class which the database record object in the same format

                if(attendanceTemp==null)
                {
                    Toast.makeText(showAttendance.this, "The is no registration for this event ", Toast.LENGTH_LONG).show();
                }
                else
                {
                     int absentsNum= attendanceTemp.getAbsent();
                     int attendedNumb =  attendanceTemp.getAttended();
                     attended.setText(String.valueOf(attendedNumb));
                     absent.setText(String.valueOf(absentsNum));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });







        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(showAttendance.this, qr_scanner.class);
                startActivity(i);
            }
        });


        initView();

    }

    private void initView() {

        FirebaseDatabase.getInstance()
                .getReference("attendance")
                .child(eventID).child("userAttendedList");
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("attendance").child(eventID).child("userAttendedList");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    userAttended user = dataSnapshot1.getValue(userAttended.class);
                    mUserList.add(user);
                }

                allDataAdapter = new FirebaseAdapterChecklist(showAttendance.this, mUserList);
                recyclerView.setAdapter(allDataAdapter);
                allDataAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public LinearLayout root;
//        public TextView name;
//        public CheckBox attended;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            root = itemView.findViewById(R.id.list_root);
//            name = itemView.findViewById(R.id.nameAttendance);
//            attended = itemView.findViewById(R.id.checkBoAttended);
//        }
//
//        public void setName(String string) {
//            name.setText(string);
//        }
//
//
//        public void setCheckbox(Boolean attendedOrNot) {
//            attended.setChecked(attendedOrNot);
//        }
//    }

//    private void fetch() {
//        Query query = FirebaseDatabase.getInstance()
//                .getReference("attendance")
//                .child(eventID).child("userAttendedList");
//
//        FirebaseRecyclerOptions<ArrayList> options =
//                new FirebaseRecyclerOptions.Builder<ArrayList>()
//                        .setQuery(query, new SnapshotParser<userAttended>() {
//                            @NonNull
//                            @Override
//                            public userAttended parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                System.out.println(snapshot.child("name").getValue().toString() +"NAAME");
//                                return new ArrayList<userAttended>();
//                            }
//                        })
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<ArrayList, ViewHolder>(options) {
//            @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.attendance_card, parent, false);
//                return new ViewHolder(view);
//            }
//
//
//            @Override
//            protected void onBindViewHolder(ViewHolder holder, final int position, userAttended model) {
//                holder.setName(model.getName());
//                holder.setCheckbox(Boolean.parseBoolean(model.isAttended()));
//
//                System.out.println("HERE IS LIST "+model.getName() +"    "+model.isAttended());
//
//
//                holder.root.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(showAttendance.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//        };
//    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }

}
