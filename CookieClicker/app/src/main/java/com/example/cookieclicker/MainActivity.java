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
    TextView productionLabel;
    MyCanvas myCanvas;
    SoundProducer musicPlayer;

    public int[] startCost = {50, 500, 1250, 2500, 5000};

    //TODO 0: zvuky
    //TODO 1: design

    @Override                                               /////////// INIT
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicPlayer = new SoundProducer(this);

        cookie = findViewById(R.id.Cookie);
        cookie.setImageResource(R.drawable.cookie);

        headline = findViewById(R.id.Headline);
        headline.setText("Cookie Clicker Mobile!");

        scoreText = findViewById(R.id.Score);

        db = new DB(this);

        gameStartTime = Calendar.getInstance().getTimeInMillis();
        score = 0;
        clickPower = 1;
        production = 0;
        running = true;
        timeHandler = new Handler();
        productionLabel = findViewById(R.id.Production);
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);

        /*SharedPreferences.Editor preferencesEditor = preferences.edit();                       //RENEW SHARED PREFS
        preferencesEditor.putString("score", Long.toString(score));
        String timeOnPause = Long.toString(Calendar.getInstance().getTimeInMillis()/1000);
        preferencesEditor.putString("timeOnPause", timeOnPause);
        preferencesEditor.apply();*/

        score = Long.parseLong(preferences.getString("score", "error score"));
        scoreText.setText(Long.toString(score));
        productionLabel.setText(Integer.toString(production) + " Cookies/s");

        myCanvas = findViewById(R.id.Canvas);

        animationListener();

        musicPlayer.mainMusic();

    }//////////// INIT



    public void resetGame(){
        for (Helper h : db.select()){
            Helper help;
            help = h;
            h.setCount(0);
            h.setCost(startCost[help.getId()-1]);

            db.update(h);
        }
        score=0;
        production=0;
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("score", Long.toString(score));
        preferencesEditor.apply();
    }


    public void updateScore(){
        score+=production;
        scoreText.setText(Long.toString(score));
        productionLabel.setText(Integer.toString(production) + " Cookies/s");
    }

    public void CookieClick(View view) {
        score+=clickPower;

        scoreText.setText(Long.toString(score));

        musicPlayer.cookieClick();
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

        musicPlayer.pause();

    }

    public void runTimeListener(){
        timeHandler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                updateScore();
                timeHandler.postDelayed(this, 1000);
            }
        },1000);
    }

    public void animationListener(){
        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myCanvas.animated();
                timeHandler.postDelayed(this, 33);
            }
        },33);
    }

    @Override
    protected void onResume() {
        super.onResume();

        musicPlayer.resume();

        runTimeListener();

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

    public void resetClick(View view) {
        resetGame();
    }
}
