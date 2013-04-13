package edu.miami.csc531;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class AddBagServlet extends HttpServlet
{
	 private static final long serialVersionUID = 3L;
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
		 int collectionLogID = (int)jsonObject.get("logid");
		 int weight = (int)jsonObject.get("weight");
		 int wetTrashPercentage = (int)jsonObject.get("wettrash");
		 int paperTrashPercentage = (int)jsonObject.get("papertrash");
		 int cardboardTrashPercentage = (int)jsonObject.get("cardboardtrash");
		 int plasticTrashPercentage = (int)jsonObject.get("plastictrash");
		 int glassTrashPercentage = (int)jsonObject.get("glasstrash");
		 int cansTrashPercentage = (int)jsonObject.get("canstrash");
		 
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 
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
		  resp.setContentType("application/json"); 
		  JSONObject jsonResult = new JSONObject();
		  jsonResult.put("Status", "OK");
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
