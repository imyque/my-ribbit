package com.imyque.ribbit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.imyque.ribbit.R;
import com.imyque.ribbit.Utility;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends Activity {
	
	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	protected Button mBtnSignUp;
	protected ProgressBar mProgressBar; 
	private SignUpCallback mSignUpCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		//Must come before set contentvwiw
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_signup);
		
		mProgressBar = (ProgressBar) findViewById(R.id.pbrSignup);
		
		
		mUsername = (EditText) findViewById(R.id.txtSignupUsername);
		mPassword = (EditText) findViewById(R.id.txtSignupPassword);
		mEmail = (EditText) findViewById(R.id.txtSignupEmail );
		mBtnSignUp = (Button) findViewById(R.id.btnSignup);
		
		//Define Signup callback for Signup listener
		mSignUpCallback = new SignUpCallback() {
			
			@Override
			public void done(ParseException e) {
				
				mProgressBar.setVisibility(View.INVISIBLE);
				setProgressBarIndeterminateVisibility(false);
				
				if (e == null) {
					
					//Hooray, new account created
					Intent intent = new Intent(SignupActivity.this, MainActivity.class) ;
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					
				} else {
					
					Utility.okDialog(SignupActivity.this, R.string.error_title, e.getMessage() );
					
				}
				
			}
		};
		
		//Define Signup listener for Signup button
		OnClickListener signUpListener = new View.OnClickListener() {	
			

			@Override
			public void onClick(View v) {
				
				mProgressBar.setVisibility(View.VISIBLE);
				
				String username = mUsername.getText().toString();
				String pwd = mPassword.getText().toString();
				String email = mEmail.getText().toString();
				
				username = username.trim();
				pwd = pwd.trim();
				email = email.trim();
				
				boolean isNetworkAvailable = Utility.isNetworkAvailable(  
						(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
				
				if (username.isEmpty() || email.isEmpty() || pwd.isEmpty() ) {
					
					mProgressBar.setVisibility(View.INVISIBLE);
					Utility.okDialog(SignupActivity.this, R.string.error_title, R.string.signup_error_message);
				
				} else if ( !isNetworkAvailable) {
					
					mProgressBar.setVisibility(View.INVISIBLE);
					Utility.okDialog(SignupActivity.this, R.string.error_title, R.string.no_network_error_message);
					
				} else {
					

					//Create new user
					ParseUser newUser = new ParseUser();
					newUser.setUsername(username);
					newUser.setEmail(email);
					newUser.setPassword(pwd);
					
					newUser.put("role", "data-entry");
					
					setProgressBarIndeterminateVisibility(true);
					
					newUser.signUpInBackground( mSignUpCallback);
					
				}
					
				
				
				
			}
		};
		
		mBtnSignUp.setOnClickListener( signUpListener);
		
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}
*/
	

}
