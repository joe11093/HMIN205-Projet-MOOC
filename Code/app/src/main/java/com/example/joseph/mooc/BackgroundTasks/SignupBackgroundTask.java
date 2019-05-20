package com.example.joseph.mooc.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.joseph.mooc.Helper.DBMooc;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Parent;
import com.example.joseph.mooc.Models.Student;
import com.example.joseph.mooc.Models.User;
import com.example.joseph.mooc.R;

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

public class SignupBackgroundTask extends AsyncTask<User, Void, String> {

    Callback cb;
    public SignupBackgroundTask(Callback cb) {
        Log.d("SignupTask", "In the signup backgroud Task");
        this.cb = cb;
    }

    @Override
    protected String doInBackground(User... params) {
        Log.d("SignupTask", "DoInBackground function");
        Log.d("SignupTask", "DoInBackground. User parameter is: " + params.toString());
        Log.d("SignupTask", "DoInBackground. User parameter is of class: " + params[0].getClass().toString());
        Log.d("SignupTask", "DoInBackground. User parameter is of type: " + params[0].getType());


        if(params[0].getClass().equals(Student.class)){
            Log.d("SignupTask", "DoInBackground: calling registerStudentInDb function");
            return this.registerStudentInDB((Student) params[0]);
        }
        else if(params[0].getClass().equals(Parent.class)){
            Log.d("SignupTask", "DoInBackground: calling registerParentInDb function");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("SignupTask", "onPostExecute Function");
        super.onPostExecute(s);
        Log.d("SignupTask", "onPostExecute: Parameter = " + s);
        cb.processData("signuptask", s);

    }

    private String registerStudentInDB(Student student){
        Log.d("SignupTask", "registerStudent Function");
        String signup= DBMooc.baseUrl + "register.php";
        //result string
        String data = "";

        if(student != null){
            try {

                URL url = new URL(signup);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                Log.d("SignupTask", "connecction is opened");
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                JSONObject json = new JSONObject();
                json.put("firstname", student.getFirstname());
                json.put("lastname", student.getLastname());
                json.put("dob", student.getDateofbirth());
                json.put("email", student.getEmailaddress());
                json.put("password", student.getPassword());
                json.put("city", student.getCity());
                json.put("country", student.getCountry());
                json.put("anneescolaire", student.getAnneeScolaire().getAnneescolaire());
                json.put("type", student.getType());
                json.put("parent", student.getParent());

                data = json.toString();
                Log.d("SignupTask", "jsontostring: " + data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                Log.d("SignupTask", "after buffered writer close");
                InputStream instream = httpURLConnection.getInputStream();
                Log.d("SignupTask", "got the input stream");
                byte[] bytes = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] temp = new byte[instream.available()];
                Log.d("SignupTask", "reading from input stream");
                while (instream.read(temp, 0, temp.length) != -1) {
                    baos.write(temp);
                    temp = new byte[instream.available()];
                }
                Log.d("SignupTask", "finished reading the response");
                bytes = baos.toByteArray();
                String result = new String(bytes, Charset.forName("utf-8"));
                Log.d("SignupTask", "result: " + result);
                instream.close();
                Log.d("SignupTask", "finishing the DoInBackground function");
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

}

