package com.example.cookieclicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

public class MyCanvas extends View {

    int width;
    int height;

    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cookie);

    ArrayList<AnimationObject> listObj = new ArrayList<>();

    public MyCanvas(Context context) {
        super(context);
        initList();
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        initList();
    }

    public MyCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initList();
    }

    public void initList(){
        AnimationObject obj = new AnimationObject(100,100,3,100);
        AnimationObject obj1 = new AnimationObject(150,120,-5,80);
        AnimationObject obj2 = new AnimationObject(300,250,6,100);
        AnimationObject obj3 = new AnimationObject(100,125,-7,120);
        AnimationObject obj4 = new AnimationObject(250,155,5,200);

        listObj.add(obj);
        listObj.add(obj1);
        listObj.add(obj2);
        listObj.add(obj3);
        listObj.add(obj4);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        width = canvas.getWidth();
        height = canvas.getHeight();

        for(AnimationObject obj : listObj)
            obj.draw(bmp, canvas);
    }

    public void animated(){

        for(AnimationObject obj : listObj){
            obj.move(width,height);
        }


        invalidate();
    }
}
