package de.codeyourapp.zitate;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity{

    ArrayList<String> userData;

    ImageView photoIView;
    TextView emailTView;
    TextView givenNameTView;
    TextView familyNameTView;
    TextView nameTView;
    TextView idTView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        photoIView = findViewById(R.id.profile_imageView);
        emailTView = findViewById(R.id.email_textView);
        givenNameTView = findViewById(R.id.givenName_textView);
        familyNameTView = findViewById(R.id.familyName_textView);
        nameTView = findViewById(R.id.name_textView);
        idTView = findViewById(R.id.userId_textView);

        // get all the the data, that comes send when singing in
        Bundle extras = getIntent().getExtras();
        HashMap<String, String> userDataMap = (HashMap<String, String>) getIntent().getSerializableExtra("userDataMap");

        // Did we get any account data at all?
        if (!userDataMap.isEmpty() || userDataMap != null) {


            if(userDataMap.containsKey("photoUrl")){
                String photo = userDataMap.get("photoUrl");
                Uri photoURL = Uri.parse(photo);

                Glide.with(ProfileActivity.this)
                        .load(photoURL).into(photoIView);
            }

            if(userDataMap.containsKey("userEmail")){
                emailTView.setText("Email: " + userDataMap.get("userEmail"));
            }

            if(userDataMap.containsKey("userGivenName")){
                givenNameTView.setText("Given name: " + userDataMap.get("userGivenName"));
            }

            if(userDataMap.containsKey("userFamilyName")){
                familyNameTView.setText("Family name: " + userDataMap.get("userFamilyName"));
            }

            if(userDataMap.containsKey("userFamilyName")){
                nameTView.setText("Name: " + userDataMap.get("userName"));
            }

            if(userDataMap.containsKey("userId")){
                idTView.setText("Id: " + userDataMap.get("userId"));
            }
        }else{
            emailTView.setText("Sorry, no data to you account was found!");
        }
    }
}