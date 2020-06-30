package com.example.androidndktutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public native String helloWorld();
    static
    {
        System.loadLibrary("ndktest");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.text_hello_world)).setText(helloWorld());
        // globally
        //TextView myAwesomeTextView = (TextView)findViewById(R.id.text_hello_world);
        //in your OnCreate() method
        //myAwesomeTextView.setText("My Awesome Text");
    }
}