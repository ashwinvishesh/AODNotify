package com.cynix.ashwin.aodnotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ashwi on 01-11-2017.
 */

public class ScreenOffBroadcastReciever extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("Broadcast Reciever" , " TRIGGERED");
        Intent AODintent = new Intent(context,AODActivity.class);
        context.startActivity(AODintent);
    }
}