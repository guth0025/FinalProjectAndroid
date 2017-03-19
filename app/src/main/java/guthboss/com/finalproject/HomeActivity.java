package guthboss.com.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ListView items;
    ArrayList<String> livingRoomItems = new ArrayList<String>();
    EditText addItemText;
    Button addItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addItemText = (EditText)findViewById(R.id.itemText);
        addItemBtn = (Button)findViewById(R.id.addItem);
        items = (ListView)findViewById(R.id.HomeItems);

        livingRoomItems.add("Lamp 1");
        livingRoomItems.add("Lamp 2");
        livingRoomItems.add("Lamp 3");
        livingRoomItems.add("Television");
        livingRoomItems.add("Blinds");

        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,livingRoomItems);
        items.setAdapter(itemsAdapter);




    }
}
