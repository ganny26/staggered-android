package com.bacon.demo.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bacon.demo.R;
import com.bacon.demo.WelcomeActivity;
import com.bumptech.glide.Glide;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private TextView infoTextView;
    private LoginButton loginButton;
    private TwitterLoginButton mLoginButton;
    private ImageView profileView;
    private CallbackManager callbackManager;
    private static final String CONSUMER_KEY = "cLjUPJvjxvC8S3BIwRStKVcGi";
    private static final String CONSUMER_SECRET = "ioRXouhGwffzdU47PXlsvVQ9ZbKkdM6qpgOfeT0UVhxZzHIGCo";
    public static final String PREFS_NAME = "test_app";
    public static final String CALLBACKURL = "app://twitter-dev";
    private FirebaseAuth mAuth;



    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        profileView = (ImageView) findViewById(R.id.display_picture) ;
        Glide.with(LoginActivity.this).load("https://graph.facebook.com/1483163761753185/picture?type=large").into(profileView);
        setUpFaceBookSDK();

        //setUpTwitterSDK();
       // setUpTwitterFirebase();

    }
//    @Override
//    private void setUpTwitterFirebase(){
//        mAuth = FirebaseAuth.getInstance();
//        mLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
//        mLoginButton.setCallback(new Callback<TwitterSession>() {
//            @Override
//            public void success(Result<TwitterSession> result) {
//                Log.d(TAG, "twitterLogin:success" + result);
//                handleTwitterSession(result.data);
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                Log.w(TAG, "twitterLogin:failure", exception);
//              //  updateUI(null);
//            }
//        });
//    }


 //   private void setUpTwitterSDK(){
//        TwitterAuthConfig twitterAuthConfig = new TwitterAuthConfig(CONSUMER_KEY,CONSUMER_SECRET)
//        TwitterConfig config = new TwitterConfig.Builder(this)
//                .logger(new DefaultLogger(Log.DEBUG))
//                .twitterAuthConfig(new TwitterAuthConfig())
//                .debug(true)
//                .build();
//
//        Twitter.initialize(config);

     //   twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
//        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
//            @Override
//            public void success(Result<TwitterSession> result) {
//                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
//                TwitterAuthToken authToken = session.getAuthToken();
//                final String token = authToken.token;
//                final String secret = authToken.secret;
//                TwitterAuthClient authClient = new TwitterAuthClient();
//                authClient.requestEmail(session, new Callback<String>() {
//                    @Override
//                    public void success(Result<String> result) {
//                        Log.i(TAG,"Twitter Result"+result+"--token--"+token+"--secret--"+secret);
//                    }
//
//                    @Override
//                    public void failure(TwitterException exception) {
//                        Log.i(TAG,"Twitter error"+exception.toString());
//                    }
//                });
//
//                //getTwitterUserInformation(result);
//            }
//
//            @Override
//            public void failure(TwitterException e) {
//                Log.i(TAG,"-error in fetching twitter details "+e.toString());
//        }
//        });
   // }

//    private void getTwitterUserInformation(Result<TwitterSession> result){
//        TwitterSession twitterSession = result.data;
//        String twitterUserName = twitterSession.getUserName();
//        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
//        twitterApiClient.getAccountService().verifyCredentials(true,true,true)
//                .enqueue(new Callback<User>() {
//                    @Override
//                    public void success(Result<User> result) {
//                        String name = result.data.name;
//                        String email = result.data.email;
//                        String photoUrlNormalSize   = result.data.profileImageUrl;
//                        //String photoUrlBiggerSize   = userResult.data.profileImageUrl.replace("_normal", "_bigger");
//                        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
//                        intent.putExtra("user_name",name);
//                        intent.putExtra("user_email",email);
//                        intent.putExtra("profile_url",photoUrlNormalSize);
//
//                    }
//
//                    @Override
//                    public void failure(TwitterException e) {
//                        Log.i(TAG,"-error in fetching twitter details "+e.toString());
//                    }
//                });
//
//
//
//    }

    private void setUpFaceBookSDK(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        infoTextView  = (TextView)findViewById(R.id.infoTextView);
       // profileView = (ImageView) findViewById(R.id.display_picture) ;
        loginButton = (LoginButton)findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email","user_birthday"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager,mFacebookCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
       // mLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG, "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        //    updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                         //   updateUI(null);
                        }


                    }
                });
    }


    public static URL getFacebookProfilePicture(String userID) {
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        } catch (Exception e) {
            Log.v(TAG, "error while fetching fb profile picture-" + e.toString());
        }
        return imageURL;
    }




    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            final String userID = loginResult.getAccessToken().getUserId();
            final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject fbJsonObject, GraphResponse response) {
                        try{
                            JSONObject facebookResponse = response.getJSONObject();
                            URL fbProfileUrl = getFacebookProfilePicture(userID);
                            facebookResponse.put("auth_token",accessToken.getToken());
                            facebookResponse.put("profile_url",fbProfileUrl);
                            Log.v(TAG, "facebook response" + facebookResponse.toString());
                           // Glide.with(LoginActivity.this).load(fbProfileUrl).into(profileView);

                        }catch (JSONException e) {
                            Log.i(TAG,"-error in fetching fb details "+e.toString());
                        }
                    }
                });
        }

        @Override
        public void onCancel() {
            infoTextView.setText("Login attempt canceled.");
        }

        @Override
        public void onError(FacebookException error) {
            infoTextView.setText("Login attempt failed.");
        }

    };

}
