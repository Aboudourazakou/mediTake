package com.example.meditake;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meditake.adapters.MedicamentAdapter;
import com.example.meditake.adapters.ProgrammeAdapter;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.databinding.FragmentAddMedicamentBinding;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMedicamentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMedicamentFragment extends Fragment {

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

    public AddMedicamentFragment() {
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
    public static AddMedicamentFragment newInstance(String param1, String param2) {
        AddMedicamentFragment fragment = new AddMedicamentFragment();
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


        for (int i = 0; i <7 ; i++) {
              Medicament medicament=new Medicament();
              medicament.setNom("Abufene");
              medicamentList.add(medicament);

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
}