package guthboss.com.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ToggleButton;

/**
 * Created by user on 4/6/2017.
 */

public class LivingRoomFragment extends Fragment {
    static int position;
    View rootView;
    Context ctx;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    public static boolean onOff;
    ToggleButton tg;


    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        Bundle bun = getArguments();
        position = bun.getInt("position");
        ctx = getActivity().getApplicationContext();


        dbHelper = new DatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        switch(position){
            case 1: //Lamp1
                rootView = inflater.inflate(R.layout.activity_lamp1, container, false);

                tg = (ToggleButton)rootView.findViewById(R.id.lamp1Toggle);

                //Set the button value to the stored DB value
                cursor = db.rawQuery("SELECT "+DatabaseHelper.ON_OFF+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='Lamp1'",null);
                cursor.moveToFirst();
                onOff = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ON_OFF)));
                tg.setChecked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ON_OFF))));

                //Update the DB with value when button is toggled
                tg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        if (tg.isChecked()){
                            ContentValues contentVal = new ContentValues();
                            contentVal.put(DatabaseHelper.ON_OFF, "TRUE");
                            db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=1",null);
                        } else {
                            ContentValues contentVal = new ContentValues();
                            contentVal.put(DatabaseHelper.ON_OFF, "FALSE");
                            db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=1",null);
                        }

                        // Create the AlertDialog
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        new CustomDialog().show(ft, "A tag");

                    }
                });

                break;
            case 2: //Lamp 2
                rootView = inflater.inflate(R.layout.activity_lamp2, container, false);

                SeekBar seekbar = (SeekBar)rootView.findViewById(R.id.lamp2Seek);

                //Set the SeekBar value to the stored DB value
                cursor = db.rawQuery("SELECT "+DatabaseHelper.LAST_POSITION+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='Lamp2'",null);
                cursor.moveToFirst();
                int lastPosition = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LAST_POSITION));
                seekbar.setProgress(lastPosition);

                //Update the DB with the value when slider is moved
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        ContentValues contentVal = new ContentValues();
                        contentVal.put(DatabaseHelper.LAST_POSITION, seekBar.getProgress());
                        db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=2",null);
                    }
                });
                break;

            case 3: //Lamp 3
                rootView = inflater.inflate(R.layout.activity_lamp3, container, false);
                final Spinner colourPicker = (Spinner)rootView.findViewById(R.id.spinner);
                seekbar = (SeekBar)rootView.findViewById(R.id.seekBar2);


                //Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(ctx, R.array.colours, android.R.layout.simple_spinner_dropdown_item);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                colourPicker.setAdapter(spinnerAdapter);

                //Set the SeekBar and Colour values to the stored DB value
                cursor = db.rawQuery("SELECT "+DatabaseHelper.LAST_POSITION+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='Lamp3'",null);
                cursor.moveToFirst();
                lastPosition = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LAST_POSITION));
                seekbar.setProgress(lastPosition);

                cursor = db.rawQuery("SELECT "+DatabaseHelper.LAST_COLOUR+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='Lamp3'",null);
                cursor.moveToFirst();
                int lastColour = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LAST_COLOUR));






                colourPicker.setSelection(lastColour);

                //Update the DB with the value when slider is moved
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        ContentValues contentVal = new ContentValues();
                        contentVal.put(DatabaseHelper.LAST_POSITION, seekBar.getProgress());
                        db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=3",null);
                    }
                });

                //Update DB with the value when colour is picked
                colourPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContentValues contentVal = new ContentValues();
                        contentVal.put(DatabaseHelper.LAST_COLOUR, position);
                        db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=3", null);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
                break;

            case 4: // Television
                rootView = inflater.inflate(R.layout.activity_television, container, false);
                final EditText channel = (EditText)rootView.findViewById(R.id.channelText);
                Button enterChannel = (Button)rootView.findViewById(R.id.channel);
                tg = (ToggleButton)rootView.findViewById(R.id.TVOnOff);




                //Set the channel selected based on the last one from the DB
                cursor = db.rawQuery("SELECT "+DatabaseHelper.LAST_POSITION+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='Television'",null);
                cursor.moveToFirst();
                lastPosition = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LAST_POSITION));
                channel.setText(lastPosition+"");

                enterChannel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues contentVal = new ContentValues();
                        if(channel.getText().toString() == "" || channel.getText().toString().isEmpty()){
                            contentVal.put(DatabaseHelper.LAST_POSITION, 0);
                            db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=4",null);
                        }else{
                            contentVal.put(DatabaseHelper.LAST_POSITION, Integer.parseInt(channel.getText().toString()));
                            db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=4",null);
                        }
                    }
                });




                //Set the button value to the stored DB value
                cursor = db.rawQuery("SELECT "+DatabaseHelper.ON_OFF+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='Television'",null);
                cursor.moveToFirst();
                onOff = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ON_OFF)));
                tg.setChecked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ON_OFF))));

                //Update the DB with value when button is toggled
                tg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        if (tg.isChecked()){
                            ContentValues contentVal = new ContentValues();
                            contentVal.put(DatabaseHelper.ON_OFF, "TRUE");
                            db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=4",null);
                        } else {
                            ContentValues contentVal = new ContentValues();
                            contentVal.put(DatabaseHelper.ON_OFF, "FALSE");
                            db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=4",null);
                        }

                        // Create the AlertDialog
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        new CustomDialog().show(ft, "A tag");

                    }
                });

                break;
            case 5: //Blinds
                rootView = inflater.inflate(R.layout.activity_blinds, container, false);
                seekbar = (SeekBar)rootView.findViewById(R.id.blindsBar);



                //Set the SeekBar value to the stored DB value
                cursor = db.rawQuery("SELECT "+DatabaseHelper.LAST_POSITION+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='Blinds'",null);
                cursor.moveToFirst();
                lastPosition = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LAST_POSITION));
                seekbar.setProgress(lastPosition);

                //Update the DB with the value when slider is moved
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        ContentValues contentVal = new ContentValues();
                        contentVal.put(DatabaseHelper.LAST_POSITION, seekBar.getProgress());
                        db.update(DatabaseHelper.LIVING_ROOM_TABLE, contentVal, "_id=5",null);
                    }
                });


                break;
            default: //For added items
                rootView = inflater.inflate(R.layout.activity_not_implemented, container, false);
        }
        return rootView;
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Add menu button and items to title bar
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Get id of item clicked in menu
        int id = item.getItemId();


        if (id == R.id.smrt_kitch) {
            startActivity(new Intent(ctx,Smart_Kitchen.class));
            return true;
        }
        else if(id == R.id.main)
        {
            startActivity(new Intent(ctx,MainActivity.class));
            return true;
        }
        else if(id == R.id.smart_home)
        {
            startActivity(new Intent(ctx,HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


public static class CustomDialog extends DialogFragment{
    @Override
        public Dialog onCreateDialog(Bundle saved)
        {

            return returnDialog(position);

        }

        public Dialog returnDialog(int position){
            switch(position){
                case 1: //Lamp1
                    if (onOff == false){
                        onOff = true;
                        return new AlertDialog.Builder(getActivity()).setTitle("Turned Lamp On").setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing
                            }
                        }).create();
                    } else{
                        onOff = false;
                        return new AlertDialog.Builder(getActivity()).setTitle("Turned Lamp Off").setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing
                            }
                        }).create();
                    }
                case 4: // Television
                    if (onOff == false){
                        onOff = true;
                        return new AlertDialog.Builder(getActivity()).setTitle("Turned Television On").setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing
                            }
                        }).create();
                    } else{
                        onOff = false;
                        return new AlertDialog.Builder(getActivity()).setTitle("Turned Television Off").setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing
                            }
                        }).create();
                    }
                case 5: //Blinds
                    break;
                default: //For added items
            }
            return null;



        }

    }
}
