package com.example.sokol.simple_notes;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sokol on 21.10.2018.
 */

public class GriedAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public GriedAdapter (Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
    }

    public int toInt(String s)
    {
        return Integer.parseInt(s);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       ListAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new ListAdapter.ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.gried_item, null);

            holder.title = (TextView) convertView.findViewById(R.id.list_title);
            holder.text = (TextView) convertView.findViewById(R.id.list_text);

            convertView.setTag(holder);
        } else {
            holder = (ListAdapter.ViewHolder) convertView.getTag();
        }

        holder.title.setText(data.get(position).get(MainActivity.COLUMN_Title));
        holder.text.setText(data.get(position).get(MainActivity.COLUMN_Text));

        return convertView;

    }
}
