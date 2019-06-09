package com.example.joseph.mooc.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class CheckConnectivity extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent arg1) {

        boolean isConnected = arg1.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if(isConnected){
            //Intent intent = new Intent(context, NoConnection.class);
            //context.startActivity(intent);
            Log.d("checkcon", "is not connected");
            Toast.makeText(context, "Internet Connection Lost", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
            Log.d("checkcon", "isconnected");
            //Intent intent = new Intent(context, LaunchActivity.class);
            //context.startActivity(intent);
        }
    }
}