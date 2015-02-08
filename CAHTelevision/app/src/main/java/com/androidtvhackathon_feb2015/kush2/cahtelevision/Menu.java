package com.androidtvhackathon_feb2015.kush2.cahtelevision;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;


/**
 * Created by christopher morales on 2/7/15.
 */
public class Menu extends Activity{

    public TextView startButton;
    public TextView answer;
    Dpad mDpad = new Dpad();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.menu);
        Intent intent= getIntent();
        setUpUIElements();
        Dpad mPad= new Dpad();
        Log.d("BUTTON: ", String.valueOf(KeyEvent.KEYCODE_X));
    }

    public void setUpUIElements()
    {
        startButton = (TextView)findViewById(R.id.startButton);
        answer = (TextView)findViewById(R.id.answerField);
    }

    public boolean onGenericMotionEvent(MotionEvent event){

            Log.d("BUTTON: ", String.valueOf(event));
            return true;


    }
    public void launchIntent(){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void loadAnwser(String ans)
    {
        answer.setText(ans);
    }

        // Check if this event is from a joystick movement and process accordingly.
//        @Override
//        public boolean onGenericMotionEvent(MotionEvent event) {
//
//            // Check if this event if from a D-pad and process accordingly.
//            if (mDpad.isDpadDevice(event)) {
//
//                int press = mDpad.getDirectionPressed(event);
//                switch (press) {
//                    case Dpad.LEFT:
//                        // Do something for LEFT direction press
//                        Log.v("LEFT DPAD: ", ""+Dpad.LEFT);
//
//                        return true;
//                    case Dpad.RIGHT:
//                        // Do something for RIGHT direction press
//                        Log.v("RIGHT DPAD: ", ""+Dpad.RIGHT);
//                        return true;
//                    case Dpad.UP:
//                        // Do something for UP direction press
//                        Log.v("UP DPAD: ", ""+Dpad.UP);
//
//                        return true;
//                    case Dpad.DOWN:
//                        // Do something for DOWN direction press
//                        Log.v("DOWN DPAD: ", ""+Dpad.DOWN);
//                        return true;
//
//                }
//            }
//            if(mDpad.isDpadDevice(event)){
//                int buttonPress = mDpad.getButtonPressed(event);
//                switch(buttonPress){
//                    case Dpad.ABUTTON:
//                        Log.v("A BUTTON: ", ""+Dpad.ABUTTON);
//                        launchIntent();
//                }
//            }
//            return mDpad.isDpadDevice(event);
//        }
}
