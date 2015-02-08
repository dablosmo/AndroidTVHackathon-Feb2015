package com.androidtvhackathon_feb2015.kush2.cahtelevision;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * Created by christopher morales on 2/7/15.
 */
public class Menu extends Activity {

    public TextView startButton;
    Dpad mDpad = new Dpad();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.menu);
        setUpUIElements();


    }

    public void setUpUIElements()
    { startButton = (TextView)findViewById(R.id.startButton); }

    public void launchIntent(){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }


        // Check if this event is from a joystick movement and process accordingly.
        @Override
        public boolean onGenericMotionEvent(MotionEvent event) {

            // Check if this event if from a D-pad and process accordingly.
            if (Dpad.isDpadDevice(event)) {

                int press = mDpad.getDirectionPressed(event);
                switch (press) {
                    case Dpad.LEFT:
                        // Do something for LEFT direction press
                        Log.v("LEFT DPAD: ", ""+Dpad.LEFT);

                        return true;
                    case Dpad.RIGHT:
                        // Do something for RIGHT direction press
                        Log.v("RIGHT DPAD: ", ""+Dpad.RIGHT);
                        return true;
                    case Dpad.UP:
                        // Do something for UP direction press
                        Log.v("UP DPAD: ", ""+Dpad.UP);

                        return true;
                    case Dpad.DOWN:
                        // Do something for DOWN direction press
                        Log.v("DOWN DPAD: ", ""+Dpad.DOWN);
                        return true;

                }
            }
            if(Dpad.isDpadDevice(event)){
                int buttonPress = mDpad.getButtonPressed(event);
                switch(buttonPress){
                    case Dpad.ABUTTON:
                        Log.v("A BUTTON: ", ""+Dpad.ABUTTON);
                        launchIntent();
                }
            }
            return Dpad.isDpadDevice(event);
        }
}
