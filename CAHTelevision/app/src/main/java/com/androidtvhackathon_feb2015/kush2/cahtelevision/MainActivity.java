/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.androidtvhackathon_feb2015.kush2.cahtelevision;

import com.service.DealerService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;
/*
 * MainActivity class that loads MainFragment
 */
public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private TextView startButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
<<<<<<< HEAD

=======
        //Starting DealerService
        System.out.println("starting service");
        Toast.makeText(getApplicationContext(), "starting service", Toast.LENGTH_SHORT).show();
        Intent serviceIntent = new Intent(getApplicationContext(), DealerService.class);
        startService(serviceIntent);
        
>>>>>>> f2606468cfc15e1081984ec7bb8dae508e4aca19
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent myIntent = new Intent(this, Menu.class);
        startActivity(myIntent);
        //setContentView(R.layout.menu);

    }
}
