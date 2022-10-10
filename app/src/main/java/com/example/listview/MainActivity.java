package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
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

public class MainActivity extends AppCompatActivity {
    EditText memail,mpassword;
    Button mloginbutton;
    String id,pass;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memail =findViewById(R.id.Email);
        mpassword=findViewById(R.id.Password);
        mloginbutton=findViewById(R.id.login);

        mloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.matches(emailPattern))
                {
                }
                else
                {

                    memail.setError("Invalid email address");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    memail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    mpassword.setError("password is required");
                    return;
                }
                if(password.length()<8)
                {
                    mpassword.setError("password should be >=8 char ");

                    return;
                }
                postDataUsingVolley(email,password);
            }
        });

    }

    private void postDataUsingVolley(String memail, String mpassword ) {
        // url to post our data
        String url = "https://employee-manage-app-backend.araj.tk/api/auth/login";

        JSONObject data = null;
        try {
            data = new JSONObject();
            // get employee name and salary
            //id = data.getString(String.valueOf(memail));
            //pass = data.getString(String.valueOf(mpassword));
            id = memail;
            pass = mpassword;
            data.put("email", id);
            data.put("password", pass);
            Log.i("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    JSONObject res = response.getJSONObject("user");
                    String admin_str = res.getString("isAdmin");
                    String name = res.getString("name");
                    String id = res.getString("_id");
                    Log.i("id", token);
                    String log = "true";
                    if (admin_str.equals(log)) {
                        Intent intent = new Intent(MainActivity.this, admin_screen.class);
                        intent.putExtra("name", name);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, member_activiy.class);
                        intent.putExtra("name", name);
                        intent.putExtra("token", token);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                    // String success = "success";
                    //Toast.makeText(MainActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

    }
}
