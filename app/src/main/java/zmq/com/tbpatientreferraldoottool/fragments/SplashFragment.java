package zmq.com.tbpatientreferraldoottool.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zmq.com.tbpatientreferraldoottool.R;

/**
 * Created by zmq162 on 10/10/16.
 */
public class SplashFragment extends Fragment implements View.OnClickListener {
    SplashCommunicator communicator;
    LinearLayout linearLayout;
    Toolbar toolbar;

    public void setSplashCommunicator(SplashCommunicator communicator){
        this.communicator = communicator;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int[] imageItem = {R.drawable.screening_pat,R.drawable.upload_patlist,R.drawable.view_patient,R.drawable.patient_status,
                R.drawable.account1,R.drawable.analytics1};
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.layout_bacground_splash);
        linearLayout.setOnClickListener(this);



//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            int i=0;
//            @Override
//            public void run() {
//                imageView.setImageResource(imageItem[i]);
//                i++;
//                if(i>imageItem.length-1){
//                    i=0;
//                }
//                handler.postDelayed(this,2000);
//            }
//        };
//        handler.postDelayed(runnable,2000);
    }

    @Override
    public void onClick(View v) {
        communicator.splashTouch();
    }

    public interface SplashCommunicator{
        void splashTouch();
    }
}
