package guthboss.com.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class HomeActivity extends AppCompatActivity {
    ListView items;
    ArrayList<String> livingRoomItems = new ArrayList<>();
    EditText addItemText;
    Button addItemBtn;
    private Context ctx;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ItemAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ctx = this;
        addItemText = (EditText)findViewById(R.id.itemText);
        addItemBtn = (Button)findViewById(R.id.addItem);
        items = (ListView)findViewById(R.id.HomeItems);



        //DATABASE
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        //Execute Query for all items in DB w/Times accessed
        cursor = db.query(DatabaseHelper.LIVING_ROOM_TABLE, new String[]{"TimesAccessed","Item"}, null,null,null,null,null);
        Log.i("HomeActivity","Cursor's column count= "+cursor.getColumnCount());


        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            livingRoomItems.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEMS)));
            Log.i("HomeActivity", "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEMS)));
            //Log.i("HomeActivity", "SQL MESSAGE:" + cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
            cursor.moveToNext();
        }

        homeAdapter = new ItemAdapter(this);
        items.setAdapter(homeAdapter);

        //Select Items from standard list
        //Needs to be altered when / If database is required
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //This implementation feels like cheating
                //Don't have to move cursor. In correct position
                //Because of the above method
                position = (int) homeAdapter.getItemID(livingRoomItems.get(position));

                switch(position){
                    case 1: //Lamp 1
                        startActivity(new Intent(ctx, Lamp1.class));
                        break;
                    case 2: //Lamp 2
                        startActivity(new Intent(ctx, Lamp2.class));
                        break;
                    case 3: //Lamp 3
                        startActivity(new Intent(ctx, Lamp3.class));
                        break;
                    case 4: // Television
                        startActivity(new Intent(ctx, Television.class));
                        break;
                    case 5: //Blinds
                       // ContentValues cv = new ContentValues();
                       // cv.put(DatabaseHelper.TIMES_ACCESSED, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TIMES_ACCESSED))+1);
                       // Log.i("HomeActivity", "Times Accessed now = "+cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TIMES_ACCESSED)));
                        startActivity(new Intent(ctx, Blinds.class));
                        break;
                    default: //For added items
                        Toast toast = Toast.makeText(HomeActivity.this, "Item not yet implemented", Toast.LENGTH_LONG);
                        toast.show();
                }
            }
        });

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livingRoomItems.add(addItemText.getText().toString());
                homeAdapter.notifyDataSetChanged();

                //Add item to DB
                ContentValues contentv = new ContentValues();
                contentv.put("Item", addItemText.getText().toString());
                db.insert(dbHelper.LIVING_ROOM_TABLE,"NullPlaceHolder",contentv);
                addItemText.setText("");
            }
        });



    }


    public class ItemAdapter extends ArrayAdapter<String>{

        public ItemAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return livingRoomItems.size();
        }

        public String getItem(int position){
            return livingRoomItems.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = HomeActivity.this.getLayoutInflater();

            View result = null;
            result = inflater.inflate(R.layout.home_items, null);
            TextView textItem = (TextView) result.findViewById(R.id.homeListItems);

            //This code will be modified for adding from database
            textItem.setText(getItem(position));

            return result;


        }
        //Using the ID from the DB for the static items which are all
        //added when DB is initially created, as they will all the same
        //unique ID's
        public long getItemID(String name){
            cursor = db.rawQuery("SELECT "+DatabaseHelper.KEY_ID+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='"+name+"'",null);
            cursor.moveToFirst();
            return cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID));
        }
    }

    @Override
    public void onResume(){
        String removedItem = livingRoomItems.get(2);
        livingRoomItems.remove(2);
        livingRoomItems.add(removedItem);
        homeAdapter.notifyDataSetChanged();
        super.onResume();
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

        return super.onOptionsItemSelected(item);
    }

}
