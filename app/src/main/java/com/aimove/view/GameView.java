package com.aimove.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.aimove.sprite.Eyes;
import com.aimove.sprite.Man;
import com.aimove.www.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder mHolder;
    private final ExecutorService mPool;
    private boolean isRunning = false;
    private Man mMan;
    private SurfaceTask mTask;
    private Eyes eyes;
    private static final String TAG = "GameView";

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPool = Executors.newFixedThreadPool(5);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context) {
        this(context, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Bitmap manBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.body);
        // create man
        Bitmap scaleManBitmap = scaleBitmap(manBitmap, 0.4f);
        mMan = new Man(scaleManBitmap, new Point(getWidth()/2 - scaleManBitmap.getWidth()/2, getHeight()/2 - scaleManBitmap.getHeight()/2));

        Bitmap eyeBBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eyeb);
        Bitmap scaleEyeBBitmap = scaleBitmap(eyeBBitmap, 0.4f);
        eyes = mMan.createEyes(scaleEyeBBitmap, new Point(385, 515), new Point(385+230, 515-10));

        mTask = new SurfaceTask();
        isRunning = true;
        mPool.execute(mTask);
    }

    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isRunning = false;
    }

    private class SurfaceTask implements Runnable {
        @Override
        public void run() {
            while(isRunning) {
                try {
                    long start = System.currentTimeMillis();
                    drawUI();
                    long end = System.currentTimeMillis();
                    long dtime = end - start;
                    int fps = (int)(1000 / dtime);
                    System.out.println("fps--" + fps);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void drawUI() {
        // 锁定界面
        Canvas canvas = mHolder.lockCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        // 画矩形
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        mMan.drawSelf(canvas);
        eyes.drawSelf(canvas);

        mHolder.unlockCanvasAndPost(canvas);
    }

    // 处理屏幕点击
    public void handTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getRawX();
                float y = event.getRawY();
                // Log.i(TAG, "x:"+ x + "y:"+y);
                eyes.rotateEyes(x, y);
                break;
            case MotionEvent.ACTION_UP:
                eyes.noFix();
                break;
            default:
                break;
        }
    }

}
