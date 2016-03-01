package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Syllabus extends Activity{
    String userId;
    String userType;
    String semisterId;
    ListView courseListView;
    ArrayList<HashMap<String, String>> courseList;
    HashMap<String, String> courseHashMap;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    LocalDataBaseHelper localDataBaseHelper;
    ArrayList<HashMap<String, String>> syllabusLocalDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syllabus);
        courseListView=(ListView)findViewById(R.id.lvCourse);

        networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
        internetConnection= networkStatusChecker.isConnectedToNetwork();
        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        syllabusLocalDataList= new ArrayList<HashMap<String, String>>();
        Bundle bundle=getIntent().getExtras();
        userType=bundle.getString("user_type");
        userId=bundle.getString("user_id");
        semisterId=bundle.getString("semister_id");
        courseList=new ArrayList<HashMap<String, String>>();
        requestQueue= Volley.newRequestQueue(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);


        if (internetConnection==true){
            if(userType.equalsIgnoreCase("student")){
                getStudentSyllabusCourseList();
            }else{
                getTeacherSyllabusCourseList();
            }
        }else{
            syllabusLocalDataList=localDataBaseHelper.getSyllabusListInfo();
            if (syllabusLocalDataList.isEmpty()==false){
                ListAdapter adapter = new SimpleAdapter(
                        Syllabus.this, syllabusLocalDataList,
                        R.layout.course_list_item, new String[] { "course_id", "course_code","course_title"},
                        new int[] { R.id.courseId, R.id.courseCode,R.id.courseTitle });

                courseList=syllabusLocalDataList;
                courseListView.setAdapter(adapter);
                Log.v("local syllabus data **********",syllabusLocalDataList.toString());
            }
        }


        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String courseId= courseList.get(position).get("course_id");
                Intent syllabusIntent=new Intent(Syllabus.this,CourseSyllabus.class);
                syllabusIntent.putExtra("course_id",courseId);
                startActivity(syllabusIntent);
            }
        });
    }

    private void getTeacherSyllabusCourseList() {
        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/teacher_course.php?teacher_id="+userId;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   syllabusLocalDataList= localDataBaseHelper.getSyllabusListInfo();
                    if (syllabusLocalDataList.isEmpty()==false){
                        localDataBaseHelper.deleteSyllabusInfo();
                    }
                    if(response.getInt("success")==1){
                        JSONArray jsonArray=response.getJSONArray("teacher_course");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String courseId = jsonObject.getString("course_id");
                            String courseCode = jsonObject.getString("course_code");
                            String CourseTitle = jsonObject.getString("course_title");
                            String credit = jsonObject.getString("credit");
                            String creditHour = jsonObject.getString("credit_hour");
                            String syllabus = jsonObject.getString("syllabus");
                            courseHashMap = new HashMap<String, String>();

                            courseHashMap.put("course_id", courseId);
                            courseHashMap.put("course_code", courseCode);
                            courseHashMap.put("course_title", CourseTitle);
                            courseHashMap.put("credit", credit);
                            courseHashMap.put("credit_hour", creditHour);
                            courseHashMap.put("syllabus", syllabus);
                            courseList.add(courseHashMap);
                        }
                        //Log.v("inserting syllabus",courseList.toString());
                        localDataBaseHelper.insertSyllabus(courseList);
                        ListAdapter adapter = new SimpleAdapter(
                                Syllabus.this, courseList,
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

    private void getStudentSyllabusCourseList() {
        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/student_course.php?semister_id="+semisterId;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    syllabusLocalDataList= localDataBaseHelper.getSyllabusListInfo();
                    if (syllabusLocalDataList.isEmpty()==false){
                        localDataBaseHelper.deleteSyllabusInfo();
                    }
                    if(response.getInt("success")==1){
                        JSONArray jsonArray=response.getJSONArray("student_course");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String courseId = jsonObject.getString("course_id");
                            String courseCode = jsonObject.getString("course_code");
                            String CourseTitle = jsonObject.getString("course_title");
                            String credit=jsonObject.getString("credit");
                            String creditHour=jsonObject.getString("credit_hour");
                            String syllabus=jsonObject.getString("syllabus");
                            courseHashMap = new HashMap<String, String>();
                            courseHashMap.put("course_id", courseId);
                            courseHashMap.put("course_code", courseCode);
                            courseHashMap.put("course_title", CourseTitle);
                            courseHashMap.put("credit", credit);
                            courseHashMap.put("credit_hour", creditHour);
                            courseHashMap.put("syllabus", syllabus);
                            courseList.add(courseHashMap);
                            Log.v("fetchin json datasyllabus******",syllabus);
                        }
                        localDataBaseHelper.insertSyllabus(courseList);

                        ListAdapter adapter = new SimpleAdapter(
                                Syllabus.this, courseList,
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

    private void showProgressDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    }

