package com.cempod.motivation20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
TabLayout tabLayout;
TextInputLayout progressLayout;
ConstraintLayout addLayout;
Button addButton;
RatingBar addRatingBar;


int target = 0;
TextInputEditText addTaskEdit;
TextInputEditText addProgressEdit;
    String taskType = "TASK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        addRatingBar = (RatingBar) findViewById(R.id.addRatingBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        addLayout = (ConstraintLayout)findViewById(R.id.addLayout) ;
        progressLayout = (TextInputLayout) findViewById(R.id.progress_layout);
        addButton = (Button) findViewById(R.id.taskAddButton);
        addTaskEdit = (TextInputEditText) findViewById(R.id.addTaskEdit);
        addProgressEdit = (TextInputEditText) findViewById(R.id.addProgressEdit);
        ItemListManager manager = new ItemListManager(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tabLayout.getSelectedTabPosition()==1){
                    target = Integer.parseInt(addProgressEdit.getText().toString());
                }

                Bundle arguments = getIntent().getExtras();
                int type = Integer.parseInt(arguments.get("Type").toString());
                if (type == R.id.action_today){
                  manager.addTodayTask(new Task(taskType, addTaskEdit.getText().toString(),false,(int)(addRatingBar.getRating()*100),target,0),new Date());

                }
                if (type == R.id.action_everyday){
                    manager.addEverydayTask(new Task(taskType, addTaskEdit.getText().toString(),false,(int)(addRatingBar.getRating()*100),target,0));

                }
                onBackPressed();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    TransitionManager.beginDelayedTransition(addLayout);
                    progressLayout.setVisibility(View.VISIBLE);
                    taskType = "PROGRESS";
                }else{
                    TransitionManager.beginDelayedTransition(addLayout);
                    progressLayout.setVisibility(View.GONE);
                    taskType = "TASK";
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}