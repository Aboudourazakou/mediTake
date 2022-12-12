package com.example.meditake;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.meditake.adapters.DaysAdapter;
import com.example.meditake.adapters.IgnoreReasonAdapter;
import com.example.meditake.adapters.ProgrammeAdapter;
import com.example.meditake.alarm.RappelPriseMedicamentReceiver;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.Rapport;
import com.example.meditake.databinding.FragmentHomeBinding;
import com.example.meditake.databinding.IgnoreMessageDialogBinding;
import com.example.meditake.databinding.RappelClickDialogBinding;
import com.example.meditake.databinding.RescheduleReminderDialogBinding;

import com.example.meditake.viewmodels.HomeActivityViewModel;
import com.example.meditake.viewmodels.HomeFragmentViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<String> daysList=new ArrayList<>();
    List<Programme> programmList =new ArrayList<>();
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    HomeActivityViewModel homeActivityViewModel;
    String today;
    Dialog dialog,dialog2;
    Rappel rappel;
    FragmentHomeBinding binding;
    HomeActivity activity;
    HomeFragmentViewModel homeFragmentViewModel;
    String selectedDay;
    String fullSelectedDay;
    ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        binding=DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        View view=binding.getRoot();
        cancelAlarm();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        binding.patientName.setText(sharedPreferences.getString("nom","")+" "+sharedPreferences.getString("prenom",""));
        init();
        homeFragmentViewModel= new ViewModelProvider(this).get(HomeFragmentViewModel.class);
       homeFragmentViewModel.setHomeFragment(this);
       homeFragmentViewModel.getUpdateMissedPills();


       homeFragmentViewModel.getAllPrograms().observe(getViewLifecycleOwner(), new Observer<List<Programme>>() {
            @Override
            public void onChanged(List<Programme> programmeList) {
                System.out.println("LISTE FINALE DE VIEW MODEL");
                System.out.println(programmeList);
                programmList=programmeList;
                setPrograms();


            }
        });


        return view;
    }
    public void createAlarm(int hour,int minutes,int requestCode) {

        alarmManager=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(getContext(), RappelPriseMedicamentReceiver.class);
        // context variable contains your `Context`
        AlarmManager mgrAlarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        pendingIntent= PendingIntent.getBroadcast(getContext(),requestCode,intent,0);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minutes);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), requestCode, intent, 0);

            mgrAlarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent);

            intentArray.add(pendingIntent);





    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence sequence="TakingMedicamentChannel";
            String description="Ce canal cree les notifications";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("takemedicament",sequence,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }


    private void cancelAlarm(){
        Intent intent=new Intent(getContext(), RappelPriseMedicamentReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(getContext(),0,intent,0);
        if(alarmManager==null){
            alarmManager= (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);

    }

    public FragmentHomeBinding getBinding(){
        return  binding;
    }




    public  void prendreMedicament(Rappel rappel){
        showDialog(rappel);
    }
    public  void showDialog(Rappel rappel){

        this.rappel=rappel;
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        RappelClickDialogBinding dialogBinding= DataBindingUtil.inflate(layoutInflater,R.layout.rappel_click_dialog,null,false);
        dialogBinding.setRappel(rappel);
        System.out.println(rappel.getMedicament().getNom()+"ocrmed");

        this.dialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        //We have added a title in the custom layout. So let's disable the default title.
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        this.dialog.setCancelable(true);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //Mention the name of the layout of your custom dialog.
        this.dialog.setContentView(dialogBinding.getRoot());

           if(rappel.getRapportList().size()>0) {
               Rapport dernierRapport=rappel.getRapportList().get(rappel.getRapportList().size()-1);
               if (dernierRapport != null && dernierRapport.getStatut().equals("pris")) {

                   dialogBinding.takePillBtn.setVisibility(View.GONE);
                   dialogBinding.takePillText.setVisibility(View.GONE);
                   dialogBinding.nottakePillBtn.setVisibility(View.VISIBLE);
                   dialogBinding.nottakePillText.setVisibility(View.VISIBLE);
               } else if (dernierRapport != null && dernierRapport.getStatut().equals("ignore")) {
                   dialogBinding.ignorePillBtn.setVisibility(View.GONE);
                   dialogBinding.ignoreText.setVisibility(View.GONE);
                   dialogBinding.notIgnorePillBtn.setVisibility(View.VISIBLE);
                   dialogBinding.notIgnoreText.setVisibility(View.VISIBLE);
               } else if (dernierRapport != null && dernierRapport.getStatut().equals("pris")) {

               }
           }




        dialogBinding.ignorePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rapport rapport=new Rapport();
                rapport.setDate(new Date().getTime());
                rapport.setStatut("ignore");
                rapport.setIdRappel(rappel.getId());
                showDialogForIgnoreMessage(rappel);


            }
        });


        dialogBinding.takePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rapport rapport=new Rapport();
                rapport.setDate(new Date().getTime());
                rapport.setStatut("pris");
                rapport.setMessage("Le pilule a ete pris le "+new SimpleDateFormat("EEE d MMM h:mm a", Locale.FRANCE).format(Calendar.getInstance().getTime()));
                rapport.setIdRappel(rappel.getId());
                rappel.setLastTimeTaken(new SimpleDateFormat("EEE d MMM h:mm a", Locale.FRANCE).format(Calendar.getInstance().getTime()));
                homeFragmentViewModel.saveRapport(rapport);
                Medicament medicament=rappel.getMedicament();
                medicament.setQte((int) (medicament.getQte()-rappel.getQtePilule()));
                homeFragmentViewModel.updateMedicament(medicament);
                homeFragmentViewModel.updateRappel(rappel);
                dialog.cancel();
            }
        });

        dialogBinding.reschedulePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogForReminderRescheduling(rappel);

            }
        });
        dialogBinding.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragmentViewModel.deleteProgramme(rappel.getProgrammeId());
                homeFragmentViewModel.getProgramsFromRoomDb();
                dialog.cancel();
            }
        });

        dialog.show();
    }




    public  void  showDialogForIgnoreMessage(Rappel rappel){
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        IgnoreMessageDialogBinding dialogBinding= DataBindingUtil.inflate(layoutInflater,R.layout.ignore_message_dialog,null,false);

        Dialog dialog2 = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        String[]items=getResources().getStringArray(R.array.reasons);

        IgnoreReasonAdapter ignoreReasonAdapter=new IgnoreReasonAdapter(this,R.layout.reasons_radio_element, Arrays.asList(items));
        dialogBinding.reasonListView.setAdapter(ignoreReasonAdapter);


        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //Mention the name of the layout of your custom dialog.
        dialog2.setContentView(dialogBinding.getRoot());
        Window window = dialog2.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        this.dialog2=dialog2;
        dialog2.show();


    }

    public  void setReasonOfIgnore(String reason){
        Rapport rapport=createRapport();
        rappel.getRapportList().add(rapport);
        rapport.setStatut("ignore");
        rapport.setMessage(reason);
        rapport.setIdRappel(rappel.getId());
        homeFragmentViewModel.saveRapport(rapport);
        dialog2.cancel();

    }
    public  void   showDialogForReminderRescheduling(Rappel rappel){
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        RescheduleReminderDialogBinding dialogBinding= DataBindingUtil.inflate(layoutInflater,R.layout.reschedule_reminder_dialog,null,false);

        Dialog dialog2 = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //Mention the name of the layout of your custom dialog.
        dialog2.setContentView(dialogBinding.getRoot());
        Window window = dialog2.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog2.show();
        EditText editText1=dialogBinding.codeInputEdittext1;
        EditText editText2=dialogBinding.codeInputEdittext2;
        EditText editText3=dialogBinding.codeInputEdittext3;
        EditText editText4=dialogBinding.codeInputEdittext4;
        Context context=getContext();

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

        dialogBinding.reschedulePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int h1=Integer.parseInt(editText1.getText().toString());
                int h2=Integer.parseInt(editText2.getText().toString());
                int m1=Integer.parseInt(editText3.getText().toString());
                int m2=Integer.parseInt(editText4.getText().toString());

                Rapport rapport=createRapport();
                rappel.setHeure(h1*10+h2);
                rapport.setIdRappel(rappel.getId());

                rappel.setMinutes(m1*10+m2);
                createAlarm(rappel.getHeure(),rappel.getMinutes(),3+ rappel.getMinutes());
                rapport.setStatut("reprogramme");
                rapport.setMessage("La pilule a ete reprogramme pour " +rappel.getHeure()+":"+rappel.getMinutes());
                homeFragmentViewModel.saveRapport(rapport);
                homeFragmentViewModel.updateRappel(rappel);
                homeFragmentViewModel.getProgramsFromRoomDb();

                dialog2.cancel();


            }
        });

    }

    public  Rapport createRapport(){
        Rapport rapport=new Rapport();
        rapport.setDate(new Date().getTime());

        return  rapport;
    }

    public  void init(){




        String[]days=getResources().getStringArray(R.array.days);
        LinkedHashMap<String,String> daysAfter=new LinkedHashMap();
        LinkedHashMap<String,String> daysBefore=new LinkedHashMap();
        Calendar calendar=Calendar.getInstance();
        Calendar calendar1=Calendar.getInstance();
        for (int i = 0; i <30 ; i++) {
            calendar.add(Calendar.DATE,i);
            String d= new SimpleDateFormat("EEE d MMM", Locale.FRANCE).format(calendar.getTime());
            daysAfter.put(d,"ici");

            if(i==0) {
                fullSelectedDay=d;
                binding.day.setText(d);
                String[] arr = d.split(" ");
                selectedDay = arr[0].substring(0,3);

            }

            calendar.add(Calendar.DATE,-1*i);

        }
        for (int i = 1; i <=30 ; i++) {
            calendar1.add(Calendar.DATE,-1*i);
            String d= new SimpleDateFormat("EEE d MMM", Locale.FRANCE).format(calendar1.getTime());
            daysBefore.put(d,"kdkd");
            calendar1.add(Calendar.DATE,i);

        }

        System.out.println("Jour j");
        List<String>arrl=new ArrayList<String>(daysBefore.keySet());
        for (String s:arrl
        ) {
            System.out.println(s);
        }





        for(int i=0;i<days.length;i++){

            daysList.add(days[i]);
        }
        List<String>totalDays=new ArrayList<>(daysBefore.keySet());
        Collections.reverse(totalDays);
        totalDays.addAll(daysAfter.keySet());
       // getProgrammes(daysAfter.get(today));
        Boolean isWeekDays=true;
        binding.daysListView.setLayoutManager(new GridLayoutManager(getContext(),7));
        binding.daysListView.setAdapter(new DaysAdapter(daysList,this,isWeekDays));
        isWeekDays=false;
        binding.dayNumberListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.dayNumberListView.setAdapter(new DaysAdapter(totalDays,this,isWeekDays));


        binding.rappelsList.setLayoutManager(new LinearLayoutManager(getContext()));


        createNotificationChannel();

    }



    public  void  setPrograms(){
        binding.rappelsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rappelsList.setAdapter(new ProgrammeAdapter(programmList,this));

    }

    public  String getSelectedDay(){
        return  selectedDay;
    }
    public  void setSelectedDay(String day){
        this.selectedDay=day;
        homeFragmentViewModel.getProgramsFromRoomDb();
    }
    public  void setFullSelectedDay(String d){
        fullSelectedDay=d;
    }
    public  String getFullSelectedDay(){return  fullSelectedDay;}


    public  List<PendingIntent> getPendingIntents(){
        return  intentArray;
    }


}