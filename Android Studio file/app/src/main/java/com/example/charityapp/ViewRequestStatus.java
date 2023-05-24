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

public class ViewRequestStatus extends AppCompatActivity implements AdapterView.OnItemClickListener{
    SharedPreferences sh;
    TextView t1,t2,t3,t4;
    ListView l1;
    ArrayList<String> Name,contact,request,date,id;
    String url,Rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        t1=findViewById(R.id.textView21);
        t2=findViewById(R.id.textView20);
        t3=findViewById(R.id.textView19);
        t4=findViewById(R.id.textView18);
        l1=findViewById(R.id.listview4);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = "http://" + sh.getString("ip", "") + ":5000/viewrequeststatus";
        RequestQueue queue = Volley.newRequestQueue(ViewRequestStatus.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    Name = new ArrayList<>();
                    contact = new ArrayList<>();
                    request = new ArrayList<>();
                    date = new ArrayList<>();
                    id = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        Name.add(jo.getString("Name"));
                        contact.add(jo.getString("Phone"));
                        request.add(jo.getString("request"));
                        date.add(jo.getString("date"));
                        id.add(jo.getString("id"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new customforRequests(ViewRequestStatus.this, Name, contact, request, date));
                    l1.setOnItemClickListener(ViewRequestStatus.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ViewRequestStatus.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Rid=id.get(i);
        AlertDialog.Builder ald=new AlertDialog.Builder(ViewRequestStatus.this);
        ald.setTitle("Request")
                .setPositiveButton(" Accept ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try
                        {


                            url="http://"+sh.getString("ip","")+":5000/acceptrequest1";


                            RequestQueue queue = Volley.newRequestQueue(ViewRequestStatus.this);

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
                                            Toast.makeText(ViewRequestStatus.this, "Accepted", Toast.LENGTH_SHORT).show();

                                            Intent ik = new Intent(getApplicationContext(), userhome.class);
                                            startActivity(ik);

                                        } else {

                                            Toast.makeText(ViewRequestStatus.this, "Error", Toast.LENGTH_SHORT).show();

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
                                    params.put("id", Rid);


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
                .setNegativeButton(" Reject ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Rid=id.get(i);
                        url="http://"+sh.getString("ip","")+":5000/rejectrequest";


                        RequestQueue queue = Volley.newRequestQueue(ViewRequestStatus.this);

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
                                        Toast.makeText(ViewRequestStatus.this, "Rejected", Toast.LENGTH_SHORT).show();

                                        Intent ik = new Intent(getApplicationContext(), userhome.class);
                                        startActivity(ik);

                                    } else {

                                        Toast.makeText(ViewRequestStatus.this, "Error", Toast.LENGTH_SHORT).show();

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
                                params.put("id", Rid);


                                return params;
                            }
                        };
                        queue.add(stringRequest);


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