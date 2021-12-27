package com.cempod.motivation20;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> todayList = new ArrayList<Task>();
    ArrayList<Task> everydayList = new ArrayList<Task>();
    ArrayList<Day> month = new ArrayList<Day>();
    Button addTaskButton  ;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout progressBarLayout;
    ConstraintLayout mainLayout;
    ConstraintLayout progressCardLayout;
    ConstraintLayout statsLayout;
    ConstraintLayout calendarNavigation;
    LinearProgressIndicator taskProgressBar;
    CircularProgressIndicator taskProgressBar2;
    TextView bottomText;
    Button nonButton;
    RecyclerView recyclerView;
    ItemListManager manager;
    CalendarManager calendarManager;

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
        LocalBroadcastManager.getInstance(this).registerReceiver(counterReceiver,
                new IntentFilter("count_change"));
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
        calendarNavigation = (ConstraintLayout) findViewById(R.id.calendarNavigation);





addTaskButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout2);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,new androidx.core.util.Pair<>(addTaskButton,"animbtn"));

        mStartForResult.launch(new Intent(MainActivity.this, AddTaskActivity.class).putExtra("Type", bottomNavigationView.getSelectedItemId()),optionsCompat);

    }
});

       // recyclerView.setPaddingRelative(0,300,0,0);

    setPadding();

RecyclerTaskAdapter todayAdapter = new RecyclerTaskAdapter(todayList, recyclerView, false);
RecyclerTaskAdapter everydayAdapter = new RecyclerTaskAdapter(everydayList, recyclerView, true);
RecyclerCalendarAdapter calendarAdapter = new RecyclerCalendarAdapter(month);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(linearLayoutManager.findFirstVisibleItemPosition() > 1 && taskProgressBar2.getVisibility()==View.VISIBLE){
            makeCompact();
        }
        if(linearLayoutManager.findFirstVisibleItemPosition() ==0  && taskProgressBar2.getVisibility()==View.GONE){
          makeBig();
        }

    }
});

manager = new ItemListManager(todayList,everydayList,this);
calendarManager = new CalendarManager(month, recyclerView);

manager.refreshLists();

recyclerView.setAdapter(todayAdapter);
        notifyAdapter(recyclerView);
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
                        TransitionManager.beginDelayedTransition(mainLayout);
                        recyclerView.setAdapter(todayAdapter);
                        calendarNavigation.setVisibility(View.GONE);
                        topAppbar.setTitle("СЕГОДНЯ");
                        makeBig();
                        setPadding();
                        notifyAdapter(recyclerView);
                    }
else{
    TransitionManager.beginDelayedTransition(mainLayout);
    recyclerView.setAdapter(todayAdapter);
    calendarNavigation.setVisibility(View.GONE);
    topAppbar.setTitle("СЕГОДНЯ");
    makeBig();
    setPadding();
    progressCardLayout.setVisibility(View.VISIBLE);
    notifyAdapter(recyclerView);
}
                }
                if(item.getItemId()==R.id.action_everyday&&bottomNavigationView.getSelectedItemId()!=R.id.action_everyday){
                    item.setChecked(true);
                    if(progressCardLayout.getVisibility()==View.VISIBLE) {
                        setPadding();
                        TransitionManager.beginDelayedTransition(mainLayout);
                        recyclerView.setAdapter(everydayAdapter);
                        calendarNavigation.setVisibility(View.GONE);
                        topAppbar.setTitle("КАЖДЫЙ ДЕНЬ");
                        makeBig();

                        notifyAdapter(recyclerView);
                    }
                    else{
                        setPadding();
                        TransitionManager.beginDelayedTransition(mainLayout);
                        recyclerView.setAdapter(everydayAdapter);
                        calendarNavigation.setVisibility(View.GONE);

                        progressCardLayout.setVisibility(View.VISIBLE);
                        topAppbar.setTitle("КАЖДЫЙ ДЕНЬ");
                        makeBig();

                        notifyAdapter(recyclerView);
                    }
                }
                if(item.getItemId()==R.id.action_calendar&&bottomNavigationView.getSelectedItemId()!=R.id.action_calendar){
                    item.setChecked(true);
                    TransitionManager.beginDelayedTransition(mainLayout);

                            recyclerView.setPadding(0,0,0,0);

                    topAppbar.setTitle("КАЛЕНДАРЬ");
                    calendarNavigation.setVisibility(View.VISIBLE);
                    calendarManager.loadMonth(new Date());
                    recyclerView.setAdapter(calendarAdapter);
                    progressCardLayout.setVisibility(View.GONE);



                    Toast toast = Toast.makeText(getApplicationContext(),recyclerView.getAdapter().toString(),Toast.LENGTH_SHORT);
                    //toast.show();
                }



                return false;
            }
        });


    }

    public void notifyAdapter(RecyclerView recyclerView){
        if(recyclerView.getAdapter()!=null){
          //  TransitionManager.beginDelayedTransition(recyclerView);
recyclerView.getAdapter().notifyDataSetChanged();
bottomNavigationView.getOrCreateBadge(R.id.action_today).setNumber(todayList.size());
bottomNavigationView.getOrCreateBadge(R.id.action_everyday).setNumber(everydayList.size());
    }
    }

    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getSelectedItemId()!=R.id.action_today){
            bottomNavigationView.setSelectedItemId(R.id.action_today);
        }else
        super.onBackPressed();
    }


        public void makeCompact()
        {
        taskProgressBar.setVisibility(View.VISIBLE);
        bottomText.setVisibility(View.GONE);
        taskProgressBar2.setVisibility(View.GONE);
        statsLayout.setVisibility(View.GONE);
        nonButton.setVisibility(View.VISIBLE);

    }
    public void makeBig()
    {
        bottomText.setVisibility(View.VISIBLE);
        taskProgressBar2.setVisibility(View.VISIBLE);
        statsLayout.setVisibility(View.VISIBLE);
        nonButton.setVisibility(View.GONE);
        taskProgressBar.setVisibility(View.GONE);

    }

    public void setPadding(){
        progressCardLayout.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setPadding(0,progressBarLayout.getHeight(),0,0);
            }
        });
    }

    private BroadcastReceiver counterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("count");
            notifyAdapter(recyclerView);
        }
    };

}