package com.example.na.courseassociate;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatusChecker {
    private Context _context;

    public NetworkStatusChecker(Context context){
        this._context=context;
    }
    public boolean isConnectedToNetwork(){
        ConnectivityManager connectivityManager=(ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();
            if(info!=null){
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
