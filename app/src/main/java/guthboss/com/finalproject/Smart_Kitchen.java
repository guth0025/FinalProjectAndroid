package guthboss.com.finalproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class Smart_Kitchen extends AppCompatActivity {
    ListView mainList;
    ArrayList<String> kitchenAppliances = new ArrayList<String>();
    EditText addAppliance;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_smart__kitchen);
        mainList = (ListView) findViewById(R.id.kitchenList);
        kitchenAppliances.add("Stove");
        kitchenAppliances.add("Microwave");
        kitchenAppliances.add("Fridge");
        kitchenAppliances.add("Freezer");
        kitchenAppliances.add("Coffee Maker");
        addAppliance = (EditText)findViewById(R.id.newappliance);
        addButton = (Button)findViewById(R.id.add);
        final ArrayAdapter<String> kitchList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,kitchenAppliances);
        mainList.setAdapter(kitchList);


        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(mainList.getAdapter().getItem(position));



            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             kitchenAppliances.add(addAppliance.getText().toString());
                addAppliance.setText("");
                kitchList.notifyDataSetChanged();
                Toast.makeText(Smart_Kitchen.this,"Added",Toast.LENGTH_LONG).show();
            }
        });


    }




}


