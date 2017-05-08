package zmq.com.tbpatientreferraldoottool.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.model.ScreenRegiDtls;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;
import zmq.com.tbpatientreferraldoottool.utility.PatientListAdapter;
import zmq.com.tbpatientreferraldoottool.utility.PatientListCardViewAdapter;

/**
 * Created by zmq162 on 10/11/16.
 */
public class PatientListFragment extends Fragment {
    ListView listView;
    RecyclerView cardView;
    DootDatabaseAdapter dootDatabaseAdapter;
    Context context;
    String purpose;
    PatientListFragmentCommunicator communicator;

    public void setPatientListFragmentCommunicator(PatientListFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dootDatabaseAdapter = new DootDatabaseAdapter(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patientlist_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        final List<ScreenRegiDtls>  regiDtlsListInfo = getRegisterdPatientDetail(DootDatabaseAdapter.TBL_PATIENTDETAIL);
        final List<ScreenRegiDtls>  regiDtlsListInfo = getRegisterdPatientDetailOnLoginKey(DootDatabaseAdapter.TBL_PATIENTDETAIL, DootConstants.LOGIN_KEY);
        listView = (ListView) getActivity().findViewById(R.id.patient_list);
        PatientListAdapter adapter = new PatientListAdapter(regiDtlsListInfo,getContext());
        listView.setAdapter(adapter);

//        cardView = (RecyclerView) getActivity().findViewById(R.id.patient_list1);
//        PatientListCardViewAdapter adapter = new PatientListCardViewAdapter(regiDtlsListInfo,getContext());
//        cardView.setAdapter(adapter);


        purpose = getArguments().getString("purpose");
        if(purpose.equals("SHOW_PATIENT_DETAIL")){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ScreenRegiDtls screenRegiDtls = regiDtlsListInfo.get(position);
                    communicator.showPatientStatusDetail(screenRegiDtls);
                }
            });
        }

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
                regiDtls.setPatientScreenResponse(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_RESPONSE)));
                regiDtls.setScreenDateTime(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREEN_DATE_TIME)));
                regiDtls.setPatientTestStatus(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_STATUS)));
                regiDtls.setPatientTestResult(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_RESULT)));
                regiDtls.setCheckDataFromLocalServer(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.CHECKDATAFROM_LOCAL_SERVER)));
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
                regiDtls.setPatientScreenResponse(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREENING_RESPONSE)));
                regiDtls.setScreenDateTime(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_SCREEN_DATE_TIME)));
                regiDtls.setPatientTestStatus(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_STATUS)));
                regiDtls.setPatientTestResult(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PATIENT_TEST_RESULT)));
                regiDtls.setCheckDataFromLocalServer(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.CHECKDATAFROM_LOCAL_SERVER)));
                screenRegiDtlsList.add(regiDtls);

            }while (cursor.moveToNext());
        }
        return screenRegiDtlsList;
    }

    public interface PatientListFragmentCommunicator{
        void showPatientStatusDetail(ScreenRegiDtls screenRegiDtls);
    }

}
