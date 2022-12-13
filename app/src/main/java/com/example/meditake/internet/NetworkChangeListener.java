package com.example.meditake.internet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
;

import com.example.meditake.ui.LoginActivity;



import java.util.List;



public class NetworkChangeListener extends BroadcastReceiver {


    Context context;
    LoginActivity login;
    public  static  boolean internetAvalaible;

    MediaPlayer mediaPlayer;


    private  List<Integer>isAlreadySent;//I noticed internet avalaible executes itself many times,so message sent are duplicated.I decide so,to limit it
    @Override

    public void onReceive(Context context, Intent intent) {
        this.context=context;

        if(!InternetConnectivityChecker.isConnectedToInternet(context)){


           if(LoginActivity.getInstance()!=null)LoginActivity.getInstance().showDialog();
           internetAvalaible=false;
        }
        else{

             internetAvalaible=true;
            if(LoginActivity.getInstance()!=null)LoginActivity.getInstance().cancelDialog();
        }
        System.out.println("Internet avalaible");

    }






}
