package com.example.meditake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.preference.PreferenceManager;
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
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.CategorieMedicamentDao;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.entities.CategorieMedicament;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.Utilisateur;
import com.example.meditake.databinding.IgnoreMessageDialogBinding;
import com.example.meditake.databinding.InternetUnavalaibleDialogBinding;
import com.example.meditake.internet.NetworkChangeListener;

import com.example.meditake.database.dto.UtilisateurLogin;
import com.example.meditake.database.entities.Medecin;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    AppDatabase db = null;
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

        db = AppDatabase.getDataBase(LoginActivity.this);


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

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.clear();
                            editor.putString("mail",user.getLogin());
                            editor.putString("nom",user.getNom());
                            editor.putString("prenom",user.getPrenom());
                            editor.putString("password",user.getMotDePasse());
                            editor.apply();

                            addCategorieAndMedicamentToDB();

                            System.out.println("Apres connexion on la ceci est un mail "+user.getLogin() +" haha");
                            Intent myIntent = new Intent(LoginActivity.this,HomeActivity.class);
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
                Intent intent = new Intent(LoginActivity.this,ChangePasswordActivity.class);
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





    void addCategorieAndMedicamentToDB(){

        CategorieMedicamentDao typeMedicamentDao = db.categorieMedicamentDao();
        typeMedicamentDao.insertAll(new CategorieMedicament(1L,"pillule"), new CategorieMedicament(2L,"Goutes ophtalmiques"), new CategorieMedicament(3L,"injections"));
        addMedicaments();

    }

    void addMedicaments(){

        MedicamentDao medicamentDao = db.medicamentDao();
        Medicament medicament=new Medicament();
        medicament.setQte(34);
        medicament.setCategorieId(1);
        medicament.setNom("Paracetamol");

        Drawable drawable=getResources().getDrawable(R.drawable.logo);
        BitmapDrawable bitDw = ((BitmapDrawable) drawable);
        Bitmap bitmap = bitDw.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        medicament.setImage(imageInByte);

        medicamentDao.insertAll(medicament);

    }


}

