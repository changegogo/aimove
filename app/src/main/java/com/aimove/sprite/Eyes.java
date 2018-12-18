package com.aimove.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Eyes{
    private Bitmap eyeWBitmap; // 眼白
    private Bitmap eyeBBitmap; // 眼珠
    private Point posLeftCenter;
    private Point posRightCenter;
    private Point posLeft;
    private Point posRight;
    private int radius = 15;


    public Eyes(Bitmap eyeWBitmap, Bitmap eyeBBitmap, Point posLeftCenter, Point posRightCenter) {
        this.eyeWBitmap = eyeWBitmap;
        this.eyeBBitmap = eyeBBitmap;

        this.posLeftCenter = posLeftCenter;
        this.posRightCenter = posRightCenter;
        this.posLeft = new Point(posLeftCenter.x, posLeftCenter.y);
        this.posRight = new Point(posRightCenter.x, posRightCenter.y);
    }

    //  + 175  -10
    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(eyeWBitmap, posLeftCenter.x - eyeWBitmap.getWidth() / 3, posLeftCenter.y - eyeWBitmap.getHeight() / 3, null);
        canvas.drawBitmap(eyeWBitmap, posRightCenter.x - eyeWBitmap.getWidth() / 3, posRightCenter.y - eyeWBitmap.getHeight() / 3, null);

        canvas.drawBitmap(eyeBBitmap, posLeft.x, posLeft.y, null);
        canvas.drawBitmap(eyeBBitmap, posRight.x, posRight.y, null);
    }
    // 以圆形旋转
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
    // 根据屏幕尺寸位移
    public void rotateEyes(float x1, float y1, float x2, float y2) {
        posLeft.x = (int)x1;
        posLeft.y = (int)y1;
        posRight.x = (int)x2;
        posRight.y = (int)y2;
    }

    public void noFix() {
        posLeft.x = posLeftCenter.x;
        posLeft.y = posLeftCenter.y;
        posRight.x = posRightCenter.x;
        posRight.y = posRightCenter.y;
    }
}
