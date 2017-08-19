package restaurantapplication.polina.example.com.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String NAME = "name";

    List<Item> items = new ArrayList<>();
    ItemAdapter adapter;
    EditText etItem;
    RecyclerView rvItems;
    int requestCode = 2017;
    public static final String ID = "id";
    public static final String EDIT = "edit";
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        rvItems = (RecyclerView) findViewById(R.id.rvItems);
        items = loadItemsFromDatabase();
        rvItems.setHasFixedSize(true);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(this, items);
        rvItems.setAdapter(adapter);

        etItem = (EditText) findViewById(R.id.etNewItem);
        etItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }
//todo
//done
public void onAddItem (View v){
    String newItem = etItem.getText().toString();
    etItem.setText("");
    if(!newItem.equals("")){
       Item item  = new Item(newItem, "2017-06-24", 1, 0);
       int id = databaseHelper.addItem(item);
        item.setId(id);
        items.add(item);
        adapter.notifyDataSetChanged();
    }

}



        public  List<Item> loadItemsFromDatabase() {
            return databaseHelper.getAllItems();
        }


        public Boolean saveListOfItemsToFile (ArrayList<String> list){
            try {
                FileOutputStream fileOutputStream = openFileOutput("data", Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(list);
                out.close();
                fileOutputStream.close();
                return true;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }

}
