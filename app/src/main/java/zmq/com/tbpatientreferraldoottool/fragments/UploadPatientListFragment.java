package zmq.com.tbpatientreferraldoottool.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.model.ScreenRegiDtls;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;
import zmq.com.tbpatientreferraldoottool.utility.PatientListAdapter;

/**
 * Created by zmq162 on 14/11/16.
 */
public class UploadPatientListFragment extends Fragment {
    ListView listView;
    Button uploadButton;
    DootDatabaseAdapter dootDatabaseAdapter;
    Context context;
    UploadPatientListFragmentCommunicator communicator;
    List<ScreenRegiDtls>  regiDtlsListInfo;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setUploadPatientListFragmentCommunicator(UploadPatientListFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dootDatabaseAdapter = new DootDatabaseAdapter(context);
//        regiDtlsListInfo = getRegisterdPatientDetail(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER);
        regiDtlsListInfo = getRegisterdPatientDetailOnLoginKey(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER,DootConstants.LOGIN_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_patientlist_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.upload_patientlist);
        uploadButton = (Button) getActivity().findViewById(R.id.upload_button);



        if(regiDtlsListInfo.size()<=0){
            DootConstants.EXCEPTION_STRING = "First Register Patient";
            communicator.noUploadPatientListFind();
        }else{
            PatientListAdapter adapter = new PatientListAdapter(regiDtlsListInfo,getContext());
            listView.setAdapter(adapter);
        }

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.uploadPatientList(regiDtlsListInfo);
            }
        });
    }

    private List<ScreenRegiDtls> getRegisterdPatientDetail(String tableName){

        List<ScreenRegiDtls> screenRegiDtlsList = new ArrayList<ScreenRegiDtls>();
        Cursor cursor = dootDatabaseAdapter.getData(tableName);

        if(cursor.moveToFirst()){
            do{
                ScreenRegiDtls regiDtls = new ScreenRegiDtls();

                regiDtls.setPatientName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_NAME)));
                regiDtls.setPatientAddress(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_ADDRESS)));
                regiDtls.setPatientPhoneNo(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_PHONE)));
                regiDtls.setPatientAge(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_AGE)));
                regiDtls.setPatientSex(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SEX)));
                regiDtls.setPatientVillage(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_VILLAGE)));
                regiDtls.setPatientVillageId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_VILLAGE_ID)));
                regiDtls.setPatientBlock(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_BLOCK)));
                regiDtls.setPatientBlockId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_BLOCK_ID)));
                regiDtls.setPatientDmc(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DMC)));
                regiDtls.setPatientDmcId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DMC_ID)));
                regiDtls.setPatientDistrict(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DISTRICT)));
                regiDtls.setPatientState(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_STATE)));
                regiDtls.setPatientCountry(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_COUNTRY)));
                regiDtls.setPatientQuestionsId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_QUESTION_ID)));
                regiDtls.setPatientScreenResponse(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_RESPONSE)));
                regiDtls.setScreenDateTime(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREEN_DATE_TIME)));
                regiDtls.setPatientTestStatus(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_STATUS)));
                regiDtls.setPatientTestResult(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_RESULT)));
                regiDtls.setPatientScreenAverage(cursor.getInt(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_AVERAGE)));
                regiDtls.setCheckDataFromLocalServer(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.CHECKDATAFROM_LOCAL_SERVER)));
                regiDtls.setLongitude(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LONGITUDE)));
                regiDtls.setLatitude(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LATITUDE)));
                screenRegiDtlsList.add(regiDtls);

            }while (cursor.moveToNext());
        }
        return screenRegiDtlsList;
    }

    private List<ScreenRegiDtls> getRegisterdPatientDetailOnLoginKey(String tableName,String loginKey){

        List<ScreenRegiDtls> screenRegiDtlsList = new ArrayList<ScreenRegiDtls>();
        Cursor cursor = dootDatabaseAdapter.getDataOnLoginKey(tableName,loginKey);

        if(cursor.moveToFirst()){
            do{
                ScreenRegiDtls regiDtls = new ScreenRegiDtls();

                regiDtls.setPatientName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_NAME)));
                regiDtls.setPatientAddress(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_ADDRESS)));
                regiDtls.setPatientPhoneNo(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_PHONE)));
                regiDtls.setPatientAge(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_AGE)));
                regiDtls.setPatientSex(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SEX)));
                regiDtls.setPatientVillage(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_VILLAGE)));
                regiDtls.setPatientVillageId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_VILLAGE_ID)));
                regiDtls.setPatientBlock(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_BLOCK)));
                regiDtls.setPatientBlockId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_BLOCK_ID)));
                regiDtls.setPatientDmc(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DMC)));
                regiDtls.setPatientDmcId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DMC_ID)));
                regiDtls.setPatientDistrict(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_DISTRICT)));
                regiDtls.setPatientState(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_STATE)));
                regiDtls.setPatientCountry(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_COUNTRY)));
                regiDtls.setPatientQuestionsId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_QUESTION_ID)));
                regiDtls.setPatientScreenResponse(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_RESPONSE)));
                regiDtls.setScreenDateTime(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREEN_DATE_TIME)));
                regiDtls.setPatientTestStatus(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_STATUS)));
                regiDtls.setPatientTestResult(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_RESULT)));
                regiDtls.setPatientScreenAverage(cursor.getInt(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_AVERAGE)));
                regiDtls.setCheckDataFromLocalServer(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.CHECKDATAFROM_LOCAL_SERVER)));
                regiDtls.setLongitude(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LONGITUDE)));
                regiDtls.setLatitude(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LATITUDE)));
                screenRegiDtlsList.add(regiDtls);

            }while (cursor.moveToNext());
        }
        return screenRegiDtlsList;
    }

    public interface UploadPatientListFragmentCommunicator{
        void noUploadPatientListFind();
        void uploadPatientList(List<ScreenRegiDtls>  regiDtlsListInfo);

    }
}
