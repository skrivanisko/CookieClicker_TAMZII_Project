package com.example.cookieclicker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends Activity{

    ImageView cookie;
    TextView headline;
    TextView scoreText;
    long score;
    int clickPower;
    int production;
    SharedPreferences preferences;
    long gameStartTime;
    boolean running;
    Handler timeHandler;
    Runnable runnable;
    DB db;

    //TODO: zkraslit kod, rozdělit do funkci
    //TODO: zvazit skore v stringu, jak bude velke, bude se muset ukladat v tisicich, možná hodit do floatu a nasobit litrem, pak string
    //TODO: podívat se k Radoskovi na SQL Lite a udělat dělníky, store a retrieve z db a nasobit tim produkci
    //TODO: napadovat canvas a další věci

    @Override                                               /////////// INIT
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookie = findViewById(R.id.Cookie);
        cookie.setImageResource(R.drawable.cookie);

        headline = findViewById(R.id.Headline);
        headline.setText("Cookie Clicker Mobile!");

        scoreText = findViewById(R.id.Score);

        db = new DB(this);

        gameStartTime = Calendar.getInstance().getTimeInMillis();
        score = 0;
        clickPower = 1;
        production = 1;
        running = true;
        timeHandler = new Handler();

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        //SharedPreferences.Editor preferencesEditor = preferences.edit();
        //preferencesEditor.putString("score", Long.toString(score));
        //preferencesEditor.apply();
        score = Long.parseLong(preferences.getString("score", "error score"));
        scoreText.setText(Long.toString(score));

        resetGame();
    }                                                       //////////// INIT

    public void resetGame(){
        for (Helper h : db.select()){
            Helper help;
            help = h;
            h.setCount(0);

            db.update(h);
        }
        score=0; //TODO:Smazat shared preferences, zastaví jen produkci ale score se usí mazat v shared prefs
    }


    public void updateScore(){
        score+=production;
        scoreText.setText(Long.toString(score));
    }

    public void CookieClick(View view) {
        score+=clickPower;

        scoreText.setText(Long.toString(score));

        Toast.makeText(this, Integer.toString(db.getProduction()), Toast.LENGTH_SHORT).show();

        for(Helper h : db.select())
            Toast.makeText(this, h.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();

        timeHandler.removeCallbacks(runnable);

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        String timeOnPause = Long.toString(Calendar.getInstance().getTimeInMillis()/1000);
        preferencesEditor.putString("timeOnPause", timeOnPause);
        preferencesEditor.putString("score", Long.toString(score));
        preferencesEditor.apply();
        super.onPause();
    }

    public void RunTimeListener(){
        timeHandler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                updateScore();
                timeHandler.postDelayed(this, 1000);
            }
        },1000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RunTimeListener();

        score = Long.parseLong(preferences.getString("score", "error score"));

        String timeOnPause = preferences.getString("timeOnPause","error");
        long timePauseSeconds = Long.parseLong(timeOnPause);
        long currTime = Calendar.getInstance().getTimeInMillis()/1000;

        long differenceSeconds = (currTime - timePauseSeconds);


        production = db.getProduction();
        score+= differenceSeconds*production;
    }

    public void enterShop(View view) {
        Intent i = new Intent(this, ShopActivity.class);
        i.putExtra("score", Long.toString(score));
        startActivity(i);
    }
}
