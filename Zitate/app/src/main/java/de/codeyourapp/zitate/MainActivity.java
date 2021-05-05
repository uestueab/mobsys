package de.codeyourapp.zitate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private Button button_accelerometer;
    private Button button_light;
    private Button button_proximity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_accelerometer = (Button) findViewById(R.id.button_accelerometer);
        button_accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccelerometerActivity();
            }
        });

        button_light = (Button) findViewById(R.id.button_light);
        button_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLightActivity();
            }
        });

        button_proximity = (Button) findViewById(R.id.button_proximity);
        button_proximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProximityActivity();
            }
        });
    }




    public void openAccelerometerActivity() {
        Intent intent = new Intent(this, AccelerometerActivity.class);
        startActivity(intent);
    }

    public void openLightActivity() {
        Intent intent = new Intent(this, LightActivity.class);
        startActivity(intent);
    }

    public void openProximityActivity() {
        Intent intent = new Intent(this, ProximityActivity.class);
        startActivity(intent);
    }
}