package zmq.com.tbpatientreferraldoottool.databaseAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by zmq162 on 19/10/16.
 */
public class DootDatabaseAdapter {

    public static final String DATABASE_NAME = "DOOT_DATABASE";
    private static final int DATABASE_VERSION = 1;

    SQLiteDatabase db;
    DatabaseHelper databaseHelper;

    public static final String TBL_DOOTDETAIL = "dootdetail";
    public static final String TBL_DMCDETAIL = "dmcdetail";
    public static final String TBL_QUESTION = "questiondetail";
    public static final String TBL_PATIENTDETAIL = "patientdetail";
    public static final String TBL_PATIENTDETAIL_BUFFER = "patientdetailbuffer";
    public static final String TBL_DOOT_ACCOUNT = "dootaccount";
    public static final String TBL_DOOT_ACCOUNT_TRANSACTION = "dootaccounttransaction";
    public static final String TBL_TBLEARNING = "tblearning";

    //******Doot detail columns
    public static final String DOOT_ID = "doot_id";
    public static final String DOOT_NAME = "doot_name";
    public static final String VILLAGE_ID = "village_id";
    public static final String VILLAGE_NAME = "village_name";
    public static final String BLOCK_ID = "block_id";
    public static final String BLOCK_NAME = "block_name";
    public static final String DISTRICT_NAME = "district_name";
    public static final String STATE_NAME = "state_name";
    public static final String COUNTRY_NAME = "country_name";
    public static final String LOGIN_KEY = "login_key";

    public static final String CREATE_TABLE_DOOTDETAIL = "CREATE TABLE " + TBL_DOOTDETAIL + " ( " +
            DOOT_ID+" INTEGER,"+DOOT_NAME+" TEXT,"+VILLAGE_ID+" INTEGER,"+VILLAGE_NAME+" TEXT,"+BLOCK_ID+" INTEGER,"+
            BLOCK_NAME+" TEXT,"+DISTRICT_NAME+" TEXT,"+STATE_NAME+" TEXT,"+COUNTRY_NAME+" TEXT,"+LOGIN_KEY+" TEXT" + " ); ";

    //*******Dmc detail column
    public static final String DMC_ID = "dmc_id";
    public static final String DMC_NAME = "dmc_name";

    public static final String CREATE_TABLE_DMCDETAIL = "CREATE TABLE " + TBL_DMCDETAIL + " ( " +
            DMC_ID+" INTEGER,"+DMC_NAME+" TEXT,"+LOGIN_KEY+" TEXT" + " ); ";

    //******QUESTION DETAIL Column
    public static final String QUESTION_ID = "question_id";
    public static final String QUESTION_TEXT = "question_text";

    public static final String CREATE_TABLE_QUESTION = "CREATE TABLE " + TBL_QUESTION + " ( " +
            QUESTION_ID+" INTEGER,"+QUESTION_TEXT+" TEXT,"+LOGIN_KEY+" TEXT" + " ); ";

    //********* PATIENT DETAIL column
    public static final String PATIENT_NAME = "patient_name";
    public static final String PATIENT_AGE = "patient_age";
    public static final String PATIENT_SEX = "patient_sex";
    public static final String PATIENT_ADDRESS = "patient_address";
    public static final String PATIENT_PHONE = "patient_phone";
    public static final String PATIENT_VILLAGE = "patient_village";
    public static final String PATIENT_VILLAGE_ID = "patient_village_id";
    public static final String PATIENT_BLOCK = "patient_block";
    public static final String PATIENT_BLOCK_ID = "patient_block_id";
    public static final String PATIENT_DMC = "patient_dmc";
    public static final String PATIENT_DMC_ID = "patient_dmc_id";
    public static final String PATIENT_DISTRICT = "patient_district";
    public static final String PATIENT_STATE = "patient_state";
    public static final String PATIENT_COUNTRY = "patient_country";
    public static final String PATIENT_QUESTION_ID = "patient_question_id";
    public static final String PATIENT_SCREENING_RESPONSE = "patient_screening_response";
    public static final String PATIENT_SCREENING_AVERAGE = "patient_screening_average";
    public static final String PATIENT_SCREEN_DATE_TIME = "patient_screen_date_time";
    public static final String PATIENT_TEST_STATUS = "patient_test_status";
    public static final String PATIENT_TEST_RESULT = "patient_test_result";
    public static final String CHECKDATAFROM_LOCAL_SERVER = "checkdatafrom_local_server";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";



    public static final String CREATE_TABLE_PATIENTDETAIL = "CREATE TABLE " + TBL_PATIENTDETAIL + " ( " +
            PATIENT_NAME+" TEXT,"+PATIENT_AGE+" INTEGER,"+PATIENT_SEX+" TEXT,"+PATIENT_ADDRESS+" TEXT,"+PATIENT_PHONE+" TEXT,"+
            PATIENT_VILLAGE+" TEXT,"+PATIENT_VILLAGE_ID+" TEXT,"+PATIENT_BLOCK+" TEXT,"+PATIENT_BLOCK_ID+" TEXT,"+
            PATIENT_DMC+" TEXT,"+PATIENT_DMC_ID+" TEXT,"+PATIENT_DISTRICT+" TEXT,"+PATIENT_QUESTION_ID+" TEXT,"+PATIENT_SCREENING_RESPONSE+" TEXT,"+
            PATIENT_SCREENING_AVERAGE+" INTEGER,"+PATIENT_STATE+" TEXT,"+PATIENT_COUNTRY+" TEXT,"+PATIENT_SCREEN_DATE_TIME+" TEXT,"+
            PATIENT_TEST_STATUS+" TEXT,"+PATIENT_TEST_RESULT+" TEXT,"+CHECKDATAFROM_LOCAL_SERVER+" TEXT,"+LONGITUDE+" TEXT,"+LATITUDE+" TEXT,"+
            LOGIN_KEY+" TEXT" + " ); ";

