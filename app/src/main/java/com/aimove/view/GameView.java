package com.aimove.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
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
    private int screenWidth;
    private int screenHeight;
    private final String TAG = "GameView";
    private final float ratio = 2.f;
    private final int manPicWidth = 929;
    private final int manPicHeight = 1944;
    private final int eyeWLeftCenterX = 355;
    private final int eyeWLeftCenterY = 521;
    private final int eyeWRightCenterX = 570;
    private final int eyeWRightCenterY = 509;

    private int bodyX;
    private int bodyY;
    private int manWidth;
    private int manHeight;
    // 眼眶宽高
    private static final int eyeWWidth = 50;
    private static final int eyeWHeight = 30;
    // 眼珠宽高
    private int eyeBWidth;
    private int eyeBHeight;


    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPool = Executors.newFixedThreadPool(5);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context) {
        this(context, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // create man
        Bitmap manBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.body);
        Bitmap scaleManBitmap = scaleBitmap(manBitmap, ratio);
        screenWidth = getWidth();
        screenHeight = getHeight();
        manWidth = scaleManBitmap.getWidth();
        manHeight = scaleManBitmap.getHeight();
        // 身体绘制左上角点坐标
        bodyX = screenWidth / 2 - manWidth/2;
        bodyY = screenHeight / 2 - manHeight/2;
        //bodyX = 0;
        //bodyY = 0;
        mMan = new Man(scaleManBitmap, new Point(bodyX, bodyY));
        // create eyes
        // 眼白
        Bitmap eyeWBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.eyew);
        Bitmap scaleEyeWBitmap = scaleBitmap(eyeWBitmap, ratio);
        // 眼珠
        Bitmap eyeBBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.eyeb);
        Bitmap scaleEyeBBitmap = scaleBitmap(eyeBBitmap, ratio);

        eyeBWidth = scaleEyeBBitmap.getWidth();
        eyeBHeight = scaleEyeBBitmap.getHeight();
        // 缩放后的眼眶中心坐标
        int eyeScaleLeftCenterX = bodyX + eyeWLeftCenterX * manWidth / manPicWidth;
        int eyeScaleLeftCenterY = bodyY + eyeWLeftCenterY * manHeight / manPicHeight;

        int eyeScaleRightCenterX = bodyX + eyeWRightCenterX * manWidth / manPicWidth;
        int eyeScaleRightCenterY = bodyY + eyeWRightCenterY * manHeight / manPicHeight;
        eyes = mMan.createEyes(scaleEyeWBitmap, scaleEyeBBitmap, new Point(eyeScaleLeftCenterX - eyeBWidth / 2, eyeScaleLeftCenterY - eyeBHeight / 2), new Point(eyeScaleRightCenterX - eyeBWidth / 2, eyeScaleRightCenterY - eyeBHeight / 2));

        //mTask = new SurfaceTask();
        //isRunning = true;
        //mPool.execute(mTask);
        drawUI();
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
        if(canvas ==null)
            return;
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //Paint paint = new Paint();
        //paint.setColor(Color.WHITE);
        //paint.setAlpha(255);
        // 画矩形
        //drawRect(0, 0, getWidth(), getHeight(), paint);
        //canvas.drawColor(Color.alpha(255), PorterDuff.Mode.CLEAR);

        eyes.drawSelf(canvas);
        mMan.drawSelf(canvas);

        /*paint.setColor(Color.GRAY);
        int xLeft = eyeWLeftCenterX - eyeWWidth / 2;
        int yLeft = eyeWLeftCenterY - eyeWHeight / 2;
        xLeft = convert(xLeft);
        yLeft = convert(yLeft);
        canvas.drawRect(bodyX + xLeft, bodyY + yLeft, bodyX + xLeft + convert(eyeWWidth), bodyY + yLeft + convert(eyeWHeight), paint);

        int xRight = eyeWRightCenterX - eyeWWidth / 2;
        int yRight = eyeWRightCenterY - eyeWHeight / 2;
        xRight = convert(xRight);
        yRight = convert(yRight);
        canvas.drawRect(bodyX + xRight, bodyY + yRight, bodyX + xRight + convert(eyeWWidth), bodyY + yRight + convert(eyeWHeight), paint);*/

        mHolder.unlockCanvasAndPost(canvas);
    }

    private int convert(int coor) {
        return coor * manWidth / manPicWidth;
    }

    // 处理屏幕点击
    public void handTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getRawX();
                float y = event.getRawY();
                // 眼眶中的位移
                int x1 = (int)(x * eyeWWidth / screenWidth);
                int y1 = (int)(y * eyeWHeight / screenHeight);
                x1 = convert(x1);
                y1 = convert(y1);
                // 左眼左上角坐标
                int xLeft = eyeWLeftCenterX - eyeWWidth / 2;
                int yLeft = eyeWLeftCenterY - eyeWHeight / 2;
                xLeft = convert(xLeft);
                yLeft = convert(yLeft);
                // 右眼左上角坐标
                int xRight = eyeWRightCenterX - eyeWWidth / 2;
                int yRight = eyeWRightCenterY - eyeWHeight / 2;
                xRight = convert(xRight);
                yRight = convert(yRight);
                eyes.rotateEyes(bodyX + xLeft + x1 - eyeBWidth / 2, bodyY + yLeft + y1 - eyeBHeight / 2,  bodyX + xRight + x1 - eyeBWidth / 2, bodyY + yRight + y1 - eyeBHeight / 2);
                drawUI();
                break;
            case MotionEvent.ACTION_UP:
                eyes.noFix();
                drawUI();
                break;
            default:
                break;
        }
    }
}
