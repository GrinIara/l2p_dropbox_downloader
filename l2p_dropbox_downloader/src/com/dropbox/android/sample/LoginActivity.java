package com.dropbox.android.sample;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    protected void login()
    {
    	SharedPreferences app_preferences =	PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = app_preferences.edit();
       	
		//PUT YOUR CREDENTIALS HERE INSTEAD OF login and pass
		
		editor.putString("LoginL2P", "ep203401");
       	editor.putString("PassL2P", "tehrantehran2");
       	editor.commit(); // Very important
        Intent i = new Intent(getBaseContext(),CourseListActivity.class);
        startActivity(i);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
