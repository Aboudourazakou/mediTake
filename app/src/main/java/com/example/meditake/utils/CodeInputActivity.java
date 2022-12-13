
package com.example.meditake.utils;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meditake.ui.NewPasswordActivity;
import com.example.meditake.R;

import java.util.ArrayList;
import java.util.List;

public class CodeInputActivity  extends AppCompatActivity {

    EditText editText1,editText2,editText3,editText4;
    Context context;
    List<String> inputList=new ArrayList<>();
    private  static  Integer NUMBER_FIELDS=0;
    String code="";
    String mail="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_input);
        editText1=findViewById(R.id.codeInputEdittext1);
        editText2=findViewById(R.id.codeInputEdittext2);
        editText3=findViewById(R.id.codeInputEdittext3);
        editText4=findViewById(R.id.codeInputEdittext4);
        TextView textView=findViewById(R.id.message);
        TextView validateBtn=findViewById(R.id.validate);


        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  enteredCode=editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString();
                if(enteredCode.equals(code)){
                           Intent intent=new Intent(CodeInputActivity.this, NewPasswordActivity.class);
                           intent.putExtra("mail",mail);
                           startActivity(intent);
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(CodeInputActivity.this).create();
                    alertDialog.setTitle("Code invalide!");
                    alertDialog.setMessage("Assurez vous d'avoir bien note le code a 4 chiffres envoye au mail que vous avez entre");
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

        context=this;

        Bundle extras=getIntent().getExtras();
        code=extras.getString("code");
        mail=extras.getString("mail");

        textView.setText("Veuillez checker votre mail "+extras.getString("mail")+".Puis entrer le code a 4 chiffres recus");



        editText1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText1.getText().toString().length() == 1)     //size as per your requirement
                {

                    editText1.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    editText2.requestFocus();
                }
                else {



                    editText1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
                    editText1.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editText2.getText().toString().length() == 1)     //size as per your requirement
                {

                    editText2.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    editText3.requestFocus();
                }
                else {

                    editText2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
                    editText1.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText3.getText().toString().length() == 1)     //size as per your requirement
                {

                    editText3.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    editText4.requestFocus();
                }
                else {
                    editText3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
                    editText2.requestFocus();

                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText4.getText().toString().length() == 1)     //size as per your requirement
                {

                    editText4.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

                }
                else {
                    editText4.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
                    editText3.requestFocus();

                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
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
