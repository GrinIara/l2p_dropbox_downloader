package de.rwth.elearning.l2p_dropbox_downloader;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
	ListView roomList;
	RoomArrayAdapter adapter;
	List<LearnRoom> l2pRoomslist;
	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist);
        listView = (ListView) findViewById(R.id.listView);
        
        String[] values = new String[] { "Course1", "Course2" };

        final ArrayList<LearnRoom> list = new ArrayList<LearnRoom>();
        for (int i = 0; i < values.length; ++i) 
        {
          LearnRoom a = new LearnRoom();
          a.setName(values[i]);
        	list.add(a);
        }
        
        final RoomArrayAdapter adapter = new RoomArrayAdapter(this, R.layout.room_list_item, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view,
              int position, long id) {
            final String item = (String) parent.getItemAtPosition(position);
            startL2pSections(l2pLink+item);
          }
        });
    }

	private void startL2pSections(String Url){
		//Intent l2psections = new Intent(this,Sections.class);

		//l2psections.putExtra("url", Url);
		//startActivity(l2psections);
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
			TextView tw1=(TextView)findViewById(R.id.l2pTextView);
			tw1.setText(((LearnRoom)objects.get(position)).getName());
			if (row == null) {
				//Log.d(tag, "Starting XML Row Inflation ... ");
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
				row = inflater.inflate(R.layout.room_list_item, parent, false);
				//Log.d(tag, "Successfully completed XML Row Inflation!");
			}

//			// Get item
//			LearnRoom learnroom = getItem(position);
//			if(learnroom != null){
//				// Get reference to Buttons
//				Button l2pButton = (Button)row.findViewById(R.id.l2pRoomButton);
//				TextView l2pnameText = (TextView)row.findViewById(R.id.l2pTextView);
//				
//				//Set Text and Tags
//				//Tags are important because they allow us to identify the Clicked Object later.
//				if(l2pnameText != null){
//					l2pnameText.setText(learnroom.getName());
//				}
//				if(l2pButton != null){
//					l2pButton.setTag(learnroom.getlinkl2p());
//					//
//				}
//
//			}
			return row;
		}
    }
}

 
    
