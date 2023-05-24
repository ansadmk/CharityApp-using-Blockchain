package com.example.charityapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddDonation extends AppCompatActivity {
    SharedPreferences sh;
    EditText e1,e2;
    Button b1,b2;
    String details,url,title,detail,detail1;
    String PathHolder="";
    byte[] filedt=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        e1=findViewById(R.id.editTextTextPersonName7);
        e2=findViewById(R.id.upimage);
        b1=findViewById(R.id.button14);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b2=findViewById(R.id.button30);



        url="http://"+sh.getString("ip","")+":5000/adddonationposts";
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
//            intent.setType("application/pdf");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, 7);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail=e1.getText().toString();
                detail1=e2.getText().toString();
                if (detail.equalsIgnoreCase("")) {
                    e1.setError("The field is empty");
                } else if (detail1.equalsIgnoreCase("")) {
                    e2.setError("The field is empty");
                } else {
                    details = e1.getText().toString();
                    uploadBitmap(title);


                }



            }
        });
    }


    ProgressDialog pd;
    private void uploadBitmap(final String title) {
        pd=new ProgressDialog(AddDonation.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response1) {
                        pd.dismiss();
                        String x=new String(response1.data);
                        try {
                            JSONObject obj = new JSONObject(new String(response1.data));
//                        Toast.makeText(Upload_agreement.this, "Report Sent Successfully", Toast.LENGTH_LONG).show();
                            if (obj.getString("task").equalsIgnoreCase("success")) {

                                Toast.makeText(AddDonation.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),donationstatus.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("details",details);

                params.put("lid",sh.getString("lid",""));

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(PathHolder , filedt ));









                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.d("File Uri", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        PathHolder = FileUtils.getPathFromURI(this, uri);
//                        PathHolder = data.getData().getPath();
//                        Toast.makeText(this, PathHolder, Toast.LENGTH_SHORT).show();

                        filedt = getbyteData(PathHolder);
                        Log.d("filedataaa", filedt + "");
//                        Toast.makeText(this, filedt+"", Toast.LENGTH_SHORT).show();
                        e2.setText(PathHolder);
                    }
                    catch (Exception e){
                        Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private byte[] getbyteData(String pathHolder) {
        Log.d("path", pathHolder);
        File fil = new File(pathHolder);
        int fln = (int) fil.length();
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(fil);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[fln];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            byteArray = bos.toByteArray();
            inputStream.close();
        } catch (Exception e) {
        }
        return byteArray;


    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),donationstatus.class);
        startActivity(i);
        super.onBackPressed(); }

}