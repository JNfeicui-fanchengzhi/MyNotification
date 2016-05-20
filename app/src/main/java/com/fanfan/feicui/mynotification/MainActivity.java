package com.fanfan.feicui.mynotification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton;
    private Notification mNotification;
    private NotificationManager mManager;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton= (Button) findViewById(R.id.btn_show);
        mButton.setOnClickListener(this);
        mManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show:
                Intent intent=new Intent(MainActivity.this,OtherActivity.class);
                PendingIntent pi=PendingIntent.getActivity(MainActivity.this,0,intent,0);
                Notification.Builder builder=new Notification.Builder(this);
                builder.setContentTitle("通知标题")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("通知内容")
                        .setTicker("通知来了")
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pi)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true);
                mNotification=builder.build();
                mManager.notify(1,mNotification);

                break;
        }
    }
}
