package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

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
    		// Navigate to mindcipher page
    		case R.id.action_mindcipher:
    			openMindCipher();
    			return true;
    		// Navigate to random droid page
    		case R.id.action_droid:
    			openDroid();
    			return true;
    		case R.id.action_settings:
    			//openSettings();
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    }
    
    public void openDroid() {
    	Intent intent = new Intent(this, DroidActivity.class);
    	String message = "This message is 'intended' for DROID.";
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    public void openMindCipher() {
    	Intent intent = new Intent(this, MindcipherActivity.class);
    	startActivity(intent);
    }
}
