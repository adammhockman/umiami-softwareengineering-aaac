package edu.miami.csc531;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class VerifyUserServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	{
		JSONObject jsonObj = new JSONObject();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		// Verify the Username and Password
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter filter = new FilterPredicate("username", FilterOperator.EQUAL, username);
		Query q = new Query("Employee").setFilter(filter);
		PreparedQuery pq = datastore.prepare(q);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		boolean verified = false;
		if(results.size() > 0)
		{
			Entity user = results.get(0);
			if (password.equals(user.getProperty("password")))
			{
				verified = true;
			}
		}
		try 
		{
			jsonObj.put("Verified", verified);
		}
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Send the response here
		resp.setContentType("application/json");       
	    try 
	    {
			jsonObj.write(resp.getWriter());
		}
	    catch (JSONException | IOException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
