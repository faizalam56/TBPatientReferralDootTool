package zmq.com.tbpatientreferraldoottool.activities;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.model.Learningdtls;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;
import zmq.com.tbpatientreferraldoottool.utility.MySound;
import zmq.com.tbpatientreferraldoottool.utility.ViewPagerAdapter;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class LearningActivity extends Activity{
	
	ViewPager viewPager;
	PagerAdapter adapter;
	TextView title;
	ImageView playSound,stopSound;
	int current;
	String leaningModule;
	int[] pageVisitValue;
	String[] header;
	DootDatabaseAdapter dootDatabaseAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_page);
//		getActionBar().hide();
		
		Intent intent = getIntent();
		
		 header = intent.getStringArrayExtra("Header");//{"demo1","demo2","demo3","demo4"};
		String content[] = intent.getStringArrayExtra("Content");//{"content 1","content 2","content 3","content 4"};
		int imageId[] = intent.getIntArrayExtra("Image");//{R.drawable.demo,R.drawable.demo,R.drawable.demo,R.drawable.demo,R.drawable.demo};
		final int sound[] = intent.getIntArrayExtra("Sound");//{R.raw.dnt_one,R.raw.dnt_two,R.raw.dnt_three,R.raw.dnt_four};
		leaningModule = intent.getStringExtra("Cursor");

		dootDatabaseAdapter = new DootDatabaseAdapter(this);

		DootConstants.learningSharedPreferences = getSharedPreferences(DootConstants.LEARNING_ANALYTICS_PREFERENCES,MODE_PRIVATE);
		if (DootConstants.learningSharedPreferences.getString(leaningModule, null) == null) {
			saveInSPdataBase(leaningModule);
		}

		String recordValue = DootConstants.learningSharedPreferences.getString(leaningModule, null);
		System.out.println("Record value.... "+recordValue);
		String[] splitValue = recordValue.split(":");
		pageVisitValue = new int[splitValue.length];
		for(int i=0;i< pageVisitValue.length;i++){
			pageVisitValue[i] = Integer.parseInt(splitValue[i]);
		}
		pageVisitValue[0] = pageVisitValue[0]+1;


//		List<Learningdtls> learnAnalyticInfo = getLearningAnalyticsDataOnLoginKey(DootDatabaseAdapter.TBL_TBLEARNING,DootConstants.LOGIN_KEY,leaningModule);
//		if(learnAnalyticInfo.size()>=0){
//			System.out.println("Record value.... "+learnAnalyticInfo);
//			String recordValue1 = learnAnalyticInfo.toString();
//			String[] splitValue1 = recordValue1.split(":");
//			pageVisitValue = new int[splitValue1.length];
//			for(int i=0;i< pageVisitValue.length;i++){
//				pageVisitValue[i] = Integer.parseInt(splitValue1[i]);
//			}
//			pageVisitValue[0] = pageVisitValue[0]+1;
//		}

		title = (TextView) findViewById(R.id.header);
		playSound = (ImageView) findViewById(R.id.playSound);
		stopSound = (ImageView) findViewById(R.id.stopSound);
		
		// Locate the ViewPager in viewpager_main.xml
				viewPager = (ViewPager) findViewById(R.id.pager);
				// Pass results to ViewPagerAdapter Class
				adapter = new ViewPagerAdapter(this, header, content, imageId, sound);
				// Binds the Adapter to the ViewPager
				adapter.getItemPosition(header);
				viewPager.setAdapter(adapter);
				title.setText(header[current]);
				
				System.out.println("Current = " + viewPager.getCurrentItem());
				viewPager.addOnPageChangeListener(new OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
						System.out.println("onPageScrolled is called");
						MySound.stopSound();


					}

					@Override
					public void onPageSelected(int position) {
						System.out.println("onPageSelected is called");
						current = position;
						title.setText(header[current]);
						pageVisitValue[position] = pageVisitValue[position]+1;
					}

					@Override
					public void onPageScrollStateChanged(int state) {
						System.out.println("onPageScrollStateChanged is called");
					}
				});

				
				playSound.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						MySound.playSound(LearningActivity.this, sound[current]);
					}
				});
				
				stopSound.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						MySound.stopSound();
					}
				});
				
	}
	
	@Override
	protected void onPause() {
//		Constants.stopSound();
		super.onPause();
	}

	private void saveInSPdataBase(String leaningModule){
		DootConstants.learningSharedPreferences = getSharedPreferences(DootConstants.LEARNING_ANALYTICS_PREFERENCES,MODE_PRIVATE);
		SharedPreferences.Editor editor = DootConstants.learningSharedPreferences.edit();
		String saveString = "";

		for(int i=0;i<header.length;i++){
			saveString += 0 + ":";
		}
		editor.putString(leaningModule,saveString);
		editor.putString(DootConstants.LEARNING_STATUS,"true");
		editor.commit();
	}

	private void saveInDataBaseAdapter(String leaningModule){
		String saveString = "";

		for(int i=0;i<header.length;i++){
			saveString += 0 + ":";
		}
		try {
			dootDatabaseAdapter.openDataBase();
			ContentValues contentValues = new ContentValues();

			contentValues.put(leaningModule,saveString);
			//*****this field is used for further check the login status of user. ***************
			contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);

			dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_TBLEARNING,contentValues);
			dootDatabaseAdapter.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<Learningdtls> getLearningAnalyticsDataOnLoginKey(String tableName,String loginKey,String learningModule){
		List<Learningdtls> learningdtlsList = new ArrayList<Learningdtls>();
		Cursor cursor = dootDatabaseAdapter.getDataOnLoginKey(tableName,loginKey);
		if(cursor.moveToFirst()){
			do{
				Learningdtls learningdtls = new Learningdtls();
				learningdtls.setLearningAnalytics(cursor.getString(cursor.getColumnIndex(learningModule)));
				learningdtlsList.add(learningdtls);
			}while (cursor.moveToNext());
		}
		return learningdtlsList;

	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		MySound.stopSound();
		DootConstants.learningSharedPreferences = getSharedPreferences(DootConstants.LEARNING_ANALYTICS_PREFERENCES,MODE_PRIVATE);
		SharedPreferences.Editor editor = DootConstants.learningSharedPreferences.edit();
		String saveString = "";

		for (int i=0;i<header.length;i++){
			saveString += pageVisitValue[i]+":";
		}
		System.out.println("SharedPref containt..." + leaningModule + "having value..." + saveString);
		editor.putString(leaningModule, saveString);
		editor.commit();

		Intent intent = new Intent(LearningActivity.this,LearnMenuActivity.class);
		startActivity(intent);
		finish();
	}

//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		MySound.stopSound();
//		String saveString = "";
//
//		for (int i=0;i<header.length;i++){
//			saveString += pageVisitValue[i]+":";
//		}
//		System.out.println("SqlDatabase containt..." + leaningModule + "having value..." + saveString);
//
//		try {
//			dootDatabaseAdapter.openDataBase();
//			ContentValues contentValues = new ContentValues();
//
//			contentValues.put(leaningModule,saveString);
//			//*****this field is used for further check the login status of user. ***************
//			contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);
//
//			dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_TBLEARNING,contentValues);
//			dootDatabaseAdapter.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		Intent intent = new Intent(LearningActivity.this,LearnMenuActivity.class);
//		startActivity(intent);
//		finish();
//	}
}
