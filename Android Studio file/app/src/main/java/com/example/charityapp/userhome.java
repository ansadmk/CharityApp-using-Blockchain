package com.example.charityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class userhome extends AppCompatActivity {
    SharedPreferences sh;
    ImageView b1,b2,b3,b4,b5,b6,b7,b8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.back);
        setContentView(R.layout.activity_userhome);
        b1=findViewById(R.id.button7);
        b2=findViewById(R.id.button8);
        b3=findViewById(R.id.button9);
        b4=findViewById(R.id.button10);
        b5=findViewById(R.id.button11);
        b6=findViewById(R.id.button12);
        b7=findViewById(R.id.button13);
        b8=findViewById(R.id.button50);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),ViewNearbyCharity.class);
                startActivity(i);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ViewRequest.class);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),donationstatus.class);
                startActivity(i);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),chatwithcharity.class);
                startActivity(i);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),sendcomplaintandviewreply.class);
                startActivity(i);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ViewRequestStatus.class);
                startActivity(i);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),allOrganisation.class);
                startActivity(i);
            }
        });
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
        super.onBackPressed(); }

}