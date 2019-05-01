package com.example.joseph.mooc.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.joseph.mooc.Helper.DBMooc;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Parent;
import com.example.joseph.mooc.Models.Student;
import com.example.joseph.mooc.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Created by josep on 4/27/2019.
 */

public class CheckObjectExistInDbAsyncTask extends AsyncTask<Object, Void, String> {

    //Context ctx;
    Callback cb;
    public CheckObjectExistInDbAsyncTask(Callback cb) {
        //this.ctx = ctx;
        this.cb = cb;
    }


    @Override
    protected String doInBackground(Object... params) {
        String checkurl= DBMooc.baseUrl + "check_exists.php";
        String type;

        Object toCheck = params[0];
        if(toCheck.getClass() == Parent.class || toCheck.getClass() == Student.class){
            //set type, cast to User and retrieve search criteria
            type = "user";
            User usersearch = (User) toCheck;
            Log.d("EMAIL", usersearch.getEmailaddress());
            String data = "";
            try{
                URL url = new URL(checkurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                //creating the JSON
                JSONObject searchinfo = new JSONObject();

                searchinfo.put("firstname", usersearch.getFirstname());
                searchinfo.put("lastname", usersearch.getLastname());
                searchinfo.put("dob", usersearch.getDateofbirth());
                searchinfo.put("email", usersearch.getEmailaddress());
                searchinfo.put("password", usersearch.getPassword());
                searchinfo.put("city", usersearch.getCity());
                searchinfo.put("country", usersearch.getCountry());
                searchinfo.put("type", "user");

                data = searchinfo.toString();
                Log.d("JSON", data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream instream = httpURLConnection.getInputStream();
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }

                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));

                instream.close();

                return result;

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }
    }
        return "no_result";
    }

    @Override
    protected void onPostExecute(String exists) {

        super.onPostExecute(exists);
        //Log.d("onpostexecute", exists);
        //Toast.makeText(ctx, "Result: " + exists, Toast.LENGTH_LONG).show();
        cb.processData(exists);
    }
}
