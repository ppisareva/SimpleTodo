package restaurantapplication.polina.example.com.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.R.id.edit;

public class MainActivity extends AppCompatActivity {
    public static final String NAME = "name";
    ArrayList<String> items;
    ArrayAdapter <String> itemsAdapter;
    ListView lvItems;
    EditText etItem;
    int requestCode = 2017;
    public static final String ID = "id";
    public static final String EDIT = "edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = loadItemsFromFile();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItem.class);
                String itemName = items.get(position);
                intent.putExtra(ID, position);
                intent.putExtra(NAME, itemName);
                startActivityForResult(intent, requestCode);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
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
//done
   public void onAddItem (View v){
       String newItem = etItem.getText().toString();
       etItem.setText("");
       if(!newItem.equals("")){
           items.add(newItem);
           itemsAdapter.notifyDataSetChanged();
       }

   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      Boolean saved =  saveListOfItemsToFile(items);
        System.out.println("Saved to File = "+ saved);
    }

    //done
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==this.requestCode&&resultCode==RESULT_OK){
            String nameChanged = data.getStringExtra(NAME);
            int position  = data.getIntExtra(ID, -1);
            if(position<=0) items.set(position, nameChanged);
            itemsAdapter.notifyDataSetChanged();
        }

    }


// done
        public  ArrayList<String> loadItemsFromFile() {

            FileInputStream fis;
            try {
                fis = openFileInput("data");
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<String> list = (ArrayList<String>) ois.readObject();
                ois.close();
                return list;
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<String>();
            }


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
