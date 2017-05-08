package zmq.com.tbpatientreferraldoottool.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.model.ScreenRegiDtls;

/**
 * Created by zmq162 on 16/11/16.
 */
public class PatientStatusFragment extends Fragment {
    TextView name, dmc, scr_date, phone, test_status, test_result, scr_detail;
    ScreenRegiDtls scrRegiDtls;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrRegiDtls = (ScreenRegiDtls) getArguments().getSerializable("screen_regi_deatail");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_status_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name = (TextView) getActivity().findViewById(R.id.name);
        name.setText(scrRegiDtls.getPatientName());

        dmc = (TextView) getActivity().findViewById(R.id.dmc);
        dmc.setText(scrRegiDtls.getPatientDmc());

        scr_date = (TextView) getActivity().findViewById(R.id.scr_date);
        scr_date.setText(scrRegiDtls.getScreenDateTime());

        phone = (TextView) getActivity().findViewById(R.id.phone);
        phone.setText(scrRegiDtls.getPatientPhoneNo());

        test_status = (TextView) getActivity().findViewById(R.id.test_status);
        test_status.setText(scrRegiDtls.getPatientTestStatus());

        test_result = (TextView) getActivity().findViewById(R.id.test_result);
        test_result.setText(scrRegiDtls.getPatientTestResult());

        scr_detail = (TextView) getActivity().findViewById(R.id.scr_detail);
        scr_detail.setText(scrRegiDtls.getPatientScreenResponse());
    }
}
