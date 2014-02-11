package com.example.myfirstapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class DroidActivity extends Activity {

	private final String TIMESTAMP_FILENAME = "ts_file";
	private final int TIMESTAMP_LENGTH_BYTES = 19;
	private final String CHAR_SET = "UTF-8";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Always calls the superclass method first.
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_droid);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		// findViewById returns a View if it exists in the layout set by 
		// setContentView.
		TextView textView = (TextView) findViewById(R.id.intent_message);
		textView.setTextSize(18);
		textView.setText(message);
		textView.setTextColor(Color.RED);

		// Read and display timestamp log
		String last_ts = readTimestampsFromFile();
		TextView tsTextView = (TextView) findViewById(R.id.timestamp_history);
		tsTextView.setText("This activity was last opened at " + last_ts);
		
		// Log this entry
		writeTimestampToFile();
		
		// Show the Up button in the action bar.
		setupActionBar();
	}
	
	private String readTimestampsFromFile() {
		// Read in previous entries
		FileInputStream inputStream;
		try {
			inputStream = openFileInput(TIMESTAMP_FILENAME);
			int avail_bytes = inputStream.available();
			Log.d("avail bytes", Integer.toString(avail_bytes));
			
			try {
				byte[] buffer = new byte[avail_bytes];
				inputStream.read(buffer);
				String buffer_str = new String(buffer, CHAR_SET);
				Log.d("last write", buffer_str.substring(avail_bytes -TIMESTAMP_LENGTH_BYTES));
				
				for (int i = 0; i < avail_bytes; i += TIMESTAMP_LENGTH_BYTES) {
					Log.d("dates", buffer_str.substring(i,i+TIMESTAMP_LENGTH_BYTES));
				}
				return buffer_str.substring(avail_bytes -TIMESTAMP_LENGTH_BYTES);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	private void writeTimestampToFile() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.US);
		String currentDateandTime = sdf.format(new Date());
		FileOutputStream outputStream;
		
		try {
			outputStream = openFileOutput(TIMESTAMP_FILENAME, Context.MODE_APPEND);
			outputStream.write(currentDateandTime.getBytes(CHAR_SET));
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.droid, menu);
		return true;
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
