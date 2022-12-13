package com.example.meditake.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meditake.R;
import com.example.meditake.adapters.RapportAdapter;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.dao.RappelDao;
import com.example.meditake.database.dao.RapportDao;
import com.example.meditake.database.dto.MailObject;
import com.example.meditake.database.dto.RapportDto;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.Rapport;
import com.example.meditake.databinding.FragmentJournalBinding;
import com.example.meditake.services.RapportService;
import com.example.meditake.services.RetrofitGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JournalFragment extends Fragment {
    FragmentJournalBinding binding;
    List<Rapport> rapportList = new ArrayList<>();
    public JournalFragment() {
        // Required empty public constructor
    }

    public static JournalFragment newInstance(String param1, String param2) {
        JournalFragment fragment = new JournalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        AppDatabase db = AppDatabase.getDataBase(getContext());

        List<Rapport> rapports = db.rapportDao().getAll();

        rapports.forEach(r->{
            Rappel rappel = db.rappelDao().getById(r.getIdRappel());

            Medicament medicament = db.medicamentDao().getById(rappel.getMedicamentId());
            rappel.setMedicament(medicament);

            Log.e("Rappel", "Rappel "+rappel);
            Log.e("Medicament", "Medicament "+medicament);

            r.setRappel(rappel);
        });

        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_journal, container, false);


        binding.journalRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.journalRecycle.setAdapter(new RapportAdapter(rapports,this));

        binding.sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

                alertDialog.setIcon(R.drawable.no_internet);
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View v = inflater.inflate(R.layout.mail_to_edittext, null);
                TextView sendBtn=v.findViewById(R.id.sendBtn);
                EditText emailEditText=v.findViewById(R.id.mailEdit);
                sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email=emailEditText.getText().toString();
                        sendReports(email);
                        alertDialog.cancel();
                    }
                });
                alertDialog.setView(v);
              alertDialog.show();


            }
        });

        return binding.getRoot();
    }





    public  void sendReports(String email){
        AppDatabase db=AppDatabase.getDataBase(getActivity().getApplicationContext());
        MailObject mail  = new MailObject();
        mail.setToEmail(email);
        mail.setSubject("Rapport sur la prise de medicament");
        RapportDao rapportDao=db.rapportDao();
        RappelDao rappelDao=db.rappelDao();
        MedicamentDao medicamentDao=db.medicamentDao();


         List<Rappel>rappelList=rappelDao.getAll();
         List<RapportDto>rapportDtoList=new ArrayList<>();

        for (Rappel rappel:rappelList) {
            Medicament medicament=medicamentDao.getById(rappel.getMedicamentId());
            List<Rapport>rapportList=rapportDao.findRapportByIdRappel(rappel.getId());
            for (Rapport rapport:rapportList) {
                RapportDto rapportDto=new RapportDto();
                rapportDto.setNomMedicament(medicament.getNom());
                rapportDto.setStatut(rapport.getStatut());
                rapportDto.setDate(rapport.getDate());
                rapportDto.setMessage(rapport.getMessage());
                rapportDtoList.add(rapportDto);

            }

        }
        mail.setRapports(rapportDtoList);

        Call<Void> sendReportToMail = RetrofitGenerator
                .getRetrofit()
                .create(RapportService.class)
                .sendReportToMail(mail);

       sendReportToMail.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                System.out.println("Erreur : "+t.getMessage());
            }
        });

    }
}