package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class upadteprofile extends AppCompatActivity {
    EditText mnewno,mname,mnewdep;
    Button mupdate;
    String mnewn,mnewname,mnewdp;
    public  String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadteprofile);
        Intent intent = getIntent();

        token = intent.getStringExtra("token");
        mnewno=findViewById(R.id.changenumber);
        mname =findViewById(R.id.change_name);
        mnewdep =findViewById(R.id.changedep);
        mupdate=findViewById(R.id.update_profile);
        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname=mname.getText().toString().trim();
                String newdep=mnewdep.getText().toString().trim();
                String newno=mnewno.getText().toString().trim();
                if(newno.length()<10 && newno.length()>10)
                {
                    mnewno.setError("password should be =10 char ");
                    return;
                }
                if(newname.length()<4)
                {
                    mname.setError("name should be >=4 char ");
                    return;
                }
                if(TextUtils.isEmpty(newdep))
                {
                    mnewdep.setError("department  is required");
                    return;
                }
                postDataUsingVolley(newname,newno,newdep);
            }
        });
    }
    public void postDataUsingVolley(String mname, String mphone,  String mdep){
        String url = "https://employee-manage-app-backend.araj.tk/api/auth/updateprofile";
        JSONObject data=null;
        data  = new JSONObject();

        try {
            mnewn= mname;
            mnewname = mphone;
            mnewdp=mdep;

            data.put("name",mnewn);
            data.put("department",mnewdp);
            data.put("contact",mphone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue;
        queue = Volley.newRequestQueue(upadteprofile.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("error")) {
                        String admin_str = response.getString("error");
                        Toast.makeText(upadteprofile.this, admin_str, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(upadteprofile.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                //String credentials = "username:password";
                String auth = "Token "
                        + token ;
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}