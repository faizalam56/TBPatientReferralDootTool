package zmq.com.tbpatientreferraldoottool.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zmq162 on 24/11/16.
 */
public class UpdateRecordAsyncTask extends AsyncTask<Void,Void,JSONArray> {
    Context context;
    ArrayList<NameValuePair> paramValue;
    Handler handler;
    String serviceName;
    NetworkTask networkTask;
    public UpdateRecordAsyncTask(Context context,String serviceName,ArrayList<NameValuePair> paramValue,Handler handler){
        this.context = context;
        this.handler = handler;
        this.paramValue = paramValue;
        this.serviceName = serviceName;
        networkTask = new NetworkTask();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONArray doInBackground(Void... params) {

        JSONArray jsonArray = networkTask.getJArrayData(serviceName, paramValue);

        Message message = Message.obtain();
        message.obj = jsonArray;
        handler.sendMessage(message);
        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
    }
}
