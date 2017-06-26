package taku.com.kadaiball;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mGyrometer;
    private DrawSurfaceView view;
    private float[] move = {5, 5};
    private Ball mBall;

    public MainActivity(){
        mBall = new Ball(200, 200, move);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new DrawSurfaceView(this, mBall, move);
        setContentView(view);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mGyrometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(view, mGyrometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(view);
    }

}
