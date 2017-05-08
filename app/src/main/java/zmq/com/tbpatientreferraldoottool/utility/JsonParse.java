package zmq.com.tbpatientreferraldoottool.utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;

/**
 * Created by zmq162 on 18/10/16.
 */
public class JsonParse {
    Context context;

    public JsonParse(Context context){
        this.context=context;
    }

    public void loginDetailsParse(JSONObject jsonObject){

        try {
            String status = jsonObject.getString("status");
            if(status.equalsIgnoreCase("true")){

                DootDatabaseAdapter dootDatabaseAdapter = new DootDatabaseAdapter(context);
                dootDatabaseAdapter.openDataBase();

                JSONArray dootArray = jsonObject.getJSONArray("doot");
                for(int i=0;i<dootArray.length();i++){
                    ContentValues contentValues = new ContentValues();

                    JSONObject dootObject = dootArray.getJSONObject(i);

                    contentValues.put(DootDatabaseAdapter.DOOT_ID,dootObject.getString("doot_id"));
                    contentValues.put(DootDatabaseAdapter.DOOT_NAME,dootObject.getString("full_name"));
                    contentValues.put(DootDatabaseAdapter.VILLAGE_ID,dootObject.getString("village_id"));
                    contentValues.put(DootDatabaseAdapter.VILLAGE_NAME,dootObject.getString("village_name"));
                    contentValues.put(DootDatabaseAdapter.BLOCK_ID,dootObject.getString("block_id"));
                    contentValues.put(DootDatabaseAdapter.BLOCK_NAME,dootObject.getString("block_name"));
                    contentValues.put(DootDatabaseAdapter.DISTRICT_NAME,dootObject.getString("district_name"));
                    contentValues.put(DootDatabaseAdapter.STATE_NAME,dootObject.getString("state_name"));
                    contentValues.put(DootDatabaseAdapter.COUNTRY_NAME, dootObject.getString("country_name"));

   //*****this field is used for further check the login status of user. ***************
                    contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);

                    System.out.println("Doot id..." + dootObject.getString("doot_id"));
                    System.out.println("Village name..." + dootObject.getString("village_name"));

                    DootConstants.DOOT_ID = dootObject.getString("doot_id");
                    DootConstants.DOOT_NAME = dootObject.getString("full_name");

                    dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_DOOTDETAIL, contentValues);

                }

                JSONArray blockDmcArray = jsonObject.getJSONArray("block_dmc");
                for(int i=0;i<blockDmcArray.length();i++){
                    ContentValues contentValues = new ContentValues();

                    JSONObject blockDmcObject = blockDmcArray.getJSONObject(i);

                    contentValues.put(DootDatabaseAdapter.DMC_ID,blockDmcObject.getString("dmc_id"));
                    contentValues.put(DootDatabaseAdapter.DMC_NAME,blockDmcObject.getString("dmc_name"));

                    //*****this field is used for further check the login status of user. ***************
                    contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);

                    System.out.println("dmc-id..." + blockDmcObject.getString("dmc_id"));

                    dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_DMCDETAIL,contentValues);
                }

                JSONArray questionArray = jsonObject.getJSONArray("questions");
                for(int i=0;i<questionArray.length();i++){
                    ContentValues contentValues = new ContentValues();

                    JSONObject questionObject = questionArray.getJSONObject(i);

                    contentValues.put(DootDatabaseAdapter.QUESTION_ID,questionObject.getString("question_id"));
                    contentValues.put(DootDatabaseAdapter.QUESTION_TEXT, questionObject.getString("question_text"));

                    //*****this field is used for further check the login status of user. ***************
                    contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);

                    System.out.println("question...id.." + questionObject.getString("question_id"));

                    dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_QUESTION,contentValues);
                }

