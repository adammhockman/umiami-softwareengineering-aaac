package aaac.dirtprosmobileapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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
			JSONObject bag = new JSONObject();
			int weight = Integer.parseInt(((EditText)findViewById(R.id.bag_weight_field)).getText().toString());
			int wetTrashPercentage = Integer.parseInt(((EditText)findViewById(R.id.wet_trash_field)).getText().toString());
			int paperTrashPercentage = Integer.parseInt(((EditText)findViewById(R.id.paper_field)).getText().toString());
			int cardboardTrashPercentage = Integer.parseInt(((EditText)findViewById(R.id.cardboard_field)).getText().toString());
			int plasticTrashPercentage = Integer.parseInt(((EditText)findViewById(R.id.plastic_field)).getText().toString());
			int glassTrashPercentage = Integer.parseInt(((EditText)findViewById(R.id.glass_field)).getText().toString());
			int cansTrashPercentage = Integer.parseInt(((EditText)findViewById(R.id.cans_field)).getText().toString());
			
			try
			{
				bag.put("weight", weight);
				bag.put("wettrash", wetTrashPercentage);
				bag.put("papertrash", paperTrashPercentage);
				bag.put("cardboardtrash", cardboardTrashPercentage);
				bag.put("plastictrash", plasticTrashPercentage);
				bag.put("glasstrash", glassTrashPercentage);
				bag.put("canstrash", cansTrashPercentage);
				Intent intent = new Intent();
				intent.putExtra("bagResult",bag.toString());
				setResult(RESULT_OK,intent);
				finish();
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	
	public class Bag
	{
		 public int collectionLogID;
		 public int weight;
		 public int wetTrashPercentage;
		 public int paperTrashPercentage;
		 public int cardboardTrashPercentage;
		 public int plasticTrashPercentage;
		 public int glassTrashPercentage;
		 public int cansTrashPercentage;
	}
//-----------------------------------------------------------------------------
}
//=============================================================================