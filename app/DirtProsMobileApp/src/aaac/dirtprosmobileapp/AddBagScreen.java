package aaac.dirtprosmobileapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBagScreen extends Activity {
//-----------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bag_screen);
	}
//-----------------------------------------------------------------------------
	public void myClickHandler(View view){
	
		
		switch(view.getId()){
		case R.id.record_weight_button:
			((EditText)findViewById(R.id.bag_weight_field)).setVisibility(View.GONE);
			((Button)findViewById(R.id.record_weight_button)).setVisibility(View.GONE);
			((EditText)findViewById(R.id.wet_trash_field)).setVisibility(View.VISIBLE);
			((EditText)findViewById(R.id.paper_field)).setVisibility(View.VISIBLE);
			((EditText)findViewById(R.id.cardboard_field)).setVisibility(View.VISIBLE);
			((EditText)findViewById(R.id.plastic_field)).setVisibility(View.VISIBLE);
			((EditText)findViewById(R.id.glass_field)).setVisibility(View.VISIBLE);
			((EditText)findViewById(R.id.cans_field)).setVisibility(View.VISIBLE);
			((Button)findViewById(R.id.record_bag_composition_button)).setVisibility(View.VISIBLE);
			break;
		case R.id.record_bag_composition_button:
			finish();
			break;
		default:
			break;
		}
	}
//-----------------------------------------------------------------------------
}
//=============================================================================