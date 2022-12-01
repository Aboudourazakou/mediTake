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

import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import com.example.meditake.adapters.IgnoreReasonAdapter;
import com.example.meditake.database.entities.Utilisateur;
import com.example.meditake.databinding.IgnoreMessageDialogBinding;
import com.example.meditake.databinding.InternetUnavalaibleDialogBinding;
import com.example.meditake.internet.NetworkChangeListener;

import com.example.meditake.database.dto.UtilisateurLogin;
import com.example.meditake.database.entities.Medecin;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    EditText phoneNumber,password;
    TextView loginButton;
    Map<String,String> comptes;
    Dialog dialog;
    Dialog internetDialog;
    public static  LoginActivity INSTANCE;
    CircularProgressIndicator mCircularProgressIndicator;

    SharedPreferences sharedPreferences;
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



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  showIsLoggingDialog();
                System.out.println("qyoi?");
                String username = phoneNumber.getText().toString();
                String pwd = password.getText().toString();

                Call<Utilisateur> utilisateurCall = RetrofitGenerator
                        .getRetrofit()
                        .create(UtilisateurService.class)
                        .login(new UtilisateurLogin(username,pwd));

                utilisateurCall.enqueue(new Callback<Utilisateur>() {
                    @Override
                    public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                        Utilisateur user = response.body();
                        System.out.println();
                        if (user==null){
                            showLogginIncorrectDialog();
                        }else {

                            sharedPreferences.edit().putString(KEY_PHONE,username).putString(KEY_PASSWORD,pwd).putBoolean("hasLoggedIn",true);
                            Intent myIntent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(myIntent);
                            finish();


                        }
                    }

                    @Override
                    public void onFailure(Call<Utilisateur> call, Throwable t) {
                        System.out.println("Erreur : "+t.getMessage());
                    }
                });

            }
        });

    }


    public void showDialog() {
        dialog = new Dialog(LoginActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
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


    @Override
    public  void onStart() {

        super.onStart();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
    }


    public  static LoginActivity getInstance(){return  INSTANCE;}

    public   void cancelDialog(){
        if(internetDialog!=null){
            internetDialog.cancel();
        }
    }




    public  void  showLogginIncorrectDialog() {
            dialog = new Dialog(LoginActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
            //We have added a title in the custom layout. So let's disable the default title.
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
            dialog.setCancelable(true);


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            //Mention the name of the layout of your custom dialog.
            dialog.setContentView(R.layout.incorrect_credentials_dialog);
            Button btn = dialog.findViewById(R.id.skippDialogButtonPhoneIncorrect);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            dialog.show();

        }

    public  void showIsLoggingDialog(){
        dialog = new Dialog(LoginActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        internetDialog=dialog;

        mCircularProgressIndicator.setVisibility(View.VISIBLE);



        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.credentials_verification_dialog);
        dialog.show();
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

