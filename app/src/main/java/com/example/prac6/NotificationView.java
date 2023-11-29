package com.example.prac6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        Calendar calender=Calendar.getInstance();
        Date currentTime=calender.getTime();

        calender.add(Calendar.MINUTE,30);
        Date deliveryTime=calender.getTime();

        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTimeString=sdf.format(currentTime);
        String deliveryTimeString=sdf.format(deliveryTime);

        TextView current=findViewById(R.id.time);
        TextView delivery=findViewById(R.id.delivery);
        current.setText("Time:"+currentTimeString);
        delivery.setText("Your order will be delivered at\n "+deliveryTimeString);

    }
}