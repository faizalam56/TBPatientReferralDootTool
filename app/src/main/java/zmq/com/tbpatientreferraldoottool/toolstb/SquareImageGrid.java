package zmq.com.tbpatientreferraldoottool.toolstb;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageGrid extends ImageView{

	public SquareImageGrid(Context context) {
			super(context);
		
	}
	public SquareImageGrid(Context context, AttributeSet attrs){
	
	        super(context, attrs);
	}

	public SquareImageGrid(Context context, AttributeSet attrs, int defStyle){
	
	        super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
	
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
	}

}
