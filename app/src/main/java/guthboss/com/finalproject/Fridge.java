package guthboss.com.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class Fridge extends AppCompatActivity {
    SeekBar setTemp;
    TextView currentTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        setTemp = (SeekBar)findViewById(R.id.set_fridge_temp);
        currentTemp = (TextView)findViewById(R.id.fridge_temp);

        setTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Progress: ", String.valueOf(progress));
                currentTemp.setText(+progress+" Degrees Farenheight");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }



    /*****************************************Action Bar Menu Handling******************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add menu button and items to title bar
        menu.add(0,0,0,"Fridge Help");
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
        else if(id == 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Fridge.this);
            builder.setMessage("Fridge: Adjust temperature of fridge by sliding dial right or left").setTitle("Fridge Help")
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
