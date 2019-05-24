package com.example.joseph.mooc.BackgroundTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.joseph.mooc.Helper.DBMooc;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Login;
import com.example.joseph.mooc.Models.User;

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
 * Created by josep on 4/28/2019.
 */

public class LoginAsyncClass extends AsyncTask<Login, Void, String> {

    Callback cb;
    public LoginAsyncClass(Callback cb) {
        this.cb = cb;
        Log.d("logintask", "Started the login asynctask");
    }

    @Override
    protected String doInBackground(Login... params) {
        Log.d("logintask", "started doInBackground");
        String loginurl= DBMooc.baseUrl + "login.php";

        Object param = params[0];
        Login login = (Login) param;
        //Log.d("loginobject", login.getEmail());

        //result string
        String data = "";
        if(login != null) {
            Log.d("logintask", "if(login != null)");
            try {

                URL url = new URL(loginurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("logintask", "connecction is opened");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                JSONObject json = new JSONObject();
                json.put("email", login.getEmail());
                json.put("password", login.getPassword());
                data = json.toString();
                Log.d("logintask", "jsontostring: " + data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                Log.d("logintask", "after buffered writer close");
                InputStream instream = httpURLConnection.getInputStream();
                Log.d("logintask", "got the input stream");
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                Log.d("logintask", "reading from input stream");
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }
                Log.d("logintask", "finished reading the response");
                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));
                Log.d("logintask", "result: " + result);
                instream.close();
                Log.d("logintask", "finishing the DoInBackground function");
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("logintask", "returnNull");
        return null;
    }

    @Override
    protected void onPostExecute(String exists) {
        Log.d("logintask", "started onPostExecute");
        super.onPostExecute(exists);
        //Log.d("onpostexecute", exists);
        //Toast.makeText(ctx, "Result: " + exists, Toast.LENGTH_LONG).show();
        Log.d("logintask", "exists: = " + exists);
        String code = "logintask";
        Log.d("logintask", "code: = " + code);
        cb.processData(code, exists);
    }
}