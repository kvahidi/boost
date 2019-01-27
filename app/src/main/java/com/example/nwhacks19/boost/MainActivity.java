package com.example.nwhacks19.boost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;
import android.widget.Button;

import com.snapchat.kit.sdk.SnapCreative;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi;

public class MainActivity extends AppCompatActivity {

    private static final float BITMOJI_CONTAINER_FOCUS_WEIGHT = 9.0f;
    private static final String EXTERNAL_ID_QUERY = "{me{externalId}}";

    final LoginStateController.OnLoginStateChangedListener mLoginStateChangedListener =
        new LoginStateController.OnLoginStateChangedListener() {
            @Override
            public void onLoginSucceeded() {
                System.out.println("Logged in");
            }

            @Override
            public void onLoginFailed() {
                // Here you could update UI to show login failure
            }

            @Override
            public void onLogout() {
                // Here you could update UI to reflect logged out state
            }
        };

    private SnapCreativeKitApi snapCreativeKitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupSnapKitAPI();

        final Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SnapLogin.getAuthTokenManager(MainActivity.this).startTokenGrant();
            }
        });
        SnapLogin.getLoginStateController(this).addOnLoginStateChangedListener(mLoginStateChangedListener);
        boolean isUserLoggedIn = SnapLogin.isUserLoggedIn(this);
        if (isUserLoggedIn) {
            System.out.println("Logged in");
        }


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCreateActivity();
            }
        });

        setupSnapKitAPI();

    }

    private void setupSnapKitAPI() {
        snapCreativeKitApi = SnapCreative.getApi(this);

    }

    private void launchCreateActivity() {
        Intent intent = new Intent(MainActivity.this, CreateQuestActivity.class);
        startActivity(intent);
    }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }


}
