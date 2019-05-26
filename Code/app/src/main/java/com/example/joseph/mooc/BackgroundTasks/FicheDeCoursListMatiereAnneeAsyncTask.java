package com.example.joseph.mooc.BackgroundTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.joseph.mooc.Helper.DBMooc;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Matiere;

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

public class FicheDeCoursListMatiereAnneeAsyncTask extends AsyncTask<Matiere, Void, String> {

    Callback cb;
    Matiere matiere;

    public FicheDeCoursListMatiereAnneeAsyncTask(Callback cb) {
        this.cb = cb;
    }
    @Override
    protected String doInBackground(Matiere... matieres) {
        Log.d("CoursListMatiereTask", "staring doInBackground");
        String path = DBMooc.baseUrl + "get_all_cours_of_matiere_annee.php";
        this.matiere = matieres[0];
        Log.d("CoursListMatiereTask", "Received matiere ID: " + this.matiere.getId() + "Annee ID: " + this.matiere.getAnnee_id());

        String data = "";
        if (this.matiere != null) {
            try {
                URL url = new URL(path);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("CoursListMatiereTask", "connection is opened");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Log.d("CoursListMatiereTask", "Got the ouput stream");

                JSONObject json = new JSONObject();
                json.put("annee_id", this.matiere.getAnnee_id());
                json.put("matiere_id", this.matiere.getId());
                data = json.toString();
                Log.d("CoursListMatiereTask", "jsontostring: " + data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                Log.d("CoursListMatiereTask", "after buffered writer close");
                InputStream instream = httpURLConnection.getInputStream();
                Log.d("CoursListMatiereTask", "got the input stream");
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                Log.d("CoursListMatiereTask", "reading from input stream");
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }
                Log.d("CoursListMatiereTask", "finished reading the response");
                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));
                Log.d("CoursListMatiereTask", "result: " + result);
                instream.close();
                Log.d("CoursListMatiereTask", "finishing the DoInBackground function");
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("CoursListMatiereTask", "OnPostExecute: " + s);
        this.cb.processData("CoursListMatiereTask", s);
    }

}