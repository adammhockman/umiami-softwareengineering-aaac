package aaac.dirtprosmobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//=============================================================================
public class MainOptionsScreen extends Activity {
	private String username; 
//-----------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_options_screen);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras == null)
		{
			return;
		}
		username = extras.getString("username");
	}
//-----------------------------------------------------------------------------
	public void myClickHandler(View view){
	
		Intent nextActivity;
		switch(view.getId()){
		case R.id.logout_button:
			finish();
			break;
		case R.id.record_collection_information_button:
			nextActivity = new Intent();
			nextActivity.putExtra("username", username);
            nextActivity.setClassName("aaac.dirtprosmobileapp",
"aaac.dirtprosmobileapp.RecordCollectionInfoScreen");
            startActivity(nextActivity);
			break;
		default:
			break;
		}
	}
//-----------------------------------------------------------------------------
}
//=============================================================================