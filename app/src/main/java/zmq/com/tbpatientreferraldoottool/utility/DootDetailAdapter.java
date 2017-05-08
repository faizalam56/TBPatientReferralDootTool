package zmq.com.tbpatientreferraldoottool.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.model.Dmcdtls;
import zmq.com.tbpatientreferraldoottool.model.Dootdtls;

/**
 * Created by zmq162 on 11/11/16.
 */
public class DootDetailAdapter extends ArrayAdapter {
    List<Dootdtls> dootDetailInfo;
    List<Dmcdtls> dmcDetailInfo;
    Context context;
    int setSpinnerItem;
    int size;
    int resElement;

    public DootDetailAdapter(List<Dootdtls> dootDetailInfo,Context context,int setSpinnerItem,int reselemet){
        super(context,reselemet,dootDetailInfo);
        this.context = context;
        this.dootDetailInfo = dootDetailInfo;
        this.setSpinnerItem = setSpinnerItem;
        size = dootDetailInfo.size();
        resElement=reselemet;
    }
    public DootDetailAdapter(List<Dmcdtls> dmcDetailInfo,Context context,int reselemet){
        super(context,reselemet,dmcDetailInfo);
        this.context = context;
        this.dmcDetailInfo = dmcDetailInfo;
        size=dmcDetailInfo.size();
        resElement=reselemet;
    }
    @Override
    public int getCount() {
        return size;
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
            convertView = inflater.inflate(resElement,parent,false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }


        if(setSpinnerItem==1) {
            Dootdtls dootdtls = dootDetailInfo.get(position);
            holder.textView.setText(dootdtls.getVillageName());
        }else if(setSpinnerItem==2){
            Dootdtls dootdtls = dootDetailInfo.get(position);
            holder.textView.setText(dootdtls.getBlockName());
        }else {
            Dmcdtls dmcdtls = dmcDetailInfo.get(position);
            holder.textView.setText(dmcdtls.getDmcName());
        }

        return convertView;
    }

    class Holder{
        TextView textView;
        public Holder(View view){
            textView = (TextView) view.findViewById(R.id.dootDts);
        }
    }
}
