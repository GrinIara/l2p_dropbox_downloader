package com.dropbox.android.sample;

public class MaterialItem 
{
	private String name;
	private String link;

	MaterialItem(){
		
	}
	
	MaterialItem(String name, String link){
		this.name = name;
		this.link = link;
	
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
}
