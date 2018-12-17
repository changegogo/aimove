package com.aimove.sprite;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Face extends Sprite {
    private int speed = 30;
    private int tx;
    private int ty;

    public Face(Bitmap defaultBitmap, Point position, Point touchPoint) {
        super(defaultBitmap, position);
        int x = touchPoint.x - position.x;
        int y = touchPoint.y - position.y;
        int len = (int) Math.sqrt(x*x + y*y);
        tx = speed * x / len;
        ty = speed * y / len;
    }

    public void move() {
        this.position.x += tx;
        this.position.y += ty;
    }

}
