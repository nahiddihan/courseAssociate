package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

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
import java.util.List;

public class CourseSchedule extends Activity{
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader ;
    HashMap<String, List<String>> listDataChild;
    String userType;String userId;
    String semisterId;
    String url;
    List<String> row;
    RequestQueue requestQueue;
    JSONArray jsonArray;
    ProgressDialog progressDialog;
    LocalDataBaseHelper localDataBaseHelper;
    ArrayList<HashMap<String,String>> scheduleArrayList;
    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_schedule);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);

        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        scheduleArrayList=new ArrayList<HashMap<String, String>>();
        networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
        internetConnection= networkStatusChecker.isConnectedToNetwork();

        requestQueue= Volley.newRequestQueue(this);
        Bundle bundle=getIntent().getExtras();
        userType=bundle.getString("user_type");
        userId=bundle.getString("user_id");
        semisterId=bundle.getString("semister_id");
        expandableListView=(ExpandableListView)findViewById(R.id.elvCourseSchedule);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        if(userType.equalsIgnoreCase("student")){
            url="http://nahiddihanbd.comlu.com/course_associate/student_course_schedule.php?semister_id="+semisterId;
        }else{
            url="http://nahiddihanbd.comlu.com/course_associate/teacher_course_schedule.php?teacher_id="+userId;
        }

        if(internetConnection==false){
            scheduleArrayList=localDataBaseHelper.getScheduleInfo();
            if (scheduleArrayList.isEmpty()==false){
                getLocalDataSchedule(scheduleArrayList);
            }
        }else{
            scheduleArrayList=localDataBaseHelper.getScheduleInfo();
            if (scheduleArrayList.isEmpty()==false){
                localDataBaseHelper.deleteScheduleInfo();
            }
            getCourseSchedule();
        }

    }

    private void getLocalDataSchedule(ArrayList<HashMap<String,String>> scheduleHashMapList) {

        for (int i=0; i<scheduleHashMapList.size(); i++) {

            row=new ArrayList<String>();
            HashMap<String,String> titleHashMap=new HashMap<String,String>();
            titleHashMap=scheduleHashMapList.get(i);
            if(listDataHeader.isEmpty()){
                listDataHeader.add(titleHashMap.get("course_title"));
                for(int j=0;j<scheduleHashMapList.size();j++){
                    HashMap<String,String> titleHashMapTwo=new HashMap<String,String>();
                    titleHashMapTwo=scheduleHashMapList.get(j);
                    if(titleHashMap.get("course_title").equalsIgnoreCase(titleHashMapTwo.get("course_title"))){
                        row.add(titleHashMapTwo.get("schedule"));
                    }
                }
                int position=listDataHeader.size();
                listDataChild.put(listDataHeader.get(position-1), row);

            }else{


                boolean a=false;
                for(int j=0;j<listDataHeader.size();j++) {

                    if (titleHashMap.get("course_title").equalsIgnoreCase(listDataHeader.get(j))) {
                        a = true;
                    }
                }
                if(a==false) {
                    listDataHeader.add(titleHashMap.get("course_title"));
                }
                for(int k=0;k<scheduleHashMapList.size();k++){
                    HashMap<String,String> titleHashMapTwo=new HashMap<String,String>();
                    titleHashMapTwo=scheduleHashMapList.get(k);
                    if(titleHashMap.get("course_title").equalsIgnoreCase(titleHashMapTwo.get("course_title"))){
                        row.add(titleHashMapTwo.get("schedule"));
                    }
                }
                int position2=listDataHeader.size();
                listDataChild.put(listDataHeader.get(position2-1), row);

            }
        }
        expandableListAdapter=new ExpandableListAdapter(getApplicationContext(),listDataHeader,listDataChild);
        expandableListView.setAdapter(expandableListAdapter);
    }
    private void getCourseSchedule() {
        showProgressDialog();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getInt("success")==1){

                                if(userType.equalsIgnoreCase("student")){
                                    jsonArray=response.getJSONArray("student_course_schedule");
                                }else{
                                    jsonArray=response.getJSONArray("teacher_course_schedule");
                                }
                                for (int i=0; i<jsonArray.length(); i++) {
                                    row=new ArrayList<String>();
                                    JSONObject c = jsonArray.getJSONObject(i);
                                    String scheduleTitle=c.getString("course_title");
                                    String scheduleText=c.getString("day")+" at "+c.getString("time")+"\n"+" duration"+": "+c.getString("duration")+"minutes";
                                    localDataBaseHelper.insertSchedule(scheduleTitle,scheduleText);
                                    if(listDataHeader.isEmpty()){
                                        listDataHeader.add(c.getString("course_title"));
                                        for(int j=0;j<jsonArray.length();j++){
                                            JSONObject d = jsonArray.getJSONObject(j);
                                            if(c.getString("course_title").equalsIgnoreCase(d.getString("course_title"))){
                                               String text=d.getString("day")+" at "+d.getString("time")+"\n"+" duration"+": "+d.getString("duration")+"minutes";
                                               row.add(text);
                                            }
                                        }
                                        int position=listDataHeader.size();
                                        listDataChild.put(listDataHeader.get(position-1), row);

                                    }else{
                                        boolean a=false;


                                        for(int j=0;j<listDataHeader.size();j++) {

                                            if (c.getString("course_title").equalsIgnoreCase(listDataHeader.get(j))) {
                                                a = true;
                                            }
                                        }
                                        if(a==false) {
                                            listDataHeader.add(c.getString("course_title"));
                                        }
                                        for(int k=0;k<jsonArray.length();k++){
                                            JSONObject d = jsonArray.getJSONObject(k);
                                            if(c.getString("course_title").equalsIgnoreCase(d.getString("course_title"))){
                                                String text=d.getString("day")+" at "+d.getString("time")+"  \n"+" duration"+": "+d.getString("duration")+"minutes";
                                                row.add(text);
                                            }
                                        }
                                        int position2=listDataHeader.size();
                                        listDataChild.put(listDataHeader.get(position2-1), row);

                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                        expandableListAdapter=new ExpandableListAdapter(getApplicationContext(),listDataHeader,listDataChild);
                        expandableListView.setAdapter(expandableListAdapter);

                        hideProgressDialog();
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


