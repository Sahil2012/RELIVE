package com.example.relive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import static androidx.recyclerview.widget.RecyclerView.*;

public class ReFbAdap extends FirestoreRecyclerAdapter<RequestPost,ReFbAdap.ViewHold> {


    mainRe li;
    public ReFbAdap(@NonNull FirestoreRecyclerOptions<RequestPost> options,mainRe li) {
        super(options);
        this.li = li;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHold viewHold, int i, @NonNull RequestPost requestPost) {

        viewHold.bGroup.setText(requestPost.getBloodGroup());
        viewHold.pName.setText(requestPost.getPatName());
        viewHold.city.setText(requestPost.getHosp() + ", " +requestPost.getCity());
        viewHold.msg.setText(requestPost.getNeed());
        viewHold.quan.setText(requestPost.getQuan());

        viewHold.cotact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                li.callMe(requestPost.getPhoneNo());
            }
        });
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item,parent,false);
        return new ViewHold(v);
    }

    class ViewHold extends RecyclerView.ViewHolder {

        TextView pName, quan, bGroup, msg, city;
        Button cotact;

        public ViewHold(View itemView) {

            super(itemView);

            pName = itemView.findViewById(R.id.ppN);
            quan = itemView.findViewById(R.id.qq);
            bGroup = itemView.findViewById(R.id.bbg);
            msg = itemView.findViewById(R.id.mMsg);
            city = itemView.findViewById(R.id.lo);
            cotact = itemView.findViewById(R.id.contact);
        }
    }
}






