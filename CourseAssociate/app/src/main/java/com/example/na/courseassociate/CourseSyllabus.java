package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

import java.util.HashMap;


public class CourseSyllabus extends Activity {

    TextView courseCodeTv;TextView courseTitleTv;
    TextView creditTv;TextView creditHourTv;
    TextView syllabusTv;
    String courseId;
    LocalDataBaseHelper localDataBaseHelper;
    HashMap<String,String> syllabusHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_syllabus);
         courseCodeTv=(TextView)findViewById(R.id.tvSyllabusCourseCode);
         courseTitleTv=(TextView)findViewById(R.id.tvSyllabusCourseTitle);
         creditTv=(TextView)findViewById(R.id.tvSyllabusCourseCredit);
         creditHourTv=(TextView)findViewById(R.id.tvSyllabusCreditHour);
         syllabusTv=(TextView)findViewById(R.id.tvSyllabusText);
        courseId=getIntent().getStringExtra("course_id");

        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        syllabusHashMap=new HashMap<String,String>();
        syllabusHashMap=localDataBaseHelper.getSyllabus(courseId);
        if (syllabusHashMap.isEmpty()==false) {

            courseCodeTv.setText(syllabusHashMap.get("course_code"));
            courseTitleTv.setText(syllabusHashMap.get("course_title"));
            creditTv.setText("Course Credit : " + syllabusHashMap.get("credit"));
            creditHourTv.setText("Credit Hour : " + syllabusHashMap.get("credit_hour"));
            syllabusTv.setText(syllabusHashMap.get("syllabus"));
        }

    }

    }

