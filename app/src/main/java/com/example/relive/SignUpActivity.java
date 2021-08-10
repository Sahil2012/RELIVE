package com.example.relive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth auth;
    private EditText email,pass,conPass,name;
    private Button signUp;
    private TextView logInPage;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.emailSign);
        pass = findViewById(R.id.passSign);
        conPass = findViewById(R.id.passConSign);
        signUp = findViewById(R.id.signInBtn);
        logInPage = findViewById(R.id.logInPage);
        name = findViewById(R.id.nameSign);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        logInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailS = email.getText().toString().trim();
                String passS = pass.getText().toString().trim();
                String passConf = conPass.getText().toString().trim();
                String nameSign = name.getText().toString().trim();

                String msg = "";

                if(emailS.equals("")) {

                    msg = "Enter Email";
                } else if(!emailS.matches(emailPattern)) {

                    msg = "Enter Valid Email";
                } else if(passS.length() < 6){

                    msg = "Password Should be more than 6 character";
                } else if(!passS.equals(passConf)){
                    msg = "Password does not match";
                } else if(nameSign.length() == 0) {
                    msg = "Enter Name";

                } else {

                        auth.createUserWithEmailAndPassword(emailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                HashMap<String,String> mp = new HashMap<>();

                                mp.put("name",nameSign);
                                mp.put("email",emailS);

                                firebaseFirestore.collection("users").document(auth.getUid()).set(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this,"SignUp Failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        msg = "Sucessfully Registered!";

                }

                Toast.makeText(SignUpActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }
}