package zmq.com.tbpatientreferraldoottool.utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.model.ScreenRegiDtls;

/**
 * Created by zmq162 on 28/11/16.
 */
public class PatientListCardViewAdapter extends RecyclerView.Adapter<PatientListCardViewAdapter.Holder> {
    Context context;
    List<ScreenRegiDtls> regiDtlsListInfo;
    public PatientListCardViewAdapter(List<ScreenRegiDtls>  regiDtlsListInfo,Context context){
        this.context = context;
        this.regiDtlsListInfo = regiDtlsListInfo;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.patient_list_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ScreenRegiDtls screenRegiDtl=regiDtlsListInfo.get(position);
        if(screenRegiDtl.getCheckDataFromLocalServer().equals("LOCAL")) {
            holder.patientName.setText(screenRegiDtl.getPatientName());
            holder.patientVillage.setText(screenRegiDtl.getPatientVillage());
            holder.patientDmc.setText(screenRegiDtl.getPatientDmc());
            holder.listImg.setImageResource(R.drawable.profile);
        }else if(screenRegiDtl.getCheckDataFromLocalServer().equals("SERVER")){
            holder.patientName.setText(screenRegiDtl.getPatientName());
            holder.patientVillage.setText(screenRegiDtl.getPatientVillage());
            holder.patientDmc.setText(screenRegiDtl.getPatientDmc());

        }
    }


    @Override
    public int getItemCount() {
        return regiDtlsListInfo.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView patientName,patientVillage,patientDmc;
        ImageView listImg;
        public Holder(View view) {
            super(view);
            patientName = (TextView) view.findViewById(R.id.patient_name);
            patientVillage = (TextView) view.findViewById(R.id.patient_village);
            patientDmc = (TextView) view.findViewById(R.id.patient_dmc);
            listImg = (ImageView) view.findViewById(R.id.listImg);
        }
    }
}
