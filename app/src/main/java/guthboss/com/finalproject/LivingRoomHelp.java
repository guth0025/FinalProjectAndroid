package guthboss.com.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class LivingRoomHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room_help);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
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
            finish();
            startActivity(new Intent(this,Smart_Kitchen.class));
        }
        else if(id == R.id.main)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));

        }
        else if(id == R.id.smart_home)
        {
            finish();
            startActivity(new Intent(this,HomeActivity.class));
        }
        else if(id == R.id.help_menu)
        {
            finish();
            startActivity((new Intent(this,LivingRoomHelp.class)));
        }
        return super.onOptionsItemSelected(item);
    }
}
