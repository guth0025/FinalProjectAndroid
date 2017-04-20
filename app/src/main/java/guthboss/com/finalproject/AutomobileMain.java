package guthboss.com.finalproject;

/**
 * Created by Gerson on 2017-04-01.
 */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AutomobileMain extends AppCompatActivity {

    ArrayList<String> autoArray = new ArrayList<>();
    ListView autoList;
    Button save;

    protected void  onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        autoList = (ListView) findViewById(R.id.thatList);

        autoArray.add("Radio");
        autoArray.add("GPS");
        autoArray.add("Lights");
        autoArray.add("Temperature");


        final ArrayAdapter<String> thatWay = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autoArray);
        autoList.setAdapter(thatWay);

        autoList.setOnItemClickListener(new  AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("id", autoArray.get(position).toString());

                if (autoArray.get(position).toString() == "Radio") {
                    startActivity(new Intent(AutomobileMain.this, Radio.class));
                } else if (autoArray.get(position).toString() == "GPS") {
                    startActivity(new Intent(AutomobileMain.this, AutomobileGPS.class));
                } else if (autoArray.get(position).toString() == "Lights") {
                    startActivity(new Intent(AutomobileMain.this, AutomobileLights.class));
                } else if (autoArray.get(position).toString() == "Temperature") {
                    startActivity(new Intent(AutomobileMain.this, AutomobileTemp.class));
                }

            }

        });

    }



}
