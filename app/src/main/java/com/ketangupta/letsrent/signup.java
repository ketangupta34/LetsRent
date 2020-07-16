package com.ketangupta.letsrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class signup extends AppCompatActivity {

    ImageView logoImg;
    TextView logoText, descText;
    com.google.android.material.textfield.TextInputLayout username, password, fullname, email, phone;
    Button signUpButton, loginButton;

    private String full_name, user_name, e_mail, phone_number, pass_word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        logoImg = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoText);
        descText = findViewById(R.id.descText);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        signUpButton = findViewById(R.id.letsGo);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    };

    public void registerUser(View view){    //signUpButton on click calls this function

        full_name = Objects.requireNonNull(fullname.getEditText()).getText().toString();
        user_name = Objects.requireNonNull(username.getEditText()).getText().toString();
        e_mail = Objects.requireNonNull(email.getEditText()).getText().toString();
        phone_number = Objects.requireNonNull(phone.getEditText()).getText().toString();
        pass_word = Objects.requireNonNull(password.getEditText()).getText().toString();

        if (!validateName() | !validateUserName() | !validateEmail() | !validatePassword() | !validatePhoneNumber()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(),phoneAuthentication.class);
        intent.putExtra("phoneNo",phone_number);
        intent.putExtra("fullName",full_name);
        intent.putExtra("userName",user_name);
        intent.putExtra("eMail",e_mail);
        intent.putExtra("password",pass_word);

        startActivity(intent);
    };

    private boolean validateName() {
        if (full_name.isEmpty()) {
            fullname.setError("field cannot be empty");
            return false;
        }
        else if (full_name.matches("(?=.*[0-9])")){
            fullname.setError("number cant be part of name");
            return false;
        }
        else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    };

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

    private boolean validateEmail() {
        if (e_mail.isEmpty()) {
            email.setError("field cannot be empty");
            return false;
        } else if (!e_mail.matches("[a-zA-Z0-9._-]+[@][a-z]+[.][a-z]+")) {
            email.setError("invalid format");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    };

    private boolean validatePhoneNumber() {
        if (phone_number.isEmpty()) {
            phone.setError("field cannot be empty");
            return false;
        } else if (!phone_number.matches("\\d{10}")) {
            phone.setError("invalid number");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    };

    private boolean validatePassword() {
        String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        if (pass_word.isEmpty()) {
            password.setError("field cannot be empty");
            return false;
        } else if (!pass_word.matches(PASSWORD)) {
            password.setError("password too weak");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    };
}
