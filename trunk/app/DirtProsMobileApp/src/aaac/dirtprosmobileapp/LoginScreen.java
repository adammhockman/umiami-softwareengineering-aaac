package aaac.dirtprosmobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
//=============================================================================
public class LoginScreen extends Activity {
//-----------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
	}
//-----------------------------------------------------------------------------
	protected void onResume(){
		super.onResume();
		((EditText)findViewById(R.id.username_field)).setText("");
        ((EditText)findViewById(R.id.password_field)).setText("");
	}
//-----------------------------------------------------------------------------
	public void myClickHandler(View view){
		
		Intent nextActivity;
		
		switch(view.getId()){
		case R.id.exit_button:
			finish();
			break;
		case R.id.login_button:
			nextActivity = new Intent();
            nextActivity.setClassName("aaac.dirtprosmobileapp",
"aaac.dirtprosmobileapp.MainOptionsScreen");
            startActivity(nextActivity);
			break;
		default:
			break;
		}
	}
//-----------------------------------------------------------------------------
}
//=============================================================================