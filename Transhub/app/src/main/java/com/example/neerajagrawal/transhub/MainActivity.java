package com.example.neerajagrawal.transhub;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;
    int pstatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar)));
        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.toolbar));
        }
        pb.setMax(4);
        final Intent i = new Intent(MainActivity.this,LogActivity.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pstatus < 4)
                {
                    pstatus+=1;
                }

                pb.post(new Runnable() {
                    @Override
                    public void run() {
                        pb.setProgress(pstatus);
                    }
                });
                        try{
                            Thread.sleep(300);

                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                if(pstatus==pb.getMax())
                {
                    startActivity(i);
                }
            }
        }).start();

    }
}
