package com.company_name.wasl_project4.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.company_name.wasl_project4.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class homeEvent extends HomePage  {


    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;

    private String orgnizerName;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference  usersRef;
    private ValueEventListener mDBListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_home, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);



        //initialize recyclerview and Firebase objects
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter(homeEvent.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(homeEvent.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mAuth = FirebaseAuth.getInstance();

        usersRef = FirebaseDatabase.getInstance().getReference("Users");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged() {
                onAuthStateChanged();
            }

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser()==null){
                    Intent loginIntent = new Intent(homeEvent.this, SignInActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };


        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(homeEvent.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Upload, UploadViewHolder> FBRA = new FirebaseRecyclerAdapter<Upload, UploadViewHolder>(
                Upload.class,
                R.layout.image_item,
                UploadViewHolder.class,
                mDatabaseRef
        ) {
            @Override
            protected void populateViewHolder(UploadViewHolder viewHolder, Upload model, int position) {
                final String post_key = getRef(position).getKey().toString();
                viewHolder.setTitle(model.getName());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImageUrl(getApplicationContext(), model.getImageUrl());

                usersRef.child(model.getOrganizer()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        orgnizerName = (String) dataSnapshot.child("name").getValue();
//                         orgnizerImgUrl=(String) dataSnapshot.child("").getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                viewHolder.setUserName(orgnizerName);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singlEvent = new Intent(homeEvent.this, get_images.class);
                        singlEvent.putExtra("EventID", post_key);
                        startActivity(singlEvent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(FBRA);
    }

    public static class UploadViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UploadViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView textViewName = itemView.findViewById(R.id.text_view_name);
            textViewName.setText(title);
        }
        public void setDesc(String desc){
            TextView post_desc = mView.findViewById(R.id.post_descreption);
            post_desc.setText(desc);
        }
        public void setImageUrl(Context ctx, String imageUrl){
            ImageView imageView = itemView.findViewById(R.id.image_view_upload);
            Picasso.with(ctx).load(imageUrl).into(imageView);
        }
        public void setUserName(String userName){
            TextView postUserName = mView.findViewById(R.id.orgnizer);
            postUserName.setText(userName);
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
