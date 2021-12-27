package com.cempod.motivation20;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarManager {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
    private List<Day> month;
    RecyclerView recyclerView;

    public CalendarManager(List<Day> month, RecyclerView recyclerView){
        this.month = month;
        this.recyclerView = recyclerView;
    }

    public void loadMonth(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        month.clear();
        Calendar d = new GregorianCalendar();
        d.setTime(date);
        for(int i = 1; i<=c.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            d.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),i);
            Date date1 = d.getTime();
            month.add(new Day(dateFormat.format(date1),0,0));
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
