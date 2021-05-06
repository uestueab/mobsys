package de.codeyourapp.zitate;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ProfileActivity extends AppCompatActivity implements Serializable{

    ArrayList<String> userData;

    ImageView profileImageView;
    TextView profileTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profile_imageView);
        profileTextView = findViewById(R.id.profile_textView);

        // get all the the data, that comes send when singing in
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            userData = extras.getStringArrayList("userData");

            String photo = userData.get(0);
            Uri photoURL = Uri.parse(photo);

            Glide.with(ProfileActivity.this)
                    .load(photoURL).into(profileImageView);

            profileTextView.setText("Email: " + userData.get(1));
        }else{
            profileTextView.setText("sorry senpai");
        }
    }
}