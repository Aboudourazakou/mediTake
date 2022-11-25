package com.example.meditake.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.HomeActivity;
import com.example.meditake.databinding.SegmentMedicamentHomeListBinding;
import com.example.meditake.models.Rappel;

import java.util.ArrayList;
import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "11/21/2022
 "Project name "MediTake
 */
public class RappelAdapter extends RecyclerView.Adapter<RappelAdapter.medicamentViewHolder> {

    List<Rappel>rappelList=new ArrayList<>();
    HomeActivity context;
    public RappelAdapter(List<Rappel> rappel,HomeActivity homeActivity) {
        this.rappelList=rappel;
        context=homeActivity;
    }

    @NonNull
    @Override
    public medicamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        SegmentMedicamentHomeListBinding binding=SegmentMedicamentHomeListBinding.inflate(layoutInflater,parent,false);
        return new RappelAdapter.medicamentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull medicamentViewHolder holder, int position) {
        Rappel rappel=rappelList.get(position);
        holder.binding.setRappel(rappel);

        for (int i = 0; i <rappel.getRapportList().size() ; i++) {
             if(rappel.getRapportList().get(i).getStatut().equals("pris")){
                 holder.binding.takenText.setVisibility(View.VISIBLE);
                 holder.binding.takenText.setText(rappel.getRapportList().get(i).getMessage());

             }
            if(rappel.getRapportList().get(i).getStatut().equals("reprogramme")){
                holder.binding.rescheduledText.setVisibility(View.VISIBLE);
                holder.binding.rescheduledText.setText(rappel.getRapportList().get(i).getMessage());

            }
            if(rappel.getRapportList().get(i).getStatut().equals("ignore")){
                holder.binding.ignoredText.setVisibility(View.VISIBLE);
                holder.binding.ignoredText.setText(rappel.getRapportList().get(i).getMessage());

            }

        }

    }

    @Override
    public int getItemCount() {
        return rappelList.size();
    }

    public class medicamentViewHolder extends  RecyclerView.ViewHolder {
        SegmentMedicamentHomeListBinding binding;
        public medicamentViewHolder(@NonNull SegmentMedicamentHomeListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            binding.rappelCardWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  context.prendreMedicament(rappelList.get(getAdapterPosition()));
                }
            });


        }
    }
}
