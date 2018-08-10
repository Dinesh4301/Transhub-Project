package com.example.neerajagrawal.transhub;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    Button signin,log;
    EditText e_id,e_pass;
    String email, pass;

    FirebaseAuth auth;
    Login_User l;
    FirebaseDatabase fdb;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initDatabase();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar)));
        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.toolbar));
        }

        auth = FirebaseAuth.getInstance();
        e_id = (EditText)findViewById(R.id.edemail);
        e_pass = (EditText)findViewById(R.id.edpass);
       log = (Button) findViewById(R.id.button);
        signin=(Button)findViewById(R.id.button2);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectUser();
                Intent i1=new Intent(LoginActivity.this,AboutusActivity.class);
                startActivity(i1);
            }
        });

    }
    private boolean validateEmail()
    {
        Pattern mail = Pattern.compile("^[A-Za-z0-9+_.-]+[@][A-Za-z]+[.][A-Za-z]$", Pattern.CASE_INSENSITIVE);
        Matcher m =mail.matcher(email);
        if(!m.matches())
        {
            return false;
        }
        return true;
    }

    private void ConnectUser() {
        email = e_id.getText().toString().trim();
        pass = e_pass.getText().toString().trim();
       /* if(validateEmail()==false)
        {
            showToast("Please Enter an Proper Email");
        }
        else */if(TextUtils.isEmpty(email))
        {
            showToast("Please Enter an Email");
        }
        else if(TextUtils.isEmpty(pass))
        {
            showToast("Please Provide an Password");
        }
        else {
            addData();
            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                showToast("User Login Successful");
                                addData();
                                Intent i1 = new Intent(LoginActivity.this, BookActivity.class);
                                startActivity(i1);
                            }
                            else
                            {
                                showToast("User Login Failed");
                                Intent i=new Intent(LoginActivity.this,SignupActivity.class);
                                startActivity(i);
                                return;
                            }
                        }
                    });

        }
    }


    private void addData() {
        /*email = em.getText().toString();
        pass = ps.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }*/
        l = new Login_User(email,pass);
        HashMap<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("Email Id", email);
        dataMap.put("Password", pass);
        myref.push().setValue(l);
        // Toast.makeText(getApplicationContext(), "Login Successful",Toast.LENGTH_SHORT).show();

    }

    public void initDatabase()
    {
        myref = FirebaseDatabase.getInstance().getReference().child("Login Data");
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void showToast(String s)
    {
        Toast.makeText(this," "+s, Toast.LENGTH_LONG).show();
    }
}
