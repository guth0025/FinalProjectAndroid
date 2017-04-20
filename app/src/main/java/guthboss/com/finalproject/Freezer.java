package guthboss.com.finalproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class Freezer extends AppCompatActivity {
    SeekBar setTemp;
    TextView currentTemp;
    DatabaseHelper db;
    SQLiteDatabase writeableDB;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freezer);
        TextView nameText = (TextView)findViewById(R.id.name);
        Bundle bundle = getIntent().getExtras();
        db = new DatabaseHelper(this);
        writeableDB = db.getWritableDatabase();
        String name = bundle.getString("name");
        Integer settings = bundle.getInt("setting");

         id = bundle.getInt("id");
        nameText.setText(name);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        setTemp = (SeekBar)findViewById(R.id.freezer_temp_setting);
        setTemp.setProgress(settings);
        currentTemp = (TextView)findViewById(R.id.fridge_temp);
        setTemperature(settings);






        setTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ContentValues updateTable = new ContentValues();
                Log.i("Progress: ", String.valueOf(progress));
                setTemperature(progress);
                updateTable.put("Setting",progress);
                writeableDB.update(db.KITCHEN_TABLE,updateTable,"_id="+id,null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void setTemperature(int prog)
    {
        currentTemp.setText("-"+prog+" Degrees Farenheight");
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Freezer.this);
            builder.setMessage("Freezer: Adjust temperature of freezer by sliding dial right or left Author: Noah Guthrie  Version: 1").setTitle("Freezer Help")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {

                        }
                    }).show();
        }
        Log.i("ITEM ID ",String.valueOf(item.getItemId()));


        return super.onOptionsItemSelected(item);
    }
}
