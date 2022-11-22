package com.example.meditake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.HomeActivity;
import com.example.meditake.databinding.DaysItemBinding;
import com.example.meditake.databinding.RappelsListWrapperBinding;
import com.example.meditake.databinding.SegmentMedicamentHomeListBinding;
import com.example.meditake.models.Rappel;

import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "11/21/2022
 "Project name "MediTake
 */
public class RappelAdapter  extends RecyclerView.Adapter<RappelAdapter.rappelViewHolder> {
    List<Rappel> rappelList;
    HomeActivity context;

    public RappelAdapter(List<Rappel> rappelList, HomeActivity homeActivity) {
        this.rappelList=rappelList;
        context=homeActivity;
    }

    @NonNull
    @Override
    public rappelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
       RappelsListWrapperBinding binding=RappelsListWrapperBinding.inflate(layoutInflater,parent,false);
        return new RappelAdapter.rappelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull rappelViewHolder holder, int position) {
       // holder.binding.setRappel(rappelList.get(position));
        holder.binding.recyclerViewMedicament.setLayoutManager(new LinearLayoutManager(context));
        holder.binding.recyclerViewMedicament.setAdapter(new RappelMedicamentAdapter());

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class rappelViewHolder extends  RecyclerView.ViewHolder {
        RappelsListWrapperBinding binding;
        public rappelViewHolder(@NonNull RappelsListWrapperBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
