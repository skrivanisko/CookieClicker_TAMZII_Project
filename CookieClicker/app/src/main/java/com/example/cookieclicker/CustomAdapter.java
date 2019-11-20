package com.example.cookieclicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Helper> helpers;
    int flags[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<Helper> obj) {
        this.context = context;
        this.helpers = obj;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return helpers.size();
    }

    @Override
    public Helper getItem(int position) {
        return helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return helpers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.activity_listview, null);
        TextView country = (TextView)convertView.findViewById(R.id.name);
        TextView production = (TextView)convertView.findViewById(R.id.production);
        TextView cost = (TextView)convertView.findViewById(R.id.cost);
        TextView count = (TextView)convertView.findViewById(R.id.count);
        TextView prodPS = (TextView)convertView.findViewById(R.id.productionPS);

        ImageView icon = (ImageView)convertView.findViewById(R.id.imageView);

        country.setText(helpers.get(position).getName());
        production.setText(Integer.toString(helpers.get(position).getProduction()));
        cost.setText(Integer.toString(helpers.get(position).getCost()));
        count.setText(Integer.toString(helpers.get(position).getCount()));
        prodPS.setText(Integer.toString(helpers.get(position).getCount()*helpers.get(position).getProduction())+" Cookies/s");

        icon.setImageResource(R.drawable.place_holder);
        return convertView;
    }
}
