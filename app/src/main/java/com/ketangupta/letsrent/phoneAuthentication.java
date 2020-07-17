package com.ketangupta.letsrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class phoneAuthentication extends Activity {

    Button letsGoBtn;
    com.google.android.material.textfield.TextInputLayout otpField;
    ProgressBar progressBar;

    private String verificationCodeBySystem;
    private String phoneNo,fullName,userName,eMail,passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_authentication);

        letsGoBtn = findViewById(R.id.letsGo);
        otpField = findViewById(R.id.otpInput);
        progressBar = findViewById(R.id.progressBar);

        phoneNo = getIntent().getStringExtra("phoneNo");
        fullName = getIntent().getStringExtra("fullName");
        userName = getIntent().getStringExtra("userName");
        passWord = getIntent().getStringExtra("password");
        eMail= getIntent().getStringExtra("eMail");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

        sendVerificationCodeToUser(phoneNo);

        letsGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEnteredCode = Objects.requireNonNull(otpField.getEditText()).getText().toString();
                if(userEnteredCode.isEmpty()){
                    otpField.setError("enter code");
                    return;
                }
                verifyCode(userEnteredCode);
            }
        });
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,             // Phone number to verify
                60,                              // Timeout duration
                TimeUnit.SECONDS,                  // Unit of timeout
                TaskExecutors.MAIN_THREAD,         // Activity (for callback binding)
                mCallbacks);                       // OnVerificationStateChangedCallbacks

    };

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String AutoDetectedCode = phoneAuthCredential.getSmsCode();
            if (AutoDetectedCode!=null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(AutoDetectedCode);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d("verification", "onVerificationFailed: failed");
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("code sent by system",s);
            verificationCodeBySystem = s;
        }
    };

    private void verifyCode(String codeByUser){
        PhoneAuthCredential credential =  PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
        signInByCredential(credential);
    };

    private void signInByCredential(PhoneAuthCredential credential){
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        fbAuth.signInWithCredential(credential).addOnCompleteListener(phoneAuthentication.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
                    DatabaseReference reference = rootnode.getReference("Users");

                    signUpHelper helperClass = new signUpHelper(fullName, userName, phoneNo, eMail, passWord);
                    reference.child(phoneNo).setValue(helperClass);

                    Intent intent = new Intent(getApplicationContext(),profilePage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Log.d("FIR KR LODU", "onComplete: FAILED");
                }
            }
        });

    };
}
