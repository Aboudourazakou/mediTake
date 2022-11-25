package com.example.meditake.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.meditake.HomeActivity;
import com.example.meditake.R;
import com.example.meditake.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "11/25/2022
 "Project name "MediTake
 */
public class IgnoreReasonAdapter  extends ArrayAdapter<String>{
   List<String> stringList;
   HomeActivity context;

    public IgnoreReasonAdapter(HomeActivity activity, int resources, List<String> reasons) {
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
            convertView=i.inflate(R.layout.reasons_radio_element,null);

        }
        if(stringList.size()>0){
          String item=stringList.get(position);
            RadioButton radioButton=convertView.findViewById(R.id.medIgnoreReason);

            radioButton.setText(item);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.setReasonOfIgnore(radioButton.getText().toString());
                }
            });

        }
        return  convertView;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    public IgnoreReasonAdapter(@NonNull Context context, int resource) {
        super(context, resource);

    }
}