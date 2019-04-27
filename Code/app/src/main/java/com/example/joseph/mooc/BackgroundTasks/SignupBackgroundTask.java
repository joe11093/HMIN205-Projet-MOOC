package com.example.joseph.mooc.BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.joseph.mooc.R;

public class SignupBackgroundTask extends AsyncTask<String, Void, String> {

    Context ctx;

    public SignupBackgroundTask(Context c) {
        this.ctx = c;
    }

    @Override
    protected String doInBackground(String... strings) {


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }


}

