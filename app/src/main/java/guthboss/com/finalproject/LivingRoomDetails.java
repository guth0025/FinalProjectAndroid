package guthboss.com.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

//Intermediary class for inflating fragments
public class LivingRoomDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room_details);

        //Create fragment and pass values
        LivingRoomFragment lvFragment = new LivingRoomFragment();
        Bundle bun = getIntent().getExtras();
        String name = bun.getString("name");
        int pos = bun.getInt("position");
        lvFragment.setArguments(bun);
        getFragmentManager().beginTransaction().add(R.id.livingFrag, lvFragment).commit();

        //Set the toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

    }

}
