package com.example.meditake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.databinding.RappelsListWrapperBinding;
import com.example.meditake.databinding.SegmentMedicamentHomeListBinding;

/***
 "Created by  TETEREOU Aboudourazakou on "11/21/2022
 "Project name "MediTake
 */
public class RappelMedicamentAdapter  extends RecyclerView.Adapter<RappelMedicamentAdapter.medicamentViewHolder> {
    @NonNull
    @Override
    public medicamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        SegmentMedicamentHomeListBinding binding=SegmentMedicamentHomeListBinding.inflate(layoutInflater,parent,false);
        return new RappelMedicamentAdapter.medicamentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull medicamentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class medicamentViewHolder extends  RecyclerView.ViewHolder {
        SegmentMedicamentHomeListBinding binding;
        public medicamentViewHolder(@NonNull SegmentMedicamentHomeListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


        }
    }
}
