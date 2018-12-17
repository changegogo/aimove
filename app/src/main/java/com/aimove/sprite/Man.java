package com.aimove.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;


public class Man extends Sprite {

    public Man(Bitmap defaultBitmap, Point position) {
        super(defaultBitmap, position);
    }

    /*public Face createFace(Context context, Point touchPoint, int drawableId) {
        Bitmap faceBitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        Face face = new Face(faceBitmap, new Point(position.x + defaultBitmap.getWidth()/2 - faceBitmap.getWidth() / 2, position.y + defaultBitmap.getHeight()/2 - faceBitmap.getHeight() / 2), touchPoint);
        return face;
    }*/

    public Eyes createEyes(Bitmap eyeBBitmap, Point posLeft, Point posRight) {
        Eyes eyes = new Eyes(eyeBBitmap, posLeft, posRight);
        return eyes;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(defaultBitmap, position.x, position.y, null);
    }
}
