package com.shvikanitzani.ourhome.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shvikanitzani.ourhome.R;
import com.shvikanitzani.ourhome.model.WebsiteTodo;

import java.util.ArrayList;

/**
 * Created by PC on 26/05/2015.
 */
public class WebsiteTodosListAdapter extends ArrayAdapter<WebsiteTodo> {

    private int listOddLineBackground;
    private int listEvenLineBackground;
    public WebsiteTodosListAdapter(Context context, ArrayList<WebsiteTodo> todos) {
        //super(context, 0, todos);
        super(context, android.R.layout.simple_list_item_2, todos);

        listEvenLineBackground=context.getResources().getColor(R.color.app_background_blue);
        listOddLineBackground= context.getResources().getColor(R.color.azure);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean isEvenLine = (position % 2) == 0;
        // Get the data item for this position
        WebsiteTodo todo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_website_todo, parent, false);
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);

        }

        if (isEvenLine)
            convertView.setBackgroundColor(listEvenLineBackground);
        else
            convertView.setBackgroundColor(listOddLineBackground);

        // Lookup view for data population
        /*TextView title = (TextView)convertView.findViewById(R.id.website_todo_title);
        TextView details = (TextView)convertView.findViewById(R.id.website_todo_details);*/
        TextView title = (TextView)convertView.findViewById(android.R.id.text1);
        TextView details = (TextView)convertView.findViewById(android.R.id.text2);
        // Populate the data into the template view using the data object

        title.setText(todo.title);
        title.setTextColor(Color.WHITE);

        details.setText(todo.details);
        details.setTextColor(Color.WHITE);
        // Return the completed view to render on screen
        return convertView;
    }
}
