package guthboss.com.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Main class for the Smart Living Room
public class HomeActivity extends AppCompatActivity {
    protected ListView items;
    protected ArrayList<String> livingRoomItems = new ArrayList<>();
    protected EditText addItemText;
    protected Button addItemBtn;
    private Context ctx;
    protected DatabaseHelper dbHelper;
    protected SQLiteDatabase db;
    protected Cursor cursor;
    protected ItemAdapter homeAdapter;
    protected static boolean flExists;
    protected Snackbar snack;
    protected static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Create and load the toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ctx = this;
        addItemText = (EditText)findViewById(R.id.itemText);
        addItemBtn = (Button)findViewById(R.id.addItem);
        items = (ListView)findViewById(R.id.HomeItems);

        //Check for frameLayout - Tablet
        flExists = (findViewById(R.id.frameLayout) != null);



        //DATABASE
        dbHelper = new DatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();

        //Call the AsyncTask to load the DB
        DBAsyncTask dbAsyncTask = new DBAsyncTask();
        dbAsyncTask.execute("any");


        homeAdapter = new ItemAdapter(this);
        items.setAdapter(homeAdapter);

        //Select Items from standard list
        //Needs to be altered when / If database is required
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Don't have to move cursor. In correct position
                //Because of the above method
                position = (int) homeAdapter.getItemID(livingRoomItems.get(position));
                name = livingRoomItems.get(position-1).toString();


                //
                if(flExists){
                    //Create a new bundle to pass and put in the position so we can
                    //choose the right fragment layout
                    Bundle bun = new Bundle();
                    bun.putString("name", name);
                    bun.putInt("position",position);
                    LivingRoomFragment lvFragment = new LivingRoomFragment();
                    lvFragment.setArguments(bun);
                    android.app.Fragment frag = getFragmentManager().findFragmentByTag("OG_Fragment");

                    if(frag instanceof LivingRoomFragment){
                        //Replace old fragment w/new fragment
                        getFragmentManager().beginTransaction().remove(frag).commit();
                        getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frameLayout, lvFragment, "OG_Fragment").commit();
                    }else{
                        getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frameLayout, lvFragment, "OG_Fragment").commit();
                    }
                } else{
                    //Call the empty frame Layout class (LivingRoomDetails)
                    Intent intent = new Intent(HomeActivity.this, LivingRoomDetails.class);
                    intent.putExtra("position",position);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }
                if(position > 5){
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

    //AsyncTask
    //Retrieve items from DB and populate the list
    class DBAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressBar dbProgress = (ProgressBar)findViewById(R.id.progressBar2);

        @Override
        protected String doInBackground(String... params) {
            //Execute Query for all items in DB w/Times accessed
            cursor = db.query(DatabaseHelper.LIVING_ROOM_TABLE, new String[]{"Item"}, null,null,null,null,null);

            //Used to calculate the progress for progress bar
            int time = 100/cursor.getCount();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                SystemClock.sleep(500);
                publishProgress(time);
                livingRoomItems.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEMS)));
                cursor.moveToNext();
                time+=time;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            dbProgress.setVisibility(View.VISIBLE);
            super.onProgressUpdate(progress);
            dbProgress.setProgress(progress[0]);

        }

        @Override
        protected void onPostExecute(String results){
            //Needed in order to populate the List
            homeAdapter.notifyDataSetChanged();
            dbProgress.setVisibility(View.INVISIBLE);
            snack.make(findViewById(R.id.HomeItems), "Database Loaded", Snackbar.LENGTH_LONG).setAction("Action", null).show();


        }
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
        //added when DB is initially created, as they will all have a constant
        //unique ID
        public long getItemID(String name){
            cursor = db.rawQuery("SELECT "+DatabaseHelper.KEY_ID+" FROM "+DatabaseHelper.LIVING_ROOM_TABLE+" WHERE "+DatabaseHelper.ITEMS+" ='"+name+"'",null);
            cursor.moveToFirst();
            return cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID));
        }
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
            if(flExists){
                //Create a new bundle to pass and put in the position so we can
                //choose the right fragment layout
                Bundle bun = new Bundle();
                bun.putInt("position",0);
                LivingRoomFragment lvFragment = new LivingRoomFragment();
                lvFragment.setArguments(bun);
                android.app.Fragment frag = getFragmentManager().findFragmentByTag("OG_Fragment");
                if(frag instanceof LivingRoomFragment){
                    //Replace old fragment w/new fragment
                    getFragmentManager().beginTransaction().remove(frag).commit();
                    getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frameLayout, lvFragment, "OG_Fragment").commit();
                }else{
                    getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frameLayout, lvFragment, "OG_Fragment").commit();
                }
            } else{
                //Call the empty frame Layout class (LivingRoomDetails)
                Intent intent = new Intent(HomeActivity.this, LivingRoomDetails.class);
                intent.putExtra("position",0);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        homeAdapter.notifyDataSetChanged();

        super.onResume();
    }

}
