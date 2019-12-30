package com.company_name.wasl_project4.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company_name.wasl_project4.R;
import com.company_name.wasl_project4.showAttendance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class qr_scanner extends HomePage implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    private TextView txtResult;
    DatabaseReference mDatabaseRef;
    String qrCode;
    String attendedUser;
    String eventID;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); // this is the FrameLayout area
        getLayoutInflater().inflate(R.layout.activity_qr_scanner, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);


         mDatabaseRef = FirebaseDatabase.getInstance().getReference("attendance");


        //init
        scannerView=(ZXingScannerView) findViewById(R.id.zxscan);
        txtResult=(TextView)findViewById(R.id.txt_result);

        //request permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(qr_scanner.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(qr_scanner.this, "You must accept this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        //here we recive  Raw result
        scannerView.startCamera();

        try{
         qrCode=rawResult.getText();
         eventID= qrCode.substring(qrCode.indexOf("-"), qrCode.length()).trim();
         attendedUser= qrCode.substring( 0, qrCode.indexOf("-")).trim();
            FirebaseDatabase.getInstance().getReference("Users").child(attendedUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {
                    UserClass user =dataSnapshot.getValue(UserClass.class);
                    if(user!=null)
                    {
                        name=user.getUsername();
                        System.out.println(" HERE IS attendance name   "+name);

                    }
                    else
                    {//no user
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

         System.out.println(" HERE IS RESULT   "+attendedUser);
            System.out.println(" HERE IS RESULT   "+eventID);


            mDatabaseRef.child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            // this listener return the snapshot as acopy of current changed record in firebase database  throgh dataSnapshot object
            {
                Attendance  attendanceTemp =dataSnapshot.getValue(Attendance.class);
                // we get the values from snapshot b y using getValue in the
                // format of User class which the database record object in the same format

                if(attendanceTemp==null)
                {
                    Toast.makeText(qr_scanner.this, "User isn't registered to this event", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(!attendanceTemp.AttendedUsers.get(attendedUser)) {
                        Integer Id= attendanceTemp.listIdtoUsers.get(attendedUser);
                        System.out.println(name);
                        userAttended userRegestered = new userAttended(name, "true");
                        userRegestered.setId(Id);
                        attendanceTemp.userAttendedList.set(Id,userRegestered);
                        attendanceTemp.AttendedUsers.put(attendedUser,true);//set user to be attended
                        attendanceTemp.updateAbsent();
                        attendanceTemp.updateAttendded();
                        mDatabaseRef.child(eventID).setValue(attendanceTemp);
                        Toast.makeText(qr_scanner.this, "User attended scanned ", Toast.LENGTH_LONG).show();
                        txtResult.setText("Scanned");
                    }



                    Bundle b1 = new Bundle();
                    b1.putString("eventIDscanned", eventID);
                    b1.putString("userIDscanned", attendedUser );

                    Intent i= new Intent(qr_scanner.this, showAttendance.class);
                    i.putExtras(b1);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        }

        catch (Exception e){
            Toast.makeText(this, "This is not a Wasl QR", Toast.LENGTH_SHORT).show();

            Intent i= new Intent(qr_scanner.this, showAttendance.class);
            startActivity(i);

        }






    }

    private void processRawResult(String text) {

    }
}
