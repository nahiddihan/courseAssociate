package com.example.na.courseassociate;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Notifications extends Activity{
    String userId;
    String semisterId;
    String user_type;
    String url;
    RequestQueue requestQueue;

    ListView notificationListView;
    ArrayList<HashMap<String, String>> notificationList;
    HashMap<String, String> notificationHashMap;
    ProgressDialog progressDialog;
    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    LocalDataBaseHelper localDataBaseHelper;
    ArrayList<HashMap<String, String>> notificationLocalDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        notificationListView=(ListView)findViewById(R.id.lvNotification);
        Bundle bundle=getIntent().getExtras();
        userId=bundle.getString("user_id");
        semisterId=bundle.getString("semister_id");
        user_type=bundle.getString("user_type");

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);

        notificationList=new ArrayList<HashMap<String, String>>();
        requestQueue= Volley.newRequestQueue(this);

        networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
        internetConnection= networkStatusChecker.isConnectedToNetwork();
        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        notificationLocalDataList=new ArrayList<HashMap<String, String>>();

        if (internetConnection==true){
            getNotifications();
        }else{

            notificationLocalDataList=localDataBaseHelper.getNotificationInfo();
            if (notificationLocalDataList.isEmpty()==false){
                ListAdapter adapter = new SimpleAdapter(
                        Notifications.this, notificationLocalDataList,
                        R.layout.notification_list_item, new String[] { "course_title", "date","time","status_details",},
                        new int[] { R.id.tvNotificationCourseTitle, R.id.tvNotificationDate,R.id.tvNotificationTime,R.id.tvNotificationText});

                notificationListView.setAdapter(adapter);
            }
        }

    }

    private void getNotifications() {
        showProgressDialog();
        url="http://nahiddihanbd.comlu.com/course_associate/student_notifications.php?semister_id="+semisterId;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   notificationLocalDataList= localDataBaseHelper.getNotificationInfo();
                    if (notificationLocalDataList.isEmpty()==false){
                        localDataBaseHelper.deleteNotificationInfo();
                    }
                    if(response.getInt("success")==1){
                        JSONArray jsonArray=response.getJSONArray("post");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String courseTitle = jsonObject.getString("course_title");
                            String date = jsonObject.getString("date");
                            String time =jsonObject.getString("time");
                            String status=jsonObject.getString("status_details");

                            localDataBaseHelper.insertNotification(courseTitle,date,time,status);

                            notificationHashMap = new HashMap<String, String>();
                            notificationHashMap.put("course_title", courseTitle);
                            notificationHashMap.put("date", date);
                            notificationHashMap.put("time", time);
                            notificationHashMap.put("status_details", status);
                            notificationList.add(notificationHashMap);

                        }

                        ListAdapter adapter = new SimpleAdapter(
                                Notifications.this, notificationList,
                                R.layout.notification_list_item, new String[] { "course_title", "date","time","status_details",},
                                new int[] { R.id.tvNotificationCourseTitle, R.id.tvNotificationDate,R.id.tvNotificationTime,R.id.tvNotificationText});

                        notificationListView.setAdapter(adapter);

                        hideProgressDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgressDialog();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideProgressDialog();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
