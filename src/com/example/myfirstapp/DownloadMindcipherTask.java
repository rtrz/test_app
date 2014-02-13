package com.example.myfirstapp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadMindcipherTask extends AsyncTask<URL, Integer, Long> {
	public MindcipherActivity _MindcipherActivity;
	public String result;
	private final String ERROR = "ENOMINDCIPHER";
	
	public DownloadMindcipherTask(MindcipherActivity m) {
		this._MindcipherActivity = m;
	}
	
	protected Long doInBackground(URL... urls) {
		
		int count = urls.length;
        
        for (int i = 0; i < count; i++) {
        	this.result = getMindCipherPuzzle();
        }
		return Long.MIN_VALUE;
	}
	
	protected void onPostExecute(Long result) {
		this._MindcipherActivity.json_returned = this.result;
		Log.d("done","finished MC request");
		
		if (this.result.equals(ERROR)) {
			this._MindcipherActivity.showPuzzle(ERROR);
		} else {
			try {
				JSONObject jObject = new JSONObject(this.result);
				String puzzle_name = jObject.getString("name");
				String puzzle_body = jObject.getString("content");
				String puzzle_answer = jObject.getString("answer");
				this._MindcipherActivity.showPuzzle(puzzle_name +
						"\n\n" + puzzle_body + "\n\n" + puzzle_answer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getMindCipherPuzzle() {
		try {
			Random generator = new Random();
			int puzzle_id = generator.nextInt(150);
			Log.d("puzzle_id", Integer.toString(puzzle_id));
			URL url = new URL("http://www.mindcipher.com/puzzles/" + Integer.toString(puzzle_id) + ".json");
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
}
