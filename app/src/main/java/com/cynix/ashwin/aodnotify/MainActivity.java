package com.cynix.ashwin.aodnotify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button startaodbt , notipermbt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startaodbt = findViewById(R.id.startaodbt);
        startaodbt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,AODActivity.class);
                startActivity(intent);
            }
        });

        notipermbt =findViewById(R.id.notipermbt);
        notipermbt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        });

    }
}
