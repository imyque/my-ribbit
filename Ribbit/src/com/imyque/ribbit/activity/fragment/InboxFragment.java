package com.imyque.ribbit.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imyque.ribbit.R;
//because of view pager on support

public class InboxFragment extends ListFragment{
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_inbox,
				container, false); 
				// always use false as the last parameter when attaching a fragment to an avtivity in code
		
		
		return rootView;
	}

}
