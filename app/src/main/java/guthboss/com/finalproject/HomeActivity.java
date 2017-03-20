package guthboss.com.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ctx = this;
        addItemText = (EditText)findViewById(R.id.itemText);
        addItemBtn = (Button)findViewById(R.id.addItem);
        items = (ListView)findViewById(R.id.HomeItems);




        //Change from hard coded to database
        livingRoomItems.add("Lamp 1");
        livingRoomItems.add("Lamp 2");
        livingRoomItems.add("Lamp 3");
        livingRoomItems.add("Television");
        livingRoomItems.add("Blinds");


        final ItemAdapter homeAdapter = new ItemAdapter(this);
        items.setAdapter(homeAdapter);

        //Select Items from standard list
        //Needs to be altered when / If database is required
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Needs to work dynamically as list order will change
                //For now set it as static

                switch(position){
                    case 0: //Lamp 1
                        startActivity(new Intent(ctx, Lamp1.class));
                        break;
                    case 1: //Lamp 2
                        startActivity(new Intent(ctx, Lamp2.class));
                        break;
                    case 2: //Lamp 3
                        startActivity(new Intent(ctx, Lamp3.class));
                        break;
                    case 3: // Television
                        startActivity(new Intent(ctx, Television.class));
                        break;
                    case 4: //Blinds
                        startActivity(new Intent(ctx, Blinds.class));
                        break;
                    default: //For added items
                        Toast toast = Toast.makeText(HomeActivity.this, "Item not yet implemented", Toast.LENGTH_LONG);
                        toast.show();
                }
            }
        });




    }

    //Preparing for Database
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
    }

}
