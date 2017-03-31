package guthboss.com.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mainList = (ListView) findViewById(R.id.kitchenList);
        kitchenAppliances.add("Light");
        kitchenAppliances.add("Microwave");
        kitchenAppliances.add("Fridge");
        kitchenAppliances.add("Freezer");

        addAppliance = (EditText)findViewById(R.id.newappliance);
        addButton = (Button)findViewById(R.id.add);
        final ArrayAdapter<String> kitchList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,kitchenAppliances);
        mainList.setAdapter(kitchList);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("id",kitchenAppliances.get(position).toString());
                if(kitchenAppliances.get(position).toString() == "Fridge")
                {
                    startActivity(new Intent(Smart_Kitchen.this,Fridge.class));
                }

                else if(kitchenAppliances.get(position).toString() == "Microwave")
                {
                    startActivity(new Intent(Smart_Kitchen.this,Microwave.class));
                }

                else if(kitchenAppliances.get(position).toString() == "Light")
                {
                    startActivity(new Intent(Smart_Kitchen.this,MainKitchenLight.class));
                }

                else if(kitchenAppliances.get(position).toString() == "Freezer")
                {
                    startActivity(new Intent(Smart_Kitchen.this,Freezer.class));
                }

                else
                {
                    startActivity(new Intent(Smart_Kitchen.this,ComingSoon.class));
                }
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





/*****************************************Action Bar Menu Handling******************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add menu button and items to title bar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       //Get id of item clicked in menu
        int id = item.getItemId();


        if (id == R.id.smrt_kitch) {
            startActivity(new Intent(this,Smart_Kitchen.class));
            return true;
        }
        else if(id == R.id.main)
        {
            startActivity(new Intent(this,MainActivity.class));
            return true;
        }
        else if(id == R.id.smart_home)
        {
            startActivity(new Intent(this,HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }




}


