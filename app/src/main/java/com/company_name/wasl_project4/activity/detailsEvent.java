package com.company_name.wasl_project4.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company_name.wasl_project4.R;
import com.company_name.wasl_project4.confirmationDialog;

public class detailsEvent extends AppCompatActivity {


    private ImageView event_img;
    private TextView for_txt;
    private TextView adress_txt;
    private TextView  Time_txt;
    private TextView Desc_txt;
    private String for_whom;
    private String Adress;
    private String Time;
    private String Desc;
    private FloatingActionButton fab;



    String img_uri;
    String selectedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event);


        event_img = (ImageView) findViewById(R.id.event_img);
        for_txt = findViewById(R.id.for_txt);
        adress_txt = findViewById(R.id.adress_txt);
        Time_txt = findViewById(R.id.Time_txt);
        Desc_txt = findViewById(R.id.Desc_txt);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        //registration {fab} event listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b1 = getIntent().getExtras();
                openDialog(b1.getString("Key"));
            }
        });


        init();

        }

    public void init() {
        // String uri ="https://firebasestorage.googleapis.com/v0/b/myapp-5e601.appspot.com/o/uploads%2F1556376298097.jpg?alt=media&token=7cf1ef7c-27df-49de-bd4e-86893fc42879";
        //String ur ="https://myapp-5e601.firebaseio.com/uploads";

        //get event info from the firebase and fill widgets with
        Bundle b1 = getIntent().getExtras();
        if (!b1.isEmpty()) {
            img_uri = b1.getString("img");
            for_whom = b1.getString("For");
            Adress = b1.getString("Adress");
            Time = b1.getString("Time");
            Desc = b1.getString("Desc");
            selectedKey = b1.getString("Key");
            for_txt.setText((String.valueOf(for_whom)));
            adress_txt.setText((String.valueOf(Adress)));
            Time_txt.setText((String.valueOf(Time)));
            Desc_txt.setText((String.valueOf(Desc)));


            Log.d("View", "IMAGE URL IS:" + img_uri);
            Glide.with(getApplicationContext()).load(img_uri).into(event_img);

    }
}

    public void openDialog(String eventID){
        confirmationDialog dialog = new confirmationDialog(eventID);
        dialog.show(getSupportFragmentManager(),"Confirmation Dialog");
    }


}
