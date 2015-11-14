package com.kennanseno.ultimate_scoreboard_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kennanseno.ultimate_scoreboard_app.Backend.DBManager;
import com.kennanseno.ultimate_scoreboard_app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    DBManager dbManager =  new DBManager(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile,email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final Intent intent = new Intent(MainActivity.this, EventsActivity.class);

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),

                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    dbManager.insertNewUser(object.getString("id"),object.getString("name"));
                                    Log.d("Test", object.toString());
                                    Log.d("Test", "ID: " + object.getString("id") + " Name:" + object.getString("name"));

                                    intent.putExtra("userId", object.getInt("id"));
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Cancel!",Toast.LENGTH_LONG).show();
                Log.d("Test","Cancel");
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(MainActivity.this, "Error!",Toast.LENGTH_LONG).show();
                Log.d("Test","Error");

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
