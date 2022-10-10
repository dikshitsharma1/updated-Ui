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

public class add_employee extends AppCompatActivity {
    EditText email,password,mphone,mdate,mtime;
    EditText mname,mdep;
    Button madd;
    String id,pass,name,dep,date,phone,time;
    RequestQueue queue;
    public  String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        Intent intent = getIntent();

        token = intent.getStringExtra("token");
        email =findViewById(R.id.EmailAddress);
        password=findViewById(R.id.TextPassword);
        mname =findViewById(R.id.employee_name);
        mphone=findViewById(R.id.editTextPhone);
        mdate=findViewById(R.id.Date3);
        mdep = findViewById(R.id.department);
        madd=findViewById(R.id.add_employee);
        mtime=findViewById(R.id.Time2);
        madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=email.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String name=mname.getText().toString().trim();
                String phone=mphone.getText().toString().trim();
                String date=mdate.getText().toString().trim();
                String dep=mdep.getText().toString().trim();
                String time=mtime.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (date.matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$"))
                {
                }
                else
                {
                    mdate.setError("invalid date type");
                    return;
                }
                if (time.matches("(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)"))
                {
                }
                else
                {
                    mtime.setError("invalid time type");
                    return;
                }

                if (id.matches(emailPattern))
                {
                }
                else
                {

                    email.setError("Invalid email address");
                    return;
                }
                if(TextUtils.isEmpty(id))
                {
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    password.setError("password is required");
                    return;
                }
                if(TextUtils.isEmpty(name))
                {
                    mname.setError("name is required");
                    return;
                }
                if(TextUtils.isEmpty(phone))
                {
                    mphone.setError("phone number is required");
                    return;
                }
                if(TextUtils.isEmpty(date))
                {
                    mdate.setError("date is required");
                    return;
                }
                if(TextUtils.isEmpty(dep))
                {
                    mdep.setError("department  is required");
                    return;
                }
                if(pass.length()<8)
                {
                    password.setError("password should be >=8 char ");
                    return;
                }
                if(name.length()<4)
                {
                    mname.setError("name should be >=4 char ");
                    return;
                }
                if(dep.length()<1)
                {
                    mdep.setError("deaprtment should be >=1 char ");
                    return;
                }
                if(phone.length()<10 && phone.length()>10)
                {
                    mphone.setError("password should be =10 char ");
                    return;
                }
                postDataUsingVolley(name,phone,id,pass,dep,date,time);

            }
        });

    }
    public void postDataUsingVolley(String mname, String mphone, String email, String password, String mdep, String mdate,String mtime){
    String url = "https://employee-manage-app-backend.araj.tk/api/auth/addemployee";
    JSONObject data=null;
        data  = new JSONObject();

        try {
            id = email;
            pass = password;
            name=mname;
            dep = mdep;
            date = mdate +" " + mtime;

            Log.i("id",date);
            phone=mphone;
            data.put("name",name);
            data.put("email",id);
            data.put("password",pass);
            data.put("joiningDate",date);
            data.put("contact",phone);
            data.put("department",dep);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    RequestQueue queue;
        queue = Volley.newRequestQueue(add_employee.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                if (response.has("error")) {
                    String admin_str = response.getString("error");
                    Toast.makeText(add_employee.this, admin_str, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(add_employee.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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