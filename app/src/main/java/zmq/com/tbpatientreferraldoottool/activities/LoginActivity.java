package zmq.com.tbpatientreferraldoottool.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.fragments.LoginFragment;
import zmq.com.tbpatientreferraldoottool.fragments.ProgressbarFragment;
import zmq.com.tbpatientreferraldoottool.model.Dootdtls;
import zmq.com.tbpatientreferraldoottool.network.NetworkTask;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;
import zmq.com.tbpatientreferraldoottool.utility.JsonParse;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginCommunicator{

    FragmentManager fragmentManager;
    //    Handler handler;
    JSONObject jsonObject;
    JsonParse jsonParse;
    ProgressDialog progressDialog;
    Cursor cursor;
    DootDatabaseAdapter dootDatabaseAdapter;
    String dootId,dootName;
    String loginKey = "FAIZ";
    List<Dootdtls> dootDetailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        fragmentManager = getSupportFragmentManager();
        jsonParse = new JsonParse(this);
        dootDatabaseAdapter = new DootDatabaseAdapter(this);

        DootConstants.sharedPreferences = getSharedPreferences(DootConstants.PREFERENCES,MODE_PRIVATE);
        DootConstants.DOOT_ID = DootConstants.sharedPreferences.getString("doot_id", "0");
        DootConstants.DOOT_NAME = DootConstants.sharedPreferences.getString("doot_name","faiz");
        DootConstants.API_KEY = DootConstants.sharedPreferences.getString("api_key", "faiz");
        DootConstants.DEVICE_IMEI = DootConstants.sharedPreferences.getString("device_imei", "faiz");

        if(savedInstanceState==null){
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.login_container,loginFragment,"loginfragment");
            fragmentTransaction.commit();
            loginFragment.setLoginCommunicator(this);
        }

    }

    @Override
    public void clickSingnIn(String username, String password) {

        String logKey = username + "" + password;
        String device_imei = getIMEI();
        DootConstants.LOGIN_KEY = logKey;

//***** server side want this api_key & device_imei along with every request to server  for tracking user login activity.
        DootConstants.API_KEY = logKey;
        DootConstants.DEVICE_IMEI = device_imei;


        if(DootConstants.sharedPreferences.getString("doot_id", null) != null){

            dootDetailInfo = getDataFromTableDootOnLoginKey(DootDatabaseAdapter.TBL_DOOTDETAIL,logKey);
            for(int i=0;i<dootDetailInfo.size();i++){
                Dootdtls dootdtls = dootDetailInfo.get(i);
                dootId = dootdtls.getDootId();
                dootName = dootdtls.getDootName();
                loginKey = dootdtls.getLoginKey();
            }
            if(loginKey.equals(logKey)){
                DootConstants.DOOT_ID = dootId;
                DootConstants.DOOT_NAME = dootName;

                Intent intent = new Intent();
                intent.setClass(getBaseContext(), DootMenuActivity.class);
                startActivity(intent);
                finish();
            }else{
                login(username,password,device_imei);
            }
        }else {
           login(username,password,device_imei);
        }
    }

    private void login(String username,String password,String device_imei){
        loadingProgressbarFragment(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? "TB Referral Login..." : "टीबी रेफरल लॉग इन...");
//        loadingProgressbar("DootLogin.....");
        String serviceName = "DootLogin";

        ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();

        paramValue.add(new BasicNameValuePair("username", username));
        paramValue.add(new BasicNameValuePair("password", password));
        paramValue.add(new BasicNameValuePair("device_imei", device_imei));
        paramValue.add(new BasicNameValuePair("X_API_KEY", DootConstants.API_KEY));


//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//               jsonObject = (JSONObject) msg.obj;
//                if(jsonObject != null){
//                    try {
//                        if(!(jsonObject.getString("status").equalsIgnoreCase("true"))){
//                            fragmentManager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                            DootConstants.EXCEPTION_STRING = "Username And Password Is Incurrect";
//                            showErrorDialog();
//                        }else{
//
//                            jsonParse.loginDetailsParse(jsonObject);
////                            progressDialog.dismiss();
//                            fragmentManager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                            Intent intent = new Intent();
//                            intent.setClass(getBaseContext(),DootMenuActivity.class);
//                            startActivity(intent);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        DootConstants.EXCEPTION_STRING = e.getMessage();
//                        showErrorDialog();
//                    }
//                }else{
//                    showErrorDialog();
//                }
//            }
//        };

//        new LoginAsyncTask(this,serviceName,paramValue,handler).execute();
        if (checkInternetAndNetworkTask()) {
            new DootComplteLoginAsyncTask(this, serviceName, paramValue).execute();
        } else {
            DootConstants.EXCEPTION_STRING = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? "No internet connection. Please try again." : "इंटरनेट कनेक्टिविटी उपलब्ध नही हैं। कृपया इंटरनेट कनेक्शन को चेक कर दोबारा कोशिश करें।";
            showErrorDialog();
        }
    }

    private boolean checkInternetAndNetworkTask(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileDataNetwork = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if(wifiNetwork != null && wifiNetwork.isConnected() || mobileDataNetwork != null && mobileDataNetwork.isConnected()){
            return true;
        }
        return false;
    }
    public void loadingProgressbarFragment(String message){

        ProgressbarFragment progressbarFragment = new ProgressbarFragment();
        Bundle argument = new Bundle();
        argument.putString("progressText", message);
        progressbarFragment.setArguments(argument);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_container, progressbarFragment, "ProgressbarFragment");

        fragmentTransaction.addToBackStack("ProgressScreen");
        fragmentTransaction.commit();
    }

    public void loadingProgressbar(String message){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
    public String getIMEI(){
        TelephonyManager mngr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();

        System.out.println("getDeviceId() " + imei);
        return imei;

    }
    public void showErrorDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Alert":"अलर्ट");
        dialog.setMessage(DootConstants.EXCEPTION_STRING);
        dialog.setIcon(R.drawable.icon);

        dialog.setPositiveButton(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Retry":"फिर से कोशिश करें", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragmentManager.popBackStack();
            }
        });

        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragmentManager.popBackStack();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    public List<Dootdtls> getDataFromTableDootOnLoginKey(String tableName,String loginKey){
        cursor = dootDatabaseAdapter.getDataOnLoginKey(tableName,loginKey);
        List<Dootdtls> dootdtlsList = new ArrayList<Dootdtls>();

        if(cursor.moveToFirst()){
            do {
                Dootdtls dootdtls = new Dootdtls();

                dootdtls.setDootId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_ID)));
                dootdtls.setDootName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_NAME)));
                dootdtls.setBlockId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.BLOCK_ID)));
                dootdtls.setBlockName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.BLOCK_NAME)));
                dootdtls.setVillageId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.VILLAGE_ID)));
                dootdtls.setVillageName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.VILLAGE_NAME)));
                dootdtls.setDistrictName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DISTRICT_NAME)));
                dootdtls.setStateName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.STATE_NAME)));
                dootdtls.setCountryName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.COUNTRY_NAME)));
                dootdtls.setLoginKey(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LOGIN_KEY)));
                dootdtlsList.add(dootdtls);
            }while (cursor.moveToNext());
        }
        return dootdtlsList;
    }

    class DootComplteLoginAsyncTask extends AsyncTask<Void, Void, JSONObject> {
        Context context;
        String serviceName;
        ArrayList<NameValuePair> paramValue;
        JsonParse jsonParse;
        NetworkTask networkTask;
        String DOOT_ID;
        DootComplteLoginAsyncTask(Context context, String serviceName, ArrayList<NameValuePair> paramValue){
            this.context = context;
            this.serviceName = serviceName;
            this.paramValue = paramValue;
            jsonParse = new JsonParse(context);
            networkTask = new NetworkTask();

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DOOT_ID = "DOOT_ID";
            DootConstants.DOOT_ID = DOOT_ID;
        }
        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONArray jsonArray;
            JSONObject jsonObject = dootLogin(serviceName, paramValue);
            if(jsonObject != null){
                try {
                    if(jsonObject.getString("status").equals("true")) {
                        jsonArray = dootPatientData();
                        if (jsonArray != null) {
                            jsonObject = dootAccount();
                            if (jsonObject != null) {
                                jsonObject = dootAccountTransaction();
                                if (jsonObject == null) {
                                    DootConstants.EXCEPTION_STRING = "Account transaction data not found.";
                                    return jsonObject;
                                }
                            } else {
                                DootConstants.EXCEPTION_STRING = "Doot account data not found.";
                                return jsonObject;
                            }
                        } else {
                            DootConstants.EXCEPTION_STRING = "Patient data not found.";
                            return jsonObject;
                        }
                    }else{
                        return jsonObject;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                DootConstants.EXCEPTION_STRING = "Login failed due to poor internet connection.";
                return jsonObject;
            }



            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null) {
                try {
                    if(jsonObject.getString("status").equals("true")) {
                        DOOT_ID = DootConstants.DOOT_ID;
                        if (!(DOOT_ID.equals("DOOT_ID"))) {
//                            fragmentManager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                            fragmentManager.popBackStack("login_screen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(), DootMenuActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            fragmentManager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            DootConstants.EXCEPTION_STRING = "Data not found. Please contact the admin";
                            showErrorDialog();
                        }
                    }else{
                        DootConstants.EXCEPTION_STRING = "Username & password does not exist.";
                        showErrorDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    DootConstants.EXCEPTION_STRING = e.getMessage();
                    showErrorDialog();
                }
            }else{
                showErrorDialog();
            }
        }

        private JSONObject dootLogin(String serviceName,ArrayList<NameValuePair> paramValue) {

            JSONObject jsonObject = networkTask.getJObjectResponse(serviceName, paramValue);

            if(jsonObject != null){
                jsonParse.loginDetailsParse(jsonObject);
            }else{
                return jsonObject;
            }
            return jsonObject;
        }
        private JSONObject dootAccount(){
            String serviceName = "AccountStatus";
            String DOOTID = DootConstants.DOOT_ID;
            String key = DootConstants.API_KEY;
            ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();
            paramValue.add(new BasicNameValuePair("doot_id",DOOTID));
            paramValue.add(new BasicNameValuePair("X_API_KEY",key));

            JSONObject jsonObject = networkTask.getJObjectResponse(serviceName, paramValue);

            if(jsonObject != null){
                jsonParse.dootAccountParse(jsonObject);
            }else{
                return jsonObject;
            }
            return jsonObject;

        }
        private JSONArray dootPatientData(){

            String serviceName = "PreviousDootRecord";
            String DOOTID = DootConstants.DOOT_ID;
            String key = DootConstants.API_KEY;
            ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();
            paramValue.add(new BasicNameValuePair("doot_id",DOOTID));
            paramValue.add(new BasicNameValuePair("X_API_KEY",key));

            JSONArray jsonArray = networkTask.getJArrayData(serviceName, paramValue);
            if(jsonArray != null ){
                jsonParse.getPatientDataParsing(jsonArray);
            }else{
                return jsonArray;
            }
            return jsonArray;
        }
        private JSONObject dootAccountTransaction() {

            String serviceName = "DootBalanceDetails";
            String DOOTID = DootConstants.DOOT_ID;
            String key = DootConstants.API_KEY;
            ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();
            paramValue.add(new BasicNameValuePair("doot_id",DOOTID));
            paramValue.add(new BasicNameValuePair("X_API_KEY",key));

            JSONObject jsonObject = networkTask.getJObjectResponse(serviceName,paramValue);


            if(jsonObject != null){
                jsonParse.dootTransactionDetailParsing(jsonObject);
            }else{
                return jsonObject;
            }
            return jsonObject;
        }
    }
}
