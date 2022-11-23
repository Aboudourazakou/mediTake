package com.example.meditake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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

    @SuppressLint({"WrongViewCast", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginphone =  findViewById(R.id.phoneEdit);
        loginpassword = findViewById(R.id.passwordEdit);
        loginButton = findViewById(R.id.loginButton);
        mCircularProgressIndicator = findViewById(R.id.circular_indicator);

        comptes = new HashMap<String,String>(){{
            put("06754","admin");
            put("09876","user");
        }};


      /*  mCircularProgressIndicator.setIndicatorDirection(CircularProgressIndicator.INDICATOR_DIRECTION_COUNTERCLOCKWISE);
        mCircularProgressIndicator.setIndicatorSize(100);
        mCircularProgressIndicator.setTrackThickness(10);
        mCircularProgressIndicator.setIndeterminate(false);
        mCircularProgressIndicator.setIndicatorColor(R.color.coton);*/

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = loginphone.getText().toString();
                String password = loginpassword.getText().toString();

                if(comptes.containsKey(phone)){

                    if(password.equals(comptes.get(phone))){

                        mCircularProgressIndicator.setVisibility(View.VISIBLE);
                        Intent myIntent = new Intent(activity_login.this,HomeActivity.class);
                        startActivity(myIntent);
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
}