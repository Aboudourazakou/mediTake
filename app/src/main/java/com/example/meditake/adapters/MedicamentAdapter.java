package com.example.meditake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.AddMedicamentFragment;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.databinding.MedicamentItemBinding;

import java.util.ArrayList;
import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "12/6/2022
 "Project name "MediTake
 */
public class MedicamentAdapter  extends RecyclerView.Adapter<MedicamentAdapter.medicamentHolder> {
    List<Medicament> medicamentList=new ArrayList<>();

    public MedicamentAdapter(List<Medicament> medicamentList, AddMedicamentFragment addMedicamentFragment) {
        this.medicamentList=medicamentList;
    }

    @NonNull
    @Override
    public medicamentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        MedicamentItemBinding binding=MedicamentItemBinding.inflate(layoutInflater,parent,false);

        return new MedicamentAdapter.medicamentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull medicamentHolder holder, int position) {
        System.out.println("ON AFFICHE TOUT");
             Medicament medicament=medicamentList.get(position);
             holder.binding.setMedicament(medicament);
    }

    @Override
    public int getItemCount() {
        return medicamentList.size();
    }

    public class medicamentHolder extends RecyclerView.ViewHolder {
        MedicamentItemBinding binding;
        public medicamentHolder(@NonNull MedicamentItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
