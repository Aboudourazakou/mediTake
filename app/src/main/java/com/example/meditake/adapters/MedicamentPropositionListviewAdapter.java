package com.example.meditake.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.meditake.R;
import com.example.meditake.database.entities.Medicament;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class MedicamentPropositionListviewAdapter extends ArrayAdapter<Medicament> {

    LayoutInflater layoutInflater;

    public MedicamentPropositionListviewAdapter(@NonNull Context context, int resource, @NonNull List<Medicament> medicaments) {
        super(context, resource, medicaments);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.medicament_proposition_listview,null,true);
        Medicament medicamentProposition  = getItem(position);

        TextView nom = rowView.findViewById(R.id.medicament_nom);
        ImageView img = rowView.findViewById(R.id.medicament_image);

        nom.setText(medicamentProposition.getNom());
        InputStream is = new ByteArrayInputStream(medicamentProposition.getImage());
        Bitmap bmp = BitmapFactory.decodeStream(is);

        img.setImageBitmap(bmp);

        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null)
            convertView = layoutInflater.inflate(R.layout.medicament_proposition_listview,parent,false);

        Medicament medicamentProposition  = getItem(position);

        TextView nom = convertView.findViewById(R.id.medicament_nom);
        ImageView img = convertView.findViewById(R.id.medicament_image);

        nom.setText(medicamentProposition.getNom());
        img.setImageResource(R.drawable.avatar_user);

        return convertView;
    }
}