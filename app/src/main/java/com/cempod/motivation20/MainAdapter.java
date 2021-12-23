package com.cempod.motivation20;

import android.animation.LayoutTransition;
import android.app.Dialog;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainAdapter extends ArrayAdapter<Task>{
List<Task> taskList;
ListView listView;
    ConstraintLayout mainLayout;
    ItemManager itemManager;
    public MainAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects, ListView listView, ConstraintLayout mainLayout) {
        super(context, resource, objects);
        taskList = objects;
        this.listView = listView;
        this.mainLayout = mainLayout;
         itemManager = new ItemManager();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(taskList.get(position).getType()!="DIVIDER"){
            if(taskList.get(position).getType()=="PROGRESS") {
                if (convertView == null || convertView.getTag() != "prog") {
                    view = inflater.inflate(R.layout.progress_task_card, parent, false);

                }
                TextView progressTaskTitle = (TextView) view.findViewById(R.id.progressTaskTitle);
                progressTaskTitle.setText(taskList.get(position).getTask());
                view.setTag("prog");
                ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.buttonLayout);
                layout.setVisibility(View.GONE);
                CardView card_view = (CardView) view.findViewById(R.id.card_view);
                TextInputEditText progressEdit = (TextInputEditText) view.findViewById(R.id.progressEdit);
                CircularProgressIndicator taskProgressBar = (CircularProgressIndicator) view.findViewById(R.id.taskProgressBar);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(progressEdit.getWindowToken(), 0);
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(200);


                taskProgressBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        LocalBroadcastManager.getInstance(getContext()).registerReceiver(new BroadcastReceiver() {
                                                                                             @Override
                                                                                             public void onReceive(Context context, Intent intent) {
                                                                                                 // Get extra data included in the Intent
                                                                                                 int message = intent.getIntExtra("message",0);
                                                                                                 if(message!=position){layout.setVisibility(View.GONE);
                                                                                                     imm.hideSoftInputFromWindow(progressEdit.getWindowToken(),0);}
                                                                                             }
                                                                                         },
                                new IntentFilter("edit_progress"));

                        Intent intent = new Intent("edit_progress");
                        // You can also include some extra data.



                        autoTransition.addListener(new Transition.TransitionListener() {
                            @Override
                            public void onTransitionStart(Transition transition) {

                            }

                            @Override
                            public void onTransitionEnd(Transition transition) {
                                if(layout.getVisibility()==View.VISIBLE){
                                    progressEdit.requestFocus();

                                    imm.showSoftInput(progressEdit, 0);

                                }
                            }

                            @Override
                            public void onTransitionCancel(Transition transition) {

                            }

                            @Override
                            public void onTransitionPause(Transition transition) {

                            }

                            @Override
                            public void onTransitionResume(Transition transition) {

                            }
                        });

                        TransitionManager.beginDelayedTransition(mainLayout,autoTransition);
                        intent.putExtra("message", position);
                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                        if (layout.getVisibility() != View.VISIBLE) {

                            layout.setVisibility(View.VISIBLE);





                        } else {
                            layout.setVisibility(View.GONE);
                            imm.hideSoftInputFromWindow(progressEdit.getWindowToken(),0);
                        }
                    }

                });
            }else{
                if (convertView == null || convertView.getTag()!="item"){
                    view = inflater.inflate(R.layout.simple_task_card,parent,false);
                }
                view.setTag("item");
                TextView simpleTaskTitle = (TextView) view.findViewById(R.id.simpleTaskTitle);
                simpleTaskTitle.setText(taskList.get(position).getTask());
            }
        }else{
            if(convertView == null || convertView.getTag()!="div"){
                view = inflater.inflate(R.layout.divider_card,parent,false);

            }
            view.setTag("div");
            TextView dividerTitle = (TextView) view.findViewById(R.id.dividerTitle);
            dividerTitle.setText(taskList.get(position).getTask());
        }
return view;
        //return itemManager.getItem(position,convertView,taskList,getContext(),parent);
    }

    /*  View view = convertView;
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(taskList.get(position).getDivide()==false){
        if(taskList.get(position).getIsCounter()==true) {
            if (convertView == null || convertView.getTag() != "prog") {
                view = inflater.inflate(R.layout.progress_task_card, parent, false);

            }
            TextView progressTaskTitle = (TextView) view.findViewById(R.id.progressTaskTitle);
            progressTaskTitle.setText(taskList.get(position).getTask());
            view.setTag("prog");
            ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.buttonLayout);
            layout.setVisibility(View.GONE);
            CardView card_view = (CardView) view.findViewById(R.id.card_view);
            TextInputEditText progressEdit = (TextInputEditText) view.findViewById(R.id.progressEdit);
            CircularProgressIndicator taskProgressBar = (CircularProgressIndicator) view.findViewById(R.id.taskProgressBar);
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(progressEdit.getWindowToken(), 0);



            taskProgressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalBroadcastManager.getInstance(getContext()).registerReceiver(new BroadcastReceiver() {
                                                                                         @Override
                                                                                         public void onReceive(Context context, Intent intent) {
                                                                                             // Get extra data included in the Intent
                                                                                             int message = intent.getIntExtra("message",0);
                                                                                             if(message!=position){layout.setVisibility(View.GONE);
                                                                                                 imm.hideSoftInputFromWindow(progressEdit.getWindowToken(),0);}
                                                                                         }
                                                                                     },
                            new IntentFilter("edit_progress"));

                    Intent intent = new Intent("edit_progress");
                    // You can also include some extra data.

                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(200);

                    autoTransition.addListener(new Transition.TransitionListener() {
                        @Override
                        public void onTransitionStart(Transition transition) {

                        }

                        @Override
                        public void onTransitionEnd(Transition transition) {
                            if(layout.getVisibility()==View.VISIBLE){
                                progressEdit.requestFocus();

                                imm.showSoftInput(progressEdit, 0);

                            }
                        }

                        @Override
                        public void onTransitionCancel(Transition transition) {

                        }

                        @Override
                        public void onTransitionPause(Transition transition) {

                        }

                        @Override
                        public void onTransitionResume(Transition transition) {

                        }
                    });
                    TransitionManager.beginDelayedTransition(listView,autoTransition);
                    intent.putExtra("message", position);
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                    if (layout.getVisibility() != View.VISIBLE) {

                        layout.setVisibility(View.VISIBLE);





                    } else {
                        layout.setVisibility(View.GONE);
                        imm.hideSoftInputFromWindow(progressEdit.getWindowToken(),0);
                    }
                }

            });
        }else{
            if (convertView == null || convertView.getTag()!="item"){
                view = inflater.inflate(R.layout.simple_task_card,parent,false);
            }
            view.setTag("item");
            TextView simpleTaskTitle = (TextView) view.findViewById(R.id.simpleTaskTitle);
            simpleTaskTitle.setText(taskList.get(position).getTask());
        }
    }else{
        if(convertView == null || convertView.getTag()!="div"){
            view = inflater.inflate(R.layout.divider_card,parent,false);

        }
        view.setTag("div");
        TextView dividerTitle = (TextView) view.findViewById(R.id.dividerTitle);
        dividerTitle.setText(taskList.get(position).getTask());
    }*/

}