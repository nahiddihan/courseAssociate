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


public class Review extends Activity{
    String userId;
    String semisterId;
    String user_type;
    String url;
    RequestQueue requestQueue;

   ProgressDialog progressDialog;
    ListView notificationListView;
    ArrayList<HashMap<String, String>> reviewList;
    HashMap<String, String> reviewHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        notificationListView=(ListView)findViewById(R.id.lvReview);
        Bundle bundle=getIntent().getExtras();
        userId=bundle.getString("user_id");
        semisterId=bundle.getString("semister_id");
        user_type=bundle.getString("user_type");

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);
        reviewList =new ArrayList<HashMap<String, String>>();
        requestQueue= Volley.newRequestQueue(this);

        getReviewList();
    }

    private void getReviewList() {
        showProgressDialog();
        url="http://nahiddihanbd.comlu.com/course_associate/teacher_notifications.php?teacher_id="+userId;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("success")==1){
                        JSONArray jsonArray=response.getJSONArray("post");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String courseTitle = jsonObject.getString("course_title");
                            String date = jsonObject.getString("date");
                            String time =jsonObject.getString("time");
                            String status=jsonObject.getString("status_details");

                            reviewHashMap = new HashMap<String, String>();
                            reviewHashMap.put("course_title", courseTitle);
                            reviewHashMap.put("date", date);
                            reviewHashMap.put("time", time);
                            reviewHashMap.put("status_details", status);
                            reviewList.add(reviewHashMap);
                        }

                        ListAdapter adapter = new SimpleAdapter(
                                Review.this, reviewList,
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
