package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.meditake.adapters.MenuItemAdapter;
import com.example.meditake.databinding.ActivityMoreMenuBinding;
import com.example.meditake.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityMoreMenuBindind extends AppCompatActivity {
    List<MenuItem>itemList=new ArrayList<>();
    ActivityMoreMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMoreMenuBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        String[]items_title=getResources().getStringArray(R.array.menuitem);

       /* Integer[]icon={R.drawable.ic_baseline_play_arrow_24,R.drawable.ic_baseline_create_24,R.drawable.ic_baseline_live_tv_24,R.drawable.ic_baseline_pie_chart_24,R.drawable.ic_baseline_notifications_24,
                R.drawable.ic_baseline_help_24,R.drawable.ic_baseline_settings_24};*/

        for(int i=0;i<items_title.length;i++){
            MenuItem item=new MenuItem(items_title[i],1);
            itemList.add(item);
        }
        MenuItemAdapter menuItemAdapter=new MenuItemAdapter(this,R.layout.more_menu_item,itemList);
        binding.menuListView.setAdapter(menuItemAdapter);
    }
}