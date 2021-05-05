package de.codeyourapp.zitate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "ProximityActivity";

    private SensorManager sensorManager;
    private Sensor proximity;
    private TextView proximityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        proximityValue = (TextView) findViewById(R.id.proximityValue);

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximity,SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered Proximity Listener");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: cm: " + event.values[0]);

        float currentValue = event.values[0];

        proximityValue.setText("cm: " + currentValue);
    }

    //stop the sensor when the activity stops to reduce battery usage
    @Override
    protected void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this);
        Log.d(TAG, "onStop: Unregistered Proximity Listener");
    }
}
