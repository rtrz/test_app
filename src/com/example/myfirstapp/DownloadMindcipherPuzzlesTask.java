package com.example.myfirstapp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadMindcipherPuzzlesTask extends AsyncTask<URL, Integer, Long> {
	private String result;
	private final String ERROR = "ENOMINDCIPHERPUZZLES";
	private MainActivity _MainActivity;
	
	public DownloadMindcipherPuzzlesTask(MainActivity ma) {
		this._MainActivity = ma;
	}
	
	protected Long doInBackground(URL... urls) {
		
		int count = urls.length;
        
        for (int i = 0; i < count; i++) {
        	this.result = getMindCipherPuzzles();
        }
		return Long.MIN_VALUE;
	}
	
	private String getMindCipherPuzzles() {
		try {
			Random generator = new Random();
			int puzzle_id = generator.nextInt(150);
			Log.d("puzzle_id", Integer.toString(puzzle_id));
			URL url = new URL("http://www.mindcipher.com/puzzles.json");
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Content-Type","application/json");
			urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			
			
			// Start streaming to a byte array.
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			int i = in.read();
			while (i != -1) {
				bo.write(i);
				i = in.read();
			}
			Log.d("json",bo.toString("UTF-8"));
			return bo.toString("UTF-8");
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.d("json",ERROR);
			return ERROR;
		}
	}
	
	protected void onPostExecute(Long result) {
		//this._MindcipherActivity.json_returned = this.result;
		Log.d("done","finished MC request");
		
		if (this.result.equals(ERROR)) {
			//this._MindcipherActivity.showPuzzle(ERROR);
		} else {
			try {
				JSONArray jArray = new JSONArray(this.result);
				int num_puzzles = jArray.length();
				List<Map<String, String>> _puzzleList = new ArrayList<Map<String,String>>();
				
				Log.d("# puzzles", Integer.toString(num_puzzles));
				
				for (int i=0; i < num_puzzles; i++) {
					JSONObject jObject = jArray.getJSONObject(i);
				
					String puzzle_name = jObject.getString("name");
					//String puzzle_body = jObject.getString("content");
					//String puzzle_answer = jObject.getString("answer");
					
					_puzzleList.add(createPuzzle("planet", puzzle_name));
					
					Log.d("name", puzzle_name);
				
				}
				
				this._MainActivity.puzzleList = _puzzleList;
				this._MainActivity.updateListView();
				
				//this._MindcipherActivity.showPuzzle(puzzle_name +
				//		"\n\n" + puzzle_body + "\n\n" + puzzle_answer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private HashMap<String, String> createPuzzle(String key, String name) {
        HashMap<String, String> puzzle = new HashMap<String, String>();
        puzzle.put(key, name);
     
        return puzzle;
    }
}
