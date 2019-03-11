package com.example.krishna.mychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView signUp;
    private EditText logEmail;
    private EditText logPass;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    progressDialog = new ProgressDialog(this);
    firebaseAuth = FirebaseAuth.getInstance();
    if(firebaseAuth.getCurrentUser() !=null){
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    btnLogin = findViewById(R.id.btn_login);
    signUp = findViewById(R.id.btn_signup);
    logEmail =findViewById(R.id.log_email);
    logPass = findViewById(R.id.log_pass);

    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginUser();
        }
    });

    signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            mainDash();
        }
    });

    }
    private void loginUser(){
        String email = logEmail.getText().toString().trim();
        String pass = logPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Email can't be empty", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(), "Password can't be empty", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "credentials are not correct.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
//        firebaseAuth.sendSignInLinkToEmail();
    }

    public void mainDash(){
        Intent i = new Intent(LoginActivity.this, Main2Activity.class);
        startActivity(i);

    }
}
