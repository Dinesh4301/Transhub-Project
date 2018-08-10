package com.example.neerajagrawal.transhub;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class AboutusActivity extends AppCompatActivity {

    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar)));
        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.toolbar));
        }

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator= getMenuInflater();
        inflator.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.edit:
                Toast.makeText(this,"You clicked on Edit Profile",Toast.LENGTH_LONG).show();
                return true;

            case R.id.aboutus:
                Toast.makeText(this,"You clicked on About Us",Toast.LENGTH_LONG).show();
                return true;

            case R.id.logout:
                Toast.makeText(this,"You clicked on Logout",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v)
    {
        switch (v.getId()) {



        case R.id.terms:
            i=new Intent(this, TermsActivity.class);
            startActivity(i);
            break;


        case R.id.bookTransport:
                i=new Intent(this,BookActivity.class);
                startActivity(i);
                break;


        }

    }

}
