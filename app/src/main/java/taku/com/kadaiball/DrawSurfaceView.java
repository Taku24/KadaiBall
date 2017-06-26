package taku.com.kadaiball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by TAKU on 2017/06/12.
 */

public class DrawSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, SensorEventListener {

    private Thread mThread;
    private Ball mBall;
    private float[] mMove;
    float[] gravity = new float[3];
    float[] geomagnetic = new float[3];
    float[] rotationMatrix = new float[9];
    protected final static double RAD2DEG = 180/Math.PI;

    public DrawSurfaceView(Context context, Ball ball, float[] move) {
        super(context);
        getHolder().addCallback(this);
        mBall = ball;
        mMove = move;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread = null;
    }

    @Override
    public void run() {
        while (mThread != null) {
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                int width = getWidth();
                int height = getHeight();
                canvas.drawColor(Color.BLACK);
                canvas.drawCircle(mBall.getBallX(), mBall.getBallY(), 10, mBall);
                getHolder().unlockCanvasAndPost(canvas);
                mBall.moveBall(width, height);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                geomagnetic = event.values.clone();
                break;

            case Sensor.TYPE_ACCELEROMETER:
                gravity = event.values.clone();
                break;
        }

        if (geomagnetic != null && gravity != null) {
            SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic); //回転角度を計算その1
            SensorManager.getOrientation(rotationMatrix, mMove); //回転角度を計算その1
            int pitch = 5 + (int) (mMove[2] * RAD2DEG);
            int roll = 5 + (int) (mMove[1] * RAD2DEG);

            if(pitch >= 50){
                mMove[0] = 50;
            } else if(-50 >= pitch){
                mMove[0] = -50;
            } else {
                mMove[0] = pitch;
            }

            if(roll >= 50){
                mMove[1] = 50;
            } else if(-50 >= roll){
                mMove[1] = -50;
            } else {
                mMove[1] = roll;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
