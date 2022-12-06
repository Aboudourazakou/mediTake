package com.example.meditake.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.HomeFragment;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.Rapport;
import com.example.meditake.databinding.SegmentMedicamentHomeListBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/***
 "Created by  TETEREOU Aboudourazakou on "11/21/2022
 "Project name "MediTake
 */
public class RappelAdapter extends RecyclerView.Adapter<RappelAdapter.medicamentViewHolder> {

    List<Rappel>rappelList=new ArrayList<>();
    HomeFragment context;

    public RappelAdapter(List<Rappel> rappel,HomeFragment homeFragment) {
        this.rappelList=rappel;
        context=homeFragment;

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
       if(rappel.getRapportList().size()>0){
           Rapport dernierRapport=rappel.getRapportList().get(rappel.getRapportList().size()-1);
        for (int i = 0; i <rappel.getRapportList().size() ; i++) {
            Rapport rapport=rappel.getRapportList().get(i);
            holder.binding.pillIsIgnored.setVisibility(View.GONE);
            holder.binding.pillIsTakenIndicator.setVisibility(View.GONE);
            holder.binding.pillIsMissed.setVisibility(View.GONE);
            holder.binding.pillIsRescheduledIndicator.setVisibility(View.GONE);
            System.out.println(context.getFullSelectedDay() +"Time de day");
            boolean isDisplayable=getTime(rapport.getDate()).equals(context.getFullSelectedDay());
            if (rapport.getStatut().equals("pris") && rapport.getId()==dernierRapport.getId() && isDisplayable ) {
                holder.binding.takenText.setVisibility(View.VISIBLE);
                holder.binding.takenText.setText(rappel.getRapportList().get(i).getMessage());
                holder.binding.pillIsTakenIndicator.setVisibility(View.VISIBLE);

            }
            if (rapport.getStatut().equals("reprogramme") && rapport.getId()==dernierRapport.getId()&& isDisplayable ) {
                holder.binding.rescheduledText.setVisibility(View.VISIBLE);
                holder.binding.rescheduledText.setText(rappel.getRapportList().get(i).getMessage());
                holder.binding.pillIsRescheduledIndicator.setVisibility(View.VISIBLE);

            }
            if (rappel.getRapportList().get(i).getStatut().equals("ignore")&& isDisplayable ) {
                holder.binding.ignoredText.setVisibility(View.VISIBLE);
                holder.binding.ignoredText.setText(rappel.getRapportList().get(i).getMessage());
                holder.binding.pillIsIgnored.setVisibility(View.VISIBLE);

            }
            if (rappel.getRapportList().get(i).getStatut().equals("manque")&& rapport.getId()==dernierRapport.getId()&& isDisplayable  ) {
                holder.binding.isMissedText.setVisibility(View.VISIBLE);
                holder.binding.isMissedText.setText(rappel.getRapportList().get(i).getMessage());
                holder.binding.pillIsMissed.setVisibility(View.VISIBLE);

            }
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

    public  String getTime(long time){
        System.out.println(new SimpleDateFormat("EEE d MMM", Locale.FRANCE).format(time)+"Time du jour");
      return   new SimpleDateFormat("EEE d MMM", Locale.FRANCE).format(time);
    }
}
