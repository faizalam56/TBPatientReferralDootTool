package zmq.com.tbpatientreferraldoottool.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.model.Dootdtls;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;

/**
 * Created by zmq162 on 13/10/16.
 */
public class DootMenuFragment extends Fragment implements View.OnClickListener {
    Toolbar toolbar;
    Button tbRefferal,tbLearning;
    Context context;
    DootDatabaseAdapter dootDatabaseAdapter;
    DootMenuCommunicator communicator;
    ImageView tbImage;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dootmenu_fragment,container,false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setDootMenuCommunicator(DootMenuCommunicator communicator){
        this.communicator =communicator;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("DootMenu");
//        toolbar.setLogo(R.drawable.icon);

        tbRefferal = (Button) getActivity().findViewById(R.id.tbreferral);
        tbLearning = (Button) getActivity().findViewById(R.id.tblearning);
        tbImage = (ImageView) getActivity().findViewById(R.id.tbImg);

        tbImage.setImageResource(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?R.drawable.tbimage:R.drawable.tbimage_hnd);

        tbRefferal.setOnClickListener(this);
        tbLearning.setOnClickListener(this);
//        dootDatabaseAdapter = new DootDatabaseAdapter(context);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tbreferral:
                communicator.chooseReferralMenu();
                break;
            case R.id.tblearning:
                communicator.chooseTbLearning();
                break;
            default:
                break;
        }
    }


    public interface DootMenuCommunicator{
        void chooseReferralMenu();
        void chooseTbLearning();
    }
}
