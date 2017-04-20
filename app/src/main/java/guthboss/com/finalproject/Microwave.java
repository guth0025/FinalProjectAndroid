package guthboss.com.finalproject;
/**
 * Class: Microwave
 *
 * Description: Uses AsyncTask to count cook time in the background.
 *
 * Author Noah Guthrie
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class Microwave extends AppCompatActivity {
    EditText enterCookTime;
    TextView cookTime;
    MicrowaveTimer timer;
    String stopTime;
    Vibrator vibrate;
    ProgressBar progBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_microwave);

        TextView nameText = (TextView)findViewById(R.id.name);

        Bundle bundle = getIntent().getExtras();

        String name = bundle.getString("name");

        nameText.setText(name);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        vibrate = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        Button cookButton = (Button)findViewById(R.id.cook_button);

        Button restButton = (Button)findViewById(R.id.microwave_reset_button);

        Button stopButton = (Button)findViewById(R.id.microwave_stop_button);

        progBar = (ProgressBar) findViewById(R.id.progress_counter);

        cookTime = (TextView)findViewById(R.id.cook_time);

        enterCookTime =(EditText)findViewById(R.id.enter_cook_time);

/**
 * cook button listener checks to see if the stop button has been pressed if not grabs the input from user and starts cook time.
 * If stop button has been pressed it grabs the time left on the clock when the stop button was pressed and restarts the timer.
 */
        cookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("EnterCookTime: ",enterCookTime.getText().toString());


                if(!enterCookTime.getText().toString().matches("") || stopTime != null)
                {
                        if(stopTime != null)
                        {
                            timer = new MicrowaveTimer();
                            Log.i("StopTime: ",stopTime);
                            progBar.setMax(Integer.parseInt(stopTime));
                            timer.execute(stopTime);
                        }
                        else if(Integer.parseInt(enterCookTime.getText().toString()) >= 0 )
                        {
                            Log.i("CookTimer: ",cookTime.getText().toString());
                            stopTime = enterCookTime.getText().toString();
                            cookTime.setText(enterCookTime.getText());
                            timer = new MicrowaveTimer();
                            progBar.setMax(Integer.parseInt(stopTime));
                                timer.execute(stopTime);

                           }




                    stopTime = null;
                    enterCookTime.setText("");
                    Toast.makeText(Microwave.this,"Cooking",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        enterCookTime.setHint("Enter Time");
                    }
            }
        });

        /**
         * Reset button listener checks to make sure there is either a timer running or a time that was saved by the stop button
         * Resets all timers and times saved
         */

        restButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer != null && !cookTime.getText().toString().matches("0"))
                {
                    timer.cancel(true);
                    cookTime.setText("0");
                    stopTime = null;
                    progBar.setProgress(0);
                    Toast.makeText(Microwave.this,"Reset",Toast.LENGTH_SHORT).show();
                }

            }
        });
/**
 *  Stop button checks to make sure that a timer is running if so it grabs the time left and stops the timer
 */
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(timer != null && !cookTime.getText().toString().matches("0")){

                    timer.cancel(true);
                    stopTime = cookTime.getText().toString();
                    cookTime.setText("Paused");
                    Toast.makeText(Microwave.this,"Stopped",Toast.LENGTH_SHORT).show();

                }
            }
        });




    }

    /**
     * MicrowaveTimer() extends AsyncTask running all of the timer counting in the background
     */
    public class MicrowaveTimer extends AsyncTask<String,Integer,String>
    {
        Integer seconds;
        @Override
        protected String doInBackground(String... params) {
            seconds = Integer.parseInt(params[0]);

            for(int i = seconds;i>=0;i--)
            {

                try {
                    Thread.sleep(1000);
                    publishProgress(i-1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if(isCancelled())
                {
                    break;
                }
            }



            return "Success";
        }

        @Override
        protected void onProgressUpdate(Integer... update)//everytime onProgressUpdate is called this runs its a way to keep in touch with main thread while running in background
        {
            super.onProgressUpdate(update);
            if(Integer.parseInt(update[0].toString())>=0)
            cookTime.setText(update[0].toString());
            //int currentProgress = update[0]%seconds;
            progBar.setProgress(update[0]);
        }

        @Override
        protected void onPostExecute(String check)
        {
            if(check.matches("Success"))
            {
                vibrate.vibrate(2000);
            }

            if(check != "Success")
            {
                stopTime = cookTime.getText().toString();
            }
        }
    }

    /*****************************************Action Bar Menu Handling******************************/

    /**
     * Creates a custom actionbar in this activity with a clickable menu button to navigate to other activities
     * @param menu
     * @return
     */
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Microwave.this);
            builder.setMessage("Microwave: Enter amount of time in seconds you wish to cook for. Start cooking by pressing cook button. Author: Noah Guthrie  Version: 1").setTitle("Microwave Help")
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
