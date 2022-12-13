package com.example.meditake.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meditake.R;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;
import com.example.meditake.utils.CodeInputActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText mail;
    Button verify_mail;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mail = findViewById(R.id.get_mail);
        verify_mail = findViewById(R.id.verify_mail);

        verify_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   verifyMail(mail.getText().toString());

            }
        });
    }

    public void verifyMail(String mail){
        UtilisateurService service = RetrofitGenerator.getRetrofit().create(UtilisateurService.class);
        progressDialog = ProgressDialog.show(ChangePasswordActivity.this, "",
                "Veuillez patienter s'il vous plait...", true);
        Call<String> call = service.verifyMail(mail);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               String s = response.body();
                System.out.println("Voci le mail : "+s);
                progressDialog.cancel();
               if("no".equals(s)){

                   AlertDialog alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this).create();
                   alertDialog.setTitle("Mail invalide");
                   alertDialog.setMessage("Ce mail n'existe pas . Veuillez mettre un autre mail");
                   alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   dialogInterface.dismiss();
                               }
                           });

                   alertDialog.show();
               }
               else{
                   Intent intent=new Intent(ChangePasswordActivity.this, CodeInputActivity.class);
                   intent.putExtra("code",response.body());
                   intent.putExtra("mail",mail);
                   startActivity(intent);
               }



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.cancel();
                System.out.println("Verify mail Failure");
                System.out.println(t.getMessage());
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}