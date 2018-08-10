package com.example.neerajagrawal.transhub;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BookActivity extends AppCompatActivity {

    Spinner weight,w1,w2;
    String vajans[]={"KG","TONNE","QUINTAL"};
    String vaj[]={"10","20","30","40","50","60","70","80","90","100"};
    Button confirm;
    DatabaseReference myref;
    FirebaseDatabase fd;
    ImageButton i1,i2,i3,i4;
    EditText pickup, drop;
    Book_User bu;
    String from, to;
    Button map1,map2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initDatabase();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar)));
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.toolbar));
        }
        i1=(ImageButton)findViewById(R.id.imageButton1);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,11);
            }
        });
        i2=(ImageButton)findViewById(R.id.imageButton2);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,12);
            }
        });
        i3=(ImageButton)findViewById(R.id.imageButton3);
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,13);
            }
        });
        i4=(ImageButton)findViewById(R.id.imageButton4);
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,14);
            }
        });

        pickup =(EditText)findViewById(R.id.pick);
        drop =(EditText)findViewById(R.id.drop);
        weight=(Spinner)findViewById(R.id.spinner);
        w1=(Spinner)findViewById(R.id.spinner2);
        w2=(Spinner)findViewById(R.id.spinner3);
        weight.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, vajans));
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,vaj);
        w1.setAdapter(arr);
        w2.setAdapter(arr);

      w1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              from =vaj[i];
          }
          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });

        w2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                to =vaj[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        confirm=(Button)findViewById(R.id.BOOKNOW);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                    String s = pickup.getText().toString();
                    String s1= drop.getText().toString();
                    String a="NEW BOOKING !!!"+"\n Weight = "+ from +" To "+ to +"\n Location :- "+" From : "+s+"  To : "+s1;
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage("9673884904",null,a,null,null);
                    addData();
                    Intent i=new Intent(BookActivity.this,ConfirmActivity.class);
                    startActivity(i);
            }
        });

        map1=(Button)findViewById(R.id.button2);
        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapButtonclick();
            }

        });
        map2=(Button)findViewById(R.id.button3);
        map2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapButtonclick();

            }
        });


    }

    private void mapButtonclick() {
        int requestCode=1;
        //Intent i=new Intent(this,MapActivity.class);
        //startActivityForResult(i,1);

    }

    public void initDatabase()
    {
        myref = FirebaseDatabase.getInstance().getReference().child("Booking Data");
    }
    private void addData() {
        String pick = pickup.getText().toString();
        String drip = drop.getText().toString();
        bu = new Book_User(pick, drip, Integer.parseInt(from), Integer.parseInt(to));
        HashMap<String, String>  map = new HashMap();
        myref.push().setValue(bu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap bmp = (Bitmap)extras.get("data");
            if(requestCode==11)
            {
                i1.setImageBitmap(bmp);
            }else if(requestCode==12)
            {
                i2.setImageBitmap(bmp);

            }else if(requestCode==13)
            {
                i3.setImageBitmap(bmp);

            }else if(requestCode==14)
            {
                i4.setImageBitmap(bmp);

            }
        }
    }
}
