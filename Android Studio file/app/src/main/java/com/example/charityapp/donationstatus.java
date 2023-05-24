package com.example.charityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class donationstatus extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SharedPreferences sh;
    Button b1;
    TextView t1,t2,t3;
    ListView l1;
    ArrayList<String> image,Details,Date,Status,did;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationstatus);
        b1=findViewById(R.id.button4);


        l1=findViewById(R.id.listview10);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AddDonation.class);
                startActivity(i);
            }
        });
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = "http://" + sh.getString("ip", "") + ":5000/donation";
        RequestQueue queue = Volley.newRequestQueue(donationstatus.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
//                    Toast.makeText(donationstatus.this, response, Toast.LENGTH_SHORT).show();

                    Details = new ArrayList<>();
                    Date = new ArrayList<>();
                    Status = new ArrayList<>();
                    image=new ArrayList<>();
                    did=new ArrayList<>();



                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        Details.add(jo.getString("Details"));
                        Date.add(jo.getString("Date"));
                        Status.add(jo.getString("Status"));
                        image.add(jo.getString("image"));
                        did.add(jo.getString("Id"));




                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new customImage(donationstatus.this,image, Details, Date, Status));
                    l1.setOnItemClickListener(donationstatus.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(donationstatus.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder ald=new AlertDialog.Builder(donationstatus.this);
        ald.setTitle("Request")
                .setPositiveButton(" delete ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try
                        {


                            url="http://"+sh.getString("ip","")+":5000/delete";


                            RequestQueue queue = Volley.newRequestQueue(donationstatus.this);

                            // Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the response string.
                                    Log.d("+++++++++++++++++", response);
                                    try {
                                        JSONObject json = new JSONObject(response);
                                        String res = json.getString("task");

                                        if (res.equalsIgnoreCase("valid")) {
//
                                            Toast.makeText(donationstatus.this, "deleted", Toast.LENGTH_SHORT).show();

                                            Intent ik = new Intent(getApplicationContext(), userhome.class);
                                            startActivity(ik);

                                        } else {

                                            Toast.makeText(donationstatus.this, "Error", Toast.LENGTH_SHORT).show();

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
                                    params.put("id", did.get(i));


                                    return params;
                                }
                            };
                            queue.add(stringRequest);

                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton(" cancel ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent i=new Intent(getApplicationContext(),donationstatus.class);
                        startActivity(i);
                    }
                });

        AlertDialog al=ald.create();
        al.show();
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
        super.onBackPressed(); }
}