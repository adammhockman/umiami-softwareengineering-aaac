package aaac.dirtprosmobileapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
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
		
		switch(view.getId()){
		case R.id.exit_button:
			finish();
			break;
		case R.id.login_button:
			String username =((EditText)findViewById(R.id.username_field)).getText().toString();
	        String password = ((EditText)findViewById(R.id.password_field)).getText().toString();
			LoginTask loginTask = new LoginTask(username, password, this);
			loginTask.execute();
			break;
		default:
			break;
		}
	}
	
	public void onLoginResult(boolean success)
	{
		Intent nextActivity;
		if (success)
		{
			nextActivity = new Intent();
			nextActivity.putExtra("username",((EditText)findViewById(R.id.username_field)).getText().toString());
            nextActivity.setClassName("aaac.dirtprosmobileapp","aaac.dirtprosmobileapp.MainOptionsScreen");
            startActivity(nextActivity);
		}
		else
		{
			Toast toast = Toast.makeText(this,"Login failed, try again",Toast.LENGTH_SHORT);
			((EditText)findViewById(R.id.password_field)).setText("");
			toast.show();
		}
	}
//-----------------------------------------------------------------------------
//=============================================================================

	public class LoginTask extends AsyncTask<Void, Void, Void>
	{
		private String username;
		private String password;
		private Context context;
		private ProgressDialog progressDialog;
		boolean loginResult = false;
		public LoginTask(String username, String password, Context c)
		{
			this.username = username;
			this.password = password;
			context = c;
		}
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
	        progressDialog = new ProgressDialog(context);
	        progressDialog.setMessage("Attempting Login...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.show();
			
		}
		@Override
		protected Void doInBackground(Void... params)
		{    
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        InputStream inputStream = null;
	        String uri = "http://dirtprosdatabase.appspot.com/login" + "?username=" + username + "&password=" + password;
			HttpGet get = new HttpGet(uri);
			String jsonString = "";
			JSONObject jsonObj = null;
			 try 
			 {
			      HttpResponse response = httpClient.execute(get);
			      if (response.getStatusLine().getStatusCode()== 200) 
			      {
			        HttpEntity entity = response.getEntity();
			        inputStream = entity.getContent();
			       
			      } 
			      else
			      {
			        Log.e("Login", "Couldn't obtain response from server");
			      }
			 }
			 catch (ClientProtocolException e)
			 {
			      e.printStackTrace();
			 } 
			 catch (IOException e) 
			 {
				e.printStackTrace();
			} 
			 
			 try
			 {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(
		                    inputStream, "iso-8859-1"), 8);
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            inputStream.close();
		            jsonString = sb.toString();
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		        }
		        try {
		            jsonObj = new JSONObject(jsonString);
		        } catch (JSONException e)
		        {
		            Log.e("JSON Parser", "Error parsing data " + e.toString());
		        }
		        try {
					loginResult = jsonObj.getBoolean("Verified");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        return null;
		}
		
		 @Override
		 protected void onPostExecute(Void v)
		 {
		        super.onPostExecute(v);
		        progressDialog.hide();
		        onLoginResult(loginResult);
		 }
	}
}