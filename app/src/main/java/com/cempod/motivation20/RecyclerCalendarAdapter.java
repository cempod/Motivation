package com.cempod.motivation20;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
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
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
    String[] week = new String[]{"Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота"};

    public RecyclerCalendarAdapter(List<Day> month, Context context) {
        this.month = month;
        this.context = context;
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
        if(getDayPos(position)==1){
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary,typedValue,true);
            int color = typedValue.data;
            ((DayViewHolder)holder).dateText.setTextColor(color);
            ((DayViewHolder)holder).dayOfWeek.setTextColor(color);

        }else{
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorOnSurface ,typedValue,true);
            int color = typedValue.data;
            ((DayViewHolder)holder).dateText.setTextColor(color);
            ((DayViewHolder)holder).dayOfWeek.setTextColor(color);
        }
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

    private int getDayPos(int position){
      Calendar now = new GregorianCalendar();
      now.setTime(new Date());
      Calendar date = new GregorianCalendar();

        try {
            date.setTime(dateFormat.parse(month.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ((now.get(Calendar.YEAR)==date.get(Calendar.YEAR)&&now.get(Calendar.DAY_OF_YEAR)==date.get(Calendar.DAY_OF_YEAR))){
            return 1;
        }
        if((now.get(Calendar.YEAR)==date.get(Calendar.YEAR)&&now.get(Calendar.DAY_OF_YEAR)>date.get(Calendar.DAY_OF_YEAR))){
            return 0;
        }
        if ((now.get(Calendar.YEAR)==date.get(Calendar.YEAR)&&now.get(Calendar.DAY_OF_YEAR)<date.get(Calendar.DAY_OF_YEAR))){
            return 2;
        }
        return 2;
    }

}
