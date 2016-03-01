package com.example.na.courseassociate;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

public class ClassRoutine extends Activity implements GestureDetector.OnGestureListener{

 TextView SundayEight1Tv;TextView SundayEight2Tv;TextView SundayEight3Tv;TextView SundayEight4Tv; TextView SundayNine1Tv;TextView SundayNine2Tv;TextView SundayNine3Tv;TextView SundayNine4Tv;TextView SundayTen1Tv; TextView SundayTen2Tv;TextView SundayTen3Tv;TextView SundayTen4Tv;TextView SundayEleven1Tv; TextView SundayEleven2Tv;TextView SundayEleven3Tv; TextView SundayEleven4Tv;TextView SundayTwelve1Tv;TextView SundayTwelve2Tv; TextView SundayTwelve3Tv;TextView SundayTwelve4Tv; TextView SundayTwo1Tv;TextView SundayTwo2Tv; TextView SundayTwo3Tv;TextView SundayTwo4Tv; TextView SundayThree1Tv;TextView SundayThree2Tv;TextView SundayThree3Tv;TextView SundayThree4Tv; TextView SundayFour1Tv; TextView SundayFour2Tv;TextView SundayFour3Tv;TextView SundayFour4Tv;
 TextView mondayEight1Tv;TextView mondayEight2Tv;TextView mondayEight3Tv;TextView mondayEight4Tv; TextView mondayNine1Tv;TextView mondayNine2Tv;TextView mondayNine3Tv;TextView mondayNine4Tv;TextView mondayTen1Tv; TextView mondayTen2Tv;TextView mondayTen3Tv;TextView mondayTen4Tv;TextView mondayEleven1Tv; TextView mondayEleven2Tv;TextView mondayEleven3Tv; TextView mondayEleven4Tv;TextView mondayTwelve1Tv;TextView mondayTwelve2Tv; TextView mondayTwelve3Tv;TextView mondayTwelve4Tv; TextView mondayTwo1Tv;TextView mondayTwo2Tv; TextView mondayTwo3Tv;TextView mondayTwo4Tv; TextView mondayThree1Tv;TextView mondayThree2Tv;TextView mondayThree3Tv;TextView mondayThree4Tv; TextView mondayFour1Tv; TextView mondayFour2Tv;TextView mondayFour3Tv;TextView mondayFour4Tv;
 TextView tuesdayEight1Tv;TextView tuesdayEight2Tv;TextView tuesdayEight3Tv;TextView tuesdayEight4Tv; TextView tuesdayNine1Tv;TextView tuesdayNine2Tv;TextView tuesdayNine3Tv;TextView tuesdayNine4Tv;TextView tuesdayTen1Tv; TextView tuesdayTen2Tv;TextView tuesdayTen3Tv;TextView tuesdayTen4Tv;TextView tuesdayEleven1Tv; TextView tuesdayEleven2Tv;TextView tuesdayEleven3Tv; TextView tuesdayEleven4Tv;TextView tuesdayTwelve1Tv;TextView tuesdayTwelve2Tv; TextView tuesdayTwelve3Tv;TextView tuesdayTwelve4Tv; TextView tuesdayTwo1Tv;TextView tuesdayTwo2Tv; TextView tuesdayTwo3Tv;TextView tuesdayTwo4Tv; TextView tuesdayThree1Tv;TextView tuesdayThree2Tv;TextView tuesdayThree3Tv;TextView tuesdayThree4Tv; TextView tuesdayFour1Tv; TextView tuesdayFour2Tv;TextView tuesdayFour3Tv;TextView tuesdayFour4Tv;
 TextView wednesdayEight1Tv;TextView wednesdayEight2Tv;TextView wednesdayEight3Tv;TextView wednesdayEight4Tv; TextView wednesdayNine1Tv;TextView wednesdayNine2Tv;TextView wednesdayNine3Tv;TextView wednesdayNine4Tv;TextView wednesdayTen1Tv; TextView wednesdayTen2Tv;TextView wednesdayTen3Tv;TextView wednesdayTen4Tv;TextView wednesdayEleven1Tv; TextView wednesdayEleven2Tv;TextView wednesdayEleven3Tv; TextView wednesdayEleven4Tv;TextView wednesdayTwelve1Tv;TextView wednesdayTwelve2Tv; TextView wednesdayTwelve3Tv;TextView wednesdayTwelve4Tv; TextView wednesdayTwo1Tv;TextView wednesdayTwo2Tv; TextView wednesdayTwo3Tv;TextView wednesdayTwo4Tv; TextView wednesdayThree1Tv;TextView wednesdayThree2Tv;TextView wednesdayThree3Tv;TextView wednesdayThree4Tv; TextView wednesdayFour1Tv; TextView wednesdayFour2Tv;TextView wednesdayFour3Tv;TextView wednesdayFour4Tv;
 TextView thursdayEight1Tv;TextView thursdayEight2Tv;TextView thursdayEight3Tv;TextView thursdayEight4Tv; TextView thursdayNine1Tv;TextView thursdayNine2Tv;TextView thursdayNine3Tv;TextView thursdayNine4Tv;TextView thursdayTen1Tv; TextView thursdayTen2Tv;TextView thursdayTen3Tv;TextView thursdayTen4Tv;TextView thursdayEleven1Tv; TextView thursdayEleven2Tv;TextView thursdayEleven3Tv; TextView thursdayEleven4Tv;TextView thursdayTwelve1Tv;TextView thursdayTwelve2Tv; TextView thursdayTwelve3Tv;TextView thursdayTwelve4Tv; TextView thursdayTwo1Tv;TextView thursdayTwo2Tv; TextView thursdayTwo3Tv;TextView thursdayTwo4Tv; TextView thursdayThree1Tv;TextView thursdayThree2Tv;TextView thursdayThree3Tv;TextView thursdayThree4Tv; TextView thursdayFour1Tv; TextView thursdayFour2Tv;TextView thursdayFour3Tv;TextView thursdayFour4Tv;
    String departmentId;
    private GestureDetectorCompat mDetector;
    Animation slide_in_left;
    Animation slide_out_right;
    Animation slide_in_right;
     Animation slide_out_left;
    ViewFlipper viewFlipper;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    LocalDataBaseHelper localDataBaseHelper;
    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_routine);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);
        initializeViews();
        Bundle bundle=getIntent().getExtras();
        departmentId=bundle.getString("department_id");
        requestQueue= Volley.newRequestQueue(this);

        mDetector = new GestureDetectorCompat(this,this);
        slide_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slide_in_right = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slide_out_right = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        slide_out_left = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);

        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
        internetConnection= networkStatusChecker.isConnectedToNetwork();

        if (internetConnection==true){
            getClassRoutine();
        }else{
            ArrayList<HashMap<String, String>> sundayLocalDataList=new ArrayList<HashMap<String, String>>();
            sundayLocalDataList=localDataBaseHelper.getSundayRoutine();
            if (sundayLocalDataList.isEmpty()==false){
                for (int i=0;i<sundayLocalDataList.size();i++){
                    String courseCode=sundayLocalDataList.get(i).get("course_code");
                    String time=sundayLocalDataList.get(i).get("time");
                    getSundayFlipper(courseCode,time);
                }
            }
            //monday
            ArrayList<HashMap<String, String>> mondayLocalDataList=new ArrayList<HashMap<String, String>>();
            mondayLocalDataList=localDataBaseHelper.getMondayRoutine();
            if (mondayLocalDataList.isEmpty()==false){
                for (int i=0;i<mondayLocalDataList.size();i++){
                    String courseCode=mondayLocalDataList.get(i).get("course_code");
                    String time=mondayLocalDataList.get(i).get("time");
                    getMondayFlipper(courseCode,time);
                }
            }

            // tuesday
            ArrayList<HashMap<String, String>> tuesdayLocalDataList=new ArrayList<HashMap<String, String>>();
            tuesdayLocalDataList=localDataBaseHelper.getTuesdayRoutine();
            if (tuesdayLocalDataList.isEmpty()==false){
                for (int i=0;i<tuesdayLocalDataList.size();i++){
                    String courseCode=tuesdayLocalDataList.get(i).get("course_code");
                    String time=tuesdayLocalDataList.get(i).get("time");
                    getTuesdayFlipper(courseCode,time);
                }
            }

            ArrayList<HashMap<String, String>> wednesdayLocalDataList=new ArrayList<HashMap<String, String>>();
            wednesdayLocalDataList=localDataBaseHelper.getWednesdayRoutine();
            if (wednesdayLocalDataList.isEmpty()==false){
                for (int i=0;i<wednesdayLocalDataList.size();i++){
                    String courseCode=wednesdayLocalDataList.get(i).get("course_code");
                    String time=wednesdayLocalDataList.get(i).get("time");
                    getWednesdayFlipper(courseCode,time);
                }
            }

            ArrayList<HashMap<String, String>> thursdayLocalDataList=new ArrayList<HashMap<String, String>>();
            thursdayLocalDataList=localDataBaseHelper.getThursdayRoutine();
            if (thursdayLocalDataList.isEmpty()==false){
                for (int i=0;i<thursdayLocalDataList.size();i++){
                    String courseCode=thursdayLocalDataList.get(i).get("course_code");
                    String time=thursdayLocalDataList.get(i).get("time");
                    getThursdayFlipper(courseCode,time);
                }
            }
        }

    }

    private void getClassRoutine() {
        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/class_routine.php?department_id="+departmentId;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if(response.getInt("success")==1){
                        // code for SUNDAY
                        JSONArray jsonArraySunday=response.getJSONArray("Sunday");
                        ArrayList<HashMap<String, String>> sundayLocalDataList=new ArrayList<HashMap<String, String>>();
                        sundayLocalDataList=localDataBaseHelper.getSundayRoutine();
                        if (sundayLocalDataList.isEmpty()==false){
                            localDataBaseHelper.deleteSundayInfo();
                            localDataBaseHelper.deleteMondayInfo();
                            localDataBaseHelper.deleteTuesdayInfo();
                            localDataBaseHelper.deleteWednesdayInfo();
                            localDataBaseHelper.deleteThursdayInfo();
                        }
                        if(jsonArraySunday.length()>0){
                            for(int i=0;i<jsonArraySunday.length();i++){
                                JSONObject jsonObject = jsonArraySunday.getJSONObject(i);
                                String courseCode=jsonObject.getString("course_code");
                                String time = jsonObject.getString("time");
                                getSundayFlipper(courseCode,time);
                                localDataBaseHelper.insertSundayRoutine(courseCode,time);

                            }
                        }

                        // code for MONDAY
                        JSONArray jsonArrayMonday=response.getJSONArray("Monday");
                        if(jsonArrayMonday.length()>0){
                            for(int i=0;i<jsonArrayMonday.length();i++){
                                JSONObject jsonObject = jsonArrayMonday.getJSONObject(i);
                                String courseCode=jsonObject.getString("course_code");
                                String time = jsonObject.getString("time");
                                getMondayFlipper(courseCode,time);
                                localDataBaseHelper.insertMondayRoutine(courseCode,time);
                            }
                        }

                        // code for TUESDAY
                        JSONArray jsonArrayTuesday=response.getJSONArray("Tuesday");
                        if(jsonArrayTuesday.length()>0){
                            for(int i=0;i<jsonArrayTuesday.length();i++){
                                JSONObject jsonObject = jsonArrayTuesday.getJSONObject(i);
                                String courseCode=jsonObject.getString("course_code");
                                String time = jsonObject.getString("time");
                                getTuesdayFlipper(courseCode,time);
                                localDataBaseHelper.insertTuesdayRoutine(courseCode,time);
                            }
                        }

                        // code for WEDNESDAY
                        JSONArray jsonArrayWednesday=response.getJSONArray("Wednesday");
                        if(jsonArrayWednesday.length()>0){
                            for(int i=0;i<jsonArrayWednesday.length();i++){
                                JSONObject jsonObject = jsonArrayWednesday.getJSONObject(i);
                                String courseCode=jsonObject.getString("course_code");
                                String time = jsonObject.getString("time");
                                getWednesdayFlipper(courseCode,time);
                                localDataBaseHelper.insertWednesdayRoutine(courseCode,time);
                            }
                        }

                        // code for THURSDAY
                        JSONArray jsonArrayThursday=response.getJSONArray("Thursday");
                        if(jsonArrayThursday.length()>0){
                            for(int i=0;i<jsonArrayThursday.length();i++){
                                JSONObject jsonObject = jsonArrayThursday.getJSONObject(i);
                                String courseCode=jsonObject.getString("course_code");
                                String time = jsonObject.getString("time");
                                getThursdayFlipper(courseCode,time);
                                localDataBaseHelper.insertThursdayRoutine(courseCode,time);
                            }
                        }
                    }
                    hideProgressDialog();
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

    private void getSundayFlipper( String courseCode, String time) {

        if(time.equalsIgnoreCase("08:00 A.M")){

            if(SundayEight1Tv.getText().toString().equalsIgnoreCase("-")){
                SundayEight1Tv.setText(courseCode);
            }else if(SundayEight2Tv.getText().toString().equalsIgnoreCase("-")){SundayEight2Tv.setText(courseCode);
            }else if(SundayEight3Tv.getText().toString().equalsIgnoreCase("-")){SundayEight3Tv.setText(courseCode);
            }else if(SundayEight4Tv.getText().toString().equalsIgnoreCase("-")){SundayEight4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("09:00 A.M")){
            if(SundayNine1Tv.getText().toString().equalsIgnoreCase("-")){SundayNine1Tv.setText(courseCode);
            }else if(SundayNine2Tv.getText().toString().equalsIgnoreCase("-")){SundayNine2Tv.setText(courseCode);
            }else if(SundayNine3Tv.getText().toString().equalsIgnoreCase("-")){SundayNine3Tv.setText(courseCode);
            }else if(SundayNine4Tv.getText().toString().equalsIgnoreCase("-")){SundayNine4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("10:00 A.M")){
            if(SundayTen1Tv.getText().toString().equalsIgnoreCase("-")){SundayTen1Tv.setText(courseCode);
            }else if(SundayTen2Tv.getText().toString().equalsIgnoreCase("-")){SundayTen2Tv.setText(courseCode);
            }else if(SundayTen3Tv.getText().toString().equalsIgnoreCase("-")){SundayTen3Tv.setText(courseCode);
            }else if(SundayTen4Tv.getText().toString().equalsIgnoreCase("-")){SundayTen4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("11:00 A.M")){
            if(SundayEleven1Tv.getText().toString().equalsIgnoreCase("-")){SundayEleven1Tv.setText(courseCode);
            }else if(SundayEleven2Tv.getText().toString().equalsIgnoreCase("-")){SundayEleven2Tv.setText(courseCode);
            }else if(SundayEleven3Tv.getText().toString().equalsIgnoreCase("-")){SundayEleven3Tv.setText(courseCode);
            }else if(SundayEleven4Tv.getText().toString().equalsIgnoreCase("-")){SundayEleven4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("12:00 P.M")){
            if(SundayTwelve1Tv.getText().toString().equalsIgnoreCase("-")){SundayTwelve1Tv.setText(courseCode);
            }else if(SundayTwelve2Tv.getText().toString().equalsIgnoreCase("-")){SundayTwelve2Tv.setText(courseCode);
            }else if(SundayTwelve3Tv.getText().toString().equalsIgnoreCase("-")){SundayTwelve3Tv.setText(courseCode);
            }else if(SundayTwelve4Tv.getText().toString().equalsIgnoreCase("-")){SundayTwelve4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("02:00 P.M")){
            if(SundayTwo1Tv.getText().toString().equalsIgnoreCase("-")){SundayTwo1Tv.setText(courseCode);
            }else if(SundayTwo2Tv.getText().toString().equalsIgnoreCase("-")){SundayTwo2Tv.setText(courseCode);
            }else if(SundayTwo3Tv.getText().toString().equalsIgnoreCase("-")){SundayTwo3Tv.setText(courseCode);
            }else if(SundayTwo4Tv.getText().toString().equalsIgnoreCase("-")){SundayTwo4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("03:00 P.M")){
            if(SundayThree1Tv.getText().toString().equalsIgnoreCase("-")){SundayThree1Tv.setText(courseCode);
            }else if(SundayThree2Tv.getText().toString().equalsIgnoreCase("-")){SundayThree2Tv.setText(courseCode);
            }else if(SundayThree3Tv.getText().toString().equalsIgnoreCase("-")){SundayThree3Tv.setText(courseCode);
            }else if(SundayThree4Tv.getText().toString().equalsIgnoreCase("-")){SundayThree4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("04:00 P.M")){
            if(SundayFour1Tv.getText().toString().equalsIgnoreCase("-")){SundayFour1Tv.setText(courseCode);
            }else if(SundayFour2Tv.getText().toString().equalsIgnoreCase("-")){SundayFour2Tv.setText(courseCode);
            }else if(SundayFour3Tv.getText().toString().equalsIgnoreCase("-")){SundayFour3Tv.setText(courseCode);
            }else if(SundayFour4Tv.getText().toString().equalsIgnoreCase("-")){SundayFour4Tv.setText(courseCode);
            }

        }
    }
    private void getMondayFlipper( String courseCode, String time) {

        if(time.equalsIgnoreCase("08:00 A.M")){

            if(mondayEight1Tv.getText().toString().equalsIgnoreCase("-")){
                mondayEight1Tv.setText(courseCode);
            }else if(mondayEight2Tv.getText().toString().equalsIgnoreCase("-")){mondayEight2Tv.setText(courseCode);
            }else if(mondayEight3Tv.getText().toString().equalsIgnoreCase("-")){mondayEight3Tv.setText(courseCode);
            }else if(mondayEight4Tv.getText().toString().equalsIgnoreCase("-")){mondayEight4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("09:00 A.M")){
            if(mondayNine1Tv.getText().toString().equalsIgnoreCase("-")){mondayNine1Tv.setText(courseCode);
            }else if(mondayNine2Tv.getText().toString().equalsIgnoreCase("-")){mondayNine2Tv.setText(courseCode);
            }else if(mondayNine3Tv.getText().toString().equalsIgnoreCase("-")){mondayNine3Tv.setText(courseCode);
            }else if(mondayNine4Tv.getText().toString().equalsIgnoreCase("-")){mondayNine4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("10:00 A.M")){
            if(mondayTen1Tv.getText().toString().equalsIgnoreCase("-")){mondayTen1Tv.setText(courseCode);
            }else if(mondayTen2Tv.getText().toString().equalsIgnoreCase("-")){mondayTen2Tv.setText(courseCode);
            }else if(mondayTen3Tv.getText().toString().equalsIgnoreCase("-")){mondayTen3Tv.setText(courseCode);
            }else if(mondayTen4Tv.getText().toString().equalsIgnoreCase("-")){mondayTen4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("11:00 A.M")){
            if(mondayEleven1Tv.getText().toString().equalsIgnoreCase("-")){mondayEleven1Tv.setText(courseCode);
            }else if(mondayEleven2Tv.getText().toString().equalsIgnoreCase("-")){mondayEleven2Tv.setText(courseCode);
            }else if(mondayEleven3Tv.getText().toString().equalsIgnoreCase("-")){mondayEleven3Tv.setText(courseCode);
            }else if(mondayEleven4Tv.getText().toString().equalsIgnoreCase("-")){mondayEleven4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("12:00 P.M")){
            if(mondayTwelve1Tv.getText().toString().equalsIgnoreCase("-")){mondayTwelve1Tv.setText(courseCode);
            }else if(mondayTwelve2Tv.getText().toString().equalsIgnoreCase("-")){mondayTwelve2Tv.setText(courseCode);
            }else if(mondayTwelve3Tv.getText().toString().equalsIgnoreCase("-")){mondayTwelve3Tv.setText(courseCode);
            }else if(mondayTwelve4Tv.getText().toString().equalsIgnoreCase("-")){mondayTwelve4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("02:00 P.M")){
            if(mondayTwo1Tv.getText().toString().equalsIgnoreCase("-")){mondayTwo1Tv.setText(courseCode);
            }else if(mondayTwo2Tv.getText().toString().equalsIgnoreCase("-")){mondayTwo2Tv.setText(courseCode);
            }else if(mondayTwo3Tv.getText().toString().equalsIgnoreCase("-")){mondayTwo3Tv.setText(courseCode);
            }else if(mondayTwo4Tv.getText().toString().equalsIgnoreCase("-")){mondayTwo4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("03:00 P.M")){
            if(mondayThree1Tv.getText().toString().equalsIgnoreCase("-")){mondayThree1Tv.setText(courseCode);
            }else if(mondayThree2Tv.getText().toString().equalsIgnoreCase("-")){mondayThree2Tv.setText(courseCode);
            }else if(mondayThree3Tv.getText().toString().equalsIgnoreCase("-")){mondayThree3Tv.setText(courseCode);
            }else if(mondayThree4Tv.getText().toString().equalsIgnoreCase("-")){mondayThree4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("04:00 P.M")){
            if(mondayFour1Tv.getText().toString().equalsIgnoreCase("-")){mondayFour1Tv.setText(courseCode);
            }else if(mondayFour2Tv.getText().toString().equalsIgnoreCase("-")){mondayFour2Tv.setText(courseCode);
            }else if(mondayFour3Tv.getText().toString().equalsIgnoreCase("-")){mondayFour3Tv.setText(courseCode);
            }else if(mondayFour4Tv.getText().toString().equalsIgnoreCase("-")){mondayFour4Tv.setText(courseCode);
            }

        }
    }
    private void getTuesdayFlipper( String courseCode, String time) {

        if(time.equalsIgnoreCase("08:00 A.M")){

            if(tuesdayEight1Tv.getText().toString().equalsIgnoreCase("-")){
                tuesdayEight1Tv.setText(courseCode);
            }else if(tuesdayEight2Tv.getText().toString().equalsIgnoreCase("-")){tuesdayEight2Tv.setText(courseCode);
            }else if(tuesdayEight3Tv.getText().toString().equalsIgnoreCase("-")){tuesdayEight3Tv.setText(courseCode);
            }else if(tuesdayEight4Tv.getText().toString().equalsIgnoreCase("-")){tuesdayEight4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("09:00 A.M")){
            if(tuesdayNine1Tv.getText().toString().equalsIgnoreCase("-")){tuesdayNine1Tv.setText(courseCode);
            }else if(tuesdayNine2Tv.getText().toString().equalsIgnoreCase("-")){tuesdayNine2Tv.setText(courseCode);
            }else if(tuesdayNine3Tv.getText().toString().equalsIgnoreCase("-")){tuesdayNine3Tv.setText(courseCode);
            }else if(tuesdayNine4Tv.getText().toString().equalsIgnoreCase("-")){tuesdayNine4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("10:00 A.M")){
            if(tuesdayTen1Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTen1Tv.setText(courseCode);
            }else if(tuesdayTen2Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTen2Tv.setText(courseCode);
            }else if(tuesdayTen3Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTen3Tv.setText(courseCode);
            }else if(tuesdayTen4Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTen4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("11:00 A.M")){
            if(tuesdayEleven1Tv.getText().toString().equalsIgnoreCase("-")){tuesdayEleven1Tv.setText(courseCode);
            }else if(tuesdayEleven2Tv.getText().toString().equalsIgnoreCase("-")){tuesdayEleven2Tv.setText(courseCode);
            }else if(tuesdayEleven3Tv.getText().toString().equalsIgnoreCase("-")){tuesdayEleven3Tv.setText(courseCode);
            }else if(tuesdayEleven4Tv.getText().toString().equalsIgnoreCase("-")){tuesdayEleven4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("12:00 P.M")){
            if(tuesdayTwelve1Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTwelve1Tv.setText(courseCode);
            }else if(tuesdayTwelve2Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTwelve2Tv.setText(courseCode);
            }else if(tuesdayTwelve3Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTwelve3Tv.setText(courseCode);
            }else if(tuesdayTwelve4Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTwelve4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("02:00 P.M")){
            if(tuesdayTwo1Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTwo1Tv.setText(courseCode);
            }else if(tuesdayTwo2Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTwo2Tv.setText(courseCode);
            }else if(tuesdayTwo3Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTwo3Tv.setText(courseCode);
            }else if(tuesdayTwo4Tv.getText().toString().equalsIgnoreCase("-")){tuesdayTwo4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("03:00 P.M")){
            if(tuesdayThree1Tv.getText().toString().equalsIgnoreCase("-")){tuesdayThree1Tv.setText(courseCode);
            }else if(tuesdayThree2Tv.getText().toString().equalsIgnoreCase("-")){tuesdayThree2Tv.setText(courseCode);
            }else if(tuesdayThree3Tv.getText().toString().equalsIgnoreCase("-")){tuesdayThree3Tv.setText(courseCode);
            }else if(tuesdayThree4Tv.getText().toString().equalsIgnoreCase("-")){tuesdayThree4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("04:00 P.M")){
            if(tuesdayFour1Tv.getText().toString().equalsIgnoreCase("-")){tuesdayFour1Tv.setText(courseCode);
            }else if(tuesdayFour2Tv.getText().toString().equalsIgnoreCase("-")){tuesdayFour2Tv.setText(courseCode);
            }else if(tuesdayFour3Tv.getText().toString().equalsIgnoreCase("-")){tuesdayFour3Tv.setText(courseCode);
            }else if(tuesdayFour4Tv.getText().toString().equalsIgnoreCase("-")){tuesdayFour4Tv.setText(courseCode);
            }

        }
    }
    private void getWednesdayFlipper( String courseCode, String time) {

        if(time.equalsIgnoreCase("08:00 A.M")){

            if(wednesdayEight1Tv.getText().toString().equalsIgnoreCase("-")){
                wednesdayEight1Tv.setText(courseCode);
            }else if(wednesdayEight2Tv.getText().toString().equalsIgnoreCase("-")){wednesdayEight2Tv.setText(courseCode);
            }else if(wednesdayEight3Tv.getText().toString().equalsIgnoreCase("-")){wednesdayEight3Tv.setText(courseCode);
            }else if(wednesdayEight4Tv.getText().toString().equalsIgnoreCase("-")){wednesdayEight4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("09:00 A.M")){
            if(wednesdayNine1Tv.getText().toString().equalsIgnoreCase("-")){wednesdayNine1Tv.setText(courseCode);
            }else if(wednesdayNine2Tv.getText().toString().equalsIgnoreCase("-")){wednesdayNine2Tv.setText(courseCode);
            }else if(wednesdayNine3Tv.getText().toString().equalsIgnoreCase("-")){wednesdayNine3Tv.setText(courseCode);
            }else if(wednesdayNine4Tv.getText().toString().equalsIgnoreCase("-")){wednesdayNine4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("10:00 A.M")){
            if(wednesdayTen1Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTen1Tv.setText(courseCode);
            }else if(wednesdayTen2Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTen2Tv.setText(courseCode);
            }else if(wednesdayTen3Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTen3Tv.setText(courseCode);
            }else if(wednesdayTen4Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTen4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("11:00 A.M")){
            if(wednesdayEleven1Tv.getText().toString().equalsIgnoreCase("-")){wednesdayEleven1Tv.setText(courseCode);
            }else if(wednesdayEleven2Tv.getText().toString().equalsIgnoreCase("-")){wednesdayEleven2Tv.setText(courseCode);
            }else if(wednesdayEleven3Tv.getText().toString().equalsIgnoreCase("-")){wednesdayEleven3Tv.setText(courseCode);
            }else if(wednesdayEleven4Tv.getText().toString().equalsIgnoreCase("-")){wednesdayEleven4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("12:00 P.M")){
            if(wednesdayTwelve1Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTwelve1Tv.setText(courseCode);
            }else if(wednesdayTwelve2Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTwelve2Tv.setText(courseCode);
            }else if(wednesdayTwelve3Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTwelve3Tv.setText(courseCode);
            }else if(wednesdayTwelve4Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTwelve4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("02:00 P.M")){
            if(wednesdayTwo1Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTwo1Tv.setText(courseCode);
            }else if(wednesdayTwo2Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTwo2Tv.setText(courseCode);
            }else if(wednesdayTwo3Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTwo3Tv.setText(courseCode);
            }else if(wednesdayTwo4Tv.getText().toString().equalsIgnoreCase("-")){wednesdayTwo4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("03:00 P.M")){
            if(wednesdayThree1Tv.getText().toString().equalsIgnoreCase("-")){wednesdayThree1Tv.setText(courseCode);
            }else if(wednesdayThree2Tv.getText().toString().equalsIgnoreCase("-")){wednesdayThree2Tv.setText(courseCode);
            }else if(wednesdayThree3Tv.getText().toString().equalsIgnoreCase("-")){wednesdayThree3Tv.setText(courseCode);
            }else if(wednesdayThree4Tv.getText().toString().equalsIgnoreCase("-")){wednesdayThree4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("04:00 P.M")){
            if(wednesdayFour1Tv.getText().toString().equalsIgnoreCase("-")){wednesdayFour1Tv.setText(courseCode);
            }else if(wednesdayFour2Tv.getText().toString().equalsIgnoreCase("-")){wednesdayFour2Tv.setText(courseCode);
            }else if(wednesdayFour3Tv.getText().toString().equalsIgnoreCase("-")){wednesdayFour3Tv.setText(courseCode);
            }else if(wednesdayFour4Tv.getText().toString().equalsIgnoreCase("-")){wednesdayFour4Tv.setText(courseCode);
            }

        }
    }
    private void getThursdayFlipper( String courseCode, String time) {

        if(time.equalsIgnoreCase("08:00 A.M")){

            if(thursdayEight1Tv.getText().toString().equalsIgnoreCase("-")){
                thursdayEight1Tv.setText(courseCode);
            }else if(thursdayEight2Tv.getText().toString().equalsIgnoreCase("-")){thursdayEight2Tv.setText(courseCode);
            }else if(thursdayEight3Tv.getText().toString().equalsIgnoreCase("-")){thursdayEight3Tv.setText(courseCode);
            }else if(thursdayEight4Tv.getText().toString().equalsIgnoreCase("-")){thursdayEight4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("09:00 A.M")){
            if(thursdayNine1Tv.getText().toString().equalsIgnoreCase("-")){thursdayNine1Tv.setText(courseCode);
            }else if(thursdayNine2Tv.getText().toString().equalsIgnoreCase("-")){thursdayNine2Tv.setText(courseCode);
            }else if(thursdayNine3Tv.getText().toString().equalsIgnoreCase("-")){thursdayNine3Tv.setText(courseCode);
            }else if(thursdayNine4Tv.getText().toString().equalsIgnoreCase("-")){thursdayNine4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("10:00 A.M")){
            if(thursdayTen1Tv.getText().toString().equalsIgnoreCase("-")){thursdayTen1Tv.setText(courseCode);
            }else if(thursdayTen2Tv.getText().toString().equalsIgnoreCase("-")){thursdayTen2Tv.setText(courseCode);
            }else if(thursdayTen3Tv.getText().toString().equalsIgnoreCase("-")){thursdayTen3Tv.setText(courseCode);
            }else if(thursdayTen4Tv.getText().toString().equalsIgnoreCase("-")){thursdayTen4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("11:00 A.M")){
            if(thursdayEleven1Tv.getText().toString().equalsIgnoreCase("-")){thursdayEleven1Tv.setText(courseCode);
            }else if(thursdayEleven2Tv.getText().toString().equalsIgnoreCase("-")){thursdayEleven2Tv.setText(courseCode);
            }else if(thursdayEleven3Tv.getText().toString().equalsIgnoreCase("-")){thursdayEleven3Tv.setText(courseCode);
            }else if(thursdayEleven4Tv.getText().toString().equalsIgnoreCase("-")){thursdayEleven4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("12:00 P.M")){
            if(thursdayTwelve1Tv.getText().toString().equalsIgnoreCase("-")){thursdayTwelve1Tv.setText(courseCode);
            }else if(thursdayTwelve2Tv.getText().toString().equalsIgnoreCase("-")){thursdayTwelve2Tv.setText(courseCode);
            }else if(thursdayTwelve3Tv.getText().toString().equalsIgnoreCase("-")){thursdayTwelve3Tv.setText(courseCode);
            }else if(thursdayTwelve4Tv.getText().toString().equalsIgnoreCase("-")){thursdayTwelve4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("02:00 P.M")){
            if(thursdayTwo1Tv.getText().toString().equalsIgnoreCase("-")){thursdayTwo1Tv.setText(courseCode);
            }else if(thursdayTwo2Tv.getText().toString().equalsIgnoreCase("-")){thursdayTwo2Tv.setText(courseCode);
            }else if(thursdayTwo3Tv.getText().toString().equalsIgnoreCase("-")){thursdayTwo3Tv.setText(courseCode);
            }else if(thursdayTwo4Tv.getText().toString().equalsIgnoreCase("-")){thursdayTwo4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("03:00 P.M")){
            if(thursdayThree1Tv.getText().toString().equalsIgnoreCase("-")){thursdayThree1Tv.setText(courseCode);
            }else if(thursdayThree2Tv.getText().toString().equalsIgnoreCase("-")){thursdayThree2Tv.setText(courseCode);
            }else if(thursdayThree3Tv.getText().toString().equalsIgnoreCase("-")){thursdayThree3Tv.setText(courseCode);
            }else if(thursdayThree4Tv.getText().toString().equalsIgnoreCase("-")){thursdayThree4Tv.setText(courseCode);
            }

        }else if(time.equalsIgnoreCase("04:00 P.M")){
            if(thursdayFour1Tv.getText().toString().equalsIgnoreCase("-")){thursdayFour1Tv.setText(courseCode);
            }else if(thursdayFour2Tv.getText().toString().equalsIgnoreCase("-")){thursdayFour2Tv.setText(courseCode);
            }else if(thursdayFour3Tv.getText().toString().equalsIgnoreCase("-")){thursdayFour3Tv.setText(courseCode);
            }else if(thursdayFour4Tv.getText().toString().equalsIgnoreCase("-")){thursdayFour4Tv.setText(courseCode);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float sensitivity = 50;

        if((e1.getX() - e2.getX()) > sensitivity){
            viewFlipper.setInAnimation(slide_in_right);
            viewFlipper.setOutAnimation(slide_out_left);
            viewFlipper.showNext();
            Toast.makeText(ClassRoutine.this,
                    "Next", Toast.LENGTH_SHORT).show();
        }else if((e2.getX() - e1.getX()) > sensitivity){
            viewFlipper.setInAnimation(slide_in_left);
            viewFlipper.setOutAnimation(slide_out_right);
            // viewFlipper.showNext();
            viewFlipper.showPrevious();
            Toast.makeText(ClassRoutine.this,
                    "Previous", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void initializeViews(){
         SundayEight1Tv=(TextView)findViewById(R.id.tvSundayEight1);
         SundayEight2Tv=(TextView)findViewById(R.id.tvSundayEight2);
         SundayEight3Tv=(TextView)findViewById(R.id.tvSundayEight3);
         SundayEight4Tv=(TextView)findViewById(R.id.tvSundayEight4);
        SundayNine1Tv=(TextView)findViewById(R.id.tvSundayNine1);
         SundayNine2Tv=(TextView)findViewById(R.id.tvSundayNine2);
         SundayNine3Tv=(TextView)findViewById(R.id.tvSundayNine3);
         SundayNine4Tv=(TextView)findViewById(R.id.tvSundayNine4);
         SundayTen1Tv=(TextView)findViewById(R.id.tvSundayTen1);
         SundayTen2Tv=(TextView)findViewById(R.id.tvSundayTen2);
        SundayTen3Tv=(TextView)findViewById(R.id.tvSundayTen3);
         SundayTen4Tv=(TextView)findViewById(R.id.tvSundayTen4);
        SundayEleven1Tv=(TextView)findViewById(R.id.tvSundayEleven1);
         SundayEleven2Tv=(TextView)findViewById(R.id.tvSundayEleven2);
         SundayEleven3Tv=(TextView)findViewById(R.id.tvSundayEleven3);
       SundayEleven4Tv=(TextView)findViewById(R.id.tvSundayEleven4);
         SundayTwelve1Tv=(TextView)findViewById(R.id.tvSundayTwelve1);
         SundayTwelve2Tv=(TextView)findViewById(R.id.tvSundayTwelve2);
         SundayTwelve3Tv=(TextView)findViewById(R.id.tvSundayTwelve3);
         SundayTwelve4Tv=(TextView)findViewById(R.id.tvSundayTwelve4);
        SundayTwo1Tv=(TextView)findViewById(R.id.tvSundayTwo1);
         SundayTwo2Tv=(TextView)findViewById(R.id.tvSundayTwo2);
        SundayTwo3Tv=(TextView)findViewById(R.id.tvSundayTwo3);
         SundayTwo4Tv=(TextView)findViewById(R.id.tvSundayTwo4);
        SundayThree1Tv=(TextView)findViewById(R.id.tvSundayThree1);
         SundayThree2Tv=(TextView)findViewById(R.id.tvSundayThree2);
         SundayThree3Tv=(TextView)findViewById(R.id.tvSundayThree3);
         SundayThree4Tv=(TextView)findViewById(R.id.tvSundayThree4);
         SundayFour1Tv=(TextView)findViewById(R.id.tvSundayFour1);
         SundayFour2Tv=(TextView)findViewById(R.id.tvSundayFour2);
        SundayFour3Tv=(TextView)findViewById(R.id.tvSundayFour3);
         SundayFour4Tv=(TextView)findViewById(R.id.tvSundayFour4);
        mondayEight1Tv=(TextView)findViewById(R.id.tvMondayEight1);
        mondayEight2Tv=(TextView)findViewById(R.id.tvMondayEight2);
        mondayEight3Tv=(TextView)findViewById(R.id.tvMondayEight3);
        mondayEight4Tv=(TextView)findViewById(R.id.tvMondayEight4);
        mondayNine1Tv=(TextView)findViewById(R.id.tvMondayNine1);
        mondayNine2Tv=(TextView)findViewById(R.id.tvMondayNine2);
        mondayNine3Tv=(TextView)findViewById(R.id.tvMondayNine3);
        mondayNine4Tv=(TextView)findViewById(R.id.tvMondayNine4);
        mondayTen1Tv=(TextView)findViewById(R.id.tvMondayTen1);
        mondayTen2Tv=(TextView)findViewById(R.id.tvMondayTen2);
        mondayTen3Tv=(TextView)findViewById(R.id.tvMondayTen3);
        mondayTen4Tv=(TextView)findViewById(R.id.tvMondayTen4);
        mondayEleven1Tv=(TextView)findViewById(R.id.tvMondayEleven1);
        mondayEleven2Tv=(TextView)findViewById(R.id.tvMondayEleven2);
        mondayEleven3Tv=(TextView)findViewById(R.id.tvMondayEleven3);
        mondayEleven4Tv=(TextView)findViewById(R.id.tvMondayEleven4);
        mondayTwelve1Tv=(TextView)findViewById(R.id.tvMondayTwelve1);
        mondayTwelve2Tv=(TextView)findViewById(R.id.tvMondayTwelve2);
        mondayTwelve3Tv=(TextView)findViewById(R.id.tvMondayTwelve3);
        mondayTwelve4Tv=(TextView)findViewById(R.id.tvMondayTwelve4);
        mondayTwo1Tv=(TextView)findViewById(R.id.tvMondayTwo1);
        mondayTwo2Tv=(TextView)findViewById(R.id.tvMondayTwo2);
        mondayTwo3Tv=(TextView)findViewById(R.id.tvMondayTwo3);
        mondayTwo4Tv=(TextView)findViewById(R.id.tvMondayTwo4);
        mondayThree1Tv=(TextView)findViewById(R.id.tvMondayThree1);
        mondayThree2Tv=(TextView)findViewById(R.id.tvMondayThree2);
        mondayThree3Tv=(TextView)findViewById(R.id.tvMondayThree3);
        mondayThree4Tv=(TextView)findViewById(R.id.tvMondayThree4);
        mondayFour1Tv=(TextView)findViewById(R.id.tvMondayFour1);
        mondayFour2Tv=(TextView)findViewById(R.id.tvMondayFour2);
        mondayFour3Tv=(TextView)findViewById(R.id.tvMondayFour3);
        mondayFour4Tv=(TextView)findViewById(R.id.tvMondayFour4);
        tuesdayEight1Tv=(TextView)findViewById(R.id.tvTuesdayEight1);
        tuesdayEight2Tv=(TextView)findViewById(R.id.tvTuesdayEight2);
        tuesdayEight3Tv=(TextView)findViewById(R.id.tvTuesdayEight3);
        tuesdayEight4Tv=(TextView)findViewById(R.id.tvTuesdayEight4);
        tuesdayNine1Tv=(TextView)findViewById(R.id.tvTuesdayNine1);
        tuesdayNine2Tv=(TextView)findViewById(R.id.tvTuesdayNine2);
        tuesdayNine3Tv=(TextView)findViewById(R.id.tvTuesdayNine3);
        tuesdayNine4Tv=(TextView)findViewById(R.id.tvTuesdayNine4);
        tuesdayTen1Tv=(TextView)findViewById(R.id.tvTuesdayTen1);
        tuesdayTen2Tv=(TextView)findViewById(R.id.tvTuesdayTen2);
        tuesdayTen3Tv=(TextView)findViewById(R.id.tvTuesdayTen3);
        tuesdayTen4Tv=(TextView)findViewById(R.id.tvTuesdayTen4);
        tuesdayEleven1Tv=(TextView)findViewById(R.id.tvTuesdayEleven1);
        tuesdayEleven2Tv=(TextView)findViewById(R.id.tvTuesdayEleven2);
        tuesdayEleven3Tv=(TextView)findViewById(R.id.tvTuesdayEleven3);
        tuesdayEleven4Tv=(TextView)findViewById(R.id.tvTuesdayEleven4);
        tuesdayTwelve1Tv=(TextView)findViewById(R.id.tvTuesdayTwelve1);
        tuesdayTwelve2Tv=(TextView)findViewById(R.id.tvTuesdayTwelve2);
        tuesdayTwelve3Tv=(TextView)findViewById(R.id.tvTuesdayTwelve3);
        tuesdayTwelve4Tv=(TextView)findViewById(R.id.tvTuesdayTwelve4);
        tuesdayTwo1Tv=(TextView)findViewById(R.id.tvTuesdayTwo1);
        tuesdayTwo2Tv=(TextView)findViewById(R.id.tvTuesdayTwo2);
        tuesdayTwo3Tv=(TextView)findViewById(R.id.tvTuesdayTwo3);
        tuesdayTwo4Tv=(TextView)findViewById(R.id.tvTuesdayTwo4);
        tuesdayThree1Tv=(TextView)findViewById(R.id.tvTuesdayThree1);
        tuesdayThree2Tv=(TextView)findViewById(R.id.tvTuesdayThree2);
        tuesdayThree3Tv=(TextView)findViewById(R.id.tvTuesdayThree3);
        tuesdayThree4Tv=(TextView)findViewById(R.id.tvTuesdayThree4);
        tuesdayFour1Tv=(TextView)findViewById(R.id.tvTuesdayFour1);
        tuesdayFour2Tv=(TextView)findViewById(R.id.tvTuesdayFour2);
        tuesdayFour3Tv=(TextView)findViewById(R.id.tvTuesdayFour3);
        tuesdayFour4Tv=(TextView)findViewById(R.id.tvTuesdayFour4);
        wednesdayEight1Tv=(TextView)findViewById(R.id.tvWednesdayEight1);
        wednesdayEight2Tv=(TextView)findViewById(R.id.tvWednesdayEight2);
        wednesdayEight3Tv=(TextView)findViewById(R.id.tvWednesdayEight3);
        wednesdayEight4Tv=(TextView)findViewById(R.id.tvWednesdayEight4);
        wednesdayNine1Tv=(TextView)findViewById(R.id.tvWednesdayNine1);
        wednesdayNine2Tv=(TextView)findViewById(R.id.tvWednesdayNine2);
        wednesdayNine3Tv=(TextView)findViewById(R.id.tvWednesdayNine3);
        wednesdayNine4Tv=(TextView)findViewById(R.id.tvWednesdayNine4);
        wednesdayTen1Tv=(TextView)findViewById(R.id.tvWednesdayTen1);
        wednesdayTen2Tv=(TextView)findViewById(R.id.tvWednesdayTen2);
        wednesdayTen3Tv=(TextView)findViewById(R.id.tvWednesdayTen3);
        wednesdayTen4Tv=(TextView)findViewById(R.id.tvWednesdayTen4);
        wednesdayEleven1Tv=(TextView)findViewById(R.id.tvWednesdayEleven1);
        wednesdayEleven2Tv=(TextView)findViewById(R.id.tvWednesdayEleven2);
        wednesdayEleven3Tv=(TextView)findViewById(R.id.tvWednesdayEleven3);
        wednesdayEleven4Tv=(TextView)findViewById(R.id.tvWednesdayEleven4);
        wednesdayTwelve1Tv=(TextView)findViewById(R.id.tvWednesdayTwelve1);
        wednesdayTwelve2Tv=(TextView)findViewById(R.id.tvWednesdayTwelve2);
        wednesdayTwelve3Tv=(TextView)findViewById(R.id.tvWednesdayTwelve3);
        wednesdayTwelve4Tv=(TextView)findViewById(R.id.tvWednesdayTwelve4);
        wednesdayTwo1Tv=(TextView)findViewById(R.id.tvWednesdayTwo1);
        wednesdayTwo2Tv=(TextView)findViewById(R.id.tvWednesdayTwo2);
        wednesdayTwo3Tv=(TextView)findViewById(R.id.tvWednesdayTwo3);
        wednesdayTwo4Tv=(TextView)findViewById(R.id.tvWednesdayTwo4);
        wednesdayThree1Tv=(TextView)findViewById(R.id.tvWednesdayThree1);
        wednesdayThree2Tv=(TextView)findViewById(R.id.tvWednesdayThree2);
        wednesdayThree3Tv=(TextView)findViewById(R.id.tvWednesdayThree3);
        wednesdayThree4Tv=(TextView)findViewById(R.id.tvWednesdayThree4);
        wednesdayFour1Tv=(TextView)findViewById(R.id.tvWednesdayFour1);
        wednesdayFour2Tv=(TextView)findViewById(R.id.tvWednesdayFour2);
        wednesdayFour3Tv=(TextView)findViewById(R.id.tvWednesdayFour3);
        wednesdayFour4Tv=(TextView)findViewById(R.id.tvWednesdayFour4);
        thursdayEight1Tv=(TextView)findViewById(R.id.tvThursdayEight1);
        thursdayEight2Tv=(TextView)findViewById(R.id.tvThursdayEight2);
        thursdayEight3Tv=(TextView)findViewById(R.id.tvThursdayEight3);
        thursdayEight4Tv=(TextView)findViewById(R.id.tvThursdayEight4);
        thursdayNine1Tv=(TextView)findViewById(R.id.tvThursdayNine1);
        thursdayNine2Tv=(TextView)findViewById(R.id.tvThursdayNine2);
        thursdayNine3Tv=(TextView)findViewById(R.id.tvThursdayNine3);
        thursdayNine4Tv=(TextView)findViewById(R.id.tvThursdayNine4);
        thursdayTen1Tv=(TextView)findViewById(R.id.tvThursdayTen1);
        thursdayTen2Tv=(TextView)findViewById(R.id.tvThursdayTen2);
        thursdayTen3Tv=(TextView)findViewById(R.id.tvThursdayTen3);
        thursdayTen4Tv=(TextView)findViewById(R.id.tvThursdayTen4);
        thursdayEleven1Tv=(TextView)findViewById(R.id.tvThursdayEleven1);
        thursdayEleven2Tv=(TextView)findViewById(R.id.tvThursdayEleven2);
        thursdayEleven3Tv=(TextView)findViewById(R.id.tvThursdayEleven3);
        thursdayEleven4Tv=(TextView)findViewById(R.id.tvThursdayEleven4);
        thursdayTwelve1Tv=(TextView)findViewById(R.id.tvThursdayTwelve1);
        thursdayTwelve2Tv=(TextView)findViewById(R.id.tvThursdayTwelve2);
        thursdayTwelve3Tv=(TextView)findViewById(R.id.tvThursdayTwelve3);
        thursdayTwelve4Tv=(TextView)findViewById(R.id.tvThursdayTwelve4);
        thursdayTwo1Tv=(TextView)findViewById(R.id.tvThursdayTwo1);
        thursdayTwo2Tv=(TextView)findViewById(R.id.tvThursdayTwo2);
        thursdayTwo3Tv=(TextView)findViewById(R.id.tvThursdayTwo3);
        thursdayTwo4Tv=(TextView)findViewById(R.id.tvThursdayTwo4);
        thursdayThree1Tv=(TextView)findViewById(R.id.tvThursdayThree1);
        thursdayThree2Tv=(TextView)findViewById(R.id.tvThursdayThree2);
        thursdayThree3Tv=(TextView)findViewById(R.id.tvThursdayThree3);
        thursdayThree4Tv=(TextView)findViewById(R.id.tvThursdayThree4);
        thursdayFour1Tv=(TextView)findViewById(R.id.tvThursdayFour1);
        thursdayFour2Tv=(TextView)findViewById(R.id.tvThursdayFour2);
        thursdayFour3Tv=(TextView)findViewById(R.id.tvThursdayFour3);
        thursdayFour4Tv=(TextView)findViewById(R.id.tvThursdayFour4);

    }



    private void showProgressDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void previousFlipper(View view){

        viewFlipper.setInAnimation(slide_in_right);
        viewFlipper.setOutAnimation(slide_out_left);
        viewFlipper.showPrevious();
    }
    public void nextFlipper(View view){

        viewFlipper.setInAnimation(slide_in_left);
        viewFlipper.setOutAnimation(slide_out_right);
        viewFlipper.showNext();
    }
}
