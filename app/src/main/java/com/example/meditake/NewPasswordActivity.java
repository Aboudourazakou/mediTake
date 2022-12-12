package com.example.meditake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.meditake.databinding.ActivityHomeBinding;
import com.example.meditake.databinding.ActivityNewPasswordBinding;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPasswordActivity extends AppCompatActivity {
    ActivityNewPasswordBinding binding;
    String mail="";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.warningText.setVisibility(View.GONE);
        Bundle extras=getIntent().getExtras();
        mail=extras.getString("mail");

        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.newPassword.getText().toString().equals(binding.confirmNewPassword.getText().toString())) {
                    binding.warningText.setVisibility(View.VISIBLE);
                }
                else{
                    progressDialog = ProgressDialog.show(NewPasswordActivity.this, "",
                            "Veuillez patienter s'il vous plait...", true);

                    UtilisateurService service = RetrofitGenerator.getRetrofit().create(UtilisateurService.class);

                    Call<String> call = service.changePassword(mail,binding.newPassword.getText().toString());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String s = response.body();
                            progressDialog.cancel();
                            if("done".equals(s)){

                                AlertDialog alertDialog = new AlertDialog.Builder(NewPasswordActivity.this).create();
                                alertDialog.setTitle("SUCCESS");
                                alertDialog.setMessage("Votre mot de passe a ete change avec succes.Veuillez revenir en arriere pour vous connecter");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });

                                alertDialog.show();
                                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        Intent intent=new Intent(NewPasswordActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialogInterface) {
                                        Intent intent=new Intent(NewPasswordActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
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

            }
        });

    }



        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
                Intent intent=new Intent(NewPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            return super.onKeyDown(keyCode, event);
        }

    }
