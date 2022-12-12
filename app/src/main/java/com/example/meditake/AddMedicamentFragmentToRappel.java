package com.example.meditake;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meditake.adapters.MedicamentAdapter;
import com.example.meditake.alarm.RappelPriseMedicamentReceiver;
import com.example.meditake.alarm.RappelRenouvellementMedicamentReceiver;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.dao.RappelDao;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.databinding.FragmentAddMedicamentBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMedicamentFragmentToRappel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMedicamentFragmentToRappel extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentAddMedicamentBinding binding;
    List<Medicament> medicamentList=new ArrayList<>();
    RecyclerView actifMedicamentRecyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddMedicamentFragmentToRappel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMedicamentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMedicamentFragmentToRappel newInstance(String param1, String param2) {
        AddMedicamentFragmentToRappel fragment = new AddMedicamentFragmentToRappel();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_add_medicament, container, false);
        AppDatabase db = AppDatabase.getDataBase(getActivity().getApplicationContext());
        MedicamentDao medicamentDao=db.medicamentDao();
        RappelDao rappelDao=db.rappelDao();

        List<Rappel>rappelList=rappelDao.getAll();
        createRappelRenouvellementMedicamentNotification();

        for (int i = 0; i <rappelList.size() ; i++) {
                Medicament medicament=medicamentDao.getById(rappelList.get(i).getMedicamentId());
                boolean isFound=false;
            for (int j = 0; j <medicamentList.size() ; j++) {
                   if(medicament.getId()==medicamentList.get(j).getId()){
                       isFound=true;
                   }
            }

            if(!isFound){
                medicamentList.add(medicament);
            }

        }

        binding.actifMedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.actifMedRecyclerView.setAdapter(new MedicamentAdapter(medicamentList,this));


       binding.addMedicament.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getContext(),AddMedicationActivity.class);
               startActivity(intent);
           }
       });

       return  binding.getRoot();
    }

    private  void createRappelRenouvellementMedicamentNotification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence sequence="RenouvelleMedicament";
            String description="Ce canal permet de creer une notification pour alerter le renouvelement de medicaments";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("renouvelleMedicament",sequence,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    public void createAlarm(int jour,int hour,int minutes,int requestCode) {


        Intent intent=new Intent(getContext(), RappelRenouvellementMedicamentReceiver.class);
        // context variable contains your `Context`
        AlarmManager mgrAlarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);


        Calendar calendar=Calendar.getInstance();
        // calendar.set(Calendar.DATE,jour);
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,49);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        System.out.println("Methode invoque");
        PendingIntent pendingIntent= PendingIntent.getBroadcast(getContext(),requestCode,intent,0);

         //pendingIntent = PendingIntent.getBroadcast(getContext(), requestCode, intent, 0);

        mgrAlarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent);







    }
}