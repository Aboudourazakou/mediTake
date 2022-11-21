package com.example.meditake.adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.HomeActivity;
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
            String arr[]=day.split(" ");
            holder.qbinding.setDay(arr[1]);
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
        }
    }

    public int getScreenWidth() {

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }
}
