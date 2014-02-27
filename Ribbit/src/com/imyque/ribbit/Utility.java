package com.imyque.ribbit;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {
	
	public static void okDialog(Context context, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setMessage(message)
		.setTitle(title)
		.setPositiveButton(android.R.string.ok, null);
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public static void okDialog(Context context, int title, int message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	
		builder.setMessage(message)
			.setTitle(title)
			.setPositiveButton(android.R.string.ok, null);
		
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public static void okDialog(Context context, String title, int message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	
		builder.setMessage(message)
			.setTitle(title)
			.setPositiveButton(android.R.string.ok, null);
		
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	
	public static void okDialog(Context context , int title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	
		builder.setMessage(message)
			.setTitle(title)
			.setPositiveButton(android.R.string.ok, null);
		
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public static boolean isNetworkAvailable(ConnectivityManager manager) {
	
		NetworkInfo netInfo = manager.getActiveNetworkInfo();
				
		boolean isDey = false;		
		
		if (netInfo != null && netInfo.isConnected()){
			isDey = true;
		}
		
		return isDey;
	}


}
