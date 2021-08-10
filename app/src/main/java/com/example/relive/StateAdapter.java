package com.example.relive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class StateAdapter extends RecyclerView.Adapter<ViewHold> {

    ArrayList<Map<String,String>> arr;

    public StateAdapter(ArrayList<Map<String,String>> arr){
        this.arr = arr;
    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_item,parent,false);

        return new ViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        holder.state.setText(arr.get(position).get("state"));
        holder.confirm.setText(arr.get(position).get("confirmed"));
        holder.recover.setText(arr.get(position).get("recovered"));
        holder.dead.setText(arr.get(position).get("deaths"));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
}

class ViewHold extends RecyclerView.ViewHolder{

    TextView state,recover,confirm,dead;

    public ViewHold(@NonNull View itemView) {
        super(itemView);
        state = itemView.findViewById(R.id.sName);
        recover = itemView.findViewById(R.id.recover);
        confirm = itemView.findViewById(R.id.confirm);
        dead = itemView.findViewById(R.id.dead);
    }
}
