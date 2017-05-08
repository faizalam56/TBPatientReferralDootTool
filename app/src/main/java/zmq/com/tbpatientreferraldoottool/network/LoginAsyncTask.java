package zmq.com.tbpatientreferraldoottool.network;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import zmq.com.tbpatientreferraldoottool.R;
import zmq.com.tbpatientreferraldoottool.utility.DootConstants;

/**
 * Created by zmq162 on 17/10/16.
 */
//public class LoginAsyncTask extends AsyncTask<Void,Void,JSONObject> {
//
//    Context context;
//    String serviceName;
//    Handler handler;
//    ArrayList<NameValuePair> paramValue;
//
//
//    public LoginAsyncTask(Context context, String serviceName, ArrayList<NameValuePair> paramValue, Handler handler){
//        this.context = context;
//        this.serviceName = serviceName;
//        this.handler = handler;
//        this.paramValue = paramValue;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected JSONObject doInBackground(Void... params) {
//        NetworkTask networkTask = new NetworkTask();
//        JSONObject jsonObject = networkTask.getLoginResponse(serviceName, paramValue);
//
//        Message message = Message.obtain();
//        message.obj = jsonObject;
//        handler.sendMessage(message);
//
//        System.out.println("response.json.." + jsonObject);
//
//        return jsonObject;
//    }
//
//    @Override
//    protected void onPostExecute(JSONObject jsonObject) {
//        super.onPostExecute(jsonObject);
//    }
//
//}
