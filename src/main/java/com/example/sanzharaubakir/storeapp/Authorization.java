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

/**
 * Created by sanzharaubakir on 02.06.17.
 */

public class Authorization extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "Authorization";
    private static final String ip = "10.64.128.175";
    private String username, password;
        public Authorization (String username, String password){
            this.username = username;
            this.password = password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Void doInBackground(Void... voids) {
            authorize();
            return null;
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        public String authorize()
        {

            String inf = "";
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost("http://" + ip + ":8080/StoreAppWeb/Authorization");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", this.username));
                nameValuePairs.add(new BasicNameValuePair("password", this.password));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                HttpResponse response = httpclient.execute(httppost);

                int responseCode = response.getStatusLine().getStatusCode();

                inf = EntityUtils.toString(response.getEntity());
                Log.d(TAG, inf);
                JSONObject reader = new JSONObject(inf);
                //if (!reader.has("error"))
                return "ok";
                //else
                //return "error";
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                return "error";
            } catch (IOException e) {
                return "error";
                // TODO Auto-generated catch block
            } catch (JSONException e) {
                e.printStackTrace();
                return "error";
            }
        }
}
