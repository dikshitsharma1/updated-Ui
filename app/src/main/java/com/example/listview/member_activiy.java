package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class member_activiy extends AppCompatActivity {
    private Button button,mupdate,mnewpass;
    private task_adapter madapter;
    private ArrayList<task_data> mroundList = new ArrayList<>();
    private RecyclerView mrecyclerView;
    String toke,id,url;
    Context mcontext;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_activiy);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        TextView edtText = findViewById(R.id.name_mem);
        edtText.setText(name);
        toke = intent.getStringExtra("token");
        id=intent.getStringExtra("id");
        url="https://employee-manage-app-backend.araj.tk/api/task/gettask/"+id;
        Log.i("a::::::::::::::::::::::::::",toke);
        mrecyclerView = findViewById(R.id.recyclerView2);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setHasFixedSize(true);

        loadData();
        button= (Button)findViewById(R.id.button);
        mupdate= (Button)findViewById(R.id.update);
        mnewpass= (Button)findViewById(R.id.password);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(member_activiy.this, newTask.class);
                intent.putExtra("token", toke);
                startActivity(intent);
            }
        });
        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(member_activiy.this, upadteprofile.class);
                intent.putExtra("token", toke);
                startActivity(intent);
            }
        });
        mnewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(member_activiy.this, upadtepassword.class);
                intent.putExtra("token", toke);
                startActivity(intent);
            }
        });
    }

    public  void loadData(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(member_activiy.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pd.dismiss();
                                try {
//                                    JSONObject res = response.getJSONObject("response");
                                    JSONArray arr = response.getJSONArray("data");
                                    for (int i=0; i < arr.length();i++ ) {
                                        JSONObject jsonObject = arr.getJSONObject(i);
                                        String task = jsonObject.getString("description");
                                        String t_name = jsonObject.getString("type");
                                        String t_dep = jsonObject.getString("description");
//                                        Log.i("kjahkj::::::::::::::::::::::::::::::",name);
                                        mroundList.add(new task_data(task,t_dep,t_name));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                madapter = new task_adapter(member_activiy.this,mroundList);
                                mrecyclerView.setAdapter(madapter);

                            }

                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof NoConnectionError){
                            ConnectivityManager cm = (ConnectivityManager)mcontext
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetwork = null;
                            if (cm != null) {
                                activeNetwork = cm.getActiveNetworkInfo();
                            }
                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
                                Toast.makeText(member_activiy.this, "Server is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(member_activiy.this, "Your device is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (error.getCause() instanceof MalformedURLException){
                            Toast.makeText(member_activiy.this, "Bad Request.", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
                                || error.getCause() instanceof JSONException
                                || error.getCause() instanceof XmlPullParserException){
                            Toast.makeText(member_activiy.this, "Parse Error (because of invalid json or xml).",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error.getCause() instanceof OutOfMemoryError){
                            Toast.makeText(member_activiy.this, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
                        }else if (error instanceof AuthFailureError){
                            Toast.makeText(member_activiy.this, "server couldn't find the authenticated request.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
                            Toast.makeText(member_activiy.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
                                || error.getCause() instanceof ConnectTimeoutException
                                || error.getCause() instanceof SocketException
                                || (error.getCause().getMessage() != null
                                && error.getCause().getMessage().contains("Connection timed out"))) {
                            Toast.makeText(member_activiy.this, "Connection timeout error",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(member_activiy.this, "An unknown error occurred.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                //String credentials = "username:password";
                String auth = "Token "
                        + toke ;
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}