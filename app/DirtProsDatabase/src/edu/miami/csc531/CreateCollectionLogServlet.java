package edu.miami.csc531;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class CreateCollectionLogServlet extends HttpServlet
{
	 private static final long serialVersionUID = 2L;
	 public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 {
		 StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = req.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		 JSONObject jsonObject = new JSONObject(jb.toString());
		 String username = jsonObject.getString("username");
		 String region = jsonObject.getString("region");
		 String facilityType = jsonObject.getString("facility");
		 String wastePartner = jsonObject.getString("partner");
		 String collectionSite = jsonObject.getString("site");
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");      
		 String date = sdf.format(Calendar.getInstance().getTime());
		  
		  JSONArray bags = jsonObject.getJSONArray("bags");
		 
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  Entity collectionLog = new Entity("CollectionLog");
		  collectionLog.setProperty("site", collectionSite);
		  collectionLog.setProperty("username", username);
		  collectionLog.setProperty("region", region);
		  collectionLog.setProperty("facility", facilityType);
		  collectionLog.setProperty("partner", wastePartner);
		  collectionLog.setProperty("date", date);
		  
		  datastore.put(collectionLog);
		  
		  long collectionLogID = collectionLog.getKey().getId();

		  for(int i = 0; i<bags.length(); i++)
		  {
			 JSONObject jsonBag = bags.getJSONObject(i);
			 int weight = (int)jsonBag.get("weight");
			 int wetTrashPercentage = (int)jsonBag.get("wettrash");
			 int paperTrashPercentage = (int)jsonBag.get("papertrash");
			 int cardboardTrashPercentage = (int)jsonBag.get("cardboardtrash");
			 int plasticTrashPercentage = (int)jsonBag.get("plastictrash");
			 int glassTrashPercentage = (int)jsonBag.get("glasstrash");
			 int cansTrashPercentage = (int)jsonBag.get("canstrash");
			 
			 Entity bag = new Entity("Bag");
			 bag.setProperty("logid",collectionLogID);
			 bag.setProperty("weight", weight);
			 bag.setProperty("wetTrashPercentage", wetTrashPercentage);
			 bag.setProperty("paperTrashPercentage", paperTrashPercentage);
			 bag.setProperty("cardboardTrashPercentage", cardboardTrashPercentage);
			 bag.setProperty("plasticTrashPercentage", plasticTrashPercentage);
			 bag.setProperty("glassTrashPercentage", glassTrashPercentage);
			 bag.setProperty("cansTrashPercentage", cansTrashPercentage);
			 
			 datastore.put(bag);
		  }
		  
		  JSONObject jsonResult = new JSONObject();
		  jsonResult.put("Status", "OK");
		  jsonResult.put("collectionLogID", collectionLogID);
		  resp.setContentType("application/json"); 
		  try
		  {
			jsonResult.write(resp.getWriter());
		  }
		  catch (JSONException | IOException e)
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	 }
}
