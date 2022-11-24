package com.example.meditake.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.HomeActivity;
import com.example.meditake.databinding.RappelsListWrapperBinding;
import com.example.meditake.models.Programm;

import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "11/21/2022
 "Project name "MediTake
 */
public class ProgrammeAdapter extends RecyclerView.Adapter<ProgrammeAdapter.programViewHolder> {
    List<Programm> programmeList;
    HomeActivity context;


    public ProgrammeAdapter(List<Programm> programmeList, HomeActivity homeActivity) {
        this.programmeList=programmeList;
        context=homeActivity;
    }


    @NonNull
    @Override
    public programViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
       RappelsListWrapperBinding binding=RappelsListWrapperBinding.inflate(layoutInflater,parent,false);
        return new ProgrammeAdapter.programViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull programViewHolder holder, int position) {
       // holder.binding.setRappel(rappelList.get(position));
        Programm programm=programmeList.get(position);
        holder.binding.time.setText(programm.getHeure()+":"+programm.getMinutes());
        holder.binding.recyclerViewMedicament.setLayoutManager(new LinearLayoutManager(context));
        holder.binding.recyclerViewMedicament.setAdapter(new RappelAdapter(programmeList.get(position).getRappel(),context));

    }

    @Override
    public int getItemCount() {

         return  programmeList.size();

    }

    public class programViewHolder extends  RecyclerView.ViewHolder {
        RappelsListWrapperBinding binding;
        public programViewHolder(@NonNull RappelsListWrapperBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
