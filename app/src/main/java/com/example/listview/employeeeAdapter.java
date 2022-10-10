package com.example.listview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.List;

public class employeeeAdapter extends RecyclerView.Adapter<employeeeAdapter.ViewHolder>  {
    private List<Data> mroundList;
    private Context mactivity;

    public employeeeAdapter(Context context, List<Data> roundList) {
        mroundList = roundList;
        mactivity = context;

    }

    @NonNull
    @Override
    public com.example.listview.employeeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mactivity).inflate(R.layout.employee_adapter, parent, false);
        return new employeeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.listview.employeeeAdapter.ViewHolder holder, int position) {
        Data roundList = mroundList.get(position);
        String data = mroundList.get(position).getName();

        holder.emp_name.setText(data);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = roundList.getId();
                String token = roundList.getToken();
                Intent intent = new Intent(mactivity, member_activiy.class);
                intent.putExtra("id", id);
                intent.putExtra("token", token);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                mactivity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mroundList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView emp_name;
        CardView cd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emp_name = itemView.findViewById(R.id.name);
            cd=itemView.findViewById(R.id.cd1);
        }

    }

}
