package com.example.myfirstapp;

import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MindcipherActivity extends Activity {

	public String json_returned;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mindcipher);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//String puzzleJson = getMindCipherPuzzle();
		//Log.d("puzz",puzzleJson);
		DownloadMindcipherTask dmt = new DownloadMindcipherTask(this);
		try {
			URL url = new URL("http://mindcipher.com/puzzles.json");
			dmt.execute(url);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showPuzzle(String puzzle) {
		TextView textView = (TextView) findViewById(R.id.mindcipher_puzzle);
		textView.setText(puzzle);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mindcipher, menu);
		return true;
	}

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
