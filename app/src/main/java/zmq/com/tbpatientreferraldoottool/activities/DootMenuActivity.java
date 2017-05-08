package zmq.com.tbpatientreferraldoottool.activities;

//import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.fragments.DootAccountFragment;
import zmq.com.tbpatientreferraldoottool.fragments.DootAccountTransactionFragment;
import zmq.com.tbpatientreferraldoottool.fragments.DootMenuFragment;
import zmq.com.tbpatientreferraldoottool.fragments.MainMenuFragment;
import zmq.com.tbpatientreferraldoottool.fragments.PatientListFragment;
import zmq.com.tbpatientreferraldoottool.fragments.PatientStatusFragment;
import zmq.com.tbpatientreferraldoottool.fragments.ProgressbarFragment;
import zmq.com.tbpatientreferraldoottool.fragments.RegistrationFragment;
import zmq.com.tbpatientreferraldoottool.fragments.ScreeningFragment;
import zmq.com.tbpatientreferraldoottool.fragments.UploadPatientListFragment;
import zmq.com.tbpatientreferraldoottool.model.ScreenRegiDtls;
import zmq.com.tbpatientreferraldoottool.network.UpdateRecordAsyncTask;
import zmq.com.tbpatientreferraldoottool.network.UploadDataAsyncTask;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;
import zmq.com.tbpatientreferraldoottool.utility.GPSTracker;
import zmq.com.tbpatientreferraldoottool.utility.JsonParse;

public class DootMenuActivity extends AppCompatActivity implements
        DootMenuFragment.DootMenuCommunicator,
        MainMenuFragment.MainMenuCommunicator,
        ScreeningFragment.ScreeningFragmentCommunicator,
        RegistrationFragment.RegistrationFragmentCommunicator,
        UploadPatientListFragment.UploadPatientListFragmentCommunicator,
        PatientListFragment.PatientListFragmentCommunicator,
        DootAccountFragment.DootAccountFragmentCommunicator,DootAccountTransactionFragment.DootAccountTransactionFragmentCommunicator{

    DrawerLayout drawerLayout;
    ListView listView;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
//    String[] drawerlistItem;
    FragmentManager fragmentManager;
    View drawerHeader;
    DootMenuFragment dootMenuFragment;
    JsonParse jsonParse;
    DootDatabaseAdapter dootDatabaseAdapter;
    double longitude,latitude;
    GPSTracker gpsTracker;
    ProgressDialog progressDialog;
    List<ScreenRegiDtls>  regiDtlsListInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_menu);
        fragmentManager = getSupportFragmentManager();
        jsonParse = new JsonParse(this);
        dootDatabaseAdapter = new DootDatabaseAdapter(this);


        if (savedInstanceState == null){
            dootMenuFragment = new DootMenuFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.mainContent, dootMenuFragment, "doot_menu");
            fragmentTransaction.commit();
            dootMenuFragment.setDootMenuCommunicator(this);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.drawerList);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        drawerlistItem = getResources().getStringArray(R.array.drawerItem);

//**** Add header on Navigation Drawer*************************
        drawerHeader = getLayoutInflater().inflate(R.layout.drawerheader, null);
        listView.setAdapter(null);
        listView.addHeaderView(drawerHeader);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                System.out.println("drawer open.....");
                MyAdapter adapter = new MyAdapter();
                listView.setAdapter(adapter);
