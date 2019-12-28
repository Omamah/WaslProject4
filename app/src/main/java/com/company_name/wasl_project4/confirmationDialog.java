package com.company_name.wasl_project4;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.company_name.wasl_project4.activity.Attendance;
import com.company_name.wasl_project4.activity.qr_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import androidmads.library.qrgenearator.QRGEncoder;

@SuppressLint("ValidFragment")
public class confirmationDialog extends AppCompatDialogFragment {

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    //FOr Getting Current User Info
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    FirebaseUser user;
    private String userID;
    private ImageView qrCodeImageView;//change place
    private Bitmap bitmap;//change place
    private QRGEncoder qrgEncoder;//change place


    private boolean isComfirmed;
    private String eventID;

    @SuppressLint("ValidFragment")
    public confirmationDialog(String eventID) {
        this.eventID=eventID;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.confirmation_dialog,null);


         mDatabaseRef = FirebaseDatabase.getInstance().getReference("attendance");

         //getting user info
        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID =user.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Users").child(userID);
        Log.d("Database ", "showData: USER ID IS : " + userID);
        Log.d("Database ", "showData: MyRef  IS : " + myRef);

        builder.setView(view)
                .setTitle("Confirmation")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isComfirmed=false;
                        //do nothing
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    isComfirmed=true;
                    createRegestration();
                    }

                });

        return builder.create();
    }

    public void createRegestration(){
        // Creating Bundle object
        Bundle b = new Bundle();
        String qrCode= userID+eventID.trim();




        mDatabaseRef.child(eventID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            // this listener return the snapshot as acopy of current changed record in firebase database  throgh dataSnapshot object
            {
                Attendance  attendanceTemp =dataSnapshot.getValue(Attendance.class); // we get the values from snapshot b y using getValue in the
                // format of User class which the database record object in the same formate

                if(attendanceTemp==null)
                {    Attendance attendance = new Attendance();
                        attendance.setEventId(eventID);
                        attendance.setCounter();
                        attendance.getUsersToQR().put(userID, qrCode);

                        // Storing data into bundle
                        b.putString("qrCode", qrCode);

                        mDatabaseRef.child(eventID).setValue(attendance);
                        Log.d("EEERRE", "onDataChange:  if this is NEW");

                    }

                else
                {

                    Attendance attendance=attendanceTemp;
                    if(!attendance.getUsersToQR().containsKey(userID)) {
                        attendance.setCounter();}//to update the attendance counter if user is new attender
                    attendance.getUsersToQR().put(userID,qrCode);

                    // Storing data into bundle
                    b.putString("qrCode", qrCode);



                    mDatabaseRef.child(eventID).setValue(attendance);
                    Log.d("EEERRE", "onDataChange:  else this is not new it's updated ");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Creating Intent object
        Intent intent = new Intent(getContext(), qr_activity.class);
        b.putString("qrCode", qrCode);

        // Storing bundle object into intent
        intent.putExtras(b);
        startActivity(intent);
    }


//        Bundle b1 = getIntent().getExtras();

//        FirebaseDatabase.getInstance().getReference("Upload").getChild("").setValue(attendance).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(getActivity(), "Event Register successfully", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(getActivity(), "Event Register Does not Register ", Toast.LENGTH_LONG).show();
//                }
//            }
//        });


    }







