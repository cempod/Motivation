package com.cempod.motivation20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class RecyclerCalendarAdapter extends RecyclerView.Adapter<RecyclerCalendarAdapter.DayViewHolder> {
    List<Day> month;

    public RecyclerCalendarAdapter(List<Day> month) {
        this.month = month;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_of_month, parent,false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
