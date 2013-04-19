package aaac.dirtprosmobileapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//=============================================================================
public class RecordCollectionInfoScreen extends Activity {
//-----------------------------------------------------------------------------
	private final int ADD_BAG_ACTIVITY = 42;
	private final int CONFIRMATION_DIALOG = 27;
	// I'm not doing onPause shennanigans, so I may lose the contents of bagArray if the activity gets recycled
	private JSONArray bagsArray = new JSONArray(); 
	private String username;
	Context context; 
//-----------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_collection_info_screen);
		context = this; 
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
		case R.id.submit_data_button:
			showDialog(CONFIRMATION_DIALOG);
			break;
		case R.id.add_bag_button:
			nextActivity = new Intent();
            nextActivity.setClassName("aaac.dirtprosmobileapp",
"aaac.dirtprosmobileapp.AddBagScreen");
            startActivityForResult(nextActivity,ADD_BAG_ACTIVITY);
			break;
		default:
			break;
		}
	}
//-----------------------------------------------------------------------------
	public void onActivityResult(int requestCode,int resultCode,Intent data) {
	
		switch(requestCode){
		case ADD_BAG_ACTIVITY:
			if (resultCode == RESULT_OK)
			{
				String jsonString = data.getStringExtra("bagResult");
				JSONObject jsonObj;
				try
				{
					jsonObj = new JSONObject(jsonString);
					bagsArray.put(jsonObj);
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			break;
		default:
			break;
		}
	}
//-----------------------------------------------------------------------------
    protected Dialog onCreateDialog(int dialogId) {
	        
	    AlertDialog.Builder dialogBuilder;
	        
	    dialogBuilder = new AlertDialog.Builder(this);
	    switch (dialogId) {
        case CONFIRMATION_DIALOG:
            dialogBuilder.setMessage("Are you sure you want to submit this data?");
            dialogBuilder.setPositiveButton("Confirm Submission",confirmationListener);
            dialogBuilder.setNegativeButton("Cancel Submission",confirmationListener);
            dialogBuilder.setCancelable(false);
            break;
        default:
            break;
        }
        return(dialogBuilder.create());
    }
//-----------------------------------------------------------------------------
    DialogInterface.OnClickListener confirmationListener = 
new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int whatWasClicked) {
            
            switch (whatWasClicked) {
            case DialogInterface.BUTTON_POSITIVE:
            	String site = ((EditText)findViewById(R.id.collectionSite_field)).getText().toString();
            	String region = ((EditText)findViewById(R.id.region_field)).getText().toString();
            	String facilityType = ((EditText)findViewById(R.id.facility_field)).getText().toString();
            	String wasteRemovalPartner = ((EditText)findViewById(R.id.partner_field)).getText().toString();
            	
         		JSONObject collectionLog  = new JSONObject();
       		 	try
       		 	{
       		 		collectionLog.put("site", site);
					collectionLog.put("username",username);
	       		 	collectionLog.put("region",region);
	       		 	collectionLog.put("facility", facilityType);
	       		 	collectionLog.put("partner",wasteRemovalPartner);
	       		 	collectionLog.put("bags",bagsArray);
	       		 	
	            	CreateCollectionLogTask uploadTask = new CreateCollectionLogTask();
	            	uploadTask.execute(collectionLog);
				} catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            	
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
            default:
                break;
            }
        }
    };
//-----------------------------------------------------------------------------

    public class CreateCollectionLogTask extends AsyncTask<JSONObject, Void, Boolean>
	{
    	ProgressDialog progressDialog;
    	
    	@Override
    	protected void onPreExecute()
    	{
    		super.onPreExecute();
	        progressDialog = new ProgressDialog(context);
	        progressDialog.setMessage("Uploading CollectionLog...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.show();
   
    	}
    	
		@Override
		protected Boolean doInBackground(JSONObject... params)
		{
			StringBuilder builder = new StringBuilder();
			JSONObject jsonObj = params[0];
			HttpParams httpParameters = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(httpParameters, 300000);
	         
	        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpPost postMethod = new HttpPost("http://www.dirtprosdatabase.appspot.com/createLog");
			try
			{
				StringEntity se = new StringEntity(jsonObj.toString());
				se.setContentType("application/json");
				postMethod.setEntity(se);
				
				HttpResponse response = httpClient.execute(postMethod);
				if(response.getStatusLine().getStatusCode() == 200)
				{
					InputStream responseStream = response.getEntity().getContent();
					BufferedReader buff = new BufferedReader(new InputStreamReader(responseStream));
					String line;
					while((line = buff.readLine()) != null)
						builder.append(line);
					responseStream.close();
					
					String json = builder.toString();
					JSONObject result = new JSONObject(json);
					
					if(!result.has("Status") || !result.getString("Status").equals("OK") )
					{
						return false;
					}
					else
					{
						return true;
					}
				}
			}
			catch (Exception e)
			{
				Log.e("Sending Log", e.toString());
			}
			return false;
		}
		
		protected void onPostExecute(Boolean result)
		{
			super.onPostExecute(result);
			progressDialog.dismiss();
			if(!result)
			{
				Toast.makeText(context, "Failed to upload log", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(context, "Upload Successful", Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}
}
//=============================================================================