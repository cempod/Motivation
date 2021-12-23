package com.cempod.motivation20;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> todayList = new ArrayList<Task>();
    ArrayList<Task> everydayList = new ArrayList<Task>();
    Button addTaskButton  ;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout progressBarLayout;
    ConstraintLayout mainLayout;
    ConstraintLayout progressCardLayout;
    ConstraintLayout statsLayout;
    LinearProgressIndicator taskProgressBar;
    CircularProgressIndicator taskProgressBar2;
    TextView bottomText;
    Button nonButton;
    RecyclerView recyclerView;
    ItemListManager manager;

       ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
               new ActivityResultCallback<ActivityResult>() {
                   @Override
                   public void onActivityResult(ActivityResult result) {
                      manager.refreshLists();
notifyAdapter(recyclerView);

                   }
               });
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTaskButton = (Button) findViewById(R.id.addTaskButton) ;
        progressBarLayout = (ConstraintLayout) findViewById(R.id.progressBarLayout);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        progressCardLayout = (ConstraintLayout) findViewById(R.id.progressCardLayout);
        statsLayout = (ConstraintLayout) findViewById(R.id.statsLayout);
        taskProgressBar = (LinearProgressIndicator) findViewById(R.id.taskProgressBar);
        taskProgressBar2 = (CircularProgressIndicator) findViewById(R.id.taskProgressBar2) ;
        bottomText = (TextView) findViewById(R.id.bottomText);
        nonButton = (Button) findViewById(R.id.nonButton);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomMenu);
        recyclerView = findViewById(R.id.recyclerView);




addTaskButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout2);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,new androidx.core.util.Pair<>(addTaskButton,"animbtn"));

        mStartForResult.launch(new Intent(MainActivity.this, AddTaskActivity.class).putExtra("Type", bottomNavigationView.getSelectedItemId()),optionsCompat);

    }
});

       // recyclerView.setPaddingRelative(0,300,0,0);

      progressCardLayout.post(new Runnable() {
          @Override
          public void run() {
        recyclerView.setPadding(0,progressBarLayout.getHeight(),0,0);
          }
      });

RecyclerTaskAdapter todayAdapter = new RecyclerTaskAdapter(todayList, recyclerView, false);
RecyclerTaskAdapter everydayAdapter = new RecyclerTaskAdapter(everydayList, recyclerView, true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(linearLayoutManager.findFirstVisibleItemPosition() > 1 && taskProgressBar2.getVisibility()==View.VISIBLE){

            //TransitionManager.beginDelayedTransition(progressCardLayout);
            taskProgressBar.setVisibility(View.VISIBLE);
            bottomText.setVisibility(View.GONE);
            taskProgressBar2.setVisibility(View.GONE);
            statsLayout.setVisibility(View.GONE);
            nonButton.setVisibility(View.VISIBLE);

        }
        if(linearLayoutManager.findFirstVisibleItemPosition() ==0  && taskProgressBar2.getVisibility()==View.GONE){
           // TransitionManager.beginDelayedTransition(mainLayout);
            bottomText.setVisibility(View.VISIBLE);
            taskProgressBar2.setVisibility(View.VISIBLE);
            statsLayout.setVisibility(View.VISIBLE);
            nonButton.setVisibility(View.GONE);
            taskProgressBar.setVisibility(View.GONE);

        }

    }
});

manager = new ItemListManager(todayList,everydayList,this);
manager.refreshLists();

recyclerView.setAdapter(todayAdapter);

        MaterialToolbar topAppbar = findViewById(R.id.topAppBar);
        topAppbar.setTitle("СЕГОДНЯ");

        bottomNavigationView.setSelectedItemId(R.id.action_today);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.action_today&&bottomNavigationView.getSelectedItemId()!=R.id.action_today){
item.setChecked(true);
if(progressCardLayout.getVisibility()==View.VISIBLE)
                    {
                        TransitionManager.beginDelayedTransition(recyclerView);
                        recyclerView.setAdapter(todayAdapter);

                        topAppbar.setTitle("СЕГОДНЯ");
                        todayAdapter.notifyDataSetChanged();
                    }
else{
    TransitionManager.beginDelayedTransition(mainLayout);
    recyclerView.setAdapter(todayAdapter);

    topAppbar.setTitle("СЕГОДНЯ");
    progressCardLayout.setVisibility(View.VISIBLE);
    todayAdapter.notifyDataSetChanged();
}
                }
                if(item.getItemId()==R.id.action_everyday&&bottomNavigationView.getSelectedItemId()!=R.id.action_everyday){
                    item.setChecked(true);
                    if(progressCardLayout.getVisibility()==View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(recyclerView);
                        recyclerView.setAdapter(everydayAdapter);

                        topAppbar.setTitle("КАЖДЫЙ ДЕНЬ");
                        everydayAdapter.notifyDataSetChanged();
                    }
                    else{
                        TransitionManager.beginDelayedTransition(mainLayout);
                        recyclerView.setAdapter(everydayAdapter);

                        topAppbar.setTitle("СЕГОДНЯ");
                        progressCardLayout.setVisibility(View.VISIBLE);
                        todayAdapter.notifyDataSetChanged();
                    }
                }
                if(item.getItemId()==R.id.action_calendar&&bottomNavigationView.getSelectedItemId()!=R.id.action_calendar){
                    item.setChecked(true);
                    TransitionManager.beginDelayedTransition(mainLayout);
                    recyclerView.setAdapter(null);
                    topAppbar.setTitle("");
                    progressCardLayout.setVisibility(View.GONE);
                }



                return false;
            }
        });
    }

    public void notifyAdapter(RecyclerView recyclerView){
        if(recyclerView.getAdapter()!=null){
            TransitionManager.beginDelayedTransition(recyclerView);
recyclerView.getAdapter().notifyDataSetChanged();
    }
    }

    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getSelectedItemId()!=R.id.action_today){
            bottomNavigationView.setSelectedItemId(R.id.action_today);
        }else
        super.onBackPressed();
    }



}