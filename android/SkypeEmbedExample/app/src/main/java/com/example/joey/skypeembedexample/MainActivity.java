package com.example.joey.skypeembedexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

// embed Skype functionality
import com.skype.android.mobilesdk.api.Modality;
import com.skype.android.mobilesdk.api.SkypeApi;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callSkype(View v) {
        try {
            SkypeApi skypeApi = new SkypeApi(getApplicationContext());

            // make a call to someone
            skypeApi.startConversation("Joey Cluett", Modality.AudioCall);

            // get the app id of bot and start chat
            //skypeApi.startConversation("28: f8cdef31-a31e-4b4a-93e4-5f571e91255a", Modality.Chat);
        } catch(Exception e) {
            e.printStackTrace();

        }
    }

}
