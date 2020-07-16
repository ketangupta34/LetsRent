package com.ketangupta.letsrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Queue;

public class Dashboard extends AppCompatActivity {

    ImageView logoImg;
    TextView logoText,descText;
    Button signUpButton,forgotPassword,signInButton;

    com.google.android.material.textfield.TextInputLayout username,password;
    ProgressBar progressBar;
    private String user_name,pass_word;

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
        progressBar = findViewById(R.id.progressBar);

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

    public void loginUser(View view){
        Log.d("loginUser", "function called");

        user_name = Objects.requireNonNull(username.getEditText()).getText().toString();
        pass_word = Objects.requireNonNull(password.getEditText()).getText().toString();

        if(!validatePassword() | !validateUserName()){
            return;
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            checkIfUser();
        }

    }

    private void checkIfUser() {
        Log.d("checkUser", "check user called");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("phone").equalTo(user_name);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String passwordinDatabase = dataSnapshot.child(user_name).child("password").getValue(String.class);

                    if(passwordinDatabase.equals(pass_word)){
                        Intent intent = new Intent(getApplicationContext(),profilePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        password.setError("password wrong");
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else{
                    username.setError("No such User");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error", "Error ");
            }
        });
    }

    private boolean validateUserName() {
        if (user_name.isEmpty()) {
            username.setError("field cannot be empty");
            return false;
        }
        else if (!user_name.matches("\\S+")) {
            username.setError("white spaces not allowed");
            return false;
        }
        else if (user_name.length() > 15) {
            username.setError("username too long");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    };
    private boolean validatePassword() {
        if (pass_word.isEmpty()) {
            password.setError("field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    };
}
