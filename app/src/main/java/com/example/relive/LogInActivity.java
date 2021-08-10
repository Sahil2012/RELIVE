package com.example.relive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private Button signUp,logIn;
    private SignInButton googleSign;
    private EditText email,pass;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        logIn = findViewById(R.id.logInBtn);
        googleSign = findViewById(R.id.signGoogle);
        signUp = findViewById(R.id.signIn);

        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS = email.getText().toString().trim();
                String passS = pass.getText().toString().trim();

                String msg = "";

                if(emailS.equals("")) {

                    msg = "Enter Email";
                    Toast.makeText(LogInActivity.this,msg,Toast.LENGTH_SHORT).show();
                } else if(!emailS.matches(emailPattern)) {

                    msg = "Enter Valid Email";
                    Toast.makeText(LogInActivity.this,msg,Toast.LENGTH_SHORT).show();

                } else if(passS.length() < 6){

                    msg = "Password Should be more than 6 character";
                    Toast.makeText(LogInActivity.this,msg,Toast.LENGTH_SHORT).show();

                } else {

                    auth.signInWithEmailAndPassword(emailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                startActivity(new Intent(LogInActivity.this,HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LogInActivity.this,"Invalid Credential",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}