package com.imyque.ribbit.activity;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.imyque.ribbit.ParseK;
import com.imyque.ribbit.R;
import com.imyque.ribbit.Utility;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditFriendsActivity extends ListActivity {

	protected static final String TAG = EditFriendsActivity.class.getSimpleName();
	
	protected List<ParseUser> mUsers;
	private ParseRelation<ParseUser> mFriendRelation;
	private ParseUser mCurrentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_edit_friends);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// allow multiple items to be checked
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();

		mCurrentUser = ParseUser.getCurrentUser();
		mFriendRelation = mCurrentUser.getRelation(ParseK.KEY_FRIENDS_RELATION);
		getActionBar().setSubtitle("Welcome " + mCurrentUser.getUsername() );
		
		setProgressBarIndeterminateVisibility(true);



		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.orderByAscending(ParseK.KEY_USERNAME);
		query.setLimit(1000);
		
		
		query.findInBackground(findUserCallBack());


	}

	// when a list item is clicked
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		if (getListView().isItemChecked(position)) {
			
			//add
			mFriendRelation.add(mUsers.get(position) );
			
		} else {
			//remove
			mFriendRelation.remove (mUsers.get(position) );
		}
		
		setProgressBarIndeterminateVisibility(true);
		mCurrentUser.saveInBackground( saveUserCallback());
		
	}


	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setDisplayShowTitleEnabled(false);
		
		

	}

	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.edit_friends, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	// Find users callback
	private FindCallback<ParseUser> findUserCallBack() {

		// find users callback
		return new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> users, ParseException e) {

				setProgressBarIndeterminateVisibility(false);

				if (e == null) {

					mUsers = users;
					String[] usernames = new String[mUsers.size()];

					int i = 0;
					for ( ParseUser u : mUsers) {
						usernames[i] = u.getUsername() ;
						i++;
					}

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditFriendsActivity.this, 
							android.R.layout.simple_list_item_checked, 
							usernames);
					setListAdapter(adapter);
					
					addFriendCheckMarks();

				} else {

					handleError(e);
				}
			}
		};

		

	}
	

	private void addFriendCheckMarks() {
		
		mFriendRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> friends, ParseException e) {
				
				if (e == null ) {
					
					//check users on the list that are in the friends relation
					for (int i = 0 ; i < mUsers.size(); i++) {
						
						ParseUser u = mUsers.get(i);
						for ( ParseUser uF : friends) {
							if (u.getObjectId().equals(uF.getObjectId())) {
								getListView().setItemChecked(i, true);
							}
						}
						
					}
					
				} else {
					handleError(e);
				}
				
			}
		});
		
	}

	// Save user callback
	private SaveCallback saveUserCallback() {
		return new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				
				setProgressBarIndeterminateVisibility(false);
				
				if ( e == null ) {
					
					//Yipee
				} else {
					
					handleError(e);
				}
				
			}
		};
	}

	private void handleError(ParseException e) {
		Log.e(TAG, e.getMessage());
		Utility.okDialog(EditFriendsActivity.this, R.string.error_title, e.getMessage() ) ;
	}

}
