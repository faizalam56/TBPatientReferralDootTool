package zmq.com.tbpatientreferraldoottool.utility;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zmq.com.tbpatientreferraldoottool.R;


public class ViewPagerAdapter extends PagerAdapter {
	// Declare Variables
	Context context;
	String[] header;
	String[] content;
	int[] imageId;
	int[] soundId;
	LayoutInflater inflater;

//	public ViewPagerAdapter(Context context, String[] header, String[] content,
//			String[] population, int[] imageId) {
//		this.context = context;
//		this.header = header;
//		this.content = content;
//		this.imageId = imageId;
//	}

	public ViewPagerAdapter(Context context, String[] header,
							String[] content, int[] imageId, int[] sound) {
		
		this.context = context;
		this.header = header;
		this.content = content;
		this.imageId = imageId;
		soundId = sound;
	}

	@Override
	public int getCount() {
		return header.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		// Declare Variables
		TextView txtheader;
		TextView txtcontent;
		TextView txtpopulation;
		ImageView imgimageId;
		ImageView sound;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.page_item, container,
				false);

		// Locate the TextViews in viewpager_item.xml
		//txtheader = (TextView) itemView.findViewById(R.id.textView1);
		txtcontent = (TextView) itemView.findViewById(R.id.textView2);
		//sound = (ImageView) itemView.findViewById(R.id.imageView2);
		
//		txtpopulation = (TextView) itemView.findViewById(R.id.population);

		// Capture position and set to the TextViews
		//txtheader.setText(header[position]);
		txtcontent.setText(content[position]);
//		txtpopulation.setText(population[position]);

		// Locate the ImageView in viewpager_item.xml
		imgimageId = (ImageView) itemView.findViewById(R.id.stopSound);
		// Capture position and set to the ImageView
		imgimageId.setImageResource(imageId[position]);

		// Add viewpager_item.xml to ViewPager
		((ViewPager) container).addView(itemView);
		
//		sound.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Constants.playSound(context, soundId[position]);
//			}
//		});

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((LinearLayout) object);

	}
}
