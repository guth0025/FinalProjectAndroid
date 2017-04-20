package guthboss.com.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


 Button house;
        Button livingRoom;
        Button kitchen;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //add custom actionbar
            Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);
            //Launch Smart kitchen
            kitchen = (Button)findViewById(R.id.smart_kitchen);
            //Launch to HomeActivity
            livingRoom = (Button)findViewById(R.id.homeActivity);

        house = (Button)findViewById(R.id.house_settings);
            livingRoom.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            });

            kitchen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,Smart_Kitchen.class));
                }
            });
house = (Button)findViewById(R.id.house_settings);

        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HouseSettingMain.class));
            }
        });
        }

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
        else if(id == R.id.help_menu)
        {
            //TODO FILL OUT HELP
        }

        return super.onOptionsItemSelected(item);

    }

}
