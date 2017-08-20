package restaurantapplication.polina.example.com.simpletodo;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  implements EditAddTaskDialogFragment.EditAddTaskDialogListener{

    List<Task> listTasks = new ArrayList<>();
    ItemAdapter adapter;
    RecyclerView rvTasks;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle(R.string.app_new_name);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorToolBar));
        setSupportActionBar(myToolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new EditAddTaskDialogFragment();
                dialog.show(getSupportFragmentManager(), getResources().getString(R.string.taskFragment));
            }
        });
        databaseHelper = new DatabaseHelper(this);
        rvTasks = (RecyclerView) findViewById(R.id.rvTasks);
        listTasks = loadItemsFromDatabase();
        rvTasks.setHasFixedSize(true);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(rvTasks);
        adapter = new ItemAdapter(this, listTasks);
        rvTasks.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position;
        Task task;
        try {
            position = adapter.getPosition();
            task = listTasks.get(position);
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case ItemAdapter.ItemsHolder.DELETE:
                databaseHelper.deleteContact(task);
                listTasks.remove(position);
                adapter.notifyDataSetChanged();
                break;
            case ItemAdapter.ItemsHolder.EDIT:
                DialogFragment dialog = EditAddTaskDialogFragment.newInstance(task, position);
                dialog.show(getSupportFragmentManager(), getResources().getString(R.string.taskFragment));
                break;
        }
        return super.onContextItemSelected(item);
    }

    public  List<Task> loadItemsFromDatabase() {
            return databaseHelper.getAllTasks();
        }


    @Override
    public void onDialogPositiveClick(Task task, int position) {
        if(position==-1){
            listTasks.add(task);
        } else {
            listTasks.set(position, task);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }
}
