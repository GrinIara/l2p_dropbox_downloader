package com.dropbox.android.sample;

public class MaterialItem 
{
	private String name;
	private String link;
	private boolean state; 
	MaterialItem(){
		
	}
	
	MaterialItem(String name, String link, boolean state){
		this.name = name;
		this.link = link;
		this.setState(state);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setlink(String link){
		this.link = link;
	}
	
	
	public String getName(){
		return this.name;
	}
	public String getlink(){
		return this.link;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
