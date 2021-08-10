package com.example.relive;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    Context context;
    mainRe li;
    FirebaseFirestore firebaseFirestore  = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firebaseFirestore.collection("post");;
    ReFbAdap reFbAdap;


    public HomeFragment(Context context,mainRe li) {
        // Required empty public constructor
        this.context = context;
        this.li = li;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.reReq);

        setQReq();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        reFbAdap.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        reFbAdap.stopListening();
    }

    private void setQReq() {

        Query query = collectionReference.orderBy("quan", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<RequestPost> opt = new FirestoreRecyclerOptions.Builder<RequestPost>().setQuery(query,RequestPost.class).build();

        reFbAdap = new ReFbAdap(opt,li);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(reFbAdap);
    }
}