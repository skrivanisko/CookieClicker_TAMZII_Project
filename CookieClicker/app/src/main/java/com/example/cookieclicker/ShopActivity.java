package com.example.cookieclicker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ShopActivity extends Activity {

    ListView list;
    DB db;
    CustomAdapter customAdapter;
    long currScore;
    public SharedPreferences preferences;
    SoundProducer musicPlayer;
    TextView prodText;
    TextView cookieCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        musicPlayer = new SoundProducer(this);

        prodText = findViewById(R.id.production);
        cookieCount = findViewById(R.id.cookieCount);


        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        db = new DB(getApplicationContext());
        String s = getIntent().getExtras().getString("score", "error");
        currScore = Long.parseLong(s);
        prodText.setText(db.getProduction() + " Cookies/s");
        cookieCount.setText("Availible Cookies: " + currScore);

        list = (ListView)findViewById(R.id.list);
        customAdapter = new CustomAdapter(getApplicationContext(), db.select()); //TODO: Dodelat pole obrázků
        list.setAdapter(customAdapter);

        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                buyHelper(position+1);
                updateList();
            }
        });

        musicPlayer.shopMusic();
    }

    public void buyHelper(int id){
        Helper helperToUpdate = null;

        for (Helper h : db.select()){
            if(h.getId() == id)
                helperToUpdate = h;
        }

        if (currScore >= helperToUpdate.getCost()){

            musicPlayer.buySound();


            currScore-=helperToUpdate.getCost();
            helperToUpdate.setCount(helperToUpdate.getCount()+1);
            helperToUpdate.setCost(helperToUpdate.getCost()*2);
            db.update(helperToUpdate);

            SharedPreferences.Editor preferencesEditor = preferences.edit();
            preferencesEditor.putString("score", Long.toString(currScore));
            preferencesEditor.apply();

            prodText.setText(db.getProduction() + " Cookies/s");
            cookieCount.setText("Availible Cookies: " + currScore);

        } else {
            musicPlayer.lackOfMoney();
            Toast.makeText(this, "Not Enough Cookies", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateList(){
        customAdapter = new CustomAdapter(getApplicationContext(), db.select()); //TODO: Dodelat pole obrázků
        list.setAdapter(customAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        musicPlayer.resume();
    }
}
