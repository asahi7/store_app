package com.example.sanzharaubakir.storeapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sanzharaubakir on 03.06.17.
 */

public class SendData extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "Authorization";
    private static final String ip = "10.64.128.175";
    private String code, quantity, task, price, name;
    public SendData (Map<String, String> information)
    {
        this.task = information.get("task");
        this.code = information.get("code");
        this.quantity = information.get("quantity");
        if (task.equals("register"))
        {
            this.price = information.get("price");
            this.name = information.get("name");
        }
    }
    @Override
    protected Void doInBackground(Void... voids) {
        access_to_servlet();
        return null;
    }

    private void access_to_servlet() {
        String inf = "";
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost("http://" + ip + ":8080/StoreAppWeb/WorkWithDB");
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("code", this.code));
            nameValuePairs.add(new BasicNameValuePair("quantity", this.quantity));
            nameValuePairs.add(new BasicNameValuePair("task", this.task));
            if (task.equals("register"))
            {
                nameValuePairs.add(new BasicNameValuePair("price", this.price));
                nameValuePairs.add(new BasicNameValuePair("name", this.name));
            }
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            HttpResponse response = httpclient.execute(httppost);

            int responseCode = response.getStatusLine().getStatusCode();

            inf = EntityUtils.toString(response.getEntity());
            Log.d(TAG, inf);
            JSONObject reader = new JSONObject(inf);
            //if (!reader.has("error"))
            //else
            //return "error";
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
