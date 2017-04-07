package guthboss.com.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LivingRoomDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room_details);

        LivingRoomFragment lvFragment = new LivingRoomFragment();
        Bundle bun = getIntent().getExtras();
        lvFragment.setArguments(bun);
        getFragmentManager().beginTransaction().add(R.id.livingFrag, lvFragment).commit();




    }
}