//                listView.setAdapter(new ArrayAdapter(DootMenuActivity.this, android.R.layout.simple_list_item_1, drawerlistItem));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setTitle(DootConstants.DOOT_NAME);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setItemChecked(position,true);
                switch (position){
                    case 1:

                        drawerLayout.closeDrawers();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(DootMenuActivity.this);
                        String title = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)? "Select your language" : "अपनी भाषा का चयन करें";
                        dialog.setTitle(title);
                        dialog.setIcon(R.drawable.icon);
                        String[] itemEng = {"English","Hindi"};
                        String[] itemHnd = {"अंग्रेज़ी","हिंदी"};
                        dialog.setItems(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? itemEng : itemHnd, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               switch (which){
                                   case 0:
                                       DootConstants.LANGUAGE = DootConstants.ENGLISH;
                                       setLanguageInSharedPrefrence(DootConstants.LANGUAGE);
                                       Locale myLocale = new Locale("en");
                                       Locale.setDefault(myLocale);
                                       android.content.res.Configuration config = new android.content.res.Configuration();
                                       config.locale = myLocale;
                                       getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                                       Fragment fragment = getSupportFragmentManager().findFragmentByTag("doot_menu");
                                       if(fragment != null && fragment.isVisible()){
                                            refreshWithFirstFragment();
                                       }else{
                                            refresh();
                                       }


                                       break;
                                   case 1:
                                       DootConstants.LANGUAGE = DootConstants.HINDI;
                                       setLanguageInSharedPrefrence(DootConstants.LANGUAGE);
                                       Locale myLocale1 = new Locale("hi");
                                       Locale.setDefault(myLocale1);
                                       android.content.res.Configuration config1 = new android.content.res.Configuration();
                                       config1.locale = myLocale1;
                                       getBaseContext().getResources().updateConfiguration(config1, getBaseContext().getResources().getDisplayMetrics());

                                       Fragment fragment1 = getSupportFragmentManager().findFragmentByTag("doot_menu");
                                       if(fragment1 !=null && fragment1.isVisible()){
                                            refreshWithFirstFragment();
                                       }else{
                                           refresh();
                                       }

                                       break;
                                   default:
                                       break;
                               }
                            }
                        });
                        dialog.show();
                        break;

                    case 2:
                        drawerLayout.closeDrawers();
                        if(hasGpsSensor()) {
                            AlertDialog.Builder settingDialog = new AlertDialog.Builder(DootMenuActivity.this);
                            settingDialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Application GPS Setting":"जीपीएस सेटिंग्स");
                            settingDialog.setMessage(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Continue application with or without GPS":"क्या आप जीपीएस सेटिंग्स करना चाहते हैं?");
                            settingDialog.setIcon(R.drawable.icon);

                            String checkYes_buttonMessage = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)? "YES" : "हाँ";
                            String checkNo_buttonMessage = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)? "NO" : "नहीं";

                            settingDialog.setPositiveButton(checkYes_buttonMessage, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    findGpsLocation();
                                }
                            });
                            settingDialog.setNegativeButton(checkNo_buttonMessage, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    longitude = 0.0;
                                    latitude = 0.0;
                                    Toast.makeText(getApplicationContext(), "Without GPS- \n Lat: " + latitude + "\nLong:" + longitude, Toast.LENGTH_LONG).show();

                                }
                            });
                            settingDialog.show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Device can't support Gps Sensor feature",Toast.LENGTH_LONG).show();
                        }
                        break;

                    case 3:
                        drawerLayout.closeDrawers();
                        final AlertDialog.Builder refreshdialog = new AlertDialog.Builder(DootMenuActivity.this);
                        String refreshTitle = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)? "Refresh Device" : "रिफ्रेश-डिवाइस";
                        refreshdialog.setTitle(refreshTitle);
                        refreshdialog.setIcon(R.drawable.icon);
                        String refreshMessage = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)? "Are you sure want to refresh your device?" : "क्या आप अपने डिवाइस रिफ्रेश करना चाहते हैं?";
                        refreshdialog.setMessage(refreshMessage);

                        String checkYes_buttonMessage = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)? "YES" : "हाँ";
                        String checkNo_buttonMessage = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)? "NO" : "नहीं";
                        refreshdialog.setPositiveButton(checkYes_buttonMessage, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


//                            deleteDatabase(DootDatabaseAdapter.DATABASE_NAME);
//                            clearSharedPrefrenceData(DootConstants.PREFERENCES);
//                            clearSharedPrefrenceData(DootConstants.LEARNING_ANALYTICS_PREFERENCES);
//                            clearSharedPrefrenceData(DootConstants.LANGUAGE_PREFERENCE);
//                            clearApplicationData();

                            dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_DOOTDETAIL,DootConstants.LOGIN_KEY);
                            dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_DMCDETAIL,DootConstants.LOGIN_KEY);
                            dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_DOOT_ACCOUNT,DootConstants.LOGIN_KEY);
                            dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_DOOT_ACCOUNT_TRANSACTION,DootConstants.LOGIN_KEY);
                            dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_PATIENTDETAIL,DootConstants.LOGIN_KEY);
                            dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER,DootConstants.LOGIN_KEY);
                            dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_QUESTION,DootConstants.LOGIN_KEY);
                            clearSharedPrefrenceData(DootConstants.LANGUAGE_PREFERENCE);
                            clearSharedPrefrenceData(DootConstants.LEARNING_ANALYTICS_PREFERENCES);

                            Intent intent = new Intent(DootMenuActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Device Refresh successfully":"डिवाइस सफलतापूर्वक रिफ्रेश हुआ",Toast.LENGTH_LONG).show();
                            }
                        });
                        refreshdialog.setNegativeButton(checkNo_buttonMessage, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        refreshdialog.setCancelable(false);
                        refreshdialog.show();

                     default:
                        break;
                }
            }
        });

    }

    private void setLanguageInSharedPrefrence(String language){

        DootConstants.languageSharedPreference = getSharedPreferences(DootConstants.LANGUAGE_PREFERENCE,MODE_PRIVATE);
        SharedPreferences.Editor editor = DootConstants.languageSharedPreference.edit();
        editor.putString("language",language);
        editor.commit();
    }

    private void refresh(){
        FragmentManager.BackStackEntry latestEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
        String fragmentNamebyTag = latestEntry.getName();

        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fragmentNamebyTag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }

    private void refreshWithFirstFragment(){
        dootMenuFragment = new DootMenuFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, dootMenuFragment, "doot_menu");
        fragmentTransaction.commit();
        dootMenuFragment.setDootMenuCommunicator(this);
    }
    /**
     *
     * Function to check Device have the GPS Feature.
     * @return boolean
     * */
    public boolean hasGpsSensor(){
        PackageManager packageManager = getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }
    private void findGpsLocation(){
        gpsTracker = new GPSTracker(DootMenuActivity.this);
        if(gpsTracker.canGetLocation()){
            longitude = gpsTracker.getLongitude();
            latitude = gpsTracker.getLatitude();
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
//        switch (item.getItemId()){
//            case R.id.menuOverflow_settings:
//                this.deleteDatabase(DootDatabaseAdapter.DATABASE_NAME);
//                clearSharedPrefrenceData(DootConstants.PREFERENCES);
//                clearSharedPrefrenceData(DootConstants.LEARNING_ANALYTICS_PREFERENCES);
//                clearApplicationData();
//
//                Intent intent = new Intent(DootMenuActivity.this,MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                Toast.makeText(this,"Device Refresh successfully",Toast.LENGTH_LONG).show();
//                return true;
//
//            default:
//                break;
//        }

        return super.onOptionsItemSelected(item);
    }
    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){
            String[] children = appDir.list();
            for(String s : children){
                if(!s.equals("lib")){
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s + " DELETED");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public void clearSharedPrefrenceData(String sharedPreferenceName){
        DootConstants.sharedPreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        SharedPreferences.Editor editor = DootConstants.sharedPreferences.edit();
//        editor.remove(key);
        editor.clear();
        editor.commit();

    }
    private boolean checkInternetAndNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileDataNetwork = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if(wifiNetwork != null && wifiNetwork.isConnected() || mobileDataNetwork != null && mobileDataNetwork.isConnected()){
            return true;
        }
        return false;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public void chooseReferralMenu() {
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, mainMenuFragment, "MainMenu");
        fragmentTransaction.addToBackStack("MainMenu");
        fragmentTransaction.commit();
        mainMenuFragment.setMainMenuCommunicator(this);
    }

    @Override
    public void chooseTbLearning() {
        Intent intent=new Intent(DootMenuActivity.this,LearnMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public int selectMainMenuItem(int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (position){
            case 0:
                ScreeningFragment screeningFragment = new ScreeningFragment();
                fragmentTransaction.replace(R.id.mainContent, screeningFragment, "screening_fragment");
                fragmentTransaction.addToBackStack("screening_fragment");
                fragmentTransaction.commit();
                screeningFragment.setScreeningFragmentCommunicator(this);
                break;
            case 1:
//                regiDtlsListInfo = getRegisterdPatientDetail(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER);
                regiDtlsListInfo = getRegisterdPatientDetailOnLoginKey(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER, DootConstants.LOGIN_KEY);
                if(regiDtlsListInfo.size()<=0){
                    DootConstants.EXCEPTION_STRING = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Please register the patient first":"कृपया पहले संदिग्ध  दर्ज करें";

                    showErrorDialog("still_same_fragment");
                }else {
                    UploadPatientListFragment uploadPatientListFragment = new UploadPatientListFragment();
                    fragmentTransaction.replace(R.id.mainContent, uploadPatientListFragment, "upload_patientlist_fragment");
                    fragmentTransaction.addToBackStack("upload_patientlist_fragment");
                    fragmentTransaction.commit();
                    uploadPatientListFragment.setUploadPatientListFragmentCommunicator(this);
                }

                break;
            case 2:
                PatientListFragment patientListFragment = new PatientListFragment();
                fragmentTransaction.replace(R.id.mainContent,patientListFragment,"patientList_fragment");
                fragmentTransaction.addToBackStack("patientList_fragment");

                Bundle argument = new Bundle();
                argument.putString("purpose", "SHOW_PATIENT_LIST");
                patientListFragment.setArguments(argument);
                fragmentTransaction.commit();
                break;
            case 3:
                PatientListFragment patientListFragment1 = new PatientListFragment();
                fragmentTransaction.replace(R.id.mainContent,patientListFragment1,"patientList_fragment");
                fragmentTransaction.addToBackStack("patientList_fragment");

                Bundle argument1 = new Bundle();
                argument1.putString("purpose",  "SHOW_PATIENT_DETAIL");
                patientListFragment1.setArguments(argument1);
                fragmentTransaction.commit();
                patientListFragment1.setPatientListFragmentCommunicator(this);
                break;
            case 4:
                DootAccountFragment dootAccountFragment = new DootAccountFragment();
                fragmentTransaction.replace(R.id.mainContent,dootAccountFragment,"dootAccount_fragment");
                fragmentTransaction.addToBackStack("dootAccount_fragment");
                fragmentTransaction.commit();
                dootAccountFragment.setDootAccountFragmentCommunicator(this);
                break;
            case 5:
//                DootConstants.learningSharedPreferences = getSharedPreferences(DootConstants.LEARNING_ANALYTICS_PREFERENCES,MODE_PRIVATE);
//
//                if(DootConstants.learningSharedPreferences.getString(DootConstants.LEARNING_STATUS,null) != null){
//                    AlertDialog.Builder settingDialog = new AlertDialog.Builder(DootMenuActivity.this);
//                    settingDialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Upload Learning Analytics":" अपलोड लर्निंग एनालिटिक्स");
//                    settingDialog.setMessage(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Do you want to upload learning analytics?":"क्या आप लर्निंग एनालिटिक्स अपलोड करना चाहते हैं?");
//                    settingDialog.setIcon(R.drawable.icon);
//                    settingDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            loadingProgressbarFragment(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Learning analytics record uploading.....":"लर्निंग एनालिटिक्स  रिकॉर्ड अपलोड...");
//                            String[] leaningModule = {DootConstants.LEARNING_1, DootConstants.LEARNING_2, DootConstants.LEARNING_3, DootConstants.LEARNING_4, DootConstants.LEARNING_5};
//                            String serviceName = "tbLearningAnalaytics";
//                            String[] name = {"doot_id", "contents", "sub_contents"};
//                            String[] value = DootConstants.setLearningAnalyticsRecord(DootMenuActivity.this, leaningModule);
//
//                            ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();
//                            for(int i=0; i<name.length; i++){
//                                paramValue.add(new BasicNameValuePair(name[i],value[i]));
//                            }
//
//                            Handler handler = new Handler(){
//                                @Override
//                                public void handleMessage(Message msg) {
//                                    JSONObject jsonObject = (JSONObject) msg.obj;
//                                    if(jsonObject != null){
//                                        try {
//                                            if(!(jsonObject.getString("success").equalsIgnoreCase("true"))){
//                                                fragmentManager.popBackStack("ProgressScreen",FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                                DootConstants.EXCEPTION_STRING = "Something happen wrong during upload learning analytics.";
//                                                showErrorDialog("general");
//                                            } else{
//                                                jsonParse.uploadLearningAnalyticsStatusParsing(jsonObject);
//                                                if(DootConstants.UPLOAD_LEARNING_ANALYTICS_STATUS.equals("true")){
//                                                    clearSharedPrefrenceData(DootConstants.LEARNING_ANALYTICS_PREFERENCES);
//                                                    fragmentManager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                                    Toast.makeText(DootMenuActivity.this,"Analytics Data Uploaded Successfully",Toast.LENGTH_LONG).show();
//                                                }
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                            DootConstants.EXCEPTION_STRING = e.getMessage();
//                                            showErrorDialog("general");
//                                        }
//                                    }else{
//                                        showErrorDialog("general");
//                                    }
//                                }
//                            };
//                            if(checkInternetAndNetwork()) {
//                                new UploadDataAsyncTask(getApplicationContext(), serviceName, paramValue, handler).execute();
//                            }else{
//                                DootConstants.EXCEPTION_STRING = "No internet connectivity. Please enable mobile wifi/data.";
//                                showErrorDialog("general");
//                            }
//                        }
//                    });
//                    settingDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    settingDialog.show();
//                }else{
//                    DootConstants.EXCEPTION_STRING = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Go to the TB Learning module.":"टीबी लर्निंग मॉड्यूल के लिए जाओ.";
//                    showErrorDialog("tbLearning");
//                }
                chooseTbLearning();
                break;

            default:
                break;
        }
        return 0;
    }


    @Override
    public void saveScreeningResult(String ansValue,String scrDateTime,int screenAverage) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegistrationFragment registrationFragment = new RegistrationFragment();
        fragmentTransaction.replace(R.id.mainContent, registrationFragment, "registration_fragment");
        fragmentTransaction.addToBackStack("registration_fragment");
        fragmentManager.popBackStack("screening_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Bundle args = new Bundle();
        args.putString("answerValue",ansValue);
        args.putString("screenDateTime",scrDateTime);
        args.putInt("screenAverage", screenAverage);
        args.putDouble("longitude", longitude);
        args.putDouble("latitude", latitude);
        registrationFragment.setArguments(args);

        fragmentTransaction.commit();
        registrationFragment.setRegistrationFragmentCommunicator(this);

    }

    @Override
    public void saveRegistrationDetail() {
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, mainMenuFragment, "MainMenu");
        fragmentManager.popBackStack("registration_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        fragmentManager.popBackStack("MainMenu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.commit();
        mainMenuFragment.setMainMenuCommunicator(this);
    }

    @Override
    public void screeningRegiNewSuspect() {
        ScreeningFragment screeningFragment = new ScreeningFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, screeningFragment, "screening_fragment");
        fragmentTransaction.addToBackStack("screening_fragment");
        fragmentManager.popBackStack("registration_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.commit();
        screeningFragment.setScreeningFragmentCommunicator(this);
    }

    @Override
    public void noUploadPatientListFind() {

        showErrorDialog("general");
    }

    @Override
    public void uploadPatientList(List<ScreenRegiDtls> regiDtlsListInfo) {

        loadingProgressbarFragment(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? "Record is uploading please wait...." : "रिकार्ड अपलोड हो रहा है, कृपया प्रतीक्षा करें...");
        String serviceName = "ReferralTbPatient";
        String[] value = DootConstants.setRegistrationRecord(regiDtlsListInfo);
        String[] name = {"doot_id", "patient_name","age", "sex","address", "mobile_no","village_id","block_id", "dmc_id",
                "district", "state","country","question_id","answer","screening_date_time","screening_average","longitude","lattitude"};

        ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();
        for(int i=0;i<name.length;i++) {
            paramValue.add(new BasicNameValuePair(name[i], value[i]));
        }
        paramValue.add(new BasicNameValuePair("X_API_KEY",DootConstants.API_KEY));
        paramValue.add(new BasicNameValuePair("device_imei",DootConstants.DEVICE_IMEI));

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                JSONObject jsonObject = (JSONObject) msg.obj;
                if(jsonObject != null){
                    try {
                        if (!(jsonObject.getString("status").equalsIgnoreCase("true"))) {
                            fragmentManager.popBackStack("ProgressScreen",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            DootConstants.EXCEPTION_STRING = "Something Wrong happen during upload patient data.";
                            showErrorDialog("general");
                        } else{
                            jsonParse.uploadPatientStatusParsing(jsonObject);
                            if(DootConstants.UPLOAD_PATIENT_STATUS.equals("true")){
//                                dootDatabaseAdapter.dropFirst_then_createTable(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER, DootDatabaseAdapter.CREATE_TABLE_PATIENTDETAIL_BUFFER);
                                dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER,DootConstants.LOGIN_KEY);
                                fragmentManager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragmentManager.popBackStack("upload_patientlist_fragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                Toast.makeText(DootMenuActivity.this,DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Data Uploaded Successfully":"डेटा को सफलतापूर्वक अपलोड की गई",Toast.LENGTH_LONG).show();
                                updateRecord();
                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        DootConstants.EXCEPTION_STRING = e.getMessage();
                        showErrorDialog("general");
                    }
                } else{
                    showErrorDialog("general");
                }
            }
        };
        if(checkInternetAndNetwork()) {
            new UploadDataAsyncTask(this, serviceName, paramValue, handler).execute();
        }else{
            DootConstants.EXCEPTION_STRING = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"No internet connectivity. Please enable mobile wifi/data.":"इंटरनेट कनेक्टिविटी उपलब्ध नही हैं। कृपया इंटरनेट कनेक्शन को चेक कर दोबारा कोशिश करें।";
            showErrorDialog("general");
        }
    }

    private boolean uploadPatientListDuringRefreshDevice(List<ScreenRegiDtls> regiDtlsListInfo){

        String serviceName = "ReferralTbPatient";
        String[] value = DootConstants.setRegistrationRecord(regiDtlsListInfo);
        String[] name = {"doot_id", "patient_name","age", "sex","address", "mobile_no","village_id","block_id", "dmc_id",
                "district", "state","country","question_id","answer","screening_date_time","screening_average","longitude","lattitude"};

        ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();
        for(int i=0;i<name.length;i++) {
            paramValue.add(new BasicNameValuePair(name[i], value[i]));
        }
        paramValue.add(new BasicNameValuePair("X_API_KEY",DootConstants.API_KEY));
        paramValue.add(new BasicNameValuePair("device_imei",DootConstants.DEVICE_IMEI));

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                JSONObject jsonObject = (JSONObject) msg.obj;
                if(jsonObject != null){
                    try {
                        if (!(jsonObject.getString("status").equalsIgnoreCase("true"))) {
                            DootConstants.EXCEPTION_STRING = "Something Wrong happen during upload patient data.";
                            showErrorDialog("general");

                        } else{
                            jsonParse.uploadPatientStatusParsing(jsonObject);
                            if(DootConstants.UPLOAD_PATIENT_STATUS.equals("true")){
                                Toast.makeText(DootMenuActivity.this,DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Register Data Uploaded Successfully":"रजिस्टर डेटा को सफलतापूर्वक अपलोड की गई",Toast.LENGTH_LONG).show();
                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        DootConstants.EXCEPTION_STRING = e.getMessage();
                        showErrorDialog("general");
                    }
                } else{
                    showErrorDialog("general");
                }
            }
        };
        if(checkInternetAndNetwork()) {
            new UploadDataAsyncTask(this, serviceName, paramValue, handler).execute();
            return true;
        }else{
            DootConstants.EXCEPTION_STRING = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"No internet connectivity. Please enable mobile wifi/data.":"इंटरनेट कनेक्टिविटी उपलब्ध नही हैं। कृपया इंटरनेट कनेक्शन को चेक कर दोबारा कोशिश करें।";
            showErrorDialog("general");
            return false;
        }

    }
    public void showErrorDialog(final String working){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Alert":"अलर्ट");

        dialog.setMessage(DootConstants.EXCEPTION_STRING);
        dialog.setIcon(R.drawable.icon);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(working.equals("general")) {
                    fragmentManager.popBackStack();
                    dialog.cancel();
                }else if(working.equals("still_same_fragment")){
                    dialog.cancel();
                }else if(working.equals("tbLearning")){
                    chooseTbLearning();
                    dialog.cancel();
                }
            }
        });

        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(working.equals("general")) {
                    fragmentManager.popBackStack();
                    dialog.cancel();
                }else if(working.equals("still_same_fragment")){
                    dialog.cancel();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public void loadingProgressbarFragment(String message){

        ProgressbarFragment progressbarFragment = new ProgressbarFragment();
        Bundle argument = new Bundle();
        argument.putString("progressText", message);
        progressbarFragment.setArguments(argument);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, progressbarFragment, "ProgressbarFragment");

        fragmentTransaction.addToBackStack("ProgressScreen");
        fragmentTransaction.commit();
    }

    public void loadingProgressbar(String message){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    public void showPatientStatusDetail(ScreenRegiDtls screenRegiDtls) {

        PatientStatusFragment patientStatusFragment = new PatientStatusFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, patientStatusFragment, "patient_status_fragment");
        fragmentTransaction.addToBackStack("patient_status_fragment");

        Bundle args = new Bundle();
        args.putSerializable("screen_regi_deatail", screenRegiDtls);
        patientStatusFragment.setArguments(args);
        fragmentTransaction.commit();
    }

    @Override
    public void dootAccountTransaction() {
        DootAccountTransactionFragment dootAccountTransactionFragment = new DootAccountTransactionFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, dootAccountTransactionFragment, "doot_account_transaction_fragment");
        fragmentTransaction.addToBackStack("doot_account_transaction_fragment");
        fragmentTransaction.commit();
        dootAccountTransactionFragment.setDootAccountTransactionFragmentCommunicator(this);
    }


    private void updateRecord(){
        loadingProgressbarFragment(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Updating Doot Records....":"अपडेट दूत रिकार्ड्स ...");
        String serviceName = "PreviousDootRecord";
        String DOOTID = DootConstants.DOOT_ID;
        String key = DootConstants.API_KEY;
        ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();
        paramValue.add(new BasicNameValuePair("doot_id",DOOTID));
        paramValue.add(new BasicNameValuePair("X_API_KEY",key));
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                JSONArray jsonArray = (JSONArray) msg.obj;
                if(jsonArray != null){
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if(!(jsonObject.getString("status").equals("true"))){
                            fragmentManager.popBackStack("ProgressScreen",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            DootConstants.EXCEPTION_STRING = "Something Wrong happen during updating patient data.";
                            showErrorDialog("general");
                        }else{
//                            dootDatabaseAdapter.dropFirst_then_createTable(DootDatabaseAdapter.TBL_PATIENTDETAIL,DootDatabaseAdapter.CREATE_TABLE_PATIENTDETAIL);
                            dootDatabaseAdapter.deleteDataFromTable(DootDatabaseAdapter.TBL_PATIENTDETAIL, DootConstants.LOGIN_KEY);
                            jsonParse.getPatientDataParsing(jsonArray);
                            fragmentManager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            Toast.makeText(DootMenuActivity.this,DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Data Update Successfully":"डेटा सफलतापूर्वक अपडेट हुआ",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        DootConstants.EXCEPTION_STRING = e.getMessage();
                        showErrorDialog("general");
                    }

                }else{
                    showErrorDialog("general");
                }
            }
        };
        if(checkInternetAndNetwork()){
            new UpdateRecordAsyncTask(DootMenuActivity.this,serviceName,paramValue,handler).execute();
        }else{
            DootConstants.EXCEPTION_STRING = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"No internet connectivity. Please try after some time.":"कृपया इंटरनेट कनेक्शन को चेक कर दोबारा कोशिश करें। ";
            showErrorDialog("general");
        }
    }

    @Override
    public void backDootAccountFragment() {
        fragmentManager.popBackStack();
    }

    class MyAdapter extends BaseAdapter{
        String[] drawerlistItem;
        int[] imageItem = {R.drawable.language,R.drawable.settting,R.drawable.refresh};

        public MyAdapter(){

            drawerlistItem = getResources().getStringArray(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?R.array.drawerItem:R.array.drawerItem_hnd);
        }
        @Override
        public int getCount() {
            return drawerlistItem.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.drawerlist_item,parent,false);

                holder = new Holder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();

            }

            holder.textView.setText(drawerlistItem[position]);
            holder.imageView.setImageResource(imageItem[position]);
            return convertView;
        }

        class Holder{
            TextView textView;
            ImageView imageView;

            public Holder(View view){
                textView = (TextView) view.findViewById(R.id.drawer_textView);
                imageView = (ImageView) view.findViewById(R.id.drawer_imageView);
            }
        }

    }

    private List<ScreenRegiDtls> getRegisterdPatientDetail(String tableName){

        List<ScreenRegiDtls> screenRegiDtlsList = new ArrayList<ScreenRegiDtls>();
        Cursor cursor = dootDatabaseAdapter.getData(tableName);

        if(cursor.moveToFirst()){
            do{
                ScreenRegiDtls regiDtls = new ScreenRegiDtls();

                regiDtls.setPatientName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_NAME)));
                regiDtls.setPatientAddress(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_ADDRESS)));
                regiDtls.setPatientPhoneNo(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_PHONE)));
                regiDtls.setPatientAge(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_AGE)));
                regiDtls.setPatientSex(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SEX)));
                regiDtls.setPatientVillage(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_VILLAGE)));
                regiDtls.setPatientVillageId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_VILLAGE_ID)));
                regiDtls.setPatientBlock(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_BLOCK)));
                regiDtls.setPatientBlockId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_BLOCK_ID)));
                regiDtls.setPatientDmc(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DMC)));
                regiDtls.setPatientDmcId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DMC_ID)));
                regiDtls.setPatientDistrict(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DISTRICT)));
                regiDtls.setPatientState(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_STATE)));
                regiDtls.setPatientCountry(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_COUNTRY)));
                regiDtls.setPatientQuestionsId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_QUESTION_ID)));
                regiDtls.setPatientScreenResponse(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_RESPONSE)));
                regiDtls.setScreenDateTime(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREEN_DATE_TIME)));
                regiDtls.setPatientTestStatus(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_STATUS)));
                regiDtls.setPatientTestResult(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_RESULT)));
                regiDtls.setPatientScreenAverage(cursor.getInt(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_AVERAGE)));
                regiDtls.setCheckDataFromLocalServer(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.CHECKDATAFROM_LOCAL_SERVER)));
                regiDtls.setLongitude(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LONGITUDE)));
                regiDtls.setLatitude(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LATITUDE)));
                screenRegiDtlsList.add(regiDtls);

            }while (cursor.moveToNext());
        }
        return screenRegiDtlsList;
    }

    private List<ScreenRegiDtls> getRegisterdPatientDetailOnLoginKey(String tableName,String loginKey){

        List<ScreenRegiDtls> screenRegiDtlsList = new ArrayList<ScreenRegiDtls>();
        Cursor cursor = dootDatabaseAdapter.getDataOnLoginKey(tableName, loginKey);

        if(cursor.moveToFirst()){
            do{
                ScreenRegiDtls regiDtls = new ScreenRegiDtls();

                regiDtls.setPatientName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_NAME)));
                regiDtls.setPatientAddress(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_ADDRESS)));
                regiDtls.setPatientPhoneNo(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_PHONE)));
                regiDtls.setPatientAge(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_AGE)));
                regiDtls.setPatientSex(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SEX)));
                regiDtls.setPatientVillage(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_VILLAGE)));
                regiDtls.setPatientVillageId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_VILLAGE_ID)));
                regiDtls.setPatientBlock(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_BLOCK)));
                regiDtls.setPatientBlockId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_BLOCK_ID)));
                regiDtls.setPatientDmc(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DMC)));
                regiDtls.setPatientDmcId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DMC_ID)));
                regiDtls.setPatientDistrict(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DISTRICT)));
                regiDtls.setPatientState(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_STATE)));
                regiDtls.setPatientCountry(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_COUNTRY)));
                regiDtls.setPatientQuestionsId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_QUESTION_ID)));
                regiDtls.setPatientScreenResponse(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_RESPONSE)));
                regiDtls.setScreenDateTime(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREEN_DATE_TIME)));
                regiDtls.setPatientTestStatus(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_STATUS)));
                regiDtls.setPatientTestResult(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_RESULT)));
                regiDtls.setPatientScreenAverage(cursor.getInt(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_AVERAGE)));
                regiDtls.setCheckDataFromLocalServer(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.CHECKDATAFROM_LOCAL_SERVER)));
                regiDtls.setLongitude(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LONGITUDE)));
                regiDtls.setLatitude(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LATITUDE)));
                screenRegiDtlsList.add(regiDtls);

            }while (cursor.moveToNext());
        }
        return screenRegiDtlsList;
    }
}
