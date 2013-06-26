package de.rwth.elearning.l2p_dropbox_downloader;

import java.util.List;
import java.util.Vector;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Alex on 6/23/13.
 */
public class CourseListActivity extends Activity {
	private ListView listView;

	private static final String l2pLink = "https://www2.elearning.rwth-aachen.de";
	RoomArrayAdapter adapter;
	List<LearnRoom> l2pRoomslist;
	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist);
        listView = (ListView) findViewById(R.id.listView);
        
		SharedPreferences app_preferences =	PreferenceManager.getDefaultSharedPreferences(this);
        String username = app_preferences.getString("LoginL2P", "ab123456");
        String password = app_preferences.getString("PassL2P", "ab123456");
        
		//if(!isOnline()){
        //l2pRoomslist = new Vector<LearnRoom>(0);
        	//showDialog(DIALOG_NO_NETWORK);
        //}else{

        new DownloadParseTask().execute(username,password);
        //}
       

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view,
              int position, long id) {
            final LearnRoom item = (LearnRoom) parent.getItemAtPosition(position);
            String roomId = item.getlinkl2p().substring(0, item.getlinkl2p().indexOf("/information"));
            startL2pMaterialList(l2pLink+roomId+"/materials/structured/Forms/all.aspx");
          }
        });
    }
    private class DownloadParseTask extends AsyncTask<Object, Object, Object>
    {
		@Override
		protected void onPreExecute(){
			//Important: SHOW LOADING DIALOG
			//showDialog(DIALOG_LOADING);
		}
		
		@Override
		protected Object doInBackground(Object... params)
		{
			List<LearnRoom> l2pRooms;
			String username = (String) params[0];
			String password = (String) params[1];
			
	        HTTPHelper pageget = new HTTPHelper();
	        //download Data
	        String allData = pageget.getData("https://www2.elearning.rwth-aachen.de/foyer/summary/default.aspx", username, password);
	        
	        //If Data seems ok, parse it.
	        if(allData.length() <= 1000){
	        	//showDialog(DIALOG_NO_NETWORK);
	        	l2pRooms = new Vector<LearnRoom>(0);
	        }else{
	        	l2pRooms = parse_HTML(allData);
	        }
	       
			return l2pRooms;

		}
		@Override
		protected void onPostExecute(Object result)
		{
			//Important: GET LIST OF ROOMS, HIDE LOADING DIALOG
			l2pRoomslist = (List<LearnRoom>) result;
			//Display the List of L2P Rooms.
			adapter = new RoomArrayAdapter(CourseListActivity.this, R.layout.room_list_item, l2pRoomslist);
	        listView.setAdapter(adapter);
	        
	        //remove the loading Dialog.
	        //dismissDialog(DIALOG_LOADING);
		}
    	
    }

	private void startL2pMaterialList(String Url){
		Intent i = new Intent(getBaseContext(),MaterialListActivity.class);
		i.putExtra("url", Url);
		startActivity(i);
	}
	
    private class RoomArrayAdapter extends ArrayAdapter<LearnRoom> 
    {
		//private static final String tag = "L2pRoomArrayAdapter";
		private Context context;
		private List<LearnRoom> objects;
		
		public RoomArrayAdapter(Context context, int textViewResourceId, List<LearnRoom> objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			this.objects = objects;
		}

		public int getCount() {
			return this.objects.size();
		}

		public LearnRoom getItem(int index) {
			return this.objects.get(index);
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View row = convertView;
			if (row == null) {
				//Log.d(tag, "Starting XML Row Inflation ... ");
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
				row = inflater.inflate(R.layout.room_list_item, parent, false);
				//Log.d(tag, "Successfully completed XML Row Inflation!");
			}
			TextView tw1 = (TextView)row.findViewById(R.id.courseItemTextView);
			String str = ((LearnRoom)objects.get(position)).getName();
			tw1.setText(str);
			return row;
		}
    }
    
	private List<LearnRoom> parse_HTML(String allData){
		List<LearnRoom> l2pRooms = new Vector<LearnRoom>(0);
		String name;
        String linkl2p;
        String linkcampus;
        String rssfeed;
		Integer start_index = allData.indexOf("ms-viewheadertr");
        Integer end_index = allData.indexOf("ms-PartSpacingVertical",start_index);
        if(start_index >= 0 && end_index > start_index && end_index >= 0){
	        allData = allData.substring(start_index,end_index);
	        
	        start_index = allData.indexOf("</table>");
	        end_index = allData.indexOf("</table>",start_index+10);
	        if(start_index >= 0 && end_index > start_index && end_index >= 0){
		        allData = allData.substring(start_index,end_index);
		        //Log.d("L2PRooms",start_index + " " + end_index);
		        String [] table_rows = allData.split("<tr");
		        
		        //Spanned htmlpage =  Html.fromHtml(allData);
		        if(table_rows == null){
		        	//Log.d("Something went wrong!");
		        }else{
		        	
		        	for(int i=1;i<table_rows.length;i++){
		        		String [] row_entries = table_rows[i].split("<*>");
		        		if(row_entries.length < 29) continue;
		        		start_index = row_entries[5].indexOf("\"",0);
		        		end_index = row_entries[5].indexOf("\"",start_index+1);
		        		linkl2p = row_entries[5].substring(start_index+1,end_index);
		        		
		        		start_index = row_entries[10].indexOf("\"",0);
		        		end_index = row_entries[10].lastIndexOf("\"");
		        		linkcampus = row_entries[10].substring(start_index+1,end_index);
		        		
		        		start_index = row_entries[25].indexOf("\"",0);
		        		end_index = row_entries[25].lastIndexOf("\"");
		        		rssfeed = row_entries[25].substring(start_index+1,end_index);
		        		
		        		name = row_entries[16].substring(0,row_entries[16].indexOf("<"));
			        	l2pRooms.add(new LearnRoom(name,linkl2p,linkcampus,rssfeed));
		        		//Log.d("L2PRooms",name + " " + linkl2p +" "+ linkcampus +" "+ rssfeed);
			        }
		        }
	        }
        }
	        //Log.d("L2PRooms",url_strings);
	    return l2pRooms;   
	}
	
}

 
    
