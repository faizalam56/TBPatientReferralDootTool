package zmq.com.tbpatientreferraldoottool.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;


public class LearnMenuFragment extends Fragment implements OnItemClickListener{
	
	GridView mMenu;
	MenuCallbacks comm;
	String item[] = {"Patient Info","Report", "Compliance", "Upload Records"};
	
	public interface MenuCallbacks{
		
		public void menuItemClickListener(int index);
		
	}
	
	public void setMenuCallbacks(MenuCallbacks comm) {
		this.comm = comm;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view  = inflater.inflate(R.layout.menu_learn_tb, container, false);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		comm = (MenuCallbacks) getActivity();
		mMenu = (GridView) getActivity().findViewById(R.id.gridView2);
		
		mMenu.setAdapter(new MyAdapter(getActivity(), R.layout.menu_element));		
		mMenu.setOnItemClickListener(this);	
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	
	class MyAdapter extends BaseAdapter{
		
		int resElement;
		Context context;
		int id[] = {R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4, R.drawable.m5,R.drawable.learning_analytics};
		int id_eng[] = {R.drawable.m1_eng, R.drawable.m2_eng, R.drawable.m3_eng, R.drawable.m4_eng, R.drawable.m5_eng,R.drawable.learning_analytics_eng};

		String item[] = {"समझ","संक्रमण और खतरे", "सावधानी और बचाव", "इलाज और नियंत्रण","रोगी की देखभाल","एनालिटिक्स"};
				
		public MyAdapter(Context context, int resource) {
			this.context = context;
			resElement = resource;
		}

		@Override
		public int getCount() {
			
			return item.length;
		}

		@Override
		public Object getItem(int position) {
			
			return item[position];
		}

		@Override
		public long getItemId(int arg0) {
			
			return arg0;
		}


		@Override
		public View getView(int position, View arg1, ViewGroup parent) {
			View row = arg1;
			MyViewHolder holder = null;
			if(row == null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(resElement, parent, false);
				holder = new MyViewHolder(row);
				row.setTag(holder);
			}
			else{
				holder = (MyViewHolder) row.getTag();
			}	
			
			holder.titleTxt.setText(""+item[position]);
			holder.titleImg.setImageResource(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?id_eng[position]:id[position]);

			if(position==5){
				DootConstants.learningSharedPreferences = getActivity().getSharedPreferences(DootConstants.LEARNING_ANALYTICS_PREFERENCES,Context.MODE_PRIVATE);
				if(DootConstants.learningSharedPreferences.getString(DootConstants.LEARNING_STATUS,null) != null){
					holder.titleImg.setImageResource(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?R.drawable.learning_analytics1_eng:R.drawable.learning_analytics1);
				}
			}
				
			return row ;
		}	
		
	}
	
	class MyViewHolder{
		TextView titleTxt;
		ImageView titleImg;
		
		public MyViewHolder(View view) {
			 titleTxt = (TextView) view.findViewById(R.id.header);
			 titleImg = (ImageView) view.findViewById(R.id.stopSound);			
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		
		comm.menuItemClickListener(index);
	}

}
