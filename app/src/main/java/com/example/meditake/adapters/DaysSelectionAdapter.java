package com.example.meditake.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.meditake.ui.AddMedicationActivity;
import com.example.meditake.R;

import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "11/25/2022
 "Project name "MediTake
 */
public class DaysSelectionAdapter  extends ArrayAdapter<String>{
    List<String> stringList;
    AddMedicationActivity context;

    public DaysSelectionAdapter(AddMedicationActivity activity, int resources, List<String> reasons) {
        super(activity,resources);
        context=activity;
        stringList=reasons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("i","Affichage list");
        if(convertView==null){
            LayoutInflater i=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=i.inflate(R.layout.checkbox_days_layout,null);

        }
        if(stringList.size()>0){
            String item=stringList.get(position);
            CheckBox checkBox=convertView.findViewById(R.id.jour);
            checkBox.setChecked(false);

            if(context.getDays("").contains(item)){
                checkBox.setChecked(true);
                Log.e("tag",item);
            }
            Log.e("jour",item+" "+context.getDays(""));
            checkBox.setText(item);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    context.getDays(checkBox.getText().toString());
                }
            });


        }
        return  convertView;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    public DaysSelectionAdapter(@NonNull Context context, int resource) {
        super(context, resource);

    }
}