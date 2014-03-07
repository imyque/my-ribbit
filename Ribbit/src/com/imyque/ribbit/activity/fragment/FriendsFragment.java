package com.imyque.ribbit.activity.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.imyque.ribbit.ParseK;
import com.imyque.ribbit.R;
import com.imyque.ribbit.Utility;
import com.imyque.ribbit.activity.EditFriendsActivity;
import com.imyque.ribbit.activity.MainActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
//because of view pager on support

public class FriendsFragment extends ListFragment{
	
	protected static final String TAG = FriendsFragment.class.getSimpleName();
	
	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mFriendRelation;
	protected ParseUser mCurrentUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_friends,
				container, false);
		
		
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();

		mCurrentUser = ParseUser.getCurrentUser();
		mFriendRelation = mCurrentUser.getRelation(ParseK.KEY_FRIENDS_RELATION);
		
		getActivity().setProgressBarIndeterminate(true);

		ParseQuery<ParseUser> query =  mFriendRelation.getQuery();
		query.orderByAscending(ParseK.KEY_USERNAME) ;
		query.findInBackground(findFriendsCallBack() ) ;
		

	}
	
	// Find users callback
	private FindCallback<ParseUser> findFriendsCallBack() {

			// find users callback
			return new FindCallback<ParseUser>() {

				@Override
				public void done(List<ParseUser> friends, ParseException e) {
					
					getActivity().setProgressBarIndeterminate(false);
					
					if (e == null) {

						mFriends = friends;
						
						String[] usernames = new String[mFriends.size()];

						int i = 0;
						for ( ParseUser u : mFriends) {
							usernames[i] = u.getUsername() ;
							i++;
						}

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), 
								android.R.layout.simple_list_item_1, 
								usernames);
						setListAdapter(adapter);

					} else {

						handleError(e);
					}
				}
			};

			

		}
		

	
		
		private void handleError(ParseException e) {
			Log.e(TAG, e.getMessage());
			Utility.okDialog(getListView().getContext() , R.string.error_title, e.getMessage() ) ;
		}

}
