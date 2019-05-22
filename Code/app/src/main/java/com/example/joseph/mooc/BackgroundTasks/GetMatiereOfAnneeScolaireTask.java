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
 * Created by josep on 5/22/2019.
 *
 * Takes the ID of the annee scolaire as a string parameter (from shared preferences)
 * Returns a JSON containing all annee scholaires
 */

public class GetMatiereOfAnneeScolaireTask extends AsyncTask<String, Void, String> {

    Callback cb;
    String id;

    public GetMatiereOfAnneeScolaireTask(Callback cb) {
        this.cb = cb;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("getmatiereanneeTask", "staring doInBackground");
        String path = DBMooc.baseUrl + "get_all_matiere_of_annee.php";
        this.id = strings[0];
        Log.d("getmatiereanneeTask", "Received annee ID: " + this.id);
        String data = "";
        if (this.id != null) {
            try {
                URL url = new URL(path);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("getmatiereanneeTask", "connecction is opened");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Log.d("getmatiereanneeTask", "Got the ouput stream");

                JSONObject json = new JSONObject();
                data = "{\"annee_id\":"+this.id + "}";
                Log.d("getmatiereanneeTask", "jsontostring: " + data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                Log.d("getmatiereanneeTask", "after buffered writer close");
                InputStream instream = httpURLConnection.getInputStream();
                Log.d("getmatiereanneeTask", "got the input stream");
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                Log.d("getmatiereanneeTask", "reading from input stream");
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }
                Log.d("getmatiereanneeTask", "finished reading the response");
                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));
                Log.d("getmatiereanneeTask", "result: " + result);
                instream.close();
                Log.d("getmatiereanneeTask", "finishing the DoInBackground function");
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }// catch (JSONException e) {
             //   e.printStackTrace();
            //}

        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        Log.d("getmatiereanneeTask", "onPostExecute");
        Log.d("getmatiereanneeTask", "result: " + s);

        super.onPostExecute(s);
        this.cb.processData("getmatiereanneeTask",s);
    }

}
