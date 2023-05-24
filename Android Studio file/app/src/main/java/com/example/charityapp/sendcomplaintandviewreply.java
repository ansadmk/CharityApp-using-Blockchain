package com.example.charityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class sendcomplaintandviewreply extends AppCompatActivity {
    SharedPreferences sh;
    EditText e1;
    Button b1;
    TextView t1,t2,t3;
    ListView l1;
    String complaint,url;
    ArrayList<String>comp,date,reply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendcomplaintandviewreply);
        e1=findViewById(R.id.editTextTextPersonName4);
        b1=findViewById(R.id.button2);

        l1=findViewById(R.id.listview4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             complaint=e1.getText().toString();
                if (complaint.equalsIgnoreCase("")) {
                    e1.setError("The field is empty");
                } else {
                    url = "http://" + sh.getString("ip", "") + ":5000/sendcomplaint";


                    RequestQueue queue = Volley.newRequestQueue(sendcomplaintandviewreply.this);

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
//
                                    Toast.makeText(sendcomplaintandviewreply.this, "success", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), sendcomplaintandviewreply.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(sendcomplaintandviewreply.this, "failed", Toast.LENGTH_SHORT).show();

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
                            params.put("complaint", complaint);
                            params.put("lid", sh.getString("lid", ""));


                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = "http://" + sh.getString("ip", "") + ":5000/viewreplayofcomplaint";
        RequestQueue queue = Volley.newRequestQueue(sendcomplaintandviewreply.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    comp = new ArrayList<>();
                    date = new ArrayList<>();
                    reply = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        comp.add(jo.getString("complaint"));
                        date.add(jo.getString("date"));
                        reply.add(jo.getString("reply"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom3(sendcomplaintandviewreply.this, comp, date, reply));
//                    l1.setOnItemClickListener(view_menu.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(sendcomplaintandviewreply.this, "err" + error, Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
        super.onBackPressed(); }
}