    public static final String CREATE_TABLE_PATIENTDETAIL_BUFFER = "CREATE TABLE " + TBL_PATIENTDETAIL_BUFFER + " ( " +
            PATIENT_NAME+" TEXT,"+PATIENT_AGE+" INTEGER,"+PATIENT_SEX+" TEXT,"+PATIENT_ADDRESS+" TEXT,"+PATIENT_PHONE+" TEXT,"+
            PATIENT_VILLAGE+" TEXT,"+PATIENT_VILLAGE_ID+" TEXT,"+PATIENT_BLOCK+" TEXT,"+PATIENT_BLOCK_ID+" TEXT,"+
            PATIENT_DMC+" TEXT,"+PATIENT_DMC_ID+" TEXT,"+PATIENT_DISTRICT+" TEXT,"+PATIENT_QUESTION_ID+" TEXT,"+PATIENT_SCREENING_RESPONSE+" TEXT,"+
            PATIENT_SCREENING_AVERAGE+" INTEGER,"+PATIENT_STATE+" TEXT,"+PATIENT_COUNTRY+" TEXT,"+PATIENT_SCREEN_DATE_TIME+" TEXT,"+
            PATIENT_TEST_STATUS+" TEXT,"+PATIENT_TEST_RESULT+" TEXT,"+CHECKDATAFROM_LOCAL_SERVER+" TEXT,"+LONGITUDE+" TEXT,"+LATITUDE+" TEXT,"+
            LOGIN_KEY+" TEXT" + " ); ";

    //************* DootAccountColumn****************************
    public static final String DOOT_ACCOUNT_NO = "doot_account_no";
    public static final String DOOT_ACCOUNT_BALANCE = "doot_account_balance";
    public static final String DOOT_ACCOUNT_OPENDATE = "doot_account_opendate";

    public static final String CREATE_TABLE_DOOTACCOUNT = "CREATE TABLE " + TBL_DOOT_ACCOUNT + " ( " +
            DOOT_ACCOUNT_NO+" TEXT,"+DOOT_ACCOUNT_BALANCE+" TEXT,"+DOOT_ACCOUNT_OPENDATE+" TEXT,"+LOGIN_KEY+" TEXT" + " ); ";


    //************DootAccountTransactionColumn************************************
    public static final String TOTAL_AMOUNT = "toatal_amount";
    public static final String PAID_AMOUNT = "paid_amount";
    public static final String DUE_AMOUNT = "due_amount";

    public static final String CREATE_TABLE_DOOTACCOUNT_TRANSACTION = "CREATE TABLE " + TBL_DOOT_ACCOUNT_TRANSACTION + " ( " +
            TOTAL_AMOUNT+" TEXT,"+PAID_AMOUNT+" TEXT,"+DUE_AMOUNT+" TEXT,"+LOGIN_KEY+" TEXT" + " ); ";

    //*********TbLearning Column************************************************

//    public static final String LEARNING_1 = "learning_1";
//    public static final String LEARNING_2 = "learning_2";
//    public static final String LEARNING_3 = "learning_3";
//    public static final String LEARNING_4 = "learning_4";
//    public static final String LEARNING_5 = "learning_5";
//
//    public static final String CREATE_TABLE_TBLEARNING = "CREATE TABLE " + TBL_TBLEARNING + " ( " +
//            LEARNING_1+" TEXT,"+LEARNING_2+" TEXT,"+LEARNING_3+" TEXT,"+LEARNING_4+" TEXT,"+
//            LEARNING_5+" TEXT,"+LOGIN_KEY+" TEXT" + " ); ";

    public DootDatabaseAdapter(Context context){
        databaseHelper = new DatabaseHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void openDataBase()throws SQLException{
        db = databaseHelper.getWritableDatabase();
    }
    public void close(){
        db.close();
    }
    public SQLiteDatabase getDatabaseInstance(){
        return db;
    }

    public long insertData(String tableName,ContentValues contentValues){
        long val =db.insert(tableName, null, contentValues);

        System.out.println("Recort insert data at "+tableName +"..."+val);
        return val;
    }

    public Cursor getData(String tableName){
        String query = "SELECT *FROM " + tableName;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }


    public  Cursor getDataOnLoginKey(String tableName,String loginKey){
        String query = "SELECT *FROM " + tableName +" WHERE "+ LOGIN_KEY +"='"+loginKey+"'";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void dropFirst_then_createTable(String drop_tableName, String create_tableName){
        String dropQuery = "DROP TABLE IF EXISTS " + drop_tableName;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        db.execSQL(dropQuery);
        db.execSQL(create_tableName);
    }

    public void deleteDataFromTable(String tableName,String loginKey){
        String deletedataquery = "DELETE FROM " + tableName +" WHERE "+ LOGIN_KEY +"='"+loginKey+"'";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        db.execSQL(deletedataquery);

    }

//    public void updateTable(String tableName,String loginKey){
//        String updateQuery = "UPDATE FROM " + tableName +" WHERE "+ LOGIN_KEY +"='"+loginKey+"'";
//        SQLiteDatabase db = databaseHelper.getReadableDatabase();
//        db.execSQL(updateQuery);
//    }
}
