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
 * Created by josep on 5/20/2019.
 * This task takes a type as parameter
 * and fetches all entries of this type from the database
 * will be used to fetch things such as the list of annee scolaires and matieres
 */

public class GetAllTask extends AsyncTask<String, Void, String> {

    Callback cb;
    String type;
    public GetAllTask(Callback cb) {
        Log.d("getalltask", "In the getall backgroud Task");
        this.cb = cb;
    }


    @Override
    protected String doInBackground(String... strings) {
        String path = DBMooc.baseUrl + "get_all_of_type.php";
        Log.d("getalltask", "staring doInBackground");
        //get the type of objects to be fetched
        type = strings[0];
        String data = "";
        if (type != null) {
            try {

                URL url = new URL(path);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("getalltask", "connecction is opened");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Log.d("getalltask", "Got the ouput stream");

                JSONObject json = new JSONObject();
                json.put("type", type);
                data = json.toString();
                Log.d("getalltask", "jsontostring: " + data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                Log.d("getalltask", "after buffered writer close");
                InputStream instream = httpURLConnection.getInputStream();
                Log.d("getalltask", "got the input stream");
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                Log.d("getalltask", "reading from input stream");
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }
                Log.d("getalltask", "finished reading the response");
                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));
                Log.d("getalltask", "result: " + result);
                instream.close();
                Log.d("getalltask", "finishing the DoInBackground function");
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
        Log.d("getalltask", "staring onPostExecute");
        super.onPostExecute(s);
        Log.d("getalltask", "staring onPostExecute");
        this.cb.processData("getall-"+type, s);
    }
}