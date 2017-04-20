package guthboss.com.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;
import android.widget.Toast;

public class Garage extends AppCompatActivity {

    ToggleButton tb1;
    ToggleButton tb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        tb1 = (ToggleButton) findViewById(R.id.tb_door);
        tb2 = (ToggleButton) findViewById(R.id.tb_light);

        Button button = (Button) findViewById(R.id.garageBack);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Garage.this, HouseSettingMain.class);
                startActivityForResult(intent, 5);
            }
        });

    }

    public void door_click(View view) {

        boolean on = ((ToggleButton) view).isChecked();
        ImageView imageViewDoor = (ImageView) findViewById(R.id.door_OpenClose);
        ImageView imageViewLight = (ImageView) findViewById(R.id.light_OnOff);

        if (on) {
            on = false;
            imageViewDoor.setImageResource(R.drawable.dooropen);
            imageViewLight.setImageResource(R.drawable.lighton);
            Toast.makeText(Garage.this, "Garage Door: Open", Toast.LENGTH_SHORT).show();

        } else {
            on = true;
            imageViewDoor.setImageResource(R.drawable.doorclose);
            imageViewLight.setImageResource(R.drawable.lightoff);
            Toast.makeText(Garage.this, "Garage Door: Close", Toast.LENGTH_SHORT).show();
        }
    }

    public void light_click(View view) {

        boolean on = ((ToggleButton) view).isChecked();
        ImageView imageViewLight2 = (ImageView) findViewById(R.id.light_OnOff);

        if (on) {
            on = false;
            imageViewLight2.setImageResource(R.drawable.lighton);
            Toast.makeText(Garage.this, "Garage Light: On", Toast.LENGTH_SHORT).show();

        } else {
            on = true;
            imageViewLight2.setImageResource(R.drawable.lightoff);
            Toast.makeText(Garage.this, "Garage Light: Off", Toast.LENGTH_SHORT).show();

        }
    }

}
