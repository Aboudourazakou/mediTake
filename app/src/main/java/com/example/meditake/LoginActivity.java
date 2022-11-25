package com.example.meditake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meditake.database.dto.UtilisateurLogin;
import com.example.meditake.database.entities.Medecin;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    EditText loginphone,loginpassword;
    TextView loginButton;
    Map<String,String> comptes;
    CircularProgressIndicator mCircularProgressIndicator;

    SharedPreferences sharedPreferences;
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

                        dialog("Connexion échouée","Mot de passe Incorrecte");

                    }

                } else{

                    dialog("Connexion échouée","numero de telephone incorrecte");
                }
            }
        });


    }

    private void dialog(String title,String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void DoLogin(String phone , String psswrd){
        editor = sharedPreferences.edit();
        editor.putString(KEY_PHONE,phone);
        editor.putString(KEY_PASSWORD,psswrd);
        editor.putBoolean("hasLoggedIn",true);
        editor.commit();

        mCircularProgressIndicator.setVisibility(View.VISIBLE);
        Intent myIntent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(myIntent);

    }



    void httpCallToBackendService(){
        UtilisateurService service = RetrofitGenerator.getRetrofit().create(UtilisateurService.class);

        Call<Medecin> call = service.logMedecinIn(new UtilisateurLogin("username1","password1"));

        call.enqueue(new Callback<Medecin>() {
            @Override
            public void onResponse(Call<Medecin> call, Response<Medecin> response) {
                int statusCode = response.code();
                Medecin medecin = response.body();

                System.out.println(medecin);
            }

            @Override
            public void onFailure(Call<Medecin> call, Throwable t) {
                // Log error here since request failed

                System.out.println(t.getMessage());
            }
        });

        Call<List<Medecin>> medecinsCall = service.getAll();

        medecinsCall.enqueue(new Callback<List<Medecin>>() {
            @Override
            public void onResponse(Call<List<Medecin>> call, Response<List<Medecin>> response) {
                List<Medecin> medecins = response.body();

                medecins.forEach(p-> Log.e("REQUETE",String.valueOf(p)));
            }

            @Override
            public void onFailure(Call<List<Medecin>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}