package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

    ImageView cookie;

    @Override                                               /////////// INIT
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookie.setImageResource(R.drawable.cookie);




    }                                                       //////////// INIT
}
