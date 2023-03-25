package com.example.todomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title_textview);
        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.description_textview);
        TextView contextTextView = (TextView) convertView.findViewById(R.id.context_textview);

        titleTextView.setText(task.getTaskTitle());
        descriptionTextView.setText(task.getDescription());
        contextTextView.setText(task.getContext());

        return convertView;
    }
}