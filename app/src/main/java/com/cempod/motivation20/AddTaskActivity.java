package com.cempod.motivation20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

public class AddTaskActivity extends AppCompatActivity {
TabLayout tabLayout;
TextInputLayout progressLayout;
ConstraintLayout addLayout;
Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        addLayout = (ConstraintLayout)findViewById(R.id.addLayout) ;
        progressLayout = (TextInputLayout) findViewById(R.id.progress_layout);
        addButton = (Button) findViewById(R.id.taskAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arguments = getIntent().getExtras();
                int type = Integer.parseInt(arguments.get("Type").toString());
                if (type == R.id.action_today){
                   Toast toast = Toast.makeText(getBaseContext(),"сугодня", Toast.LENGTH_SHORT);
                toast.show();
                }
                if (type == R.id.action_everyday){
                    Toast toast = Toast.makeText(getBaseContext(),"каждый день", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    TransitionManager.beginDelayedTransition(addLayout);
                    progressLayout.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(addLayout);
                    progressLayout.setVisibility(View.GONE);
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