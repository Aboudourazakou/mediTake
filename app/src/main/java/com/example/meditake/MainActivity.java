package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
<<<<<<< HEAD
                startActivity(new Intent(MainActivity.this,activity_login.class));
                finish();
            }
        },1000);


=======
                startActivity(new Intent(MainActivity.this, AddMedicationActivity.class));
                finish();
            }
        }, 4000);
>>>>>>> 7c7a128ceb941affc3de1939f9897bd55925f08b
    }

}