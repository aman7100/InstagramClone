package com.example.instagramclone;

import android.app.Application;
import android.util.Log;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
// Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        /* user + M4vSrVYUj6yt*/
// Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myappID")
                .clientKey("mFjsYfEtRkg9")
                .server("http://3.144.244.170/parse/")
                .build()
        );

//        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
