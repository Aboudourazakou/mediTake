package com.example.meditake;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meditake.adapters.RapportAdapter;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dto.RapportDto;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.Rapport;
import com.example.meditake.databinding.FragmentJournalBinding;
import com.example.meditake.services.RapportService;
import com.example.meditake.services.RetrofitGenerator;

import java.util.ArrayList;
import java.util.List;

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

            r.setRappel(rappel);
        });

        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_journal, container, false);


        binding.journalRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.journalRecycle.setAdapter(new RapportAdapter(rapports,this));

        binding.sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RapportService rapportService = RetrofitGenerator.getRetrofit().create(RapportService.class);
                List<RapportDto> rapportDtos = new ArrayList<>();

                rapports.forEach(r->{
                    RapportDto rapportDto= new RapportDto();
                    rapportDto.setDate(r.getDate());
                    rapportDto.setMessage(r.getMessage());
                    rapportDto.setStatut(r.getStatut());
                    rapportDto.setNomMedicament(r.getRappel().getMedicament().getNom());
                    rapportDtos.add(rapportDto);
                });
                rapportService.sendReportToMail(rapportDtos);
            }
        });

        return binding.getRoot();
    }
}