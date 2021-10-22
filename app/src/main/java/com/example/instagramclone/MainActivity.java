package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.security.Key;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener{
    Boolean signUpModeActive=true;
    TextView loginTextview;
    EditText usernameEditText;
    EditText PasswordEditText;
    public void showUserList(){
        Intent intent=new Intent(getApplicationContext(),UserlistActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
         if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
         {
           signUpclicked(view);
         }
        return false;
    }


    @Override
    public void onClick(View view)
    {
         if(view.getId()==R.id.textView) {
             Button signUpButton = findViewById(R.id.signUpButton);
             if (signUpModeActive) {
                 signUpModeActive = false;
                 signUpButton.setText("Login");
                 loginTextview.setText("or,Sign up");
             } else {
                 signUpModeActive = true;
                 signUpButton.setText("Sign up");
                 loginTextview.setText("or,Login");
             }
         }
             else if(view.getId()==R.id.logoimageView || view.getId()==R.id.backgroundLayout)
         {
             InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
             inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
         }
         }


    public void signUpclicked(View view)
    {

        if(usernameEditText.getText().toString().matches("") || PasswordEditText.getText().toString().matches(""))
        {
            Toast.makeText(this,"A username and Password is Required!",Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(signUpModeActive) {


                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(PasswordEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Sign Up", "Success");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else
            {
                //Login
                ParseUser.logInInBackground(usernameEditText.getText().toString(), PasswordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null)
                        {
                        Log.i("Login","OK");
                        showUserList();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Instagram");
        loginTextview=findViewById(R.id.textView);
        loginTextview.setOnClickListener(this);
        usernameEditText=findViewById(R.id.UsernameEditText);
        PasswordEditText=findViewById(R.id.PasswordEditText);
        ImageView logoImageView=findViewById(R.id.logoimageView);
        ConstraintLayout backgroundLayout=findViewById(R.id.backgroundLayout);
        logoImageView.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);
        PasswordEditText.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null)
        {
            showUserList();
        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}