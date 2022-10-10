package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class newTask extends AppCompatActivity {
    Spinner spinner;
    EditText mdate,mtime,mabout,mmin;
    Button button1;
    String res="";
    String token,date,work,about,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        mdate=findViewById(R.id.etdate2);
        mabout=findViewById(R.id.textInputEditText);
        mtime=findViewById(R.id.editTextTime);
        mmin=findViewById(R.id.editTextNumber);
        button1=(Button)findViewById(R.id.save);
        spinner=(Spinner) findViewById(R.id.spinner);

        List<String>list=new ArrayList<String>();
        list.add("Break");
        list.add("Meeting");
        list.add("Work");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setSelection(i);
                res=spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time=mtime.getText().toString().trim();
                String date=mdate.getText().toString().trim();
                String about=mabout.getText().toString().trim();
                String min=mmin.getText().toString().trim();
                work=res;
                if(about.length()<1)
                {
                    mabout.setError("deaprtment should be >=1 char ");
                    return;
                }
                if (date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"))
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
                if(TextUtils.isEmpty(date))
                {
                    mdate.setError("date is required");
                    return;
                }
                postDataUsingVolley(date,time,about,min);
                //Toast.makeText(newTask.this, "Task Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void postDataUsingVolley(String mdate, String mtime, String mabout, String mmin){
        String url = "https://employee-manage-app-backend.araj.tk/api/task/addtask";
        JSONObject data=null;
        data  = new JSONObject();

        try {
            Log.i("id",work);
            data.put("description",mabout);
            data.put("type",work);
            data.put("timeTaken",mmin);
            data.put("starTime", mdate+" "+mtime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue;
        queue = Volley.newRequestQueue(newTask.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("error")) {
                        String admin_str = response.getString("error");
                        Toast.makeText(newTask.this, admin_str, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(newTask.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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