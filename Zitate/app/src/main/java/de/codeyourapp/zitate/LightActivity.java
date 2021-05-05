package de.codeyourapp.zitate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class LightActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "lightActivity";

    private SensorManager sensorManager;
    private Sensor light;
    private TextView lightValue;
    private ImageView lightBulb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        lightValue = (TextView) findViewById(R.id.lightValue);
        lightBulb = (ImageView) findViewById(R.id.lightBulb);

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, light,SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered Light Listener");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: lx: " + event.values[0]);

        float currentValue = event.values[0];

        if(currentValue < 250){
            Log.d(TAG, "onSensorChanged: Weak illuminance.");
            lightValue.setText("lx: " + currentValue);
            lightBulb.setImageResource(R.drawable.no_light);
        }else if (currentValue >= 250 && currentValue <= 1000){
            Log.d(TAG, "onSensorChanged: Normal illuminance.");
            lightValue.setText("lx: " + currentValue);
            lightBulb.setImageResource(R.drawable.small_light);
        }else{
            Log.d(TAG, "onSensorChanged: Strong illuminance.");
            lightValue.setText("lx: " + currentValue);
            lightBulb.setImageResource(R.drawable.ok_light);
        }
    }

    //stop the sensor when the activity stops to reduce battery usage
    @Override
    protected void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this);
        Log.d(TAG, "onStop: Unregistered Light Listener");
    }
}
