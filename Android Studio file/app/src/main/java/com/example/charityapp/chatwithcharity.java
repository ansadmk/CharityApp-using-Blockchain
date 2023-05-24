package com.example.charityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chatwithcharity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SharedPreferences sh;
    TextView t1,t2,t3,t4;
    ListView l1;
    String url;
    ArrayList<String> name,address,email,Phone,id;
    String chid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nearby_charity);

        l1=findViewById(R.id.listview3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = "http://" + sh.getString("ip", "") + ":5000/viewallcharityorganisation";
        RequestQueue queue = Volley.newRequestQueue(chatwithcharity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
//                    Toast.makeText(chatwithcharity.this, response+"", Toast.LENGTH_SHORT).show();

                    JSONArray ar = new JSONArray(response);

                    name = new ArrayList<>();
                    address = new ArrayList<>();
                    email = new ArrayList<>();
                    Phone = new ArrayList<>();
                    id = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        name.add(jo.getString("Name"));
                        address.add(jo.getString("Place"));
                        email.add(jo.getString("email"));
                        Phone.add(jo.getString("Phone"));
                        id.add(jo.getString("Lid"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom4(chatwithcharity.this, name, address, email, Phone));
                    l1.setOnItemClickListener(chatwithcharity.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(chatwithcharity.this, "err" + error, Toast.LENGTH_SHORT).show();
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
        chid=id.get(i);

        Intent I=new Intent(getApplicationContext(),Test.class);
        SharedPreferences.Editor edp = sh.edit();
        edp.putString("uid", chid);
        edp.commit();
        startActivity(I);
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),userhome.class);
        startActivity(i);
        super.onBackPressed(); }
}