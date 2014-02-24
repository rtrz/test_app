package com.example.myfirstapp;

import java.io.Serializable;

// Puzzle class to store details for each puzzle
public class Puzzle implements Serializable {
	private static final long serialVersionUID = 4861597073026532544L;
	
	// ID according to mindcipher.com
	public int mindcipher_id;
	
	// ID is the context of the application (e.g. within a list)
	public int list_id;
	
	// Puzzle data
	public String name;
	public String content;
	public String answer;
	public String solution;
	
	public Puzzle(int _mindcipher_id, int _list_id,
				  String _name, String _content,
				  String _answer, String _solution) {
		mindcipher_id = _mindcipher_id;
		list_id = _list_id;
		name = _name;
		content = _content;
		answer = _answer;
		solution = _solution;
	}
}
