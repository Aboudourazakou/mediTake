package com.example.meditake.internet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
;

import com.example.meditake.activity_login;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class NetworkChangeListener extends BroadcastReceiver {


    Context context;
    activity_login login;
    public  static  boolean internetAvalaible;

    MediaPlayer mediaPlayer;


    private  List<Integer>isAlreadySent;//I noticed internet avalaible executes itself many times,so message sent are duplicated.I decide so,to limit it
    @Override

    public void onReceive(Context context, Intent intent) {
        this.context=context;

        if(!InternetConnectivityChecker.isConnectedToInternet(context)){


           if(activity_login.getInstance()!=null)activity_login.getInstance().showDialog();
           internetAvalaible=false;
        }
        else{

             internetAvalaible=true;
            if(activity_login.getInstance()!=null)activity_login.getInstance().cancelDialog();
        }
        System.out.println("Internet avalaible");

    }










}