//                SharedPreferences sharedPreferences = context.getSharedPreferences(DootConstants.PREFERENCES,Context.MODE_PRIVATE);
                DootConstants.sharedPreferences = context.getSharedPreferences(DootConstants.PREFERENCES,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = DootConstants.sharedPreferences.edit();
                editor.putString("doot_id",DootConstants.DOOT_ID);
                editor.putString("doot_name",DootConstants.DOOT_NAME);

//***** server side want this api_key & device_imei along with every request to server side for tracking user login activity.
                editor.putString("api_key",DootConstants.API_KEY);
                editor.putString("device_imei",DootConstants.DEVICE_IMEI);
                editor.commit();
                dootDatabaseAdapter.close();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
        }
    }

    public void uploadPatientStatusParsing(JSONObject jsonObject){

        try{
            String status = jsonObject.getString("status");
            if(status.equalsIgnoreCase("true")){
                DootConstants.UPLOAD_PATIENT_STATUS = status;
            }else{

            }
        }catch (JSONException e){
            DootConstants.EXCEPTION_STRING = e.getMessage();
        }
    }

    public void dootAccountParse(JSONObject jsonObject){

        try{
            String status = jsonObject.getString("status");
            if(status.equalsIgnoreCase("true")){

                DootDatabaseAdapter dootDatabaseAdapter = new DootDatabaseAdapter(context);
                dootDatabaseAdapter.openDataBase();

                JSONArray accountArray = jsonObject.getJSONArray("account_detals");
                for(int i=0;i<accountArray.length();i++){
                    ContentValues contentValues = new ContentValues();

                    JSONObject accountObject = accountArray.getJSONObject(i);

                    contentValues.put(DootDatabaseAdapter.DOOT_ACCOUNT_NO,accountObject.getString("account_number"));
                    contentValues.put(DootDatabaseAdapter.DOOT_ACCOUNT_BALANCE,accountObject.getString("balance"));
                    contentValues.put(DootDatabaseAdapter.DOOT_ACCOUNT_OPENDATE,accountObject.getString("account_opening_date"));
//*****this field is used for further check the login status of user. ***************
                    contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);
                    System.out.println("account No....." + accountObject.getString("account_number"));

                    dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_DOOT_ACCOUNT,contentValues);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
        }
    }

    public void dootTransactionDetailParsing(JSONObject jsonObject){

        try {
            DootDatabaseAdapter dootDatabaseAdapter = new DootDatabaseAdapter(context);
            dootDatabaseAdapter.openDataBase();

            ContentValues contentValues = new ContentValues();

            contentValues.put(DootDatabaseAdapter.TOTAL_AMOUNT,jsonObject.getString("total_amount"));
            contentValues.put(DootDatabaseAdapter.PAID_AMOUNT,jsonObject.getString("paid_amount"));
            contentValues.put(DootDatabaseAdapter.DUE_AMOUNT,jsonObject.getString("due_amount"));
            //*****this field is used for further check the login status of user. ***************
            contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);

            System.out.println("Toatal Amount......." + jsonObject.getString("total_amount"));
            System.out.println("Paid Amount......." + jsonObject.getString("paid_amount"));
            System.out.println("Due Amount......."+jsonObject.getString("due_amount"));

            dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_DOOT_ACCOUNT_TRANSACTION,contentValues);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getPatientDataParsing(JSONArray jsonArray){

        try {
            DootDatabaseAdapter dootDatabaseAdapter = new DootDatabaseAdapter(context);
            dootDatabaseAdapter.openDataBase();

            for(int i = 0; i<jsonArray.length();i++){
                JSONObject jsonObjectOnArray = jsonArray.getJSONObject(i);
                ContentValues contentValues = new ContentValues();

                String status = jsonObjectOnArray.getString("status");
                if(status.equals("true")){
                    JSONObject jsonObjOnPrevDootRec = jsonObjectOnArray.getJSONObject("PreviousDootRecord");

                    contentValues.put(DootDatabaseAdapter.PATIENT_NAME,jsonObjOnPrevDootRec.getString("patient_name"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_AGE,jsonObjOnPrevDootRec.getString("age"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_SEX,jsonObjOnPrevDootRec.getString("sex"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_ADDRESS,"NotFound");
                    contentValues.put(DootDatabaseAdapter.PATIENT_PHONE,jsonObjOnPrevDootRec.getString("mobile_no"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_VILLAGE,jsonObjOnPrevDootRec.getString("village_name"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_VILLAGE_ID,jsonObjOnPrevDootRec.getString("village_id"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_BLOCK,"NotFound");
                    contentValues.put(DootDatabaseAdapter.PATIENT_BLOCK_ID,"NotFound");
                    contentValues.put(DootDatabaseAdapter.PATIENT_DMC,jsonObjOnPrevDootRec.getString("dmc_name"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_DMC_ID,jsonObjOnPrevDootRec.getString("dmc_id"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_DISTRICT,"NotFound");
                    contentValues.put(DootDatabaseAdapter.PATIENT_STATE,"NotFound");
                    contentValues.put(DootDatabaseAdapter.PATIENT_COUNTRY,"NotFound");
                    contentValues.put(DootDatabaseAdapter.PATIENT_SCREEN_DATE_TIME,jsonObjOnPrevDootRec.getString("screening_date_time"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_SCREENING_AVERAGE,jsonObjOnPrevDootRec.getString("screening_average"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_TEST_STATUS,jsonObjOnPrevDootRec.getString("test_status"));
                    contentValues.put(DootDatabaseAdapter.PATIENT_TEST_RESULT,jsonObjOnPrevDootRec.getString("test_result"));

                    JSONArray jsonArrayOfQuesAnswer = jsonObjectOnArray.getJSONArray("question_answer");
                    String answer = "";
                    String questionId = "";
                    for(int j=0;j<jsonArrayOfQuesAnswer.length();j++){
                       JSONObject jsonObjOfAnswer = jsonArrayOfQuesAnswer.getJSONObject(j);
                        answer = answer + jsonObjOfAnswer.getString("answer") + ":" ;
                        questionId = questionId + jsonObjOfAnswer.getString("question_id") + ":";
                    }
                    if( !(answer .equals(""))){
                        answer = answer.substring(0,answer.length()-1);
                        questionId = questionId.substring(0,questionId.length()-1);
                    }
                    System.out.println("Answer value...."+answer);
                    System.out.println("Question Id ...."+questionId);

                    contentValues.put(DootDatabaseAdapter.PATIENT_SCREENING_RESPONSE, answer);
                    contentValues.put(DootDatabaseAdapter.PATIENT_QUESTION_ID,questionId);

                    contentValues.put(DootDatabaseAdapter.CHECKDATAFROM_LOCAL_SERVER,"SERVER");

            //*****this field is used for further check the login status of user. ***************
                    contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);

                    dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_PATIENTDETAIL,contentValues);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void uploadLearningAnalyticsStatusParsing(JSONObject jsonObject){

        try{
            String status = jsonObject.getString("success");
            if(status.equalsIgnoreCase("true")){
                DootConstants.UPLOAD_LEARNING_ANALYTICS_STATUS = status;
            }else{

            }
        }catch (JSONException e){
            DootConstants.EXCEPTION_STRING = e.getMessage();
        }
    }
}
