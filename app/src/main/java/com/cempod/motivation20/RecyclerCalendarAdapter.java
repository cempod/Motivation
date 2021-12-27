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
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
    String[] week = new String[]{"Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота"};

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
        ((DayViewHolder)holder).dateText.setText(getDate(position));
        ((DayViewHolder)holder).dayOfWeek.setText(getDayOfWeek(position));
    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;
        TextView dayOfWeek;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.dateText);
            dayOfWeek = (TextView) itemView.findViewById(R.id.dayOfWeekText);
        }
    }

    private String getDate(int position){
Calendar c = new GregorianCalendar();
Date date = new Date();
        try {
            date = dateFormat.parse(month.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        return Integer.toString(c.get(Calendar.DATE));
    }

    private String getDayOfWeek(int position){
        Calendar c = new GregorianCalendar();
        Date date = new Date();
        try {
            date = dateFormat.parse(month.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        return week[c.get(Calendar.DAY_OF_WEEK)-1];
    }

}
