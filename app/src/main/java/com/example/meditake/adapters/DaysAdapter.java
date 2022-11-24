package com.example.meditake.adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.HomeActivity;
import com.example.meditake.R;
import com.example.meditake.databinding.DaysItemBinding;

import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "11/21/2022
 "Project name "MediTake
 */
public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.daysViewHolder>{
    List<String>dayList;
    Boolean isWeekDays;
    HomeActivity activity;
    int activeDay=RecyclerView.NO_POSITION;


    public DaysAdapter(List<String> daysList, HomeActivity homeActivity, Boolean isWeekDays) {
        this.dayList=daysList;
        this.isWeekDays=isWeekDays;
        activity=homeActivity;
    }

    @NonNull
    @Override
    public daysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        DaysItemBinding binding=DaysItemBinding.inflate(layoutInflater);

        return new DaysAdapter.daysViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull daysViewHolder holder, int position) {
        String day=dayList.get(position);

        if(!isWeekDays){

            holder.qbinding.setDay(getDay(day,1));
           if(activeDay==position) {
                    holder.qbinding.idDay.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.round_element));
                    holder.qbinding.idDay.setTextColor(ContextCompat.getColor(activity, R.color.white));
           }
           else {
               holder.qbinding.idDay.setTextColor(ContextCompat.getColor(activity, R.color.black));
               holder.qbinding.idDay.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.transparent_background));

           }
        }
        else  holder.qbinding.setDay(day);
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class daysViewHolder  extends  RecyclerView.ViewHolder{
        DaysItemBinding qbinding;
        public daysViewHolder(@NonNull DaysItemBinding qbinding) {

            super(qbinding.getRoot());
            this.qbinding=qbinding;

          if(!isWeekDays){
              this.qbinding.idDay.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    int previous=activeDay;
                    activeDay=getAdapterPosition();
                    activity.getBinding().day.setText(dayList.get(activeDay));
                    notifyItemChanged(previous);
                    notifyItemChanged(activeDay);
                    activity.simulateDb(getDay(dayList.get(activeDay),0));

                  }
              });
          }



    }
    }

    public  String getDay(String day,int i){
        String arr[]=day.split(" ");
        return  arr[i];
    }


}
