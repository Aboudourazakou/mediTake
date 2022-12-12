package com.example.meditake.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.HomeFragment;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.databinding.RappelsListWrapperBinding;

import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "11/21/2022
 "Project name "MediTake
 */
public class ProgrammeAdapter extends RecyclerView.Adapter<ProgrammeAdapter.programViewHolder> {
    List<Programme> programmeList;
    HomeFragment context;



    public ProgrammeAdapter(List<Programme> programmeList, HomeFragment homeFragment) {
        this.programmeList=programmeList;
        context=homeFragment;
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
        Programme programm=programmeList.get(position);
        holder.binding.time.setText(programm.getHeure()+":"+programm.getMinutes());
        holder.binding.recyclerViewMedicament.setLayoutManager(new LinearLayoutManager(context.getContext()));
        holder.binding.recyclerViewMedicament.setAdapter(new RappelAdapter(programmeList.get(position).getRappelList(),context));

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
