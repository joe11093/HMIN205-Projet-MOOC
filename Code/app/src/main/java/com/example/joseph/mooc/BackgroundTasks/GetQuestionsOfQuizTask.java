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
 * Created by josep on 5/26/2019.
 */

public class GetQuestionsOfQuizTask extends AsyncTask<String, Void, String> {

    Callback cb;
    String id;

    public GetQuestionsOfQuizTask(Callback cb) {
        this.cb = cb;
    }


    @Override
    protected String doInBackground(String... strings) {

        Log.d("QuestionsOfQuizTask", "staring doInBackground");
        String path = DBMooc.baseUrl + "get_questions_of_quiz.php";
        this.id = strings[0];
        Log.d("QuestionsOfQuizTask", "Received quiz ID: " + this.id);

        String data = "";
        if (this.id != null) {
            try {
                URL url = new URL(path);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("QuestionsOfQuizTask", "connection is opened");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Log.d("QuestionsOfQuizTask", "Got the ouput stream");

                data = "{\"qcm_id\":" + this.id + "}";
                Log.d("QuestionsOfQuizTask", "jsontostring: " + data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                Log.d("QuestionsOfQuizTask", "after buffered writer close");
                InputStream instream = httpURLConnection.getInputStream();
                Log.d("QuestionsOfQuizTask", "got the input stream");
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                Log.d("QuestionsOfQuizTask", "reading from input stream");
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }
                Log.d("QuestionsOfQuizTask", "finished reading the response");
                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));
                Log.d("QuestionsOfQuizTask", "result: " + result);
                instream.close();
                Log.d("QuestionsOfQuizTask", "finishing the DoInBackground function");
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("QuestionsOfQuizTask", "OnPostExecute: " + s);
        this.cb.processData("QuestionsOfQuizTask", s);


    }
}
