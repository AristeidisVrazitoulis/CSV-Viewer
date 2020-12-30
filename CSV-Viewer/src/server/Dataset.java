package server;

import java.util.ArrayList;

import utils.SimpleCSVReader;

public class Dataset {
	
	private String name;
	private ArrayList<String[]> data;
	
	public Dataset(String name) {
		this.name = name;
		data=new ArrayList<String[]>();
	}
	public Dataset(String name,ArrayList<String[]> data) {
		this.name=name;
		this.data=new ArrayList<String[]>();
		this.data=data;
	}
	public String getName() {
		return name;
	}
	
	public ArrayList<String[]> getData(){
		return data;
	}
	public void setData(String canonicalPath) {
		SimpleCSVReader csvreader=new SimpleCSVReader();
		data=csvreader.load(canonicalPath);
		
	}
	
	public String[] getHeader() {
		return data.get(0);
	}
}
