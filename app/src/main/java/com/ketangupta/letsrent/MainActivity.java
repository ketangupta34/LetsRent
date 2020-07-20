package com.ketangupta.letsrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 2500;

    Animation carAnim,textAnim;

    ImageView carImg;
    TextView logo,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        carImg = findViewById(R.id.logoImage);
        logo = findViewById(R.id.logoText);
        description = findViewById(R.id.descText);

        carAnim = AnimationUtils.loadAnimation(this,R.anim.intro_car_animation);
        textAnim = AnimationUtils.loadAnimation(this,R.anim.intro_text_animation);

        carImg.setAnimation(carAnim);
        logo.setAnimation(textAnim);
        description.setAnimation(textAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser fbAuth = FirebaseAuth.getInstance().getCurrentUser();
                if(fbAuth != null){
                    Log.d("letsRent", "user was logged in");
                    Intent intent = new Intent(getApplicationContext(),profilePage.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.d("letsRent", "user was not there");
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(carImg, "logo_transition");
                    pairs[1] = new Pair<View, String>(logo, "logo_text_transition");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                    finish();
                }
            }
        },SPLASH_TIME);
    }
}
