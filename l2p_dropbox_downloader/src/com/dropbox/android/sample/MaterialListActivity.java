package com.dropbox.android.sample;

import java.util.List;
import java.util.Vector;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MaterialListActivity extends Activity  
{
    //Define some Constants we use for Dialog-creation.
	static final int DIALOG_LOADING = 0;
	static final int DIALOG_NO_NETWORK = 1;
	static final int DIALOG_L2P_WRONGPASS = 2;
	static final int DIALOG_CAMPUS_WRONGPASS = 3;
    
    protected Dialog onCreateDialog(int id){
    	Dialog dialog;
    	AlertDialog.Builder builder;
    	switch(id) {
    	case DIALOG_LOADING:
    		dialog = ProgressDialog.show(this, "", "Downloading...", true);
    		break;
    	case DIALOG_NO_NETWORK:
    		builder = new AlertDialog.Builder(this);
    		builder.setTitle("Network failure");
    		builder.setMessage("You are not connected to any Network. To see your L2P Rooms you need a network connection.");
    		dialog = builder.create();
    		break;
    	case DIALOG_L2P_WRONGPASS:
    		builder = new AlertDialog.Builder(this);
    		builder.setTitle("Wrong Username/Password for L2P");
    		builder.setMessage("Please make sure you entered the right username/password. We could not verify your username and password.");
    		dialog = builder.create();
    		break;
    	case DIALOG_CAMPUS_WRONGPASS:
    		builder = new AlertDialog.Builder(this);
    		builder.setTitle("Wrong Username/Password for Campus");
    		builder.setMessage("Please make sure you entered the right username/password. We could not verify your username and password.");
    		dialog = builder.create();
    		break;
    	default:
    		dialog = null;
    	}
    	return dialog;
    }
    
	
	
    private String leanroom_url;
	private String username;
	private String password;
	private List<MaterialItem> materials;
	private ListView materialList;
	
    @Override  
    public void onCreate(Bundle savedInstanceState) 
    {
    	
    	super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_material_list);
        
        materialList = (ListView) findViewById(R.id.materialListView);	
		
        Button login = (Button) findViewById(R.id.uploadButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent i = new Intent(getBaseContext(),DBRoulette.class);
                startActivity(i);
            }
        });
        
		SharedPreferences app_preferences =	PreferenceManager.getDefaultSharedPreferences(this);
        username = app_preferences.getString("LoginL2P", "ab123456");
        password = app_preferences.getString("PassL2P", "ab123456");
        leanroom_url = getIntent().getExtras().getString("url");
        
        //Chech whether we are online.
        //if(!isOnline()){
		//	materials = new Vector<Materiallist>(0);
        //}else{
        	new DownloadParseTask().execute(username,password);
        //}
    }
    
	private class DownloadParseTask extends AsyncTask<Object, Object, Object>{
		@Override
		protected void onPreExecute(){
			//Important: SHOW LOADING DIALOG
			showDialog(DIALOG_LOADING);
		}
		
		@Override
		protected Object doInBackground(Object... params) {
	        //Get the Url of the L2P Learnroom
	        
	        HTTPHelper pageget = new HTTPHelper();
	        String allData = pageget.getData(leanroom_url, username, password);
	        
	        materials = parse_HTML(allData);
	        
	        return materials;
		}
		
		@Override
		protected void onPostExecute(Object result){
			//Important: GET LIST OF MATERIALS, HIDE LOADING DIALOG
	        MaterialArrayAdapter adapter = new MaterialArrayAdapter(getBaseContext(), R.layout.material_list_item, materials);
	        materialList.setAdapter(adapter);
	        dismissDialog(DIALOG_LOADING);
		}
	}
    
	private class MaterialArrayAdapter extends ArrayAdapter<MaterialItem> {
		private static final String tag = "MaterialArrayAdapter";
		private Context context;
		private List<MaterialItem> objects;
		
		public MaterialArrayAdapter(Context context, int textViewResourceId, List<MaterialItem> objects) 
		{
			super(context, textViewResourceId, objects);
			this.context = context;
			this.objects = objects;
		}

		public int getCount() {
				return this.objects.size();
			}

		public MaterialItem getItem(int index) {
				return this.objects.get(index);
			}
			
		public View getView(int position, View convertView, ViewGroup parent) {
				View row = convertView;
				if (row == null) {
					//Log.d(tag, "Starting XML Row Inflation ... ");
					LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
					row = inflater.inflate(R.layout.material_list_item, parent, false);
					//Log.d(tag, "Successfully completed XML Row Inflation!");
				}
				MaterialItem materialitem = getItem(position);
				if(materialitem != null){
					// Get reference to Buttons
					TextView l2pnameText = (TextView)row.findViewById(R.id.materialItemTextView);
					//Set Text and Tags
					//We need the Tag in order to identify the button later.
					if(l2pnameText != null){
						l2pnameText.setText(materialitem.getName());
					}
					
				}
				return row;
			}
	

	}
    
    private List<MaterialItem> parse_HTML(String allData){
		List<MaterialItem> materials = new Vector<MaterialItem>(0);
		String name;
        String link;
		Integer start = allData.indexOf("ms-vb-icon");
		if (start >= 0){
		    allData = allData.substring(start+12);
		    Integer endindex= allData.indexOf("</TABLE>");
		    if(endindex >=0){
			    allData = allData.substring(0, endindex);
			    String [] table_rows = allData.split("\"ms-vb-icon\">");
				       
		        if(table_rows == null){
		        	//Log.d("Something went wrong!");
		        }else{
		        	
		        	for(int i=0;i<table_rows.length;i++){
		        		String [] row_entries = table_rows[i].split("<*>");
		        		if(row_entries.length < 29) continue;
		        		start = row_entries[0].indexOf("\"",0);
		        		endindex = row_entries[0].indexOf("\"",start+1);
		        		link = row_entries[0].substring(start+1,endindex);
		        		//Log.d("link", link);
		        		name = row_entries[1].substring(row_entries[1].indexOf("\" title=\"")+9, row_entries[1].indexOf("\" SRC=\""));
		        		//Log.d("name", name);
		        		materials.add(new MaterialItem(name,link));
		
			        }
		        }
		    }
		}
	    return materials;   
	}

}
