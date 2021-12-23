package com.cempod.motivation20;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.Transliterator;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class RecyclerTaskAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {
private List<Task> taskList;
RecyclerView recyclerView;
    public RecyclerTaskAdapter(List<Task> taskList, RecyclerView recyclerView){
this.taskList = taskList;
this.recyclerView = recyclerView;

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    public interface RowType {
        int SIMPLE_TASK_TYPE =   0;
        int PROGRESS_TASK_TYPE = 1;

    }

    class SimpleTaskViewHolder extends RecyclerView.ViewHolder{

        TextView simpleTaskTitle;
        RatingBar ratingbar;

        public SimpleTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingbar = (RatingBar) itemView.findViewById(R.id.simpleTaskRatingBar);
            simpleTaskTitle = (TextView) itemView.findViewById(R.id.simpleTaskTitle);
        }
    }

    class ProgressTaskViewHolder extends RecyclerView.ViewHolder{

        TextView progressTaskTitle;
        RatingBar ratingBar;
        CircularProgressIndicator taskProgressBar;
        ConstraintLayout buttonLayout;
        TextInputEditText progressEdit;


        public ProgressTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            progressTaskTitle = (TextView) itemView.findViewById(R.id.progressTaskTitle);
            taskProgressBar = (CircularProgressIndicator) itemView.findViewById(R.id.taskProgressBar);
            buttonLayout = (ConstraintLayout) itemView.findViewById(R.id.buttonLayout);
            progressEdit = (TextInputEditText) itemView.findViewById(R.id.progressEdit);
            ratingBar = (RatingBar) itemView.findViewById(R.id.progressTaskRatingBar);
            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(200);

            InputMethodManager imm = (InputMethodManager) itemView.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);


            autoTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
if(buttonLayout.getVisibility()==View.VISIBLE){
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
            taskProgressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    TransitionManager.beginDelayedTransition(recyclerView, autoTransition);

                    if(buttonLayout.getVisibility()==View.GONE){
                        buttonLayout.setVisibility(View.VISIBLE);
                    }else{
                        buttonLayout.setVisibility(View.GONE);
                        imm.hideSoftInputFromWindow(progressEdit.getWindowToken(),0);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (taskList.get(position).getType()){
            case "TASK":return RowType.SIMPLE_TASK_TYPE;
            case  "PROGRESS" : return RowType.PROGRESS_TASK_TYPE;
            default:return 9;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
if(viewType == RowType.SIMPLE_TASK_TYPE){
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_task_card, parent,false);
    return new SimpleTaskViewHolder(view);
}
        if(viewType == RowType.PROGRESS_TASK_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_task_card, parent,false);
            return new ProgressTaskViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
if(holder instanceof SimpleTaskViewHolder){
    ((SimpleTaskViewHolder)holder).simpleTaskTitle.setText(taskList.get(position).getTask());
    ((SimpleTaskViewHolder)holder).ratingbar.setRating(taskList.get(position).getRating()/100.f);
}
if(holder instanceof ProgressTaskViewHolder){
    ((ProgressTaskViewHolder)holder).progressTaskTitle.setText(taskList.get(position).getTask());
    ((ProgressTaskViewHolder)holder).ratingBar.setRating(taskList.get(position).getRating()/100.f);
}
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    ItemTouchHelper.Callback callback =
            new SimpleItemTouchHelperCallback(this);
    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {


        touchHelper.attachToRecyclerView(recyclerView);
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        touchHelper.attachToRecyclerView(null);
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
