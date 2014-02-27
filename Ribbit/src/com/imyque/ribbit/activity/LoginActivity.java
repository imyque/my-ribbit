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
import android.widget.TextView;

import com.imyque.ribbit.R;
import com.imyque.ribbit.Utility;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {

	protected TextView mTxvSignUp;
	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mBtnLogin;
	private LogInCallback mLoginCallback;

	protected ProgressBar mProgressBar; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Must come before set contentvwiw
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_login);

		mProgressBar = (ProgressBar) findViewById(R.id.pbrLogin);

		mUsername = (EditText) findViewById(R.id.txtUsername);
		mPassword = (EditText) findViewById(R.id.txtPassword);
		mBtnLogin = (Button) findViewById(R.id.btnLogin);

		//Define Login callback for Login listener
		mLoginCallback = new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				
				mProgressBar.setVisibility(View.INVISIBLE);
				setProgressBarIndeterminateVisibility(false);
				
				if (e == null) {

					//Hooray, login success
					
					Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);

				} else {

					Utility.okDialog(LoginActivity.this, R.string.error_title, e.getMessage() );

				}

			}
		};

		//Define Login listener for Login button
		OnClickListener signUpListener = new View.OnClickListener() {	


			@Override
			public void onClick(View v) {

				mProgressBar.setVisibility(View.VISIBLE);

				String username = mUsername.getText().toString();
				String pwd = mPassword.getText().toString();
				

				username = username.trim();
				pwd = pwd.trim();
				
				boolean isNetworkAvailable = Utility.isNetworkAvailable(  
						(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

				if (username.isEmpty() || pwd.isEmpty() ) {

					mProgressBar.setVisibility(View.INVISIBLE);
					Utility.okDialog(LoginActivity.this, R.string.error_title, R.string.signup_error_message);

				} else if ( !isNetworkAvailable) {

					mProgressBar.setVisibility(View.INVISIBLE);
					Utility.okDialog(LoginActivity.this, R.string.error_title, R.string.no_network_error_message);					
					
					
				}	else {
				
					setProgressBarIndeterminateVisibility(true);
					ParseUser.logInInBackground(username , pwd, mLoginCallback);

				}




			}
		};

		mBtnLogin.setOnClickListener( signUpListener);


		//Signup link
		mTxvSignUp = (TextView) findViewById(R.id.txvSignup);
		mTxvSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				mTxvSignUp.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
				mTxvSignUp.setTextColor(getResources().getColor(android.R.color.white)) ;
				Intent intent = new Intent(LoginActivity.this, SignupActivity.class);  //Context is the login activity
				startActivity(intent);
				mTxvSignUp.setBackgroundColor(getResources().getColor(android.R.color.transparent));
				mTxvSignUp.setTextColor(getResources().getColor(android.R.color.holo_blue_dark)) ;

			}
		});
	}

/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
*/
}
