package com.example.joseph.mooc.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.joseph.mooc.Helper.DBMooc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

// FOR TESTS ONLY

/**
 * Created by josep on 4/16/2019.
 */

public class BackgroundTask extends AsyncTask<String, Void, String> {


    Context ctx;

    public BackgroundTask(Context ctx) {
        super();
        this.ctx = ctx;
    }

    protected String doInBackground(String... params) {
        //127.0.0.1/moooc-server ?
        String reg_url= DBMooc.baseUrl + "register.php";
        String method = params[0];
        Log.d("backgroundtask", "backgroundtask");
        if(method.equals("login")){
            String id = params[1];
            String firstname = params[2];
            Log.d("methond", method);

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                //to write to the db
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                        URLEncoder.encode("firstname", "UTF-8")+"="+URLEncoder.encode(firstname,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream instream = httpURLConnection.getInputStream();
                instream.close();
                Log.d("return", "return");
                return "Registration Successful";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("loginresult", result);
        Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }
}
