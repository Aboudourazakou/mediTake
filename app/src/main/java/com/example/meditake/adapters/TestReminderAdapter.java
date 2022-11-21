/*
package com.example.meditake.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditake.databinding.SegmentMedicamentHomeListBinding;
import com.example.meditake.models.TestReminder;

import java.util.List;

*/
/***
 "Created by  TETEREOU Aboudourazakou on "11/19/2022
 "Project name "MediTake
 *//*

public class TestReminderAdapter extends RecyclerView.Adapter<TestReminderAdapter.reminderViewHolder> {
    List<TestReminder>testReminderList;

    public TestReminderAdapter(List<TestReminder> testReminderList) {
        this.testReminderList=testReminderList;
    }

    @NonNull
    @Override
    public reminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        SegmentMedicamentHomeListBinding qb=SegmentMedicamentHomeListBinding.inflate(layoutInflater,parent,false);
        return new reminderViewHolder(qb);
    }

    @Override
    public void onBindViewHolder(@NonNull reminderViewHolder holder, int position) {
        TestReminder testReminder=testReminderList.get(position);
        holder.binding.setTest(testReminder);

    }

    @Override
    public int getItemCount() {
        return testReminderList.size();
    }

    public class reminderViewHolder extends RecyclerView.ViewHolder {
        SegmentMedicamentHomeListBinding binding;
        public reminderViewHolder(@NonNull SegmentMedicamentHomeListBinding binding) {
            super(binding.getRoot());
        }
    }
}
*/
