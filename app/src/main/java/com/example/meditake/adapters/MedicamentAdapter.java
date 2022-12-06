package com.example.meditake.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/***
 "Created by  TETEREOU Aboudourazakou on "12/6/2022
 "Project name "MediTake
 */
public class MedicamentAdapter  extends RecyclerView.Adapter<MedicamentAdapter.medicamentHolder> {
    @NonNull
    @Override
    public medicamentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull medicamentHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class medicamentHolder extends RecyclerView.ViewHolder {
        public medicamentHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
