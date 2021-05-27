package de.codeyourapp.zitate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button button_accelerometer;
    private Button button_light;
    private Button button_proximity;

    private SignInButton singInButton;
    private GoogleSignInClient mGoogleSingInClient;
    private String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private Button button_singOut;
    private  int RC_SIGN_IN = 9001;

    private String channelId  = "notifyChannel";
    private String channelName = "customNotifyChannel";


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

        singInButton = findViewById(R.id.button_singIn);
        // Initialize FireBase authentication
        mAuth = FirebaseAuth.getInstance();

        button_singOut = findViewById(R.id.button_singOut);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSingInClient = GoogleSignIn.getClient(this,gso);

        //perform action on button press
        singInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                singIn();
            }
        });

        button_singOut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseUser user = mAuth.getCurrentUser();
                String displayName = user.getDisplayName();

                mGoogleSingInClient.signOut();
                Toast.makeText(MainActivity.this,"Log out: "+ displayName,Toast.LENGTH_SHORT).show();
                button_singOut.setVisibility(View.INVISIBLE);
            }
        });

        NotificationManager notificationManager =  getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName,
                    NotificationManager.IMPORTANCE_LOW));
        }


    }


    private void singIn(){
        Intent singInIntent = mGoogleSingInClient.getSignInIntent();
        startActivityForResult(singInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this,"SignIn Successful",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }catch (ApiException e){
            Toast.makeText(MainActivity.this,"SignIn Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"FireBaseAuth Successful",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    Toast.makeText(MainActivity.this,"SignIn Failed",Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser fUser){
         button_singOut.setVisibility(View.VISIBLE);
         GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
         if(account != null){
             String photoUrl = account.getPhotoUrl().toString();
             String userEmail =  account.getEmail();
             String userGivenName =  account.getGivenName();
             String userFamilyName =  account.getFamilyName();
             String userName =  account.getDisplayName();
             String userId =  account.getId();

             HashMap<String, String> userDataMap = new HashMap<String, String>();
             userDataMap.put("photoUrl",photoUrl);
             userDataMap.put("userEmail",userEmail);
             userDataMap.put("userGivenName",userGivenName);
             userDataMap.put("userFamilyName",userFamilyName);
             userDataMap.put("userName",userName);
             userDataMap.put("userId",userId);

             //Toast.makeText(MainActivity.this,personName + " " + personEmail,Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
             intent.putExtra("userDataMap",userDataMap);
             startActivity(intent);
        }
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
