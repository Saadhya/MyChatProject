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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {

    private EditText regPass;
    private EditText regEmail;
    private Button otpBtn;
    private TextView signIn;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        progressDialog = new ProgressDialog(this);

        signIn = findViewById(R.id.btn_signin);
        otpBtn = findViewById(R.id.btn_otp);
        regPass = findViewById(R.id.reg_pass);
        regEmail =findViewById(R.id.reg_email);

        otpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mainDash();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                Toast.makeText(getApplicationContext(), "hello guys",Toast.LENGTH_SHORT).show();

            }
        });
    }
//    @Override
//    public void onClick(View v){
//        if(v == otpBtn){
//            registerUser();
//        }
//        if(v == signIn){
////            will open login activity here
//            finish();
//            mainDash();
//        }
//
//    }

    private void registerUser(){

        String email = regEmail.getText().toString().trim();
        String pass = regPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "email can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(), "Password can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Register in process Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "data is saved", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "data is not saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void mainDash(){
        Intent i = new Intent(Main2Activity.this, LoginActivity.class);
        startActivity(i);

    }
}
