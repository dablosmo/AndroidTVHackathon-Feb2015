package com.sdkcdg.cahphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cahphone.R;
import com.sdkcdg.bluetooth.BluetoothService;


public class MainActivity extends Activity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Toast.makeText(this, "Initializing Connection", Toast.LENGTH_SHORT).show();
		Intent serviceIntent = new Intent(getApplicationContext(), BluetoothService.class);
		startService(serviceIntent);
    }
}
