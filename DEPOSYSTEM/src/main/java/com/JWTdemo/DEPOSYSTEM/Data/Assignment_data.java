package com.JWTdemo.DEPOSYSTEM.Data;

import org.springframework.stereotype.Component;

@Component
public class Assignment_data {

	private int id;
	private int location;
	private int time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Assignment_data(int id, int location, int time) {
		super();
		this.id = id;
		this.location = location;
		this.time = time;
	}
	public Assignment_data() {
		super();
	}
	@Override
	public String toString() {
		return "Assignment_data [id=" + id + ", location=" + location + ", time=" + time + "]";
	}
	
}
