package zmq.com.tbpatientreferraldoottool.databaseAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zmq162 on 19/10/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("faiz alam");
        db.execSQL(DootDatabaseAdapter.CREATE_TABLE_DOOTDETAIL);
        db.execSQL(DootDatabaseAdapter.CREATE_TABLE_DMCDETAIL);
        db.execSQL(DootDatabaseAdapter.CREATE_TABLE_QUESTION);
        db.execSQL(DootDatabaseAdapter.CREATE_TABLE_PATIENTDETAIL);
        db.execSQL(DootDatabaseAdapter.CREATE_TABLE_PATIENTDETAIL_BUFFER);
        db.execSQL(DootDatabaseAdapter.CREATE_TABLE_DOOTACCOUNT);
        db.execSQL(DootDatabaseAdapter.CREATE_TABLE_DOOTACCOUNT_TRANSACTION);
//        db.execSQL(DootDatabaseAdapter.CREATE_TABLE_TBLEARNING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        onCreate(db);
    }
}
