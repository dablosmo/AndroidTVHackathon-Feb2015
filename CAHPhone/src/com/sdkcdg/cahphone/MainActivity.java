package com.sdkcdg.cahphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.cahphone.R;


public class MainActivity extends Activity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        System.out.println("starting service");
		Intent serviceIntent = new Intent(getApplicationContext(), BluetoothService.class);
		startService(serviceIntent);
    }
}
