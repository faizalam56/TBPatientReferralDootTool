package zmq.com.tbpatientreferraldoottool.utility;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.model.ScreenRegiDtls;

/**
 * Created by zmq162 on 10/11/16.
 */
public class PatientListAdapter extends BaseAdapter {
    Context context;
    String[] pat_Name,pat_Village,pat_Dmc;
    DootDatabaseAdapter dootDatabaseAdapter;
    List<ScreenRegiDtls> regiDtlsListInfo;
    public PatientListAdapter(List<ScreenRegiDtls>  regiDtlsListInfo,Context context){
       this.context = context;
       this.regiDtlsListInfo = regiDtlsListInfo;
    }
    @Override
    public int getCount() {
        return regiDtlsListInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.patient_list_item,parent,false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        ScreenRegiDtls screenRegiDtl=regiDtlsListInfo.get(position);
        if(screenRegiDtl.getCheckDataFromLocalServer().equals("SERVER")){
            holder.listImg.setImageResource(R.drawable.profile_photo);
        }else if(screenRegiDtl.getCheckDataFromLocalServer().equals("LOCAL")) {
            holder.listImg.setImageResource(R.drawable.profile);
        }
        holder.patientName.setText(screenRegiDtl.getPatientName());
        holder.patientVillage.setText(screenRegiDtl.getPatientVillage());
        holder.patientDmc.setText(screenRegiDtl.getPatientDmc());

        return convertView;
    }

    class Holder{

        TextView patientName,patientVillage,patientDmc;
        ImageView listImg;

        public Holder(View view){
            patientName = (TextView) view.findViewById(R.id.patient_name);
            patientVillage = (TextView) view.findViewById(R.id.patient_village);
            patientDmc = (TextView) view.findViewById(R.id.patient_dmc);
            listImg = (ImageView) view.findViewById(R.id.listImg);
        }
    }


}
