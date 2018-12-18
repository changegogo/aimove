package com.aimove.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;


public class Man extends Sprite {

    public Man(Bitmap defaultBitmap, Point position) {
        super(defaultBitmap, position);
    }

    public Eyes createEyes(Bitmap eyeWBitmap, Bitmap eyeBBitmap, Point posLeft, Point posRight) {
        Eyes eyes = new Eyes(eyeWBitmap, eyeBBitmap, posLeft, posRight);
        return eyes;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(defaultBitmap, position.x, position.y, null);
    }
}
