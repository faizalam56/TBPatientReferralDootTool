package zmq.com.tbpatientreferraldoottool.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zmq162 on 16/11/16.
 */
public class UploadDataAsyncTask extends AsyncTask<Void,Void,JSONObject> {

    Context context;
    ArrayList<NameValuePair> paramValue;
    String serviceName;
    Handler handler;
    public UploadDataAsyncTask(Context context, String serviceName, ArrayList<NameValuePair> paramValue, Handler handler){

        this.context = context;
        this.serviceName = serviceName;
        this.paramValue = paramValue;
        this.handler = handler;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        NetworkTask networkTask = new NetworkTask();
        JSONObject jsonObject = networkTask.getJObjectResponse(serviceName,paramValue);

        Message message = Message.obtain();
        message.obj = jsonObject;
        handler.sendMessage(message);
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
