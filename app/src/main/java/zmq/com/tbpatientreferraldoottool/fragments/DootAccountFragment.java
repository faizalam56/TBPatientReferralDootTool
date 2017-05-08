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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.model.DootAccount;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;

/**
 * Created by zmq162 on 18/11/16.
 */
public class DootAccountFragment extends Fragment {
    TextView dootName,dootId,dootAccountNo,dootAccountBalance,dootAccountOpenDate;
    Button passbookButton;
    DootDatabaseAdapter dootDatabaseAdapter;
    List<DootAccount> dootAcuntsInfo;
    DootAccount dootAcuntDtls;
    DootAccountFragmentCommunicator communicator;
    public  void setDootAccountFragmentCommunicator(DootAccountFragmentCommunicator communicator){
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
        dootAcuntsInfo = getDataFramTableDootAccount(DootDatabaseAdapter.TBL_DOOT_ACCOUNT);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doot_account_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dootName = (TextView) getActivity().findViewById(R.id.dootname);
        dootId = (TextView) getActivity().findViewById(R.id.dootId);
        dootAccountNo = (TextView) getActivity().findViewById(R.id.dootAccountNo);
        dootAccountBalance = (TextView) getActivity().findViewById(R.id.dootAccountBalance);
        dootAccountOpenDate = (TextView) getActivity().findViewById(R.id.dootAccountOpenDate);
        passbookButton = (Button) getActivity().findViewById(R.id.passbookButton);

        if(dootAcuntsInfo.size()<=0){
               showErrorDialog();
        }else {
            for (int i = 0; i < dootAcuntsInfo.size(); i++) {
                dootAcuntDtls = dootAcuntsInfo.get(i);

                dootName.setText(DootConstants.DOOT_NAME);
                dootId.setText(DootConstants.DOOT_ID);
                dootAccountNo.setText(dootAcuntDtls.getDootAccountNo());
                dootAccountBalance.setText(dootAcuntDtls.getDootAccountBalance());
                dootAccountOpenDate.setText(dootAcuntDtls.getDootAccountOpenDate().substring(0,10));
            }
        }

        passbookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.dootAccountTransaction();
            }
        });

    }

    private List<DootAccount> getDataFramTableDootAccount(String tableName){
        List<DootAccount> dootAccountList = new ArrayList<DootAccount>();
        Cursor cursor = dootDatabaseAdapter.getData(tableName);

        if(cursor.moveToFirst()){
            do {
                DootAccount dootAccountDtls = new DootAccount();

                dootAccountDtls.setDootAccountNo(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_ACCOUNT_NO)));
                dootAccountDtls.setDootAccountBalance(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_ACCOUNT_BALANCE)));
                dootAccountDtls.setDootAccountOpenDate(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DOOT_ACCOUNT_OPENDATE)));
                dootAccountList.add(dootAccountDtls);

            }while (cursor.moveToNext());
        }
        return  dootAccountList;
    }

    public void showErrorDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("ALERT");
        dialog.setMessage("No Account Detail Found");
        dialog.setIcon(R.drawable.icon);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public interface DootAccountFragmentCommunicator{
        void dootAccountTransaction();
    }
}
