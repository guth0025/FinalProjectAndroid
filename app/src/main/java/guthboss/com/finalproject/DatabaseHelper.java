package guthboss.com.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 3/29/2017.
 */
//TODO implement for other tables, once known

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "projectDB";
    public static final int VERSION_NUM = 6;
    public static final String KEY_ID = "_id";
    public static final String TIMES_ACCESSED = "TimesAccessed";
    public static final String ON_OFF = "OnOff";
    public static final String LAST_POSITION = "LastPosition"; //This is a catch all for the position of the blinds, dimmer, and the television channel
    public static final String ITEMS = "Item";
    public static final String LIVING_ROOM_TABLE = "LivingRoomTable";

    //Must put in Default value of 0 to avoid null pointer
    public static final String CREATE_TABLE_MESSAGE = "create table "+LIVING_ROOM_TABLE+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ITEMS+" text, "+TIMES_ACCESSED+ " INTEGER DEFAULT 0, "+ON_OFF+" BOOLEAN DEFAULT TRUE, "+LAST_POSITION+" INTEGER DEFAULT 0);";

    public DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Add initial items to DB
        db.execSQL(CREATE_TABLE_MESSAGE);
        db.execSQL("insert into "+LIVING_ROOM_TABLE+" ("+ITEMS+") Values ('Lamp1')");
        db.execSQL("insert into "+LIVING_ROOM_TABLE+" ("+ITEMS+") Values ('Lamp2')");
        db.execSQL("insert into "+LIVING_ROOM_TABLE+" ("+ITEMS+") Values ('Lamp3')");
        db.execSQL("insert into "+LIVING_ROOM_TABLE+" ("+ITEMS+") Values ('Television')");
        db.execSQL("insert into "+LIVING_ROOM_TABLE+" ("+ITEMS+") Values ('Blinds')");

        Log.i("DatabaseHelper", "Calling onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+LIVING_ROOM_TABLE);
        onCreate(db);
        Log.i("DatabaseHelper", "Calling onUpgrade");

    }


}
