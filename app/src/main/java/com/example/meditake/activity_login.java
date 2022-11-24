package com.example.meditake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.HashMap;
import java.util.Map;

public class activity_login extends AppCompatActivity {

    EditText loginphone,loginpassword;
    TextView loginButton;
    Map<String,String> comptes;
    CircularProgressIndicator mCircularProgressIndicator;

    SharedPreferences  sharedPreferences;
    SharedPreferences.Editor editor;
    public static final  String SHARED_PREF_NAME = "mysharedpref";
    private static final  String KEY_PHONE = "myphone";
    private static final  String KEY_PASSWORD = "mypassword";


    @SuppressLint({"WrongViewCast", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginphone =  findViewById(R.id.phoneEdit);
        loginpassword = findViewById(R.id.passwordEdit);
        loginButton = findViewById(R.id.loginButton);
        mCircularProgressIndicator = findViewById(R.id.circular_indicator);

        sharedPreferences = getSharedPreferences(this.SHARED_PREF_NAME,  MODE_PRIVATE);

        comptes = new HashMap<String,String>(){{
            put("06754","admin");
            put("09876","user");
        }};




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = loginphone.getText().toString();
                String password = loginpassword.getText().toString();

                if(comptes.containsKey(phone)){

                    if(password.equals(comptes.get(phone))){

                       DoLogin(phone,password);

                    }
                    else{
                      AlertDialog alertDialog = new AlertDialog.Builder(activity_login.this).create();
                        alertDialog.setTitle("Connexion échouée");
                        alertDialog.setMessage("Mot de passe Incorrecte");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        alertDialog.show();
                        //Toast.makeText(activity_login.this, "Connexion non Reussie "+n, Toast.LENGTH_SHORT).show();

                    }

                } else{
                    AlertDialog alertDialog = new AlertDialog.Builder(activity_login.this).create();
                    alertDialog.setTitle("Connexion échouée");
                    alertDialog.setMessage("numero de telephone incorrecte");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });


    }

    private void DoLogin(String phone , String psswrd){
        editor = sharedPreferences.edit();
        editor.putString(KEY_PHONE,phone);
        editor.putString(KEY_PASSWORD,psswrd);
        editor.putBoolean("hasLoggedIn",true);
        editor.commit();

        mCircularProgressIndicator.setVisibility(View.VISIBLE);
        Intent myIntent = new Intent(activity_login.this,HomeActivity.class);
        startActivity(myIntent);

        String p = sharedPreferences.getString(KEY_PHONE,null);
        String mdp = sharedPreferences.getString(KEY_PASSWORD,null);
        Toast.makeText(activity_login.this, "phone : "+p+"  mdp: "+mdp+"", Toast.LENGTH_SHORT).show();

    }


}