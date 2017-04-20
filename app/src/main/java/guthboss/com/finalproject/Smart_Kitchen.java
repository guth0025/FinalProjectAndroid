package guthboss.com.finalproject;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class Smart_Kitchen extends AppCompatActivity {
    ListView mainList;
    ArrayList<String> kitchenAppliances = new ArrayList<String>();
    Button addButton;
    DatabaseHelper db;
    SQLiteDatabase writeableDB;
    Cursor cursor;
    ArrayAdapter<String> kitchList;
    Boolean undo = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_smart__kitchen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        mainList = (ListView) findViewById(R.id.kitchenList);

        db = new DatabaseHelper(this);

        writeableDB = db.getWritableDatabase();

        cursor = writeableDB.rawQuery("SELECT * FROM KitchenTable;",null);//Cursor uses this query to retrive data from db

        addDatabaseToArray();

        kitchList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kitchenAppliances);//Add array list to list view

        addButton = (Button) findViewById(R.id.add);

        mainList.setAdapter(kitchList);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ID", kitchenAppliances.get(position).toString());

                getType(kitchenAppliances.get(position));

            }
        });

        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {







                Snackbar.make(findViewById(android.R.id.content), "Removing "+kitchenAppliances.get(position), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                undo = true;
                                Toast.makeText(Smart_Kitchen.this, "Delete Undo", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(Color.RED).addCallback(new Snackbar.Callback()
                         {

                             @Override
                             public void onDismissed(Snackbar snackbar, int event) {

                                if(!undo)
                                {
                                    writeableDB.execSQL("DELETE FROM "+DatabaseHelper.KITCHEN_TABLE+" WHERE "+DatabaseHelper.KITCHEN_NAME+" = '" + kitchenAppliances.get(position) + "'");
                                    kitchenAppliances.remove(position);
                                    kitchList.notifyDataSetChanged();
                                    Toast.makeText(Smart_Kitchen.this,"Removed", Toast.LENGTH_LONG).show();
                                }
                                else
                                    {
                                        undo = false;
                                    }

                             }

                            }).show();


               /* AlertDialog.Builder builder = new AlertDialog.Builder(Smart_Kitchen.this);
                builder.setMessage("Would you like to delete "+kitchenAppliances.get(position)+"?").setTitle("Delete?")
                        .setPositiveButton("OK",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {



                            }
                        }

                        ).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                        Toast.makeText(Smart_Kitchen.this, "Canceled", Toast.LENGTH_LONG).show();
                                    }
                                }


                ).show();*/

                return true;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItem = new Intent(getApplicationContext(),AddKitchenItem.class);
                startActivityForResult(addItem,4);

            }
        });





    }


    /**
     * getType() Takes content of kitchenAppliances ArrayList at a given position and finds the corresponding data in the Database
     * @param idPosition
     */
    public void getType(String idPosition) {//Checks which type list item is and sends you to correct activity
        cursor = writeableDB.rawQuery("SELECT * FROM KitchenTable;",null);//Cursor uses this query to retrive data from db
        Intent intent;
        if(cursor != null && cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(0);//get id

                String type = cursor.getString(1);//get message field

                String name = cursor.getString(2);//name

                Integer setting = cursor.getInt(3);//setting either 0,1,2 for (off,dim,on) or temperature for fridge/freezer



                if(name.matches(idPosition))
                {
                   switch(type)
                   {
                       case "Fridge":
                           intent = new Intent(Smart_Kitchen.this,Fridge.class);

                           intent.putExtra("id",id);

                           intent.putExtra("name",name);

                           intent.putExtra("setting",setting);

                           startActivity(intent);

                           break;

                       case "Freezer":

                           intent = new Intent(Smart_Kitchen.this,Freezer.class);

                           intent.putExtra("id",id);

                           intent.putExtra("name",name);

                           intent.putExtra("setting",setting);

                           startActivity(intent);

                           break;

                       case "Microwave":

                           intent = new Intent(Smart_Kitchen.this,Microwave.class);

                           intent.putExtra("id",id);

                           intent.putExtra("name",name);

                           intent.putExtra("setting",setting);

                           startActivity(intent);

                           break;

                       case "MainKitchenLight":

                           intent = new Intent(Smart_Kitchen.this,MainKitchenLight.class);

                           intent.putExtra("id",id);

                           intent.putExtra("name",name);

                           intent.putExtra("setting",setting);

                           startActivity(intent);

                           break;

                       default:
                           Toast.makeText(this,type+ " : Type Not Found", Toast.LENGTH_SHORT).show();
                           break;

                   }


                }


                Log.i("ID",String.valueOf(id));
                Log.i("Space","Space");
                Log.i("Type ",type);
                Log.i("Space","Space");
                Log.i("Name ", name);
                Log.i("Space","Space");
            }while (cursor.moveToNext());

        }
    }


    public void addNewAppliance(String name, String type)//Adds new item to database and array list
    {
        ContentValues newAppliance = new ContentValues();

        kitchenAppliances.add(name);
        newAppliance.put("Type",type);
        newAppliance.put("Name",name); // add user input into message field
        newAppliance.put("Setting",0);
        writeableDB.insert("KitchenTable","",newAppliance); //add user input into messages table

        newAppliance.clear();//clears content value for next input



        kitchList.notifyDataSetChanged();


    }


    @Override
    public void onDestroy()

    {

        super.onDestroy();

        db.close();//close database when app is closed

    }


    //Retrieves result from AddKitchen.class
    public void onActivityResult(int requestCode, int responseCode, Intent data)
    {

        if(responseCode == 0)
        {
            String name = data.getStringExtra("Name ");
            String type = "Fridge";

            addNewAppliance(name,type);

        }

        else if(responseCode == 1)
        {
            String name = data.getStringExtra("Name ");
            String type = "Freezer";

            addNewAppliance(name,type);

        }

        else if(responseCode == 2)
        {
            String name = data.getStringExtra("Name ");
            String type = "MainKitchenLight";

            addNewAppliance(name,type);

        }

        else if(responseCode == 3)
        {
            String name = data.getStringExtra("Name ");
            String type = "Microwave";

            addNewAppliance(name,type);

        }
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
            startActivity(new Intent(this, Smart_Kitchen.class));
            return true;
        } else if (id == R.id.main) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (id == R.id.smart_home) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    public void addDatabaseToArray()
    {
        if(cursor != null && cursor.moveToFirst()) {//Run through Kitchen Table and add all to ArrayList

            do {

                int id = cursor.getInt(0);

                String type = cursor.getString(1);//get message field

                String name = cursor.getString(2);

                if(type != null)

                    kitchenAppliances.add(name);


                Log.i("ID",String.valueOf(id));
                Log.i("space","");
                Log.i("Type ",type);
                Log.i("space","");
                Log.i("Name ", name);
                Log.i("space","");

            }while (cursor.moveToNext());

        }
    }


}


