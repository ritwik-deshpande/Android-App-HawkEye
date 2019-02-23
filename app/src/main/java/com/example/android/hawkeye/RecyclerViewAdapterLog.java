package com.example.android.hawkeye;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerViewAdapterLog extends  RecyclerView.Adapter<RecyclerViewAdapterLog.MyViewHolder>{

    private ArrayList<EntryLog> el;
    Context context;
    Date d;


    public RecyclerViewAdapterLog(ArrayList<EntryLog> el, Context context) {
        this.el = el;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterLog.MyViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_entry_log,parent,false);

        RecyclerViewAdapterLog.MyViewHolder myViewHolder= new RecyclerViewAdapterLog.MyViewHolder(v);

        d= new Date();

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterLog.MyViewHolder myViewHolder, final int i) {

        myViewHolder.rnum.setText(el.get(i).getRnum());
        myViewHolder.tuid.setText(el.get(i).getUid());

         d = el.get(i).getTimestamp();

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(d);


        Log.d("TAG","The value of tuid is:"+el.get(i).getUid());

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        int year = calendar.get(Calendar.YEAR);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        int minute= calendar.get(Calendar.MINUTE);

        Log.d("TAG","The current date is:"+day+"/"+month+"/"+year);

        myViewHolder.adate.setText(day+"/"+month+"/"+year);

        myViewHolder.atime.setText(hour+":"+minute);

        myViewHolder.desc.setText(el.get(i).getDsc());

        myViewHolder.ddesc.setText(el.get(i).getTimestamp_dsc()+" Date:");

        myViewHolder.tdesc.setText(el.get(i).getTimestamp_dsc()+" Time:");

    }

    @Override
    public int getItemCount() {
        return el.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tuid;
        TextView rnum;
        TextView adate;
        TextView atime;
        TextView desc;
        TextView ddesc;
        TextView tdesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tuid=(TextView)itemView.findViewById(R.id.uid);
            rnum=(TextView)itemView.findViewById(R.id.reg_num);
            adate=(TextView)itemView.findViewById(R.id.date);
            atime=(TextView)itemView.findViewById(R.id.time);
            desc=(TextView)itemView.findViewById(R.id.v_dsc);
            ddesc=(TextView)itemView.findViewById(R.id.dd);
            tdesc=(TextView)itemView.findViewById(R.id.td);
        }
    }
}
