package com.example.charityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class Ip extends AppCompatActivity {

    SharedPreferences sh;
    EditText e1;
    Button b1;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        e1=findViewById(R.id.editTextTextPersonName8);
        b1=findViewById(R.id.button15);
        b1.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ip=e1.getText().toString();

                SharedPreferences.Editor ed=sh.edit();
                ed.putString("ip",ip);
                ed.commit();
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });

    }
}