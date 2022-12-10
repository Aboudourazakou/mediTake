package com.example.meditake.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.JournalFragment;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Rapport;
import com.example.meditake.databinding.RapportItemViewBinding;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/***
 "Created by godwin Kvg on "12/7/2022
 "Project name "MediTake
 */
public class RapportAdapter extends RecyclerView.Adapter<RapportAdapter.rapportHolder> {
    List<Rapport> rapportList =new ArrayList<>();

    public RapportAdapter(List<Rapport> rapports, JournalFragment journalFragment){
        rapportList = rapports;
    }

    @NonNull
    @Override
    public rapportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        RapportItemViewBinding binding = RapportItemViewBinding.inflate(layoutInflater,parent,false);

        return new rapportHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull rapportHolder holder, int position) {
        Rapport rapport = rapportList.get(position);

        InputStream is = new ByteArrayInputStream(rapport.getRappel().getMedicament().getImage());
        Bitmap bmp = BitmapFactory.decodeStream(is);

        holder.binding.medicamentImg.setImageBitmap(bmp);

        holder.binding.medNom.setText(rapport.getRappel().getMedicament().getNom());
        holder.binding.medInfo.setText(rapport.getMessage());

        holder.binding.date.setText(new SimpleDateFormat("EEE d MMM", Locale.FRANCE).format(rapport.getDate()));

        holder.binding.status.setText("Status : "+rapport.getStatut());
    }

    @Override
    public int getItemCount() {
        return rapportList.size();
    }

    public class rapportHolder extends RecyclerView.ViewHolder{
        RapportItemViewBinding binding;
        public rapportHolder(@NonNull RapportItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}