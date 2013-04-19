package edu.miami.csc531;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class GetLogsServlet extends HttpServlet
{
	private static final long serialVersionUID = 4L;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	{
		  String username = req.getParameter("username");
		 
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  Filter filter = new FilterPredicate("username", FilterOperator.EQUAL, username);
		  Query q = new Query("CollectionLog").setFilter(filter);
		  PreparedQuery pq = datastore.prepare(q);
		  FetchOptions fetchOptions = FetchOptions.Builder.withLimit(100);
		  QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		  
		  JSONArray jsonLogArray = new JSONArray();
		  for(Entity collectionLog : results)
		  {
			  long logID = collectionLog.getKey().getId();
			  String site = (String) collectionLog.getProperty("site");
			  String region = (String) collectionLog.getProperty("region");
			  String facilityType = (String) collectionLog.getProperty("facility");
			  String wastePartner = (String)collectionLog.getProperty("partner");
			  String date = (String)collectionLog.getProperty("date");
			  
			  JSONObject collectionLogJSON = new JSONObject();
			  collectionLogJSON.put("site", site);
			  collectionLogJSON.put("date", date);
			  collectionLogJSON.put("logID", logID);
			  collectionLogJSON.put("region", region);
			  collectionLogJSON.put("facility", facilityType);
			  collectionLogJSON.put("parnter",wastePartner);
			  
			  Filter bagFilter = new FilterPredicate("logid", FilterOperator.EQUAL, logID);
			  Query bagQuery = new Query("Bag").setFilter(bagFilter);
			  PreparedQuery bagPq = datastore.prepare(bagQuery);
			  FetchOptions bagFetchOptions = FetchOptions.Builder.withLimit(100);
			  QueryResultList<Entity> bagResults = bagPq.asQueryResultList(bagFetchOptions);
			  
			  JSONArray bagArray = new JSONArray();
			  
			  for (Entity bag : bagResults)
			  {
				  JSONObject jsonBag = new JSONObject();
				  
				  jsonBag.put("bagID", bag.getKey().getId());
				  jsonBag.put("weight",bag.getProperty("weight"));
				  jsonBag.put("wetTrashPercentage",bag.getProperty("wetTrashPercentage"));
				  jsonBag.put("paperTrashPercentage", bag.getProperty("paperTrashPercentage"));
				  jsonBag.put("cardboardTrashPercentage",bag.getProperty("cardboardTrashPercentage"));
				  jsonBag.put("plasticTrashPercentage",bag.getProperty("plasticTrashPercentage"));
				  jsonBag.put("glassTrashPercentage", bag.getProperty("glassTrashPercentage"));
				  jsonBag.put("cansTrashPercentage", bag.getProperty("cansTrashPercentage"));
				  
				  bagArray.put(jsonBag);
			  }
			  collectionLogJSON.put("Bags", bagArray);
			  jsonLogArray.put(collectionLogJSON);
		  }
		  resp.setContentType("application/json"); 
		  JSONObject jsonResult = new JSONObject();
		  jsonResult.put("Status", "OK");
		  jsonResult.put("Logs", jsonLogArray);
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
