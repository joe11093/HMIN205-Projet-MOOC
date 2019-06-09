package com.example.joseph.mooc.BackgroundTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.joseph.mooc.Helper.DBMooc;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.ActivityDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by josep on 5/26/2019.
 */

public class AddActivityToDBTask extends AsyncTask<ActivityDB, Void, String> {

    //Callback cb;

    public AddActivityToDBTask(){
        //this.cb = cb;
    }


    @Override
    protected String doInBackground(ActivityDB... activities) {
        String loginurl= DBMooc.baseUrl + "add_activity_todb.php";
        ActivityDB act = (ActivityDB) activities[0];

        //result string
        String data = "";
        if(act != null){
            try {

                URL url = new URL(loginurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("activitytoDBtask", "connecction is opened");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                Log.d("activitytoDBtask", "user id from act: " + act.getUser_id());
                JSONObject json = new JSONObject();
                json.put("user_id", act.getUser_id());
                json.put("type", act.getType());
                json.put("ref", act.getRef());
                json.put("activite_text", act.getActivite_text());
                data = "{\"user_id\":" + act.getUser_id();
                data = data + ",\"type\":\"" + act.getType()+"\"";
                data = data + ",\"ref\":" + act.getRef();
                data = data + ",\"activite_text\":\"" + act.getActivite_text()+"\"";
                data = data + ("}");
                //data = json.toString();
                Log.d("activitytoDBtask", "jsontostring: " + data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                Log.d("activitytoDBtask", "after buffered writer close");
                InputStream instream = httpURLConnection.getInputStream();
                Log.d("activitytoDBtask", "got the input stream");
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                Log.d("activitytoDBtask", "reading from input stream");
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }
                Log.d("activitytoDBtask", "finished reading the response");
                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));
                Log.d("activitytoDBtask", "result: " + result);
                instream.close();
                Log.d("activitytoDBtask", "finishing the DoInBackground function");
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(String res) {
        Log.d("activitytoDBtask", "started onPostExecute");
        super.onPostExecute(res);
        //Log.d("onpostexecute", exists);
        //Toast.makeText(ctx, "Result: " + exists, Toast.LENGTH_LONG).show();
        Log.d("activitytoDBtask", "exists: = " + res);
        String code = "activitytoDBtask";
        Log.d("activitytoDBtask", "code: = " + code);
        //cb.processData(code, res);
    }
}