package com.example.joseph.mooc.BackgroundTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.joseph.mooc.Helper.DBMooc;
import com.example.joseph.mooc.Interfaces.Callback;

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
 * Created by josep on 5/29/2019.
 */

public class GetActivityLogofUserTask  extends AsyncTask<String, Void, String> {

    Callback cb;

    public GetActivityLogofUserTask(Callback cb)

    {

        this.cb = cb;
    }


    @Override
    protected String doInBackground(String... strings) {
        Log.d("getActivityLogTask", "In the getall backgroud Task");
        String path = DBMooc.baseUrl + "get_activities_of_user.php";
        String user_id = strings[0];
        String data = "";

        if (user_id != null) {
            try {
                URL url = new URL(path);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("getActivityLogTask", "connecction is opened");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Log.d("getActivityLogTask", "Got the ouput stream");

                JSONObject json = new JSONObject();
                json.put("user_id", user_id);
                data = "{\"user_id\":"+user_id+"}";
                Log.d("getActivityLogTask", "jsontostring: " + data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                Log.d("getActivityLogTask", "after buffered writer close");
                InputStream instream = httpURLConnection.getInputStream();
                Log.d("getActivityLogTask", "got the input stream");
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                Log.d("getActivityLogTask", "reading from input stream");
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }
                Log.d("getActivityLogTask", "finished reading the response");
                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));
                Log.d("getActivityLogTask", "result: " + result);
                instream.close();
                Log.d("getActivityLogTask", "finishing the DoInBackground function");
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
    protected void onPostExecute(String s)
    {
        Log.d("getActivityLogTask", "staring onPostExecute");
        super.onPostExecute(s);
        Log.d("getActivityLogTask", s);
        this.cb.processData("getActivityLogTask", s);

    }
}
