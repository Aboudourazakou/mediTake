package com.example.meditake;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meditake.database.AppDatabase;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText mail;
    Button verify_mail;

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

        Call<String> call = service.verifyMail(mail);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               String s = response.body();
                System.out.println("Voci le mail : "+s);
               if("yes".equals(s)){

                   Toast.makeText(getApplicationContext(), "mail existe:  "+s, Toast.LENGTH_LONG).show();

               }
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



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Verify mail Failure");
                System.out.println(t.getMessage());
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });

    }
}