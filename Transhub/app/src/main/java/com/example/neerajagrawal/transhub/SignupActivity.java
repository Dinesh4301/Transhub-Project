package com.example.neerajagrawal.transhub;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class SignupActivity extends AppCompatActivity {
    DatabaseReference myref;
    String name, cno, email, pass;
    EditText first, last, cono, id, pwd;
    ImageButton profile;
    FirebaseAuth auth;
    Transport_User tu;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initDatabase();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar)));
        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.toolbar));
        }

        auth = FirebaseAuth.getInstance();
        first = (EditText) findViewById(R.id.firstName);
        last = (EditText) findViewById(R.id.lastName);
        cono = (EditText) findViewById(R.id.contactNO);
        id = (EditText) findViewById(R.id.eMail);
        pwd = (EditText) findViewById(R.id.password);
        submit=(Button)findViewById(R.id.submit);
        getIntent();

    }

    public void onClick(View v)
    {
        registerUser();
        addData();

        String s = "Signed Up Succesfully\n Now Login to continue...";
        Toast.makeText(this," "+s,Toast.LENGTH_LONG).show();
        Intent i = new Intent(SignupActivity.this,LogActivity.class);
        startActivity(i);

    }
    private void registerUser()
    {
        email = id.getText().toString();
        pass = pwd.getText().toString();

      /*  if(validateEmail()==false)
        {
            showToast("Please fill proper Email");
        }*/
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass))
        {
            showToast("Please fill proper information");
        }
        else {
           auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                showToast("User Profile Created Successfully");
                                //  Intent i = new Intent(SignupActivity.this, SignupDetailActivity.class);
                                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                                startActivity(i);
                            } else {
                                showToast("Failed");
                            }
                        }
                    });
        }
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

    private void addData() {
        HashMap<String, String> data = new HashMap<>();
        name = first.getText().toString()+last.getText().toString();
        cno = cono.getText().toString();
        email= id.getText().toString();
        pass = pwd.getText().toString();
        tu = new Transport_User(name, cno, email, pass);
        /*data.put("Name : ", name);
        data.put("Contact No :", cno);
        data.put("Email Id :", email);
        data.put("Password : ", pass);*/
        myref.push().setValue(tu);
        String s = "Signed Up Succesfully \nNow Login to continue...";
        toastDisp(s);
        // Intent i = new Intent(SignupActivity.this,LoginActivity.class);
        //  startActivity(i);
        clear();
    }
    public void clear()
    {
        first.setText("");
        last.setText("");
        cono.setText("");
        id.setText("");
        pwd.setText("");
    }

    private void toastDisp(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void initDatabase()
    {
        myref = FirebaseDatabase.getInstance().getReference().child("SignUp Data");
    }
    private void showToast(String s)
    {
        Toast.makeText(this," "+s,Toast.LENGTH_LONG).show();
    }
    public void createMenu(Menu menu)
    {
        MenuItem cam = menu.add(0,1,1,"Camera");
        MenuItem img = menu.add(0,2,2,"Gallery");
    }
    public boolean MenuChoice(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 1 : opencamera();
                return true;

            case 2 : opengallery();
                return  true;

        }
        return  false;
    }
    private void opencamera()
    {
        Intent  i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0) {
            if (resultCode == RESULT_OK) {
                Bitmap mp = (Bitmap) data.getExtras().get("data");
                profile.setImageBitmap(mp);
            }
        }
        if(requestCode==1){
            if(resultCode == RESULT_OK) {
                try {
                    Uri u = data.getData();
                    String[] file = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(u, file, null, null, null);
                    cursor.moveToFirst();

                    int index = cursor.getColumnIndex(file[0]);
                    String imgd = cursor.getString(index);
                    cursor.close();

                    profile.setImageBitmap(BitmapFactory.decodeFile(imgd));
                }catch(Exception e)
                {
                    showToast("Unable to Load Image");
                }
            }

        }
    }

    private void opengallery()
    {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,1);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        createMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // return super.onContextItemSelected(item);
        return MenuChoice(item);
    }

}

