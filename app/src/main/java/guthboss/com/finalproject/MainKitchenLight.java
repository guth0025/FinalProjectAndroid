package guthboss.com.finalproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainKitchenLight extends AppCompatActivity {
    ImageView lightOn;
    ImageView lightOff;
    ImageView dim;
    DatabaseHelper db;
    SQLiteDatabase writeAbleDB;
    ContentValues lightCheck;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_kitchen_light);

        TextView nameText = (TextView)findViewById(R.id.name);

        Bundle bundle = getIntent().getExtras();

        String name = bundle.getString("name");

        Integer settings = bundle.getInt("setting");

        id = bundle.getInt("id");

        nameText.setText(name);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        lightOn = (ImageView)findViewById(R.id.light_on);

        lightOff = (ImageView)findViewById(R.id.light_off);

        dim = (ImageView)findViewById(R.id.dim_image);

        Switch onOff = (Switch)findViewById(R.id.on_off);

        Button dimButton = (Button)findViewById(R.id.dim_button);

        db = new DatabaseHelper(this);

        writeAbleDB = db.getWritableDatabase();

        if(settings == 0)
        {
            dim.animate().alpha(0);
            lightOn.animate().alpha(0);
            lightOff.animate().alpha(1);
            onOff.setChecked(false);
        }
        else if(settings == 1)
        {
            lightOff.animate().alpha(0);
            lightOn.animate().alpha(0);
            dim.animate().alpha(1);
        }

        else if(settings == 2)
        {
            dim.animate().alpha(0);
            lightOff.animate().alpha(0);
            lightOn.animate().alpha(1);
            onOff.setChecked(true);
        }

        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                     lightCheck = new ContentValues();
                    dim.animate().alpha(0);
                    lightOff.animate().alpha(0);
                    lightOn.animate().alpha(1);
                    lightCheck.put("Setting",2);
                    writeAbleDB.update(db.KITCHEN_TABLE,lightCheck,"_id="+id,null);
                    Toast.makeText(MainKitchenLight.this, "Light On", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        lightCheck = new ContentValues();
                        dim.animate().alpha(0);
                        lightOn.animate().alpha(0);
                        lightOff.animate().alpha(1);
                        lightCheck.put("Setting",0);
                        writeAbleDB.update(db.KITCHEN_TABLE,lightCheck,"_id="+id,null);
                        Toast.makeText(MainKitchenLight.this, "Light Off", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        dimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lightCheck = new ContentValues();
                lightOff.animate().alpha(0);
                lightOn.animate().alpha(0);
                dim.animate().alpha(1);
                lightCheck.put("Setting",1);
                writeAbleDB.update(db.KITCHEN_TABLE,lightCheck,"_id="+id,null);
                Toast.makeText(MainKitchenLight.this, "Light Dimmed", Toast.LENGTH_SHORT).show();
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
        else if(id == R.id.help_menu)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainKitchenLight.this);
            builder.setMessage("Light: Switch light on or off by clicking on off switch or dim by pressing dim button. Author: Noah Guthrie  Version: 1").setTitle("Light Help")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {

                        }
                    }).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
