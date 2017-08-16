package restaurantapplication.polina.example.com.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class EditItem extends AppCompatActivity {
    int itemId;
    String itemName;
    EditText etItem;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        intent = getIntent();
        itemId = getIntent().getIntExtra(MainActivity.ID, -1);
        itemName = getIntent().getStringExtra(MainActivity.NAME);
        etItem =(EditText) findViewById(R.id.etItemName);
        etItem.setText(itemName);
        etItem.setSelection(itemName.length());

    }

    public void onEditItem (View v){
        intent.putExtra(MainActivity.NAME, etItem.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
