package zmq.com.tbpatientreferraldoottool.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import org.json.JSONObject;

import java.util.Locale;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.fragments.SplashFragment;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;
import zmq.com.tbpatientreferraldoottool.utility.JsonParse;

public class MainActivity extends AppCompatActivity implements
        SplashFragment.SplashCommunicator{

    FragmentManager fragmentManager;
//    Handler handler;
    JSONObject jsonObject;
    JsonParse jsonParse;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        fragmentManager = getSupportFragmentManager();
        jsonParse = new JsonParse(this);

        DootConstants.languageSharedPreference = getSharedPreferences(DootConstants.LANGUAGE_PREFERENCE,MODE_PRIVATE);

        DootConstants.sharedPreferences = getSharedPreferences(DootConstants.PREFERENCES,MODE_PRIVATE);
        DootConstants.DOOT_ID = DootConstants.sharedPreferences.getString("doot_id", "0");
        DootConstants.DOOT_NAME = DootConstants.sharedPreferences.getString("doot_name","faiz");
        DootConstants.API_KEY = DootConstants.sharedPreferences.getString("api_key","faiz");
        DootConstants.DEVICE_IMEI = DootConstants.sharedPreferences.getString("device_imei","faiz");


        if (savedInstanceState==null){
            SplashFragment splashFragment = new SplashFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_container, splashFragment, "splash_screen");
            fragmentTransaction.addToBackStack("splash_screen");
            fragmentTransaction.commit();

            splashFragment.setSplashCommunicator(this);
        }


    }


    @Override
    public void splashTouch() {//here we write database result.

//        if(DootConstants.sharedPreferences.getString("doot_id", null) != null) {
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this,DootMenuActivity.class);
//            startActivity(intent);

            if(DootConstants.languageSharedPreference.getString("language",DootConstants.ENGLISH).equals(DootConstants.ENGLISH)){
                DootConstants.LANGUAGE = DootConstants.ENGLISH;
                Locale myLocale = new Locale("en");
                Locale.setDefault(myLocale);
                android.content.res.Configuration config = new android.content.res.Configuration();
                config.locale = myLocale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            }else{
                DootConstants.LANGUAGE = DootConstants.HINDI;
                Locale myLocale1 = new Locale("hi");
                Locale.setDefault(myLocale1);
                android.content.res.Configuration config1 = new android.content.res.Configuration();
                config1.locale = myLocale1;
                getBaseContext().getResources().updateConfiguration(config1, getBaseContext().getResources().getDisplayMetrics());

            }

//        }else{

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
//        }
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Exit Application":"एप्लीकेशन बंद करे");
        dialog.setMessage(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Are you sure you want to exit?":"क्या आप वास्तव मे बाहर जाना चाहते हैं?");
        dialog.setIcon(R.drawable.icon);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

}
