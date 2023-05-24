package com.example.charityapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class Login extends AppCompatActivity {
    SharedPreferences sh;
    EditText e1,e2;
    Button b1,b2;

    String uname,pswds;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.back2);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.editTextTextPersonName2);
        e2=findViewById(R.id.editTextTextPersonName3);
        b1=findViewById(R.id.button3);
        b2=findViewById(R.id.b2);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),UserRegisteration.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = e1.getText().toString();
                pswds = e2.getText().toString();
                if (uname.equalsIgnoreCase("")) {
                    e1.setError("Enter User Name");
                } else if (pswds.equalsIgnoreCase("")) {
                    e2.setError("Enter Password");
                } else {


                    url = "http://" + sh.getString("ip", "") + ":5000/login";


                    RequestQueue queue = Volley.newRequestQueue(Login.this);

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {
                                    String lid = json.getString("lid");
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();

                                    Intent i = new Intent(getApplicationContext(), LocationService.class);
                                    startService(i);

                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uname", uname);
                            params.put("password", pswds);

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });


          }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        AlertDialog.Builder ald=new AlertDialog.Builder(Login.this);
        ald.setTitle("Do you want to Exit")
                .setPositiveButton(" YES ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent in=new Intent(Intent.ACTION_MAIN);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.addCategory(Intent.CATEGORY_HOME);
                        startActivity(in);
                    }
                })
                .setNegativeButton(" NO ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog al=ald.create();
        al.show();

    }
}