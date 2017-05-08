package zmq.com.tbpatientreferraldoottool.fragments;

import android.app.Activity;

import android.content.ContentValues;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.model.Dmcdtls;
import zmq.com.tbpatientreferraldoottool.model.Dootdtls;
import zmq.com.tbpatientreferraldoottool.model.QuestionDtls;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;

/**
 * Created by zmq162 on 5/11/16.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    EditText name,age,address,phoneNo,district,state,country,block;
    Spinner sex,village,dmc;
//    Spinner block;
    Button save,cancel;
    DootDatabaseAdapter dootDatabaseAdapter;
    Cursor cursor;
    String[] villageName,dmcName,quesId;
//    String[] blockName;

    String districtName,stateName,countryName,screenDateTime,vill_id,dmc_id,blocl_id,blockName;
    RegistrationFragmentCommunicator communicator;
    String ansValue,questionId;
    int screenAverage;
    List<Dootdtls> dootDetailInfo;
    List<Dmcdtls> dmcdtlsInfo;
    List<QuestionDtls> questionDtlsInfo;
    double latitude,longitude;

    public void setRegistrationFragmentCommunicator(RegistrationFragmentCommunicator communicator){
        this.communicator = communicator;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dootDatabaseAdapter = new DootDatabaseAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        dootDetailInfo = getDataFromTableDoot(DootDatabaseAdapter.TBL_DOOTDETAIL);
        dootDetailInfo = getDataFromTableDootOnLoginKey(DootDatabaseAdapter.TBL_DOOTDETAIL,DootConstants.LOGIN_KEY);
        System.out.println("Size of dootDetailList.."+dootDetailInfo.size());
        villageName = new String[dootDetailInfo.size()];
//        blockName = new String[dootDetailInfo.size()];

        for(int i=0;i<dootDetailInfo.size();i++){
            Dootdtls dootdtls = dootDetailInfo.get(i);
            villageName[i] = dootdtls.getVillageName();
//            blockName[i] = dootdtls.getBlockName();
            blockName = dootdtls.getBlockName();
            districtName = dootdtls.getDistrictName();
            stateName = dootdtls.getStateName();
            countryName = dootdtls.getCountryName();
        }

//        dmcdtlsInfo = getDataFromTableDmcDetail(DootDatabaseAdapter.TBL_DMCDETAIL);
        dmcdtlsInfo = getDataFromTableDmcDetailOnLoginKey(DootDatabaseAdapter.TBL_DMCDETAIL, DootConstants.LOGIN_KEY);
        dmcName = new String[dmcdtlsInfo.size()];

        for(int i=0;i<dmcdtlsInfo.size();i++){
            Dmcdtls dmcdtls = dmcdtlsInfo.get(i);
            dmcName[i] = dmcdtls.getDmcName();
        }

        questionDtlsInfo = getDataFronTableQuestion(DootDatabaseAdapter.TBL_QUESTION);
        quesId = new String[questionDtlsInfo.size()];
        for(int i=0;i<questionDtlsInfo.size();i++){
            QuestionDtls quesDtls = questionDtlsInfo.get(i);
            quesId[i] = quesDtls.getQuestionId();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_fragment,container,false);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (EditText) getActivity().findViewById(R.id.fill_patientName);
        address = (EditText) getActivity().findViewById(R.id.fill_patientAddress);
        phoneNo = (EditText) getActivity().findViewById(R.id.fill_pateintPhoneNo);

        block = (EditText) getActivity().findViewById(R.id.fill_pateintBlock);
        block.setText(blockName);

        district = (EditText) getActivity().findViewById(R.id.fill_pateintDistrict);
        district.setText(districtName);

        state = (EditText) getActivity().findViewById(R.id.fill_pateintState);
        state.setText(stateName);

        country = (EditText) getActivity().findViewById(R.id.fill_pateintCountry);
        country.setText(countryName);

        age = (EditText) getActivity().findViewById(R.id.fill_pateintAge);
        sex = (Spinner) getActivity().findViewById(R.id.fill_pateintSex);


 //################# when we use our Custom Adapter, apply following ################################

//        village = (Spinner) getActivity().findViewById(R.id.fill_pateintVillage);
//        DootDetailAdapter adapter = new DootDetailAdapter(dootDetailInfo,getActivity(),1,R.layout.doot_detail_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        village.setAdapter(adapter);
//        village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Dootdtls dootdtls = dootDetailInfo.get(position);
//                vill_id = dootdtls.getVillageId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//         block = (Spinner) getActivity().findViewById(R.id.fill_pateintBlock);
//        DootDetailAdapter adapter1 = new DootDetailAdapter(dootDetailInfo,getActivity(),2,R.layout.doot_detail_item);
//        block.setAdapter(adapter1);
//        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Dootdtls dootdtls = dootDetailInfo.get(position);
//                blocl_id = dootdtls.getBlockId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        dmc = (Spinner) getActivity().findViewById(R.id.fill_pateintDmc);
//        DootDetailAdapter adapter2 = new DootDetailAdapter(dmcdtlsInfo,getActivity(),R.layout.doot_detail_item);
//        dmc.setAdapter(adapter2);
//        dmc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Dmcdtls dmcdtls = dmcdtlsInfo.get(position);
//                dmc_id = dmcdtls.getDmcId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//#################### when use ArrayAdapter then using following code #################################
//******** for selection of village_name and Village_id.***********
        village = (Spinner) getActivity().findViewById(R.id.fill_pateintVillage);
        ArrayAdapter<String> village_arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,villageName);
        village_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        village.setAdapter(village_arrayAdapter);
        village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Dootdtls dootdtls = dootDetailInfo.get(position);
                vill_id = dootdtls.getVillageId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//********* for selection of Block_name and Block_id****************************************
//        block = (Spinner) getActivity().findViewById(R.id.fill_pateintBlock);
//        ArrayAdapter<String> block_arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,blockName);
//        block_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        block.setAdapter(block_arrayAdapter);
//        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Dootdtls dootdtls = dootDetailInfo.get(position);
//                blocl_id = dootdtls.getBlockId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


//******* for selection of Dmc_name and Dmc_id ********************************
        dmc = (Spinner) getActivity().findViewById(R.id.fill_pateintDmc);
        ArrayAdapter<String> dmc_arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,dmcName);
        dmc_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dmc.setAdapter(dmc_arrayAdapter);
        dmc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Dmcdtls dmcdtls = dmcdtlsInfo.get(position);
                dmc_id = dmcdtls.getDmcId();
                System.out.println("Dmc-Id......" + dmc_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save = (Button) getActivity().findViewById(R.id.save);
        cancel = (Button) getActivity().findViewById(R.id.cancel);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        ansValue = getArguments().getString("answerValue");
        screenDateTime = getArguments().getString("screenDateTime");
        screenAverage = getArguments().getInt("screenAverage");
        longitude = getArguments().getDouble("longitude");
        latitude = getArguments().getDouble("latitude");

        questionId = quesId[0]+":"+quesId[1]+":"+quesId[2]+":"+quesId[3];
        System.out.println("Screen-Tme and Date...."+screenDateTime  +" questionId "+questionId);
        System.out.println("Longitude....."+longitude+"....Latitude...."+latitude);

    }

    public List<Dootdtls> getDataFromTableDoot(String tableName){
         cursor = dootDatabaseAdapter.getData(tableName);
        List<Dootdtls> dootdtlsList = new ArrayList<Dootdtls>();

        if(cursor.moveToFirst()){
            do {
                Dootdtls dootdtls = new Dootdtls();

                dootdtls.setDootId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_ID)));
                dootdtls.setDootName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_NAME)));
                dootdtls.setBlockId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.BLOCK_ID)));
                dootdtls.setBlockName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.BLOCK_NAME)));
                dootdtls.setVillageId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.VILLAGE_ID)));
                dootdtls.setVillageName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.VILLAGE_NAME)));
                dootdtls.setDistrictName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DISTRICT_NAME)));
                dootdtls.setStateName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.STATE_NAME)));
                dootdtls.setCountryName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.COUNTRY_NAME)));
                dootdtlsList.add(dootdtls);
            }while (cursor.moveToNext());
        }
        return dootdtlsList;
    }

    public List<Dootdtls> getDataFromTableDootOnLoginKey(String tableName,String loginKey){
        cursor = dootDatabaseAdapter.getDataOnLoginKey(tableName, loginKey);
        List<Dootdtls> dootdtlsList = new ArrayList<Dootdtls>();

        if(cursor.moveToFirst()){
            do {
                Dootdtls dootdtls = new Dootdtls();

                dootdtls.setDootId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_ID)));
                dootdtls.setDootName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_NAME)));
                dootdtls.setBlockId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.BLOCK_ID)));
                dootdtls.setBlockName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.BLOCK_NAME)));
                dootdtls.setVillageId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.VILLAGE_ID)));
                dootdtls.setVillageName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.VILLAGE_NAME)));
                dootdtls.setDistrictName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DISTRICT_NAME)));
                dootdtls.setStateName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.STATE_NAME)));
                dootdtls.setCountryName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.COUNTRY_NAME)));
                dootdtls.setLoginKey(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.LOGIN_KEY)));
                dootdtlsList.add(dootdtls);
            }while (cursor.moveToNext());
        }
        return dootdtlsList;
    }

    public List<Dmcdtls> getDataFromTableDmcDetail(String tableName){
        List<Dmcdtls> dmcdtlsList = new ArrayList<Dmcdtls>();
        cursor = dootDatabaseAdapter.getData(tableName);
        if(cursor.moveToFirst()){
            do{
                Dmcdtls dmcdtls = new Dmcdtls();

                dmcdtls.setDmcId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DMC_ID)));
                dmcdtls.setDmcName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DMC_NAME)));
                dmcdtlsList.add(dmcdtls);
            }while (cursor.moveToNext());
        }
        return  dmcdtlsList;
    }

    public List<Dmcdtls> getDataFromTableDmcDetailOnLoginKey(String tableName,String loginKey){
        List<Dmcdtls> dmcdtlsList = new ArrayList<Dmcdtls>();
        cursor = dootDatabaseAdapter.getDataOnLoginKey(tableName,loginKey);
        if(cursor.moveToFirst()){
            do{
                Dmcdtls dmcdtls = new Dmcdtls();

                dmcdtls.setDmcId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DMC_ID)));
                dmcdtls.setDmcName(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DMC_NAME)));
                dmcdtlsList.add(dmcdtls);
            }while (cursor.moveToNext());
        }
        return  dmcdtlsList;
    }

    public List<QuestionDtls> getDataFronTableQuestion(String tableName){
        List<QuestionDtls> questionDtlsList = new ArrayList<QuestionDtls>();
        cursor = dootDatabaseAdapter.getData(tableName);
        if(cursor.moveToFirst()){
            do {
                QuestionDtls questionDtls = new QuestionDtls();

                questionDtls.setQuestionId(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.QUESTION_ID)));
                questionDtls.setQuestionText(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.QUESTION_TEXT)));
                questionDtlsList.add(questionDtls);

            }while (cursor.moveToNext());
        }
        return  questionDtlsList;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                communicator.saveRegistrationDetail();
                hideKeypad();
                break;
            case R.id.save:
                if(name.getText().toString().length()<=0){
                    name.requestFocus();
                    Toast.makeText(getContext(),DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Please enter the suspect name":"कृप्या संदिग्ध का नाम दर्ज करें। ",Toast.LENGTH_SHORT).show();
                }else if (address.getText().toString().length()<=0){
                    address.requestFocus();
                    Toast.makeText(getContext(),DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Please enter the suspect address ":"कृप्या संदिग्ध का पता दर्ज करें। ",Toast.LENGTH_SHORT).show();
                } else if(phoneNo.getText().toString().length()<=0){
                    phoneNo.requestFocus();
                    Toast.makeText(getContext(),DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Please enter suspect phone no. ":"कृप्या संदिग्ध का फोन नंबर दर्ज करें। ",Toast.LENGTH_SHORT).show();
                } else if(age.getText().toString().length()<=0){
                    age.requestFocus();
                    Toast.makeText(getContext(),DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Please enter the suspect age ":"कृप्या संदिग्ध का उम्र दर्ज करें। ",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        dootDatabaseAdapter.openDataBase();

                        ContentValues contentValues = new ContentValues();

                        contentValues.put(DootDatabaseAdapter.PATIENT_NAME,name.getText().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_AGE,age.getText().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_SEX,sex.getSelectedItem().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_ADDRESS,address.getText().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_PHONE,phoneNo.getText().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_VILLAGE,village.getSelectedItem().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_VILLAGE_ID,vill_id);
//                        contentValues.put(DootDatabaseAdapter.PATIENT_BLOCK,block.getSelectedItemPosition());
                        contentValues.put(DootDatabaseAdapter.PATIENT_BLOCK,block.getText().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_BLOCK_ID,blocl_id);
                        contentValues.put(DootDatabaseAdapter.PATIENT_DMC,dmc.getSelectedItem().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_DMC_ID,dmc_id);
                        contentValues.put(DootDatabaseAdapter.PATIENT_DISTRICT,district.getText().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_STATE,state.getText().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_COUNTRY,country.getText().toString());
                        contentValues.put(DootDatabaseAdapter.PATIENT_QUESTION_ID,questionId);
                        contentValues.put(DootDatabaseAdapter.PATIENT_SCREENING_RESPONSE,ansValue);
                        contentValues.put(DootDatabaseAdapter.PATIENT_SCREENING_AVERAGE,screenAverage);
                        contentValues.put(DootDatabaseAdapter.PATIENT_SCREEN_DATE_TIME, screenDateTime);
                        contentValues.put(DootDatabaseAdapter.PATIENT_TEST_STATUS,"Pending...");
                        contentValues.put(DootDatabaseAdapter.PATIENT_TEST_RESULT,"Pending...");
                        contentValues.put(DootDatabaseAdapter.CHECKDATAFROM_LOCAL_SERVER,"LOCAL");
                        contentValues.put(DootDatabaseAdapter.LONGITUDE,longitude);
                        contentValues.put(DootDatabaseAdapter.LATITUDE,latitude);

                //*****this field is used for further check the login status of user. ***************
                        contentValues.put(DootDatabaseAdapter.LOGIN_KEY,DootConstants.LOGIN_KEY);

                        dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_PATIENTDETAIL, contentValues);
                        dootDatabaseAdapter.insertData(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER,contentValues);
                        dootDatabaseAdapter.close();
                        showConfarmationDialog();
                        hideKeypad();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        DootConstants.EXCEPTION_STRING = e.getMessage();
                        showErrorDialog();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void showConfarmationDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? "CONFIRMATION" : "पुष्टीकरण");
        dialog.setIcon(R.drawable.icon);
        dialog.setMessage(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH) ? "Registration complete.\nDo you want to register a new TB suspect for referral ?" : "पंजीकरण पूरा हुआ।\nक्या आप रेफरल के लिए नये  टीबी संदिग्ध व्यक्ति को रजिस्टर करना चाहते है?");

        String check_yes = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"Yes":"हाँ";
        String check_no = DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?"No":"नहीं";

        dialog.setPositiveButton(check_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                communicator.screeningRegiNewSuspect();
            }
        });
        dialog.setNegativeButton(check_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent homeActivity = new Intent(getActivity(),MainActivity.class);
//                homeActivity.addCategory(Intent.CATEGORY_HOME);
//                homeActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                homeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ((Activity)getActivity()).finish();
//                startActivity(homeActivity);

                communicator.saveRegistrationDetail();

            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
    public void showErrorDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("ALERT");
        dialog.setMessage(DootConstants.EXCEPTION_STRING);
        dialog.setIcon(R.drawable.icon);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public interface RegistrationFragmentCommunicator{
        /**
         * @ my name is faiz alam.
         */
        void saveRegistrationDetail();
        void screeningRegiNewSuspect();
    }

    public void hideKeypad(){
        InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = ((Activity)getActivity()).getCurrentFocus();
        if(view==null){
            return;
        }else{
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}
