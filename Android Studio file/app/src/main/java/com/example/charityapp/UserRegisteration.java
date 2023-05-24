package com.example.charityapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RadioButton;
import android.widget.TextView;


public class UserRegisteration extends AppCompatActivity {
    SharedPreferences sh;
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    TextView t1;
    RadioButton r1,r2;
    Button b1;
    String fname,lname,place,post,pin,email,phone,uname,pswd,url,gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_registeration);
        e1=findViewById(R.id.editTextTextPersonNam);
        e2=findViewById(R.id.editTextTextPersonNam1);
        e3=findViewById(R.id.editTextTextPersonNam2);
        e4=findViewById(R.id.editTextTextPersonNam3);
        e5=findViewById(R.id.editTextTextPersonNam4);
        e6=findViewById(R.id.editTextTextPersonNam5);
        e7=findViewById(R.id.editTextTextPersonNam6);
        e8=findViewById(R.id.editTextTextPersonNam7);
        e9=findViewById(R.id.editTextTextPersonNam8);
        t1=findViewById(R.id.textView8);
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        b1=findViewById(R.id.button6);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();
                gender = "";
                if (r1.isChecked()) {
                    gender = r1.getText().toString();
                } else {
                    gender = r2.getText().toString();
                }
                place = e3.getText().toString();
                post = e4.getText().toString();
                pin = e5.getText().toString();
                email = e6.getText().toString();
                phone = e7.getText().toString();
                uname = e8.getText().toString();
                pswd = e9.getText().toString();
                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Enter first name");
                } else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter last name");
                } else if (place.equalsIgnoreCase("")) {
                    e3.setError("Enter Place");
                } else if (post.equalsIgnoreCase("")) {
                    e4.setError("Enter Place");
                } else if (pin.equalsIgnoreCase("")) {
                    e5.setError("Enter Your Pin");
                } else if (pin.length() != 6) {
                    e5.setError("invalid pin");
                    e5.requestFocus();
                } else if (email.equalsIgnoreCase("")) {
                    e6.setError("Enter Your Email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    e6.setError("Enter Valid Email");
                    e6.requestFocus();
                } else if (phone.equalsIgnoreCase("")) {
                    e7.setError("Enter Your Phone No");
                } else if (phone.length() < 10) {
                    e7.setError("Minimum 10 nos required");
                    e7.requestFocus();
                } else if (uname.equalsIgnoreCase("")) {
                    e8.setError("Enter Your username");
                } else if (pswd.equalsIgnoreCase("")) {
                    e9.setError("Enter Your password");
                } else {


                    url = "http://" + sh.getString("ip", "") + ":5000/reg_user";


                    RequestQueue queue = Volley.newRequestQueue(UserRegisteration.this);

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
//                                String lid = json.getString("lid");
//                                SharedPreferences.Editor edp = sh.edit();
//                                edp.putString("lid", lid);
//                                edp.commit();
                                    Toast.makeText(UserRegisteration.this, "Registration was a success", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), Login.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(UserRegisteration.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

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
                            params.put("fname", fname);
                            params.put("lname", lname);
                            params.put("gender", gender);
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin", pin);
                            params.put("email", email);
                            params.put("phone", phone);
                            params.put("username", uname);
                            params.put("password", pswd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Login.class);
        startActivity(i);
        super.onBackPressed(); }

}