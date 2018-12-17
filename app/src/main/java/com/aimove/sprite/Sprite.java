package com.aimove.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public abstract class Sprite {
    public Bitmap defaultBitmap;
    public Point position;

    public Sprite(Bitmap defaultBitmap, Point position) {
        this.defaultBitmap = defaultBitmap;
        this.position = position;
    }

    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(defaultBitmap, position.x, position.y, null);
    }
}
