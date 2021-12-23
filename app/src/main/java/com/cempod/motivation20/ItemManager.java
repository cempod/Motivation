package com.cempod.motivation20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Stack;

public class ItemManager {

    private Stack<View> dividerStack = new Stack<>();
    private Stack<View> taskStack = new Stack<>();
    private Stack<View> progressTaskStack = new Stack<>();

    static class ViewHolder {
        TextView progressTaskTitle;
        TextView dividerTitle;
        TextView simpleTaskTitle;
        ConstraintLayout layout;
        TextInputEditText progressEdit;
        CircularProgressIndicator taskProgressBar;
        String type;
    }


    public View getItem(int position, View convertview, List<Task> taskList, Context context, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;

        if (convertview != null) {
            holder = (ViewHolder) convertview.getTag();
            if (holder.type == taskList.get(position).getType()) {
                return convertview;
            } else {
                switch (holder.type) {
                    case "DIVIDER":
                        dividerStack.push(convertview);
                        break;
                    case "TASK":
                        taskStack.push(convertview);
                        break;
                    case "PROGRESS":
                        progressTaskStack.push(convertview);
                        break;
                }
                switch (taskList.get(position).getType()) {
                    case "DIVIDER":
                        if (dividerStack.empty() == false) {
                            return dividerStack.pop();
                        } else {
                            View view = inflater.inflate(R.layout.divider_card, parent, false);
                            holder = new ViewHolder();
                            holder.dividerTitle = (TextView) view.findViewById(R.id.dividerTitle);
                            holder.dividerTitle.setText(taskList.get(position).getTask());
                            holder.type = "DIVIDER";
                            view.setTag(holder);
                            return view;
                        }

                    case "TASK":
                        if (taskStack.empty() == false) {
                            return taskStack.pop();
                        } else {
                            View view = inflater.inflate(R.layout.simple_task_card, parent, false);
                            holder = new ViewHolder();
                            holder.simpleTaskTitle = (TextView) view.findViewById(R.id.simpleTaskTitle);
                            holder.simpleTaskTitle.setText(taskList.get(position).getTask());
                            holder.type = "TASK";
                            view.setTag(holder);
                            return view;
                        }

                    case "PROGRESS":
                        if (progressTaskStack.empty() == false) {
                            return progressTaskStack.pop();
                        } else {
                            View view = inflater.inflate(R.layout.simple_task_card, parent, false);
                            holder = new ViewHolder();
                            holder.progressTaskTitle = (TextView) view.findViewById(R.id.progressTaskTitle);
                            holder.progressTaskTitle.setText(taskList.get(position).getTask());
                            holder.type = "PROGRESS";
                            view.setTag(holder);
                            return view;
                        }

                }
            }
        }else{

            switch (taskList.get(position).getType()) {
                case "DIVIDER":
                    if (dividerStack.empty() == false) {
                        return dividerStack.pop();
                    } else {
                        View view = inflater.inflate(R.layout.divider_card, parent, false);
                        holder = new ViewHolder();
                        holder.dividerTitle = (TextView) view.findViewById(R.id.dividerTitle);
                        holder.dividerTitle.setText(taskList.get(position).getTask());
                        holder.type = "DIVIDER";
                        view.setTag(holder);
                        return view;
                    }

                case "TASK":
                    if (taskStack.empty() == false) {
                        return taskStack.pop();
                    } else {
                        View view = inflater.inflate(R.layout.simple_task_card, parent, false);
                        holder = new ViewHolder();
                        holder.simpleTaskTitle = (TextView) view.findViewById(R.id.simpleTaskTitle);
                        holder.simpleTaskTitle.setText(taskList.get(position).getTask());
                        holder.type = "TASK";
                        view.setTag(holder);
                        return view;
                    }

                case "PROGRESS":
                    if (progressTaskStack.empty() == false) {
                        return progressTaskStack.pop();
                    } else {
                        View view = inflater.inflate(R.layout.simple_task_card, parent, false);
                        holder = new ViewHolder();
                        holder.progressTaskTitle = (TextView) view.findViewById(R.id.progressTaskTitle);
                        holder.progressTaskTitle.setText(taskList.get(position).getTask());
                        holder.type = "PROGRESS";
                        view.setTag(holder);
                        return view;
                    }

            }
        }
return convertview;

    }




}
