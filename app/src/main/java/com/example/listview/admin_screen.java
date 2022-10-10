package com.example.listview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class admin_screen extends AppCompatActivity {
    private employeeeAdapter madapter;
    private ArrayList<Data> mroundList = new ArrayList<>();
    private RecyclerView mrecyclerView;
    Button maddmem;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        token = intent.getStringExtra("token");
        EditText edtText = (EditText) findViewById(R.id.editTextTextPersonName);
        edtText.setText(name);
        maddmem=findViewById(R.id.button);

        maddmem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_screen.this, add_employee.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });
        mrecyclerView = findViewById(R.id.recyclerView);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setHasFixedSize(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Live Matches");
        //back buttons
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        loadData();
    }
    public  void loadData(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(admin_screen.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,"https://employee-manage-app-backend.araj.tk/api/auth/getemployee", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pd.dismiss();
                                try {
//                                    JSONObject res = response.getJSONObject("response");
                                    JSONArray arr = response.getJSONArray("data");
                                    for (int i=0; i < arr.length();i++ ) {
                                        JSONObject jsonObject = arr.getJSONObject(i);
                                        String name = jsonObject.getString("name");
                                        String id = jsonObject.getString("_id");
//                                        Log.i("kjahkj::::::::::::::::::::::::::::::",name);
                                        mroundList.add(new Data(name,id,token));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                madapter = new employeeeAdapter(admin_screen.this,mroundList);
                                mrecyclerView.setAdapter(madapter);

                            }

                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // method to handle errors.
                        Toast.makeText(admin_screen.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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
