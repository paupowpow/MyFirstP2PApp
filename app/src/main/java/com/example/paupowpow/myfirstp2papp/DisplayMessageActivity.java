package com.example.paupowpow.myfirstp2papp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // grabs the intent that started the activity
        // Every Activity is invoked by an Intent, regardless of how the user navigated there
        Intent intent = getIntent();
        // getStringExtra() retrieves data from first activity
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);

        // create a TextView and set size and message
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // identify the layout, cast the layout to ViewGroup
        // (because it is the superclass of all layouts and contains the addView() method)
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}
