package com.example.na.courseassociate;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class Courses  extends Activity {

    String userId;
    String userType;
    String semisterId;
    ListView courseListView;
    ArrayList<HashMap<String, String>> courseList;
    ArrayList<HashMap<String, String>> courseLocalDataList;

    HashMap<String, String> courseHashMap;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    LocalDataBaseHelper localDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list);
        courseListView=(ListView)findViewById(R.id.lvCourse);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);

        Bundle bundle=getIntent().getExtras();
        userType=bundle.getString("user_type");
        userId=bundle.getString("user_id");
        semisterId=bundle.getString("semister_id");

        networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
        internetConnection= networkStatusChecker.isConnectedToNetwork();
        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        courseList=new ArrayList<HashMap<String, String>>();
        courseLocalDataList=new ArrayList<HashMap<String, String>>();

        requestQueue= Volley.newRequestQueue(this);

        if(internetConnection==true){

            if(userType.equalsIgnoreCase("student")){
                getStudentCourses();
            }else{
                getTeacherCourse();
            }
        }else{
            courseLocalDataList=localDataBaseHelper.getCourseInfo();
            if (courseLocalDataList.isEmpty()==false){

                ListAdapter adapter = new SimpleAdapter(
                        Courses.this, courseLocalDataList,
                        R.layout.course_list_item, new String[] { "course_id", "course_code","course_title"},
                        new int[] { R.id.courseId, R.id.courseCode,R.id.courseTitle });

                courseListView.setAdapter(adapter);

            }
        }




 courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String a= courseList.get(position).get("course_code");
            }
        });
    }

    public void getTeacherCourse() {
        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/teacher_course.php?teacher_id="+userId;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    courseLocalDataList=localDataBaseHelper.getCourseInfo();
                    if (courseLocalDataList.isEmpty()==false){
                        localDataBaseHelper.deleteCourseInfo();
                    }
                    if(response.getInt("success")==1){
                        JSONArray jsonArray=response.getJSONArray("teacher_course");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String courseId = jsonObject.getString("course_id");
                            String courseCode = jsonObject.getString("course_code");
                            String CourseTitle = jsonObject.getString("course_title");
                            courseHashMap = new HashMap<String, String>();
                            courseHashMap.put("course_id", courseId);
                            courseHashMap.put("course_code", courseCode);
                            courseHashMap.put("course_title", CourseTitle);
                            courseList.add(courseHashMap);
                            localDataBaseHelper.insertCourse(Integer.parseInt(courseId),courseCode,CourseTitle);
                        }

                        ListAdapter adapter = new SimpleAdapter(
                                Courses.this, courseList,
                                R.layout.course_list_item, new String[] { "course_id", "course_code","course_title"},
                                new int[] { R.id.courseId, R.id.courseCode,R.id.courseTitle });

                        courseListView.setAdapter(adapter);
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

    private void getStudentCourses() {
        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/student_course.php?semister_id="+semisterId;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    courseLocalDataList=localDataBaseHelper.getCourseInfo();
                    if (courseLocalDataList.isEmpty()==false){
                        localDataBaseHelper.deleteCourseInfo();
                    }
                    if(response.getInt("success")==1){
                        JSONArray jsonArray=response.getJSONArray("student_course");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String courseId = jsonObject.getString("course_id");
                            String courseCode = jsonObject.getString("course_code");
                            String CourseTitle = jsonObject.getString("course_title");
                            courseHashMap = new HashMap<String, String>();
                            courseHashMap.put("course_id", courseId);
                            courseHashMap.put("course_code", courseCode);
                            courseHashMap.put("course_title", CourseTitle);
                            courseList.add(courseHashMap);

                        }

                        ListAdapter adapter = new SimpleAdapter(
                                Courses.this, courseList,
                                R.layout.course_list_item, new String[] { "course_id", "course_code","course_title"},
                                new int[] { R.id.courseId, R.id.courseCode,R.id.courseTitle });

                        courseListView.setAdapter(adapter);
                        localDataBaseHelper.insertCourse(courseList);
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
