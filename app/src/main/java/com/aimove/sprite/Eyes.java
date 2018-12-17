package com.aimove.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Eyes{

    private Bitmap eyeBBitmap; // 眼珠
    private Point posLeftCenter;
    private Point posRightCenter;
    private Point posLeft;
    private Point posRight;
    private int radius = 8;

    public Eyes(Bitmap eyeWBitmap, Point posLeftCenter, Point posRightCenter) {
        this.eyeBBitmap = eyeWBitmap;

        this.posLeftCenter = posLeftCenter;
        this.posRightCenter = posRightCenter;
        this.posLeft = new Point(posLeftCenter.x, posLeftCenter.y);
        this.posRight = new Point(posRightCenter.x, posRightCenter.y);
    }

    //  + 175  -10
    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(eyeBBitmap, posLeft.x, posLeft.y, null);
        canvas.drawBitmap(eyeBBitmap, posRight.x, posRight.y, null);
    }

    public void rotateEyes(float x, float y) {
        float xLeft = posLeftCenter.x + (radius * (x - posLeftCenter.x)) / (float)Math.sqrt((x-posLeftCenter.x)*(x-posLeftCenter.x) + (y-posLeftCenter.y)*(y-posLeftCenter.y));
        float yLeft = posLeftCenter.y + (radius * (y - posLeftCenter.y)) / (float)Math.sqrt((x-posLeftCenter.x)*(x-posLeftCenter.x) + (y-posLeftCenter.y)*(y-posLeftCenter.y));
        posLeft.x = (int)xLeft;
        posLeft.y = (int)yLeft;

        float xRight = posRightCenter.x + (radius * (x - posRightCenter.x)) / (float)Math.sqrt((x-posRightCenter.x)*(x-posRightCenter.x) + (y-posRightCenter.y)*(y-posRightCenter.y));
        float yRight = posRightCenter.y + (radius * (y - posRightCenter.y)) / (float)Math.sqrt((x-posRightCenter.x)*(x-posRightCenter.x) + (y-posRightCenter.y)*(y-posRightCenter.y));
        posRight.x = (int)xRight;
        posRight.y = (int)yRight;
    }

    public void noFix() {
        posLeft.x = posLeftCenter.x;
        posLeft.y = posLeftCenter.y;
        posRight.x = posRightCenter.x;
        posRight.y = posRightCenter.y;
    }
}
