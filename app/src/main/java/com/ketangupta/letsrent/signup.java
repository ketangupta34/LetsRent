package com.ketangupta.letsrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class signup extends AppCompatActivity {

    ImageView logoImg;
    TextView logoText,descText;
    com.google.android.material.textfield.TextInputLayout username,password;
    Button signUpButton,loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        logoImg = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.descText);
        descText=findViewById(R.id.descText);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });

    }
}
