package com.example.joseph.mooc.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.joseph.mooc.Helper.DBMooc;
import com.example.joseph.mooc.Models.Parent;
import com.example.joseph.mooc.Models.Student;
import com.example.joseph.mooc.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
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

/**
 * Created by josep on 4/27/2019.
 */

public class CheckObjectExistInDbAsyncTask extends AsyncTask<Object, Void, Boolean> {

    Context ctx;

    public CheckObjectExistInDbAsyncTask(Context ctx) {
        this.ctx = ctx;
    }


    @Override
    protected Boolean doInBackground(Object... params) {
        String checkurl= DBMooc.baseUrl + "check_exists.php";
        String type;

        Object toCheck = params[0];
        if(toCheck.getClass() == Parent.class || toCheck.getClass() == Student.class){
            //set type, cast to User and retrieve search criteria
            type = "user";
            User usersearch = (User) toCheck;
            Log.d("EMAIL", usersearch.getEmailaddress());
            String email = usersearch.getEmailaddress();
            String data = "";
            try{
                URL url = new URL(checkurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                //creating the JSON
                JSONObject searchinfo = new JSONObject();
                searchinfo.put("type", "user");
                searchinfo.put("email", email);
                Log.d("JSON", searchinfo.toString());
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                Log.d("newDataoupustream","newDataoupustream");
                wr.writeBytes("searchinfo="+searchinfo.toString());
                Log.d("afterwritebytes","afterwritebytes");
                wr.flush();
                wr.close();
                Log.d("afterclose","afterclose");
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                int inputStreamData = inputStreamReader.read();
                Log.d("inputstreamdata", ""+inputStreamData);
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                    Log.d("datainwhile", data);
                }

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }
    }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean exists) {

        super.onPostExecute(exists);
        Log.d("onpostexecute", exists.toString());
        Toast.makeText(ctx, "Result: " + exists, Toast.LENGTH_LONG).show();
        /*
        if(exists == "true"){
            Toast.makeText(ctx, "Email already exists", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(ctx, "You can sign up", Toast.LENGTH_LONG).show();
        */
    }
}
