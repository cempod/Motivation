package com.cempod.motivation20;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> todayList = new ArrayList<Task>();
    ArrayList<Task> everydayList = new ArrayList<Task>();
    ArrayList<Day> month = new ArrayList<Day>();
    Button addTaskButton  ;
    BottomNavigationView bottomNavigationView;
    MotionLayout mainLayout;
    ConstraintLayout calendarLayout;
    ConstraintLayout calendarNavigation;
    LinearProgressIndicator taskProgressBar;
    CircularProgressIndicator taskProgressBar2;
    TextView bottomText;
    Button nonButton;
    RecyclerView recyclerView;
    RecyclerView calendarRecyclerView;
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
        //progressBarLayout = (ConstraintLayout) findViewById(R.id.progressBarLayout);
        mainLayout = (MotionLayout) findViewById(R.id.mainLayout);
        calendarLayout = (ConstraintLayout) findViewById(R.id.calendarLayout);
       // progressCardLayout = (ConstraintLayout) findViewById(R.id.progressCardLayout);
       // statsLayout = (ConstraintLayout) findViewById(R.id.statsLayout);
        taskProgressBar = (LinearProgressIndicator) findViewById(R.id.taskProgressBar);
        taskProgressBar2 = (CircularProgressIndicator) findViewById(R.id.taskProgressBar2) ;
        bottomText = (TextView) findViewById(R.id.bottomText);
       // nonButton = (Button) findViewById(R.id.nonButton);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomMenu);
        recyclerView = findViewById(R.id.recyclerView);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        calendarNavigation = (ConstraintLayout) findViewById(R.id.calendarNavigation);





addTaskButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout2);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,new androidx.core.util.Pair<>(addTaskButton,"animbtn"));

        mStartForResult.launch(new Intent(MainActivity.this, AddTaskActivity.class).putExtra("Type", bottomNavigationView.getSelectedItemId()),optionsCompat);

    }
});


RecyclerTaskAdapter todayAdapter = new RecyclerTaskAdapter(todayList, recyclerView, false);
RecyclerTaskAdapter everydayAdapter = new RecyclerTaskAdapter(everydayList, recyclerView, true);
RecyclerCalendarAdapter calendarAdapter = new RecyclerCalendarAdapter(month, MainActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager calendarLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
calendarRecyclerView.setLayoutManager(calendarLayoutManager);

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
                    if(bottomNavigationView.getSelectedItemId()==R.id.action_calendar){
                        TransitionManager.beginDelayedTransition(mainLayout);
                        calendarLayout.setVisibility(View.GONE);
                        mainLayout.setVisibility(View.VISIBLE);
                    }
item.setChecked(true);

                        TransitionManager.beginDelayedTransition(mainLayout);
                        recyclerView.setAdapter(todayAdapter);
                        calendarNavigation.setVisibility(View.GONE);
                        topAppbar.setTitle("СЕГОДНЯ");

                        notifyAdapter(recyclerView);


                }
                if(item.getItemId()==R.id.action_everyday&&bottomNavigationView.getSelectedItemId()!=R.id.action_everyday) {
                    if(bottomNavigationView.getSelectedItemId()==R.id.action_calendar){
                        TransitionManager.beginDelayedTransition(mainLayout);
                        calendarLayout.setVisibility(View.GONE);
                        mainLayout.setVisibility(View.VISIBLE);
                    }
                    item.setChecked(true);

                    TransitionManager.beginDelayedTransition(mainLayout);
                    recyclerView.setAdapter(everydayAdapter);
                    calendarNavigation.setVisibility(View.GONE);

                    topAppbar.setTitle("КАЖДЫЙ ДЕНЬ");
                }
                if(item.getItemId()==R.id.action_calendar&&bottomNavigationView.getSelectedItemId()!=R.id.action_calendar){
                    item.setChecked(true);
                    TransitionManager.beginDelayedTransition(calendarLayout);
                    mainLayout.setVisibility(View.GONE);
                    calendarLayout.setVisibility(View.VISIBLE);
                    topAppbar.setTitle("КАЛЕНДАРЬ");
                    calendarNavigation.setVisibility(View.VISIBLE);
                    calendarManager.loadMonth(new Date());
                    calendarRecyclerView.setAdapter(calendarAdapter);


                    Calendar calendar = new GregorianCalendar();
                    calendarRecyclerView.scrollToPosition(calendar.get(Calendar.DATE)-1);

                    Toast toast = Toast.makeText(getApplicationContext(),Integer.toString(calendar.get(Calendar.DATE)-1),Toast.LENGTH_SHORT);
                   // toast.show();
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


    private BroadcastReceiver counterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("count");
            notifyAdapter(recyclerView);
        }
    };

}