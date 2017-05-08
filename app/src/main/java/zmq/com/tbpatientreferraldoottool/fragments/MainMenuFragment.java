package zmq.com.tbpatientreferraldoottool.fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.databaseAdapter.DootDatabaseAdapter;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;

/**
 * Created by zmq162 on 25/10/16.
 */
public class MainMenuFragment extends Fragment {
    View view;
    GridView gridView;
    MainMenuCommunicator communicator;

    public void setMainMenuCommunicator(MainMenuCommunicator communicator){
        this.communicator = communicator;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mainmenu_fragment,container,false);
        gridView = (GridView) view.findViewById(R.id.gridView);

        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                communicator.selectMainMenuItem(position);
            }
        });
        return view;
    }

    class MyAdapter extends BaseAdapter{

        Context context;
        String[] listItem;
        int[] imageItem = {R.drawable.screening_pat,R.drawable.upload_patlist,R.drawable.view_patient,R.drawable.patient_status,
                            R.drawable.account1,R.drawable.learning_module};

        public MyAdapter(){
            System.out.println("from myadapter..."+DootConstants.LANGUAGE);
            this.context = context;
            listItem = getResources().getStringArray(DootConstants.LANGUAGE.equals(DootConstants.ENGLISH)?R.array.main_menu:R.array.main_menu_hnd);
        }
        @Override
        public int getCount() {
            return imageItem.length;
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

            Holder holder=null;

            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.menu_grid,parent,false);

                holder=new Holder(convertView);
                convertView.setTag(holder);

            }else{
                holder = (Holder) convertView.getTag();
            }


            holder.textView.setText(listItem[position]);
            holder.imageView.setImageResource(imageItem[position]);

            if(position==1){
                DootDatabaseAdapter dootDatabaseAdapter = new DootDatabaseAdapter(getContext());
//                Cursor cursor = dootDatabaseAdapter.getData(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER);
                Cursor cursor = dootDatabaseAdapter.getDataOnLoginKey(DootDatabaseAdapter.TBL_PATIENTDETAIL_BUFFER, DootConstants.LOGIN_KEY);

                    int isData = cursor.getCount();
                    System.out.println("check outside database have data"+isData);
                    if (isData > 0) {
                        System.out.println("check inside database have data");
//                        convertView.setPadding(5, 5, 5, 5);
//                        convertView.setBackgroundColor(Color.RED);
                        holder.imageView.setImageResource(R.drawable.cloudupload1);
                    }

            }

            return convertView;
        }

        class Holder{
            TextView textView;
            ImageView imageView;

            public Holder(View convertView){
                imageView = (ImageView) convertView.findViewById(R.id.grid_imageView);
                textView = (TextView) convertView.findViewById(R.id.grid_textView);
            }
        }
    }

    public interface MainMenuCommunicator{
        int selectMainMenuItem(int position);
    }
}
