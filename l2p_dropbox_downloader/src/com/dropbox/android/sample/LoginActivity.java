package com.dropbox.android.sample;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	

	
	EditText textEditUser;
	EditText textEditPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button) findViewById(R.id.btn_login);
        textEditUser = (EditText) findViewById(R.id.et_un);
        textEditPass = (EditText) findViewById(R.id.et_pw);
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
		String login = textEditUser.getText().toString();
		String pass = textEditPass.getText().toString();
		//Important: When you login you need to save login and pass of a user here
		editor.putString("LoginL2P", login);
       	editor.putString("PassL2P", pass );

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
