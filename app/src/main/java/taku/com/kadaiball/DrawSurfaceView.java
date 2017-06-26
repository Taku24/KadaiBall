package taku.com.kadaiball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by TAKU on 2017/06/12.
 */

public class DrawSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, SensorEventListener {

    private Thread mThread;
    private Ball mBall;
    private float[] mMove;

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
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            /*
             * Pitch -> y
             * Roll -> x
             */
            float pitch = event.values[0];
            float roll = event.values[1];
            mMove[0] = roll;
            mMove[1] = pitch;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
