package com.ketangupta.letsrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profilePage extends AppCompatActivity {

    Button signOut;
    TextView tv1,tv2;
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        signOut = findViewById(R.id.logOutBtn);
        tv1 =findViewById(R.id.txtview1);
        tv2 = findViewById(R.id.txtview2);

        FirebaseUser fbUser =  FirebaseAuth.getInstance().getCurrentUser();
        if(fbUser != null){
            tv1.setText(fbUser.getPhoneNumber());
            tv2.setText(fbUser.getEmail());
        }


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fbAuth !=null){
                    Log.d("a", "onClick: user was there");
                }
                FirebaseAuth.getInstance().signOut();
                if(fbAuth == null){
                    Log.d("a", "khali ho gaya");
                }
            }
        });
    }
}
