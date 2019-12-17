package com.company_name.wasl_project4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.company_name.wasl_project4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class get_images extends HomePage {



    ImageView event_img;
    TextView for_txt;
    TextView adress_txt;
    TextView  Time_txt;
    TextView Desc_txt;


    String selectedKey = null;


    private DatabaseReference mDatabase;
    private Button deleteBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_images);

//
//        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); // this is the FrameLayout area within your activity_main.xml
//        getLayoutInflater().inflate(R.layout.activity_get_images, contentFrameLayout);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getMenu().getItem(0).setChecked(true);


        event_img = (ImageView)findViewById(R.id.event_img);
        for_txt = findViewById(R.id.for_txt);
        adress_txt = findViewById(R.id.adress_txt);
        Time_txt = findViewById(R.id.Time_txt);
        Desc_txt = findViewById(R.id.Desc_txt);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);

        mDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        mAuth = FirebaseAuth.getInstance();

        selectedKey = getIntent().getExtras().getString("EventID");

        deleteBtn.setVisibility(View.INVISIBLE);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(selectedKey).removeValue();

                Intent eventsHome = new Intent(get_images.this, homeEvent.class);
                startActivity(eventsHome);
            }
        });


        mDatabase.child(selectedKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String event_desc = (String) dataSnapshot.child("desc").getValue();
                String event_image = (String) dataSnapshot.child("imageUrl").getValue();
                String event_time = (String) dataSnapshot.child("time").getValue();
                String event_for = (String) dataSnapshot.child("for").getValue();
                String event_address = (String) dataSnapshot.child("adress").getValue();

                String post_uid = (String) dataSnapshot.child("organizer").getValue();

                for_txt.setText((String.valueOf(event_for)));
                adress_txt.setText((String.valueOf(event_address)));
                Time_txt.setText((String.valueOf(event_time)));
                Desc_txt.setText((String.valueOf(event_desc)));

                Picasso.with(get_images.this).load(event_image).into(event_img);

                if (mAuth.getCurrentUser().getUid().equals(post_uid)){
                    deleteBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        }


    }
