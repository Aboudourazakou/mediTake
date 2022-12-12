package com.example.meditake.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.meditake.HomeActivity;
import com.example.meditake.R;

/***
 "Created by  TETEREOU Aboudourazakou on "12/12/2022
 "Project name "MediTake
 */
public class RappelRenouvellementMedicamentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context, HomeActivity.class);
        System.out.println("Le renouvellement est la monsieur ");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,20,intent1,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"renouvelleMedicament")
                .setSmallIcon(R.drawable.logo ).setContentTitle("Renouvellement de medicament")
                .setContentText("Bonjour!Veuillez renouveller le medicament.Visiter votre la Rubrique de suivi pour retrouver le medicament a renouveler")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent) ;
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(11,builder.build());

    }
}
