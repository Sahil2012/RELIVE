package com.example.relive;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {



    private log l;
    public ProfileFragment(log l) {
        // Required empty public constructor
        this.l = l;
    }

    private Button logout;
    private TextView name,place,email;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        name = v.findViewById(R.id.nameAcct);
        place = v.findViewById(R.id.cityAcct);
        email = v.findViewById(R.id.emailAct);
        logout = v.findViewById(R.id.logOut);

        firebaseFirestore.collection("users").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    name.setText(documentSnapshot.getString("name"));
                    email.setText(documentSnapshot.getString("email"));
                    place.setText(documentSnapshot.getString("city"));
                    return;
                } else {

                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.logoutMet();
            }
        });

        return v;
    }
}