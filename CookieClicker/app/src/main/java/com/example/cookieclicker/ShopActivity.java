package com.example.cookieclicker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;

public class ShopActivity extends Activity {

    ListView list;
    DB db;
    CustomAdapter customAdapter;
    long currScore;
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        db = new DB(getApplicationContext());
        String s = getIntent().getExtras().getString("score", "error");
        currScore = Long.parseLong(s);

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
    }

    public void buyHelper(int id){
        Helper helperToUpdate = null;

        for (Helper h : db.select()){
            if(h.getId() == id)
                helperToUpdate = h;
        }

        if (currScore >= helperToUpdate.getCost()){
            helperToUpdate.setCount(helperToUpdate.getCount()+1);
            db.update(helperToUpdate);
            currScore-=helperToUpdate.getCost();
        } else Toast.makeText(this, "Not Enough Cookies", Toast.LENGTH_SHORT).show();
    }

    public void updateList(){
        customAdapter = new CustomAdapter(getApplicationContext(), db.select()); //TODO: Dodelat pole obrázků
        list.setAdapter(customAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("score", Long.toString(currScore));
        preferencesEditor.apply();
        super.onPause();
    }
}
