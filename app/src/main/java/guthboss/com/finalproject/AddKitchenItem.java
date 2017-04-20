package guthboss.com.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddKitchenItem extends AppCompatActivity {
    Button submitType;
    Spinner applianceType;
    EditText nameOfAppliance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kitchen_item);

        submitType = (Button)findViewById(R.id.submit_new_item);
        applianceType = (Spinner)findViewById(R.id.appliance_type);
        nameOfAppliance = (EditText)findViewById(R.id.name_of_item);

        submitType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                if(applianceType.getSelectedItem().toString().matches("Fridge"))
                {

                   result.putExtra("Name ",nameOfAppliance.getText().toString());
                    setResult(0,result);
                    finish();
                }
                else if(applianceType.getSelectedItem().toString().matches("Freezer"))
                {

                    result.putExtra("Name ",nameOfAppliance.getText().toString());
                    setResult(1,result);
                    finish();
                }

                else if(applianceType.getSelectedItem().toString().matches("Light"))
                {

                    result.putExtra("Name ",nameOfAppliance.getText().toString());
                    setResult(2,result);
                    finish();
                }

                else if(applianceType.getSelectedItem().toString().matches("Microwave"))
                {

                    result.putExtra("Name ",nameOfAppliance.getText().toString());
                    setResult(3,result);
                    finish();
                }


            }
        });


    }
}
