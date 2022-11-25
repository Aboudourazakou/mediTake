package com.example.meditake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditake.adapters.IgnoreReasonAdapter;
import com.example.meditake.databinding.IgnoreMessageDialogBinding;
import com.example.meditake.databinding.InternetUnavalaibleDialogBinding;
import com.example.meditake.internet.NetworkChangeListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class activity_login extends AppCompatActivity {

    EditText phoneNumber,password;
    TextView loginButton;
    Map<String,String> comptes;
    Dialog dialog;
    Dialog internetDialog;
    public static  activity_login INSTANCE;
    CircularProgressIndicator mCircularProgressIndicator;

    SharedPreferences  sharedPreferences;
    SharedPreferences.Editor editor;
    public static final  String SHARED_PREF_NAME = "mysharedpref";
    private static final  String KEY_PHONE = "myphone";
    private static final  String KEY_PASSWORD = "mypassword";
    NetworkChangeListener networkChangeListener=new NetworkChangeListener();


    @SuppressLint({"WrongViewCast", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        INSTANCE=this;

        phoneNumber = findViewById(R.id.phoneEdit);
        password = findViewById(R.id.passwordEdit);
        loginButton = findViewById(R.id.loginButton);
        mCircularProgressIndicator = findViewById(R.id.circular_indicator);

        sharedPreferences = getSharedPreferences(this.SHARED_PREF_NAME, MODE_PRIVATE);

        comptes = new HashMap<String, String>() {{
            put("06754", "admin");
            put("09876", "user");
        }};

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(phoneNumber.equals("yes") &&password.equals("yes")){
                        showIsLoggingDialog();

                  }else{
                      showLogginIncorrectDialog();
                  }
            }
        });

    }

    public void showDialog() {
        dialog = new Dialog(activity_login.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        internetDialog=dialog;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.internet_unavalaible_dialog);
        Button btn=dialog.findViewById(R.id.skippDialogButtonPhoneIncorrect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog();
            }
        });
        dialog.show();
            }

            private void DoLogin(String phone, String psswrd) {
                editor = sharedPreferences.edit();
                editor.putString(KEY_PHONE, phone);
                editor.putString(KEY_PASSWORD, psswrd);
                editor.putBoolean("hasLoggedIn", true);
                editor.commit();

                mCircularProgressIndicator.setVisibility(View.VISIBLE);
                Intent myIntent = new Intent(activity_login.this, HomeActivity.class);
                startActivity(myIntent);

            }


    @Override
    public  void onStart() {

        super.onStart();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
    }


    public  static activity_login getInstance(){return  INSTANCE;}

    public   void cancelDialog(){
        if(internetDialog!=null){
            internetDialog.cancel();
        }
    }



    public  void  showLogginIncorrectDialog(){
        dialog = new Dialog(activity_login.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.incorrect_credentials_dialog);
        Button btn=dialog.findViewById(R.id.skippDialogButtonPhoneIncorrect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.cancel();
            }
        });
        dialog.show();

    }


    public  void showIsLoggingDialog(){
        dialog = new Dialog(activity_login.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        internetDialog=dialog;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.credentials_verification_dialog);
        dialog.show();
    }



    }