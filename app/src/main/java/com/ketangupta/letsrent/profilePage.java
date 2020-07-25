package com.ketangupta.letsrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

public class profilePage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar tool_Bar;


    Button signOut;
    TextView tv1, tv2;
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //TOOLBAR and NAVIGATION BAR code
        tool_Bar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);

        setSupportActionBar(tool_Bar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, tool_Bar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.bringToFront();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navOne:
                        Toast.makeText(getApplicationContext(),"1st item selected",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navTwo:
                        Toast.makeText(getApplicationContext(),"2nd item selected",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navThree:
                        Toast.makeText(getApplicationContext(),"3rd item selected",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navLogOut:
                        if (fbAuth != null) {
                            Log.d("a", "User was there");
                        }
                        FirebaseAuth.getInstance().signOut();
                        if (fbAuth == null) {
                            Log.d("a", "khali ho gaya");
                        }
                }
                return true;
            }
        });
        //TOOLBAR and NAVIGATION BAR code

        signOut = findViewById(R.id.logOutBtn);
        tv1 = findViewById(R.id.txtview1);
        tv2 = findViewById(R.id.txtview2);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            tv1.setText(fbUser.getPhoneNumber());
            tv2.setText(fbUser.getEmail());
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fbAuth != null) {
                    Log.d("a", "User was there");
                }
                FirebaseAuth.getInstance().signOut();
                if (fbAuth == null) {
                    Log.d("a", "khali ho gaya");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
