package com.example.meditake.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.meditake.R;
import com.example.meditake.models.MenuItem;

import java.util.List;

/***
 "Created by  TETEREOU Aboudourazakou on "9/22/2022
 "Project name "WeKwiz
 */
public class MenuItemAdapter extends ArrayAdapter<MenuItem> {
    Context context;
    List<MenuItem>itemList;
    public MenuItemAdapter(@NonNull Context context, int resource, @NonNull List<MenuItem> itemList) {
        super(context, resource);
        this.context=context;
        this.itemList=itemList;
        Log.e("i","Affichage list constructor");

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Log.e("i","Affichage list");
     if(convertView==null){
         LayoutInflater i=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
         convertView=i.inflate(R.layout.more_menu_item,null);
     }
     if(itemList.size()>0){MenuItem item=itemList.get(position);
        // ImageView menu_icon=convertView.findViewById(R.id.id.icon_menu);
        // menu_icon.setImageResource(item.getImage());
         TextView item_title=convertView.findViewById(R.id.menu_title);
         item_title.setText(item.getTitle());

     }
     return  convertView;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }
}
