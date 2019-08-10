package com.cynix.ashwin.aodnotify;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import java.text.SimpleDateFormat;
import android.os.BatteryManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Date;


/**
 * Created by ashwi on 26-10-2017.
 */

public class ClockView extends View
{

    private int height,width = 0;
    private int padding = 0;
    private boolean isInit = false;
    private Paint clocktextpaint,ampmtextpaint,datetextpaint,battextpaint;
    private int fontSize;
    private String timeStringH,timeStringM, timeStringTOD,dateString;
    private float centerX,centerY;
    Typeface BebasNeueBold,Valencia,Monoid;
    Drawable BgImage;

    public ClockView(Context context)
    {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        if(!isInit)
        {
            initAOD();
        }
        canvas.drawColor(Color.BLACK);
        drawClock(canvas);
        drawDate(canvas);
        drawBattery(canvas);
        postInvalidateDelayed(1000);
    }

    private void drawBattery(Canvas canvas)
    {
        /** Calculating Battery percentage */

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getContext().registerReceiver(null, ifilter);
        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;
        float batteryPct = level / (float) scale;
        int BatteryPercentage = (int) (batteryPct * 100);


        float batX,batY;
        batX = centerX+15;
        batY = (float) (centerY + (1.4 * fontSize));

        battextpaint.reset();
        battextpaint.setColor(getResources().getColor(R.color.white));
        battextpaint.setTextSize(fontSize / 5);
        battextpaint.setAntiAlias(true);
        battextpaint.setTypeface(BebasNeueBold);
        battextpaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(BatteryPercentage + " %", batX,batY, battextpaint);

        /** FIX THIS PART */

        int batDrawableId;
        if(BatteryPercentage == 100)
            batDrawableId = R.drawable.batteryicon_full;
        else if(BatteryPercentage>90)
            batDrawableId = R.drawable.batteryicon_ten;
        else if (BatteryPercentage>80)
            batDrawableId = R.drawable.batteryicon_nine;
        else if (BatteryPercentage>70)
            batDrawableId = R.drawable.batteryicon_eight;
        else if (BatteryPercentage>60)
            batDrawableId = R.drawable.batteryicon_seven;
        else if (BatteryPercentage>50)
            batDrawableId = R.drawable.batteryicon_six;
        else if (BatteryPercentage>40)
            batDrawableId = R.drawable.batteryicon_five;
        else if (BatteryPercentage>30)
            batDrawableId = R.drawable.batteryicon_four;
        else if (BatteryPercentage>20)
            batDrawableId = R.drawable.batteryicon_three;
        else if (BatteryPercentage>10)
            batDrawableId = R.drawable.batteryicon_two;
        else
            batDrawableId = R.drawable.batteryicon_one;

        Drawable batteryIcon =getResources().getDrawable(batDrawableId);
        batteryIcon.setBounds((int)batX+20,(int)batY-35,(int)batX+40,(int)batY);
        batteryIcon.draw(canvas);
    }


    private void drawDate(Canvas canvas)
    {
        datetextpaint.reset();
        datetextpaint.setAntiAlias(true);
        datetextpaint.setColor(getResources().getColor(R.color.white));
        datetextpaint.setTextSize(fontSize/4);
        datetextpaint.setTypeface(BebasNeueBold);
        datetextpaint.setTextAlign(Paint.Align.CENTER);
        dateString = DateFetchAndFormat("EEE, dd MMMM");
        canvas.drawText(dateString,centerX, (float) (centerY + (1.1 * fontSize)),datetextpaint);
    }

    private void initAOD()
    {
        height = getHeight();
        width = getWidth();
        centerX = width/2f;
        centerY = height/2f;
        //fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,13,getResources().getDisplayMetrics());
        fontSize = 250;

        clocktextpaint = new Paint();
        ampmtextpaint = new Paint();
        datetextpaint = new Paint();
        battextpaint = new Paint();

        BebasNeueBold = Typeface.createFromAsset(getContext().getAssets(),"fonts/BebasNeueBold.otf");
        Valencia = Typeface.createFromAsset(getContext().getAssets(),"fonts/Valencia.otf");
        Monoid = Typeface.createFromAsset(getContext().getAssets(),"fonts/Monoid.ttf");

        BgImage = getResources().getDrawable(R.drawable.bg1);
        BgImage.setBounds((int) (centerX-(width/2)), (int) (centerY-(width/2)),(int) (centerX+width/2), (int) (centerY+(width/2)));
        isInit = true;
    }

    private void drawClock(Canvas canvas)
    {
        clocktextpaint.reset();
        clocktextpaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        clocktextpaint.setAntiAlias(true);
        clocktextpaint.setTextSize(fontSize);
        clocktextpaint.setTypeface(Valencia);
        clocktextpaint.setTextAlign(Paint.Align.CENTER);

        ampmtextpaint.reset();
        ampmtextpaint.setColor(getResources().getColor(android.R.color.white));
        ampmtextpaint.setAntiAlias(true);
        ampmtextpaint.setTextSize(fontSize/3);
        ampmtextpaint.setTypeface(Valencia);



        timeStringH = DateFetchAndFormat("hh");
        timeStringM = DateFetchAndFormat("mm");
        timeStringTOD = DateFetchAndFormat("a");


        BgImage.draw(canvas);



        canvas.drawText(timeStringH,centerX,centerY-(fontSize/2),clocktextpaint);
        canvas.drawText(timeStringM,centerX,centerY+(fontSize/2),clocktextpaint);
        canvas.drawText(timeStringTOD,centerX+(fontSize/2),centerY+(fontSize/2),ampmtextpaint);
    }

    private String DateFetchAndFormat(String opt)
    {
        Date curDate = new Date();
        SimpleDateFormat hformatter = new SimpleDateFormat(opt);
        return hformatter.format(curDate);
    }



}
