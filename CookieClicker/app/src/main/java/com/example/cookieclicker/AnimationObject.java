package com.example.cookieclicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimationObject {
    private int x,y;
    private int speedX, speedY;
    private int size;

    public AnimationObject(int x, int y, int speed, int size){
        this.x = x;
        this.y = y;
        this.speedX = speed;
        this.speedY = speed;
        this.size = size;
    }

    public void reverseVector(int w, int h){
        if(x>w-size || x<0) speedX=-speedX;
        if(y>h-size || y<0) speedY=-speedY;
    }

    public void move(int w, int h){

        reverseVector(w,h);

        x+=speedX;
        y+=speedY;
    }

    public void draw(Bitmap bmp, Canvas c){
        c.drawBitmap(bmp, null,
                new Rect(x,y,x+size,y+size), null);
    }

}
