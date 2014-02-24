package com.example.myfirstapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	// Constants for intent messages
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static String PUZZLE = "com.example.myfirstapp.PUZZLE";
	public final static String PUZZLE_ANSWER = "com.example.myfirstapp.PUZZLE_ANSWER";
	public final static String PUZZLE_CONTENT = "com.example.myfirstapp.PUZZLE_CONTENT";
	public final static String PUZZLE_NAME = "com.example.myfirstapp.PUZZLE_NAME";
	public final static String PUZZLE_SOLUTION = "com.example.myfirstapp.PUZZLE_SOLUTION";
	
	public List<Map<String, String>> puzzleList = new ArrayList<Map<String,String>>();
	public List<Puzzle> puzzleDetailedList = new ArrayList<Puzzle>();
	private SimpleAdapter simpleAdpt;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Show a single ListView element "Loading puzzles..."
        showLoadingPuzzles();
        
        // Asynchronous download of MC puzzle list, which also sets the ListView puzzles.
        DownloadMindcipherPuzzlesTask dmt = new DownloadMindcipherPuzzlesTask(this);
		try {
			URL url = new URL("http://mindcipher.com/puzzles.json");
			dmt.execute(url);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void updateListView() {
    	simpleAdpt = new SimpleAdapter(this, puzzleList, 
        		android.R.layout.simple_list_item_1, 
        		new String[] {"planet"}, new int[] {android.R.id.text1});
        
        ListView lv = (ListView) findViewById(R.id.puzzleList);
        lv.setAdapter(simpleAdpt);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view,
        	    int position, long id) {
        		  Puzzle _p = puzzleDetailedList.get((int)id);
        		  String message = _p.name;
        		  Toast.makeText(getApplicationContext(),
        				  message, Toast.LENGTH_SHORT)
        				  .show();
        		  openPuzzle(_p);
        	  }
    	}); 
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
    
    public void openPuzzle(Puzzle _p) {
    	Intent intent = new Intent(this, PuzzleActivity.class);
    	
    	// populate intent with puzzle data
    	intent.putExtra(PUZZLE, _p);
    	
    	startActivity(intent);
    }
    
    private void showLoadingPuzzles() {
    	 puzzleList.add(createPuzzle("planet", "Loading puzzles..."));
    	 updateListView();
    }
    
    private HashMap<String, String> createPuzzle(String key, String name) {
        HashMap<String, String> puzzle = new HashMap<String, String>();
        puzzle.put(key, name);
     
        return puzzle;
    }
}
