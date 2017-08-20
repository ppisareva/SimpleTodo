package restaurantapplication.polina.example.com.simpletodo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * Created by polina on 8/17/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsHolder> {

    Context context;
    List <Task> itemsList;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    int position;

    public ItemAdapter(@NonNull Context context, @NonNull List<Task> objects) {
        this.context=context;
        itemsList=objects;
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
    public void onBindViewHolder(final ItemsHolder holder, final int position) {
        Task task = itemsList.get(position);
        holder.itemName.setText(task.getTaskName());
        holder.itemDate.setText(setDate(task.getDueDate()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
        if(task.isTaskCompletion()==0) {
            holder.itemLayout.setBackgroundColor(setPriority(task.getTaskPriority()));
        } else  {
            holder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.colorDoneBackground));
            holder.itemDate.setTextColor(context.getResources().getColor(R.color.colorDoneText));
            holder.itemDate.setTypeface(null, Typeface.ITALIC);
            holder.itemName.setTextColor(context.getResources().getColor(R.color.colorDoneText));
            holder.itemName.setTypeface(null, Typeface.ITALIC);
        }
    }

    @Override
    public void onViewRecycled(ItemsHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    class ItemsHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView itemName;
        TextView itemDate;
        LinearLayout itemLayout;
        public static final int EDIT = 34;
        public static final int DELETE = 56;

        public ItemsHolder(View itemView) {
            super(itemView);
            itemName = (TextView)itemView.findViewById(R.id.taskNameList);
            itemDate = (TextView)itemView.findViewById(R.id.taskDateList);
            itemLayout = (LinearLayout)itemView.findViewById(R.id.taskLayoutList);
            itemView.setOnCreateContextMenuListener(this);
        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, EDIT, Menu.NONE, R.string.action_edit);
            menu.add(Menu.NONE, DELETE, Menu.NONE, R.string.action_delete);

        }
    }


    private String setDate (String dt){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
            case Task.PRIORITY_HIGH:
                return context.getResources().getColor(R.color.colorHighPriority);
            case Task.PRIORITY_NORMAL:
                return context.getResources().getColor(R.color.colorNormalPriority);
            case Task.PRIORITY_LOW:
                return context.getResources().getColor(R.color.colorLowPriority);
        }
        return context.getResources().getColor(R.color.colorNoPriority);
    }
    }

