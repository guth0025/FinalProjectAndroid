package guthboss.com.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Radio extends AppCompatActivity {

    private SQLiteDatabase autoDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        final EditText rs1 = (EditText) findViewById(R.id.Station1);
        final EditText rs2 = (EditText) findViewById(R.id.Station2);
        final EditText rs3 = (EditText) findViewById(R.id.Station3);
        final EditText rs4 = (EditText) findViewById(R.id.Station4);
        final EditText rs5 = (EditText) findViewById(R.id.Station5);
        final EditText rs6 = (EditText) findViewById(R.id.Station6);

        Button btn = (Button) findViewById(R.id.radioBtn);

        DatabaseHelper autodbHelper = new DatabaseHelper(this);
        autoDB = autodbHelper.getWritableDatabase();
        final ContentValues autoTempValues = new ContentValues();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert the radio stations into the database
                autoTempValues.put(DatabaseHelper.KEY_ID,rs1.getText().toString() );
                autoTempValues.put(DatabaseHelper.ITEMS,"Station 1");
                autoDB.insert(DatabaseHelper.DATABASE_NAME, "", autoTempValues);

                autoTempValues.put(DatabaseHelper.KEY_ID,rs2.getText().toString() );
                autoTempValues.put(DatabaseHelper.ITEMS,"Station 2");
                autoDB.insert(DatabaseHelper.DATABASE_NAME, "", autoTempValues);

                autoTempValues.put(DatabaseHelper.KEY_ID,rs3.getText().toString() );
                autoTempValues.put(DatabaseHelper.ITEMS,"Station 3");
                autoDB.insert(DatabaseHelper.DATABASE_NAME, "", autoTempValues);

                autoTempValues.put(DatabaseHelper.KEY_ID,rs4.getText().toString() );
                autoTempValues.put(DatabaseHelper.ITEMS,"Station 4");
                autoDB.insert(DatabaseHelper.DATABASE_NAME, "", autoTempValues);

                autoTempValues.put(DatabaseHelper.KEY_ID,rs5.getText().toString() );
                autoTempValues.put(DatabaseHelper.ITEMS,"Station 5");
                autoDB.insert(DatabaseHelper.DATABASE_NAME, "", autoTempValues);

                autoTempValues.put(DatabaseHelper.KEY_ID,rs6.getText().toString() );
                autoTempValues.put(DatabaseHelper.ITEMS,"Station 6");
                autoDB.insert(DatabaseHelper.DATABASE_NAME, "", autoTempValues);

                Cursor results = autoDB.query(false,DatabaseHelper.DATABASE_NAME,new String[]{DatabaseHelper.KEY_ID,DatabaseHelper.ITEMS,DatabaseHelper.KEY_ID},null,
                        null,null,null,null,null
                );

                int messageIndex = results.getColumnIndex(DatabaseHelper.KEY_ID);
                results.moveToFirst();
                while(!results.isAfterLast()) {

                    results.moveToNext();
                }
                for(int num = 0 ;num<results.getColumnCount();num++)

                    Toast.makeText(Radio.this, getString(R.string.autoSuccessful), Toast.LENGTH_LONG).show();
            }
        });

    }

}
