package guthboss.com.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Lamp3 extends AppCompatActivity {
    Spinner colourPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp3);

        colourPicker = (Spinner)findViewById(R.id.spinner);

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.colours, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colourPicker.setAdapter(spinnerAdapter);
    }
}
