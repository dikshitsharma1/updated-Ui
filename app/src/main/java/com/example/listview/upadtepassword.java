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

public class upadtepassword extends AppCompatActivity {
    EditText mold,mnew;
    Button mupdate;
    String molpass,mnewpass;
    public  String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadtepassword);
        Intent intent = getIntent();

        token = intent.getStringExtra("token");
        mold=findViewById(R.id.old_paasword);
        mnew =findViewById(R.id.new_password);
        mupdate=findViewById(R.id.update_pass);
        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass=mold.getText().toString().trim();
                String newpass=mnew.getText().toString().trim();
                if(newpass.length()<8)
                {
                    mnew.setError("password should be >=8 char ");

                    return;
                }
                if(oldpass.length()<8)
                {
                    mold.setError("password should be >=8 char ");

                    return;
                }
                postDataUsingVolley(oldpass,newpass);
            }
        });
    }
    public void postDataUsingVolley(String oldpass, String newpass){
        String url = "https://employee-manage-app-backend.araj.tk/api/auth/resetpassword";
        JSONObject data=null;
        data  = new JSONObject();

        try {
            molpass = oldpass;
            mnewpass = newpass;

            data.put("oldPassword",molpass);
            data.put("newPassword",mnewpass);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue;
        queue = Volley.newRequestQueue(upadtepassword.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("error")) {
                        String admin_str = response.getString("error");
                        Toast.makeText(upadtepassword.this, admin_str, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(upadtepassword.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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