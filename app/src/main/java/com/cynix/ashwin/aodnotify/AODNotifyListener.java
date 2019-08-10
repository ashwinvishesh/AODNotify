package com.cynix.ashwin.aodnotify;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;


public class AODNotifyListener extends NotificationListenerService
{

    String name = "NotilightService";
    SQLiteDatabase db;
    DBHelper dbh;
    BroadcastReceiver myBR;


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(name,"onCreate Called");
        dbh = new DBHelper(this);
        db = dbh.getDb();
        myBR = new ScreenOffBroadcastReciever();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        registerReceiver(myBR,intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(name,"onBind Called");
        return super.onBind(intent);

    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn)
    {
        String packageName = sbn.getPackageName();
        int notificationStatusBarIconId = sbn.getNotification().icon;
        int notificationID = sbn.getId();
        Log.e(name, packageName + ", IconId :"+notificationStatusBarIconId+ ", ID: "+ notificationID  );
      //  Toast.makeText(this, packageName + ", IconId :"+notificationStatusBarIconId+ ", ID: "+ notificationID , Toast.LENGTH_SHORT).show();
        dbh.InsertNewNotification(notificationID,notificationStatusBarIconId,packageName);
        AODActivity.data.clear();
        AODActivity.data.addAll(dbh.ReadNotilist(this));
        AODActivity.adapter.notifyDataSetChanged();

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn)
    {
        String packageName = sbn.getPackageName();
        Log.e(name,"package removed : " + packageName);
        dbh.RemoveNotification(packageName);
        AODActivity.data.clear();
        AODActivity.data = dbh.ReadNotilist(this);
        AODActivity.adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(myBR);
    }
}
