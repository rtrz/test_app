package com.example.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // Place menu items into the action bar. Callback method to inflate the
    // menu resource into the given Menu object.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    // Callback response to action buttons.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle presses on action bar items.
    	switch (item.getItemId()) {
    		case R.id.action_droid:
    			//openDroid();
    			return true;
    		case R.id.action_settings:
    			//openSettings();
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    }
}
