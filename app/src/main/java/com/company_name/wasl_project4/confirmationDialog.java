package com.company_name.wasl_project4;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.company_name.wasl_project4.activity.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

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


    private boolean isComfirmed;
    private String event;

    @SuppressLint("ValidFragment")
    public confirmationDialog(String event) {
        this.event=event;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.confirmation_dialog,null);


         mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

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
                    createRegestration(event);
                    }

                });

        return builder.create();
    }

    public void createRegestration(String Event){

        Upload  upload = new Upload();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Upload.getUsersToQR().put("QR CODE HERE", userId);

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





    }

