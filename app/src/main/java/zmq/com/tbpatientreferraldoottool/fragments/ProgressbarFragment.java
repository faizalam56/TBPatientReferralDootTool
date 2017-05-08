package zmq.com.tbpatientreferraldoottool.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zmq.com.tbpatientreferraldoottool.R;

/**
 * Created by zmq162 on 15/10/16.
 */
public class ProgressbarFragment extends Fragment {
    TextView progressText;
    String textMsg,title;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.progressbar_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textMsg = getArguments().getString("progressText");

        progressText = (TextView) getActivity().findViewById(R.id.progress_txt);
        progressText.setText(textMsg);


    }
}
