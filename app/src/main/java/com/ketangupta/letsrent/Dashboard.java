package com.ketangupta.letsrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    ImageView logoImg;
    TextView logoText,descText;
    Button signUpButton,forgotPassword,signInButton;

    com.google.android.material.textfield.TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        logoImg = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoText);
        descText = findViewById(R.id.descText);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUpButton = findViewById(R.id.signUpButton);
        signInButton = findViewById(R.id.signInButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,signup.class);

                Pair[] pairs = new Pair[8];
                pairs[0] = new Pair<View,String>(logoImg,"logo_transition");
                pairs[1] = new Pair<View,String>(logoText,"logo_text_transition");
                pairs[2] = new Pair<View,String>(descText,"desc_text_transition");
                pairs[3] = new Pair<View,String>(username,"username_transition");
                pairs[4] = new Pair<View,String>(password,"password_transition");
                pairs[5] = new Pair<View,String>(forgotPassword,"fpassword_transition");
                pairs[6] = new Pair<View,String>(signInButton,"sign_in_transition");
                pairs[7] = new Pair<View,String>(signUpButton,"sign_up_transition");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Dashboard.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });
    }
}
