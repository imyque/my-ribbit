package com.imyque.ribbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends Activity {
	
	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	protected Button mBtnSignUp;
	protected ProgressBar mProgressBar; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		mProgressBar = (ProgressBar) findViewById(R.id.pbrSignup);
		
		
		mUsername = (EditText) findViewById(R.id.txtSignupUsername);
		mPassword = (EditText) findViewById(R.id.txtSignupPassword);
		mEmail = (EditText) findViewById(R.id.txtSignupEmail );
		mBtnSignUp = (Button) findViewById(R.id.btnSignup);
		
		mBtnSignUp.setOnClickListener( new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				mProgressBar.setVisibility(View.VISIBLE);
				
				String username = mUsername.getText().toString();
				String pwd = mPassword.getText().toString();
				String email = mEmail.getText().toString();
				
				username = username.trim();
				pwd = pwd.trim();
				email = email.trim();
				
				if (username.isEmpty() || email.isEmpty() || pwd.isEmpty() ) {
					
					mProgressBar.setVisibility(View.INVISIBLE);
					Utility.okDialog(SignupActivity.this, R.string.signup_error_title, R.string.signup_error_message);
					
				} else {
					
					//Create new user
					ParseUser newUser = new ParseUser();
					newUser.setUsername(username);
					newUser.setEmail(email);
					newUser.setPassword(pwd);
					
					newUser.put("role", "data-entry");
					
					newUser.signUpInBackground( new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							
							mProgressBar.setVisibility(View.INVISIBLE);
							
							if (e == null) {
								
								//Hooray, new account created
								Intent intent = new Intent(SignupActivity.this, MainActivity.class) ;
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
								
							} else {
								
								Utility.okDialog(SignupActivity.this, R.string.signup_error_title, e.getMessage() );
								
							}
							
						}
					});
					
					
				}
					
				
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	

}
