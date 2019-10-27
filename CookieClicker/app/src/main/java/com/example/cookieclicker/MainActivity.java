package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    ImageView cookie;
    TextView headline;
    TextView scoreText;
    long score;
    int clickPower;

    @Override                                               /////////// INIT
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookie = findViewById(R.id.Cookie);
        cookie.setImageResource(R.drawable.cookie);

        headline = findViewById(R.id.Headline);
        headline.setText("Cookie Clicker Mobile!");

        scoreText = findViewById(R.id.Score);
        scoreText.setText("0");

        score = 0;
        clickPower = 1;




    }                                                       //////////// INIT

    public void CookieClick(View view) {
        score+=clickPower;

        scoreText.setText(Long.toString(score));
    }
}
