package com.imyque.ribbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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
				

				if (username.isEmpty() || pwd.isEmpty() ) {

					mProgressBar.setVisibility(View.INVISIBLE);
					Utility.okDialog(LoginActivity.this, R.string.error_title, R.string.signup_error_message);

				} else {

					//Login
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

				Intent intent = new Intent(LoginActivity.this, SignupActivity.class);  //Context is the login activity
				startActivity(intent);

			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
