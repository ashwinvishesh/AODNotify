package com.cynix.ashwin.aodnotify;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class AODActivity extends AppCompatActivity
{
    int verticalBias;
    boolean isVisible;

    SQLiteDatabase db;
    DBHelper dbh;



    Handler handler = new Handler();
    Runnable r;
    int burnProtectionDelay = 240000;

    public static NotiRecyclerAdapter adapter;
    RecyclerView notificationIconRecyclerView;
    public static List<NotificationItemInformation> data = Collections.EMPTY_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aod);
        isVisible = true;

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        dbh = new DBHelper(this);
        db = dbh.getDb();
        notificationIconRecyclerView = (RecyclerView) findViewById(R.id.notificationIconRecyclerView);
        data = dbh.ReadNotilist(this);
        adapter = new NotiRecyclerAdapter(this,data);
        notificationIconRecyclerView.setAdapter(adapter);
        notificationIconRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));




        r = new Runnable()
        {
            @Override
            public void run()
            {
                Log.e("Runnable ", "RUNNING");
                if (isVisible)
                {
                    BurnProtection();
                    handler.postDelayed(this, burnProtectionDelay);
                }
            }
        };
        handler.postDelayed(r, burnProtectionDelay);



    }

    public void BurnProtection()
    {
        ConstraintSet cs = new ConstraintSet();
        cs.clone(AODActivity.this, R.layout.activity_aod);
        Random r = new Random();
        verticalBias = 3 + r.nextInt(7);
        Log.e("Vertical Bias  ", " " + verticalBias);
        cs.setVerticalBias(R.id.ClockViewBox, (float) verticalBias / 10);
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.aodConstraintLayout);
        cs.applyTo(cl);
    }

    @Override
    protected void onPause()
    {
        Log.e("onPause ", " CALLED");
        super.onPause();
        isVisible = false;
        handler.removeCallbacks(r);
    }

    @Override
    protected void onResume()
    {
        Log.e("onResume ", " CALLED");
        super.onResume();
        isVisible = true;
        handler.removeCallbacks(r);
        handler.postDelayed(r,burnProtectionDelay);
    }

    @Override
    protected void onRestart()
    {

        Log.e("onRestart ", " CALLED");
        super.onRestart();
        isVisible = true;
    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(this,"Back Button Pressed",Toast.LENGTH_SHORT).show();
    }

 /*   public List<NotificationItemInformation> getRecyclerData()
    {
        Log.e("getRecyclerData ", " Called ");
        List<NotificationItemInformation>  data = new ArrayList<>();
        data = dbh.ReadNotilist(this);

        Context remotePackageContext = null;
        NotificationItemInformation current = null;

        for (int i = 0 ; i < data.size() ; i++)
        {
            current = data.get(i);
            try
            {
                remotePackageContext = getApplicationContext().createPackageContext(current.packageName, 0);
                data.get(i).notiIcon = remotePackageContext.getResources().getDrawable(current.iconId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }*/
}