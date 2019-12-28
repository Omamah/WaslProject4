package com.company_name.wasl_project4.activity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.company_name.wasl_project4.R;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class qr_activity extends AppCompatActivity {
    private ImageView qrCodeImageView;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private TextView textView;

    String qrCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_activity);

        qrCodeImageView=(ImageView) findViewById(R.id.qrcode);
        textView=(TextView) findViewById(R.id.qrMessage) ;
        Bundle b1 = getIntent().getExtras();

        qrCode = b1.getString("qrCode");



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(qrCode.length()>0){
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display =manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);

            int width = point.x;
            int height= point.y;
            int smallerdimension= width<height? width:height;

            smallerdimension=smallerdimension*3/4;

            qrgEncoder= new QRGEncoder(qrCode, null, QRGContents.Type.TEXT,smallerdimension);

            try{
                bitmap=qrgEncoder.encodeAsBitmap();
                qrCodeImageView.setImageBitmap(bitmap);


            }
            catch (WriterException e){
                Log.v("Genrator QR", e.toString());

        }
        }
        else
        {
            textView.setText("You are not registered");
        }
    }
}
