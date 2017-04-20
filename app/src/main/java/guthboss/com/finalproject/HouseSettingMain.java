package guthboss.com.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HouseSettingMain extends AppCompatActivity {

    ListView settingsList;
    ArrayList<String> houseSettings = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_house_setting_main);

        settingsList = (ListView) findViewById(R.id.settingList);
        houseSettings.add("Garage");
        houseSettings.add("House Temperature");
        houseSettings.add("Weather");

        final ArrayAdapter<String> houseSetList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,houseSettings);
        settingsList.setAdapter(houseSetList);

        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("id", houseSettings.get(position).toString());
                if(houseSettings.get(position).toString() == "Garage")
                {
                    startActivity(new Intent(HouseSettingMain.this, Garage.class));
                }

                else if(houseSettings.get(position).toString() == "House Temperature")
                {
                    startActivity(new Intent(HouseSettingMain.this, HouseTemp.class));
                }

                else if(houseSettings.get(position).toString() == "Weather")
                {
                    startActivity(new Intent(HouseSettingMain.this, Weather.class));
                }
            }
        });

    }
}