package com.example.meditake.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import com.example.meditake.R;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.CategorieMedicamentDao;
import com.example.meditake.database.entities.CategorieMedicament;
import com.example.meditake.database.entities.Utilisateur;
import com.example.meditake.internet.NetworkChangeListener;

import com.example.meditake.database.dto.UtilisateurLogin;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    EditText mail,password;
    TextView loginButton , forgetPassword;
    Map<String,String> comptes;
    Dialog dialog;
    Dialog internetDialog;
    public static  LoginActivity INSTANCE;
    CircularProgressIndicator mCircularProgressIndicator;
    ProgressDialog progressDialog;

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

        mail = findViewById(R.id.mailEdit);
        password = findViewById(R.id.passwordEdit);
        loginButton = findViewById(R.id.loginButton);
        forgetPassword = findViewById(R.id.forgetPasswordBtn);
        mCircularProgressIndicator = findViewById(R.id.circular_indicator);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  showIsLoggingDialog();
                progressDialog = ProgressDialog.show(LoginActivity.this, "",
                        "Veuillez patienter s'il vous plait...", true);

                String username = mail.getText().toString();
                String pwd = password.getText().toString();

                Call<Utilisateur> utilisateurCall = RetrofitGenerator
                        .getRetrofit()
                        .create(UtilisateurService.class)
                        .login(new UtilisateurLogin(username,pwd));

                utilisateurCall.enqueue(new Callback<Utilisateur>() {
                    @Override
                    public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                        Utilisateur user = response.body();
                        progressDialog.cancel();
                        System.out.println();
                        if (user==null){
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("IDENTIFIANTS INCORRECTS");
                            alertDialog.setIcon(R.drawable.no_internet);
                            alertDialog.setMessage("Veuillez corriger vos identifiants.Puis reessayer.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });

                            alertDialog.show();
                        }else {
                             addCategorieAndMedicamentToDB();
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.clear();
                            editor.putString("mail",user.getLogin());
                            editor.putString("nom",user.getNom());
                            editor.putString("prenom",user.getPrenom());
                            editor.putString("password",user.getMotDePasse());
                            editor.apply();

                            System.out.println("Apres connexion on la ceci est un mail "+user.getLogin() +" haha");
                            Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(myIntent);
                            finish();


                        }
                    }

                    @Override
                    public void onFailure(Call<Utilisateur> call, Throwable t) {
                        progressDialog.cancel();
                        System.out.println("Erreur : "+t.getMessage());
                    }
                });

            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("PAS D'INTERNET");
        alertDialog.setIcon(R.drawable.no_internet);
        alertDialog.setMessage("Veuillez verifier si vous etes connecte a internet!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        alertDialog.show();
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










    void addCategorieAndMedicamentToDB(){
        AppDatabase db=AppDatabase.getDataBase(getApplicationContext());
        CategorieMedicamentDao typeMedicamentDao = db.categorieMedicamentDao();
        typeMedicamentDao.insertAll(new CategorieMedicament(1L,"pillule"), new CategorieMedicament(2L,"Goutes ophtalmiques"), new CategorieMedicament(3L,"injections"));

    }
}

