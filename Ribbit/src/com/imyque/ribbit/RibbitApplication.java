package com.imyque.ribbit;

import android.app.Application;

import com.parse.Parse;


public class RibbitApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, "xTRbmVtPrDVOie3pfUS0VAAuiNiXZRsZqomFIpGW", "fOrdgWjY4KYCkhf54QUcts1mX4ae0spBt0dce2Xh");
		  
/*		
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
		
		  
		Map<String, String> dimensions = new HashMap<String, String>();
		// What type of news is this?
		dimensions.put("category", "politics");
		// Is it a weekday or the weekend?
		dimensions.put("dayType", "weekday");
		// Send the dimensions to Parse along with the 'read' event
		 
		ParseAnalytics.trackEvent("read", dimensions);
*/		
		
	}

}
