package com.example.cookieclicker;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundProducer {

    MediaPlayer player;
    Context c;

    public SoundProducer(Context c){this.c = c;}

    public void buySound(){
        MediaPlayer.create(c, R.raw.buy).start();
    }
    public void shopMusic(){
        player = MediaPlayer.create(c, R.raw.shop);
        player.start();
        player.setLooping(true);
    }
    public void mainMusic(){
        player = MediaPlayer.create(c, R.raw.theme);
        player.start();
        player.setLooping(true);
    }
    public void cookieClick(){
        MediaPlayer.create(c, R.raw.cookie_click).start();
    }
    public void lackOfMoney(){
        MediaPlayer.create(c, R.raw.error).start();
    }

    public void stop(){player.stop();}
    public void pause(){player.pause();}
    public void resume(){player.start();}
}
