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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.model.DootAccountTransaction;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;

/**
 * Created by zmq162 on 18/11/16.
 */
public class DootAccountTransactionFragment extends Fragment {
    TextView dootName,dootId,dootTotalAmount,dootPaidAmount,dootDueAmount;
    Button backButton;
    DootDatabaseAdapter dootDatabaseAdapter;
    List<DootAccountTransaction> dootAccountTransacInfo;
    DootAccountTransaction dootAccountTransaction;
    DootAccountTransactionFragmentCommunicator communicator;

    public void setDootAccountTransactionFragmentCommunicator(DootAccountTransactionFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dootDatabaseAdapter = new DootDatabaseAdapter(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dootAccountTransacInfo = getDataFromTableDootAccountTransaction(DootDatabaseAdapter.TBL_DOOT_ACCOUNT_TRANSACTION);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doot_accounttransaction_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dootName = (TextView) getActivity().findViewById(R.id.dootname);
        dootId = (TextView) getActivity().findViewById(R.id.dootId);
        dootTotalAmount = (TextView) getActivity().findViewById(R.id.dootTotalAmount);
        dootPaidAmount = (TextView) getActivity().findViewById(R.id.dootPaidAmount);
        dootDueAmount = (TextView) getActivity().findViewById(R.id.dootDueAmount);
        backButton = (Button) getActivity().findViewById(R.id.backButton);

        if(dootAccountTransacInfo.size()<=0){
            showErrorDialog();
        }else{
            for(int i=0;i<dootAccountTransacInfo.size();i++){
                dootAccountTransaction = dootAccountTransacInfo.get(i);

                dootName.setText(DootConstants.DOOT_NAME);
                dootId.setText(DootConstants.DOOT_ID);
                dootTotalAmount.setText(dootAccountTransaction.getTotalAmount());
                dootPaidAmount.setText(dootAccountTransaction.getPaidAmount());
                dootDueAmount.setText(dootAccountTransaction.getDueAmount());
            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.backDootAccountFragment();
            }
        });

    }

    private List<DootAccountTransaction> getDataFromTableDootAccountTransaction(String tableName){
        List<DootAccountTransaction> dootAccountTransactionList = new ArrayList<DootAccountTransaction>();
        Cursor cursor = dootDatabaseAdapter.getData(tableName);

        if (cursor.moveToFirst()){
            do{
                DootAccountTransaction dootAcntTransacDtls = new DootAccountTransaction();

                dootAcntTransacDtls.setTotalAmount(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.TOTAL_AMOUNT)));
                dootAcntTransacDtls.setPaidAmount(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.PAID_AMOUNT)));
                dootAcntTransacDtls.setDueAmount(cursor.getString(cursor.getColumnIndex(DootDatabaseAdapter.DUE_AMOUNT)));

                dootAccountTransactionList.add(dootAcntTransacDtls);

            }while (cursor.moveToNext());
        }
        return  dootAccountTransactionList;
    }
    private void showErrorDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("ALERT");
        dialog.setMessage("No Account Transaction Detail Found");
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

    public interface DootAccountTransactionFragmentCommunicator{
        void backDootAccountFragment();
    }
}
