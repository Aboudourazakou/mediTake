package com.example.meditake.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.fragment.AddMedicamentFragmentToRappel;
import com.example.meditake.fragment.AddPill;
import com.example.meditake.ui.HomeActivity;
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
    AddMedicamentFragmentToRappel context;


    public MedicamentAdapter(List<Medicament> medicamentList, AddMedicamentFragmentToRappel addMedicamentFragmentToRappel) {
        this.context=addMedicamentFragmentToRappel;
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

        Medicament medicament=medicamentList.get(position);


        Bitmap bitmap = BitmapFactory.decodeByteArray(medicament.getImage(), 0,medicament.getImage().length);

       holder.binding.setMedicament(medicament);

        System.out.println(medicament.getQte()+" Ceci est bien sur la quantite "+medicament.getMinQte());
        holder.binding.iconPill.setImageBitmap(bitmap);
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

            binding.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    HomeActivity.selectedMedicamentId=medicamentList.get(getAdapterPosition()).getId();
                   HomeActivity.homeActivity.replaceFragment(new AddPill());
                }
            });


        }
    }
}
