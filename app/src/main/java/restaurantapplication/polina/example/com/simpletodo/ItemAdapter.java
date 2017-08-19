package restaurantapplication.polina.example.com.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.itemTextAppearance;
import static android.R.attr.resource;
import static android.media.CamcorderProfile.get;

/**
 * Created by polina on 8/17/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsHolder> {

    Context context;
    List <Item> itemsList;

    public ItemAdapter(@NonNull Context context, @NonNull List<Item> objects) {
        this.context=context;
        itemsList=objects;
    }

    public Context getContext() {
        return context;
    }



    @Override
    public ItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.task_list_layout, parent, false);
        ItemsHolder viewHolder = new ItemsHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemsHolder holder, int position) {
        Item item = itemsList.get(position);
        holder.itemName.setText(item.getTaskName());
        holder.itemDate.setText(setDate(item.getDueDate()));
        if(item.isTaskCompletion()==0) {
            holder.itemLayout.setBackgroundColor(setPriority(item.getTaskPriority()));
        } else  {
            holder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.colorDoneBackground));
            holder.itemDate.setTextColor(context.getResources().getColor(R.color.colorDoneText));
            holder.itemDate.setTypeface(null, Typeface.ITALIC);
            holder.itemName.setTextColor(context.getResources().getColor(R.color.colorDoneText));
            holder.itemName.setTypeface(null, Typeface.ITALIC);
        }

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemName;
        TextView itemDate;
        LinearLayout itemLayout;

        public ItemsHolder(View itemView) {
            super(itemView);
            itemName = (TextView)itemView.findViewById(R.id.taskNameList);
            itemDate = (TextView)itemView.findViewById(R.id.taskDateList);
            itemLayout = (LinearLayout)itemView.findViewById(R.id.taskLayoutList);
            itemLayout.setOnClickListener(this);

        }


//todo
        @Override
        public void onClick(View v) {

        }
        }

    private String setDate (String dt){
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();
        try {
            date = df.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df = new SimpleDateFormat("dd MMM");
        return df.format(date);
    }

    private int setPriority(int taskPriority) {
        switch (taskPriority){
            case 1:
                return context.getResources().getColor(R.color.colorLowPriority);
            case 2:
                return context.getResources().getColor(R.color.colorNormalPriority);
            case 3:
                return context.getResources().getColor(R.color.colorHighPriority);
        }
        return context.getResources().getColor(R.color.colorNoPriority);
    }
    }

