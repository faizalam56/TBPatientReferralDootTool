package zmq.com.tbpatientreferraldoottool.network;

import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import zmq.com.tbpatientreferraldoottool.utility.DootConstants;

/**
 * Created by zmq162 on 17/10/16.
 */
public class NetworkTask {

//    private  String url = "http://192.168.1.32/tbdoot/Doot_Controller/";
    private  String url = "http://connect2mfi.org/tbdoot/Doot_Controller/";

    public JSONObject getJObjectResponse(String serviceName, ArrayList<NameValuePair> paramValue){
        JSONObject jsonObject = null;
        String URL = url+serviceName;
        try{
            HttpPost httpPost = new HttpPost(URL);
            System.out.println("Parimeter value..."+paramValue);
            httpPost.setEntity(new UrlEncodedFormEntity(paramValue));

            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse httpResponse = httpClient.execute(httpPost);

            System.out.println("Response as http ..." + httpResponse.toString());
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("Response Status Code....."+statusCode);

            InputStream content = httpResponse.getEntity().getContent();
            if(content != null){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line=bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                content.close();

                jsonObject = new JSONObject(stringBuilder.toString());

                System.out.println("JsonObject From server...."+jsonObject);
            }else{
                DootConstants.EXCEPTION_STRING = "We didnot get appropiate result";
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException");
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
            return jsonObject;
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException");
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
            return jsonObject;
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
            return jsonObject;
        } catch (JSONException e) {
            System.out.println("JSONException");
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
            return jsonObject;
        }

        return jsonObject;
    }

    public JSONArray getJArrayData(String serviceName, ArrayList<NameValuePair> paramValue){
        JSONArray jsonArray = null;
        String URL = url+serviceName;
        try{
            HttpPost httpPost = new HttpPost(URL);
            System.out.println("Parimeter value..."+paramValue);
            httpPost.setEntity(new UrlEncodedFormEntity(paramValue));

            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse httpResponse = httpClient.execute(httpPost);

            System.out.println("Response as http ..." + httpResponse.toString());
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("Response Status Code....."+statusCode);

            InputStream content = httpResponse.getEntity().getContent();
            if(content != null){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line=bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                content.close();
                System.out.println("JsonArray Before From server...." );
                jsonArray = new JSONArray(stringBuilder.toString());

                System.out.println("JsonArray From server...."+jsonArray);
            }else{
                DootConstants.EXCEPTION_STRING = "We didnot get appropiate result";
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException");
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
            return jsonArray;
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException");
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
            return jsonArray;
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
            return jsonArray;
        } catch (JSONException e) {
            System.out.println("JSONException");
            e.printStackTrace();
            DootConstants.EXCEPTION_STRING = e.getMessage();
            return jsonArray;
        }

        return jsonArray;
    }


}
