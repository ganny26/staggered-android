package com.bacon.demo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bacon.demo.R;
import com.bacon.demo.WelcomeActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private TextView infoTextView;
    private LoginButton loginButton;
    private TwitterLoginButton twitterLoginButton;
    private CallbackManager callbackManager;
    private static final String CONSUMER_KEY = "cLjUPJvjxvC8S3BIwRStKVcGi";
    private static final String CONSUMER_SECRET = "ioRXouhGwffzdU47PXlsvVQ9ZbKkdM6qpgOfeT0UVhxZzHIGCo";
    public static final String PREFS_NAME = "test_app";
    public static final String CALLBACKURL = "app://twitter-dev";
    private TwitterCore twitter;
    private   TwitterAuthClient twitterAuthClient;


    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpFaceBookSDK();
        setUpTwitterOAuth();
        //setUpTwitterSDK();

    }

    private void setUpTwitterOAuth(){
      //  Twitter.initialize(this);
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize(this,new Callback<TwitterSession>(){
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
            }
        } );
    }
    private void setUpTwitterSDK(){
        Twitter.initialize(this);
//        TwitterConfig config = new TwitterConfig.Builder(this)
//                .logger(new DefaultLogger(Log.DEBUG))
//                .twitterAuthConfig(new TwitterAuthConfig(CONSUMER_KEY,CONSUMER_SECRET))
//                .debug(true)
//                .build();
//        Twitter.initialize(config);

        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                final String token = authToken.token;
                final String secret = authToken.secret;
                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        Log.i(TAG,"Twitter Result"+result+"--token--"+token+"--secret--"+secret);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.i(TAG,"Twitter error"+exception.toString());
                    }
                });

                //getTwitterUserInformation(result);
            }

            @Override
            public void failure(TwitterException e) {
                Log.i(TAG,"-error in fetching twitter details "+e.toString());
        }
        });
    }

    private void getTwitterUserInformation(Result<TwitterSession> result){
        TwitterSession twitterSession = result.data;
        String twitterUserName = twitterSession.getUserName();
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        twitterApiClient.getAccountService().verifyCredentials(true,true,true)
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        String name = result.data.name;
                        String email = result.data.email;
                        String photoUrlNormalSize   = result.data.profileImageUrl;
                        //String photoUrlBiggerSize   = userResult.data.profileImageUrl.replace("_normal", "_bigger");
                        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                        intent.putExtra("user_name",name);
                        intent.putExtra("user_email",email);
                        intent.putExtra("profile_url",photoUrlNormalSize);

                    }

                    @Override
                    public void failure(TwitterException e) {
                        Log.i(TAG,"-error in fetching twitter details "+e.toString());
                    }
                });



    }

    private void setUpFaceBookSDK(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        infoTextView  = (TextView)findViewById(R.id.infoTextView);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager,mFacebookCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     //   callbackManager.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }




    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            try{
                String userID = loginResult.getAccessToken().getUserId();
               String authToken = loginResult.getAccessToken().getToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                        Bundle facebookData = getFacebookData(jsonObject);
                        Log.i(TAG,"user information "+facebookData);
                    }
                });

                infoTextView.setText(userID );
            }catch (Exception e){
                Log.i(TAG,"-error in fetching fb details "+e.toString());
            }
        }

        @Override
        public void onCancel() {
            infoTextView.setText("Login attempt canceled.");
        }

        @Override
        public void onError(FacebookException error) {
            infoTextView.setText("Login attempt failed.");
        }

        private Bundle getFacebookData(JSONObject object) {
            Bundle bundle = new Bundle();

            try {
                String id = object.getString("id");
                URL profile_pic;
                try {
                    profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                    Log.i("profile_pic", profile_pic + "");
                    bundle.putString("profile_pic", profile_pic.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }

                bundle.putString("idFacebook", id);
                if (object.has("first_name"))
                    bundle.putString("first_name", object.getString("first_name"));
                if (object.has("last_name"))
                    bundle.putString("last_name", object.getString("last_name"));
                if (object.has("email"))
                    bundle.putString("email", object.getString("email"));
                if (object.has("gender"))
                    bundle.putString("gender", object.getString("gender"));




            } catch (Exception e) {
                Log.d(TAG, "BUNDLE Exception : "+e.toString());
            }

            return bundle;
        }
    };






}
