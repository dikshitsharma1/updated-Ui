package com.example.listview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class task_adapter extends RecyclerView.Adapter<task_adapter.ViewHolder> {
    private List<task_data> mroundList;
    private Context mactivity;

    public task_adapter(Context context, List<task_data> roundList) {
        mroundList = roundList;
        mactivity = context;

    }

    @NonNull
    @Override
    public task_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mactivity).inflate(R.layout.task_adapter, parent, false);
        return new task_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull task_adapter.ViewHolder holder, int position) {
        task_data roundList = mroundList.get(position);
        String task = mroundList.get(position).getTask();
        String Description = mroundList.get(position).getTask();
        String department = mroundList.get(position).getTask();
        holder.etask_name.setText(task);
        holder.dep.setText(department);
        holder.des.setText(Description);


    }

    @Override
    public int getItemCount() {
        return mroundList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView etask_name,dep,des;
        CardView cd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etask_name= itemView.findViewById(R.id.task);
            dep=itemView.findViewById(R.id.t_department);
            des=itemView.findViewById(R.id.des);
            cd=itemView.findViewById(R.id.cd2);
        }

    }

}
