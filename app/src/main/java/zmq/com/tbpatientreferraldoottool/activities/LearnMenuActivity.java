package zmq.com.tbpatientreferraldoottool.activities;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.fragments.LearnMenuFragment;
import zmq.com.tbpatientreferraldoottool.fragments.ProgressbarFragment;
import zmq.com.tbpatientreferraldoottool.model.Learningdtls;
import zmq.com.tbpatientreferraldoottool.network.UploadDataAsyncTask;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;
import zmq.com.tbpatientreferraldoottool.utility.JsonParse;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LearnMenuActivity extends ActionBarActivity implements LearnMenuFragment.MenuCallbacks {

	FragmentManager fragmentManager;
	JsonParse jsonParse;
	DootDatabaseAdapter dootDatabaseAdapter;
	List<Learningdtls> learningDetailInfo;
	
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_container);
        getSupportActionBar().hide();

		fragmentManager = getSupportFragmentManager();
		jsonParse = new JsonParse(this);
		dootDatabaseAdapter = new DootDatabaseAdapter(this);
        
        if(savedInstanceState == null){
        	FragmentTransaction transaction = fragmentManager.beginTransaction();
        	LearnMenuFragment mMenuFragment = new LearnMenuFragment();
        	transaction.replace(R.id.container, mMenuFragment, "MenuFragment");
        	transaction.commit(); 
        	mMenuFragment.setMenuCallbacks(this);
        }
    }

	@Override
	public void menuItemClickListener(int index) {
		
		Intent intent = new Intent(this, LearningActivity.class);
		switch (index) {
		case 0:
			System.out.println("dootid "+ DootConstants.DOOT_ID);
			System.out.println("dootid "+ DootConstants.API_KEY);

			intent.putExtra("Header", DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_1_eng : DootConstants.module_1);
			intent.putExtra("Content",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_1_content_eng : DootConstants.module_1_content);
			intent.putExtra("Sound",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_1_sound_eng : DootConstants.module_1_sound);
			intent.putExtra("Image", DootConstants.module_1_img);
			intent.putExtra("Cursor",DootConstants.LEARNING_1);
			startActivity(intent);
			finish();
			break;
		case 1:
			intent.putExtra("Header",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_2_eng : DootConstants.module_2);
			intent.putExtra("Content",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_2_content_eng : DootConstants.module_2_content);
			intent.putExtra("Sound",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_2_sound_eng : DootConstants.module_2_sound);
			intent.putExtra("Image", DootConstants.module_2_img);
			intent.putExtra("Cursor",DootConstants.LEARNING_2);
			startActivity(intent);
			finish();
			break;
		case 2:
			intent.putExtra("Header",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_3_eng : DootConstants.module_3);
			intent.putExtra("Content",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_3_content_eng : DootConstants.module_3_content);
			intent.putExtra("Sound",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_3_sound_eng : DootConstants.module_3_sound);
			intent.putExtra("Image", DootConstants.module_3_img);
			intent.putExtra("Cursor",DootConstants.LEARNING_3);
			startActivity(intent);
			finish();
			break;
		case 3:
			intent.putExtra("Header",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_4_eng : DootConstants.module_4);
			intent.putExtra("Content",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_4_content_eng : DootConstants.module_4_content);
			intent.putExtra("Sound",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_4_sound_eng : DootConstants.module_4_sound);
			intent.putExtra("Image", DootConstants.module_4_img);
			intent.putExtra("Cursor",DootConstants.LEARNING_4);
			startActivity(intent);
			finish();
			break;
		case 4:
			intent.putExtra("Header",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_5_eng : DootConstants.module_5);
			intent.putExtra("Content",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_5_content_eng : DootConstants.module_5_content);
			intent.putExtra("Sound",DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?DootConstants.module_5_sound_eng : DootConstants.module_5_sound);
			intent.putExtra("Image", DootConstants.module_5_img);
			intent.putExtra("Cursor", DootConstants.LEARNING_5);
			startActivity(intent);
			finish();
			break;
		case 5:
			
			DootConstants.learningSharedPreferences = getSharedPreferences(DootConstants.LEARNING_ANALYTICS_PREFERENCES,MODE_PRIVATE);
//                if((DootConstants.learningSharedPreferences.getString(DootConstants.LEARNING_1,null) != null)||
//                        (DootConstants.learningSharedPreferences.getString(DootConstants.LEARNING_2,null) != null)||
//                        (DootConstants.learningSharedPreferences.getString(DootConstants.LEARNING_3,null) != null)||
//                        (DootConstants.learningSharedPreferences.getString(DootConstants.LEARNING_4,null) != null)||
//                        (DootConstants.learningSharedPreferences.getString(DootConstants.LEARNING_5,null) != null)) {
			if(DootConstants.learningSharedPreferences.getString(DootConstants.LEARNING_STATUS,null) != null){
				AlertDialog.Builder settingDialog = new AlertDialog.Builder(LearnMenuActivity.this);
				settingDialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Upload Learning Analytics":" अपलोड लर्निंग एनालिटिक्स");
				settingDialog.setMessage(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Do you want to upload learning analytics?":"क्या आप लर्निंग एनालिटिक्स अपलोड करना चाहते हैं?");
				settingDialog.setIcon(R.drawable.icon);
				settingDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						loadingProgressbarFragment(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Learning analytics record uploading.....":"लर्निंग एनालिटिक्स  रिकॉर्ड अपलोड...");
						String[] leaningModule = {DootConstants.LEARNING_1, DootConstants.LEARNING_2, DootConstants.LEARNING_3, DootConstants.LEARNING_4, DootConstants.LEARNING_5};
						String serviceName = "tbLearningAnalaytics";
						String[] name = {"doot_id", "contents", "sub_contents"};
						String[] value = DootConstants.setLearningAnalyticsRecord(LearnMenuActivity.this, leaningModule);

						ArrayList<NameValuePair> paramValue = new ArrayList<NameValuePair>();
						for(int i=0; i<name.length; i++){
							paramValue.add(new BasicNameValuePair(name[i],value[i]));
						}
						paramValue.add(new BasicNameValuePair("X_API_KEY",DootConstants.API_KEY));

						Handler handler = new Handler(){
							@Override
							public void handleMessage(Message msg) {
								JSONObject jsonObject = (JSONObject) msg.obj;
								if(jsonObject != null){
									try {
										if(!(jsonObject.getString("success").equalsIgnoreCase("true"))){
											fragmentManager.popBackStack("ProgressScreen",FragmentManager.POP_BACK_STACK_INCLUSIVE);
											DootConstants.EXCEPTION_STRING = "Something happen wrong during upload learning analytics.";
											showErrorDialog("general");
										} else{
											jsonParse.uploadLearningAnalyticsStatusParsing(jsonObject);
											if(DootConstants.UPLOAD_LEARNING_ANALYTICS_STATUS.equals("true")){
												clearSharedPrefrenceData(DootConstants.LEARNING_ANALYTICS_PREFERENCES);
												fragmentManager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
												Toast.makeText(LearnMenuActivity.this, DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Analytics Data Uploaded Successfully":"डेटा को सफलतापूर्वक अपलोड की गई", Toast.LENGTH_LONG).show();
											}
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
						if(checkInternetAndNetwork()) {
							new UploadDataAsyncTask(getApplicationContext(), serviceName, paramValue, handler).execute();
						}else{
							DootConstants.EXCEPTION_STRING = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"No internet connectivity. Please enable mobile wifi/data.":"इंटरनेट कनेक्टिविटी उपलब्ध नही हैं। कृपया इंटरनेट कनेक्शन को चेक कर दोबारा कोशिश करें। ";
							showErrorDialog("general");
						}
					}
				});
				settingDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				settingDialog.show();
			}else{
				DootConstants.EXCEPTION_STRING = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Please run through the TB Learning module first.":"कृपया अपलोड करने से पहले टी.बी. लर्निंग मॉडयूल का इस्तेमाल करें।";
				showErrorDialog("tbLearning");
			}
			break;

		default:
			break;
		}
	}

	public void loadingProgressbarFragment(String message){

		ProgressbarFragment progressbarFragment = new ProgressbarFragment();
		Bundle argument = new Bundle();
		argument.putString("progressText", message);
		progressbarFragment.setArguments(argument);

		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.container, progressbarFragment, "ProgressbarFragment");

		fragmentTransaction.addToBackStack("ProgressScreen");
		fragmentTransaction.commit();
	}

	public void showErrorDialog(final String working){
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Alert":"अलर्ट");

		dialog.setMessage(DootConstants.EXCEPTION_STRING);
		dialog.setIcon(R.drawable.icon);

		dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (working.equals("general")) {
					fragmentManager.popBackStack();
					dialog.cancel();
				} else if (working.equals("tbLearning")) {
					dialog.cancel();
				}
			}
		});

		dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (working.equals("general")) {
					fragmentManager.popBackStack();
					dialog.cancel();
				} else if (working.equals("tbLearning")) {
					dialog.cancel();
				}
			}
		});
		dialog.setCancelable(false);
		dialog.show();
	}

	public void clearSharedPrefrenceData(String sharedPreferenceName){
		DootConstants.learningSharedPreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
		SharedPreferences.Editor editor = DootConstants.learningSharedPreferences.edit();
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

//	private List<Learningdtls> getLearningAnalyticsDataOnLoginKey(String tableName,String loginKey,String learningModule){
//		List<Learningdtls> learningdtlsList = new ArrayList<Learningdtls>();
//		Cursor cursor = dootDatabaseAdapter.getDataOnLoginKey(tableName, loginKey);
//		if(cursor.moveToFirst()){
//			do{
//				Learningdtls learningdtls = new Learningdtls();
//				learningdtls.setLearningAnalytics(cursor.getString(cursor.getColumnIndex(learningModule)));
//				learningdtlsList.add(learningdtls);
//			}while (cursor.moveToNext());
//		}
//		return learningdtlsList;
//
//	}
    
}
