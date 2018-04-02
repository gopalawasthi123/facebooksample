package com.example.gopalawasthi.facebooksample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    LoginButton loginButton;
    private ProfileTracker profileTracker;
    private String firstName,lastName, email,birthday,gender;
    private URL profilePicture;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();




        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(EMAIL,"user_birthday","user_posts");
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request =  GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.d("FAB",object.toString());
                            Log.d("FAB",response.toString());

                        try{

                            userId = object.getString("id");

                            profilePicture = new URL("https://graph.facebook.com/"
                                                    + userId + "/picture?width=500&height=500");

                            if(object.has("first_name")){
                                firstName = object.getString("first_name");
                            }
                            if(object.has("last_name")){
                                lastName = object.getString("last_name");
                            }
                            if(object.has("email")){
                                email = object.getString("email");
                            }
                            if(object.has("birthday")){
                                birthday = object.getString("birthday");
                            }
                             if(object.has("gender")){
                                gender = object.getString("gender");
                             }

                    Intent intent = new Intent(MainActivity.this,Info.class);
                             intent.putExtra("firstname",firstName);
                             intent.putExtra("lastname",lastName);
                             intent.putExtra("picture",profilePicture.toString());
                             intent.putExtra("gender",gender);
                             startActivity(intent);
                             finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }


                    }

                });
            Bundle bundle = new Bundle();
            bundle.putString("fields","id, first_name, last_name, email, birthday, gender");
            request.setParameters(bundle);
            request.executeAsync();
            }


            @Override
            public void onCancel() {
                // App code
            }


            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
