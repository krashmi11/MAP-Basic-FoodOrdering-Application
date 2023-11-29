package com.example.prac6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "MyChannel";
    private static final CharSequence CHANNEL_NAME = "My Channel";
    private static final String CHANNEL_DESCRIPTION = "Description for My Channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Double cost=getIntent().getDoubleExtra("Cost",0.0);
        ArrayList<String> al=getIntent().getStringArrayListExtra("items");
        TextView costText=findViewById(R.id.total);
        costText.setText("Order Cost:"+cost);
        TextView itemsText = findViewById(R.id.items);
        StringBuilder itemsStringBuilder = new StringBuilder();
        for (String item : al) {
            itemsStringBuilder.append(item).append("\n");
        }
        itemsText.setText(itemsStringBuilder.toString());
        Button order=findViewById(R.id.placeOrder);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Order");
        builder.setMessage("Are you sure you want to place this order?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked Yes, trigger the notification
                addNotification(CartActivity.this, "FoodyExpress", "Your Order is delivering in 30 minutes.");
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }
    private void addNotification(Context context,String title,String content){
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.bell)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Log.d("Notification", "Notification triggered");
        Intent notificationI=new Intent(this,NotificationView.class);
        notificationI.putExtra("notificationId",0);
        PendingIntent contentIntent=PendingIntent.getActivity(this,0,notificationI,PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(contentIntent);
        notificationManager.notify(0,builder.build());

    }
}