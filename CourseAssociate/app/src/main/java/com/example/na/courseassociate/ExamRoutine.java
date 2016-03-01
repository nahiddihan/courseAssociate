package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;


public class ExamRoutine extends Activity {
    TextView dateOne;TextView dateOneAM;TextView dateOnePM;
    TextView dateTwo;TextView dateTwoAM;TextView dateTwoPM;
    TextView dateThree;TextView dateThreeAM;TextView dateThreePM;
    TextView dateFour;TextView dateFourAM;TextView dateFourPM;
    TextView dateFive;TextView dateFiveAM;TextView dateFivePM;
    TextView dateSix;TextView dateSixAM;TextView dateSixPM;
    TextView dateSeven;TextView dateSevenAM;TextView dateSevenPM;
    String semisterId;
    String userType;
    String userId;
    String url;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    LocalDataBaseHelper localDataBaseHelper;
    ArrayList<HashMap<String, String>> finalExamLocalDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_routine);
        initialize();
        Bundle bundle=getIntent().getExtras();
        userId=bundle.getString("user_id");
        userType=bundle.getString("user_type");
        semisterId=bundle.getString("semister_id");
        requestQueue= Volley.newRequestQueue(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);

        networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
        internetConnection= networkStatusChecker.isConnectedToNetwork();
        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        finalExamLocalDataList=new ArrayList<HashMap<String, String>>();

        if(userType.equalsIgnoreCase("student")){
            url="http://nahiddihanbd.comlu.com/course_associate/student_exam_routine.php?semister_id="+semisterId;
        }else{
            url="http://nahiddihanbd.comlu.com/course_associate/teacher_exam_routine.php?teacher_id="+userId;
        }

        if (internetConnection==true){
            getExamRoutine();
        }else{
            finalExamLocalDataList=localDataBaseHelper.getFinalExamInfo();
            if (finalExamLocalDataList.isEmpty()==false){
                for(int i=0;i<finalExamLocalDataList.size();i++){
                    String localDataCourseCode=finalExamLocalDataList.get(i).get("course_code");
                    String localDataDate=finalExamLocalDataList.get(i).get("date");
                    String localDataTime=finalExamLocalDataList.get(i).get("time");
                    if (userType.equalsIgnoreCase("student")){
                        getStudentExamRoutine(i,localDataDate,localDataTime,localDataCourseCode);
                    }else{
                        getTeacherExamRoutine(i,localDataDate,localDataTime,localDataCourseCode);
                    }
                }

            }
        }
    }

    private void getExamRoutine() {
       showProgressDialog();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    finalExamLocalDataList=localDataBaseHelper.getFinalExamInfo();
                    if (finalExamLocalDataList.isEmpty()==false){
                        localDataBaseHelper.deleteFinalExamInfo();
                    }
                    if(response.getInt("success")==1){
                        if(userType.equalsIgnoreCase("student")) {
                            JSONArray jsonArray=response.getJSONArray("student_exam_routine");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String date=jsonObject.getString("exam_date");
                                String courseCode = jsonObject.getString("course_code");
                                String time = jsonObject.getString("exam_time");
                                localDataBaseHelper.insertFinalExam(courseCode,date,time);
                                getStudentExamRoutine(i,date,time,courseCode);
                            }

                        }else{
                            JSONArray jsonArray=response.getJSONArray("teacher_exam_routine");

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String date=jsonObject.getString("exam_date");
                                String courseCode = jsonObject.getString("course_code");
                                String time = jsonObject.getString("exam_time");
                                localDataBaseHelper.insertFinalExam(courseCode,date,time);
                                getTeacherExamRoutine(i,date,time,courseCode);
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

                hideProgressDialog();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }


    public void getStudentExamRoutine(int i,String date,String time,String courseCode){
    switch(i){
        case 0:
            dateOne.setText(date);
            if(time.equalsIgnoreCase("morning")){
                dateOneAM.setText(courseCode);
            }else{
               dateOnePM.setText(courseCode);
            }
            break;
        case 1:
            dateTwo.setText(date);
            if(time.equalsIgnoreCase("morning")){
                dateTwoAM.setText(courseCode);
            }else{
                dateTwoPM.setText(courseCode);
            }
            break;
        case 2:
            dateThree.setText(date);
            if(time.equalsIgnoreCase("morning")){
                dateThreeAM.setText(courseCode);
            }else{
                dateThreePM.setText(courseCode);
            }
            break;
        case 3:
            dateFour.setText(date);
            if(time.equalsIgnoreCase("morning")){
                dateFourAM.setText(courseCode);
            }else{
                dateFourPM.setText(courseCode);
            }
            break;
        case 4:
            dateFive.setText(date);
            if(time.equalsIgnoreCase("morning")){
                dateFiveAM.setText(courseCode);
            }else{
                dateFivePM.setText(courseCode);
            }
            break;
        case 5:
            dateSix.setText(date);
            if(time.equalsIgnoreCase("morning")){
                dateSixAM.setText(courseCode);
            }else{
                dateSixPM.setText(courseCode);
            }
            break;
        case 6:
            dateSeven.setText(date);
            if(time.equalsIgnoreCase("morning")){
                dateSevenAM.setText(courseCode);
            }else{
                dateSevenPM.setText(courseCode);
            }
            break;
        case 7:
            break;

    }
}

    public void getTeacherExamRoutine(int i,String date,String time,String courseCode){
        switch(i){
            case 0:
                dateOne.setText(date);
                if(time.equalsIgnoreCase("morning")){
                    dateOneAM.setText(courseCode);
                }else{
                    dateOnePM.setText(courseCode);
                }
                break;
            case 1:
                dateTwo.setText(date);
                if(time.equalsIgnoreCase("morning")){
                    dateTwoAM.setText(courseCode);
                }else{
                    dateTwoPM.setText(courseCode);
                }
                break;
            case 2:
                dateThree.setText(date);
                if(time.equalsIgnoreCase("morning")){
                    dateThreeAM.setText(courseCode);
                }else{
                    dateThreePM.setText(courseCode);
                }
                break;
            case 3:
                dateFour.setText(date);
                if(time.equalsIgnoreCase("morning")){
                    dateFourAM.setText(courseCode);
                }else{
                    dateFourPM.setText(courseCode);
                }
                break;
            case 4:
                dateFive.setText(date);
                if(time.equalsIgnoreCase("morning")){
                    dateFiveAM.setText(courseCode);
                }else{
                    dateFivePM.setText(courseCode);
                }
                break;
            case 5:
                dateSix.setText(date);
                if(time.equalsIgnoreCase("morning")){
                    dateSixAM.setText(courseCode);
                }else{
                    dateSixPM.setText(courseCode);
                }
                break;
            case 6:
                dateSeven.setText(date);
                if(time.equalsIgnoreCase("morning")){
                    dateSevenAM.setText(courseCode);
                }else{
                    dateSevenPM.setText(courseCode);
                }
                break;
            case 8:
                break;

        }
    }
    private void initialize() {
         dateOne=(TextView)findViewById(R.id.tvDateOne);
         dateOneAM=(TextView)findViewById(R.id.tvDateOneAM);
         dateOnePM=(TextView)findViewById(R.id.tvDateOnePM);
        dateTwo=(TextView)findViewById(R.id.tvDAteTwo);
         dateTwoAM=(TextView)findViewById(R.id.tvDAteTwoAM);
        dateTwoPM=(TextView)findViewById(R.id.tvDateTwoPM);
        dateThree=(TextView)findViewById(R.id.tvDateThree);
         dateThreeAM=(TextView)findViewById(R.id.tvDateThreeAM);
         dateThreePM=(TextView)findViewById(R.id.tvDateThreePM);
         dateFour=(TextView)findViewById(R.id.tvDateFour);
        dateFourAM=(TextView)findViewById(R.id.tvDateFourAM);
         dateFourPM=(TextView)findViewById(R.id.tvDateFourPM);
        dateFive=(TextView)findViewById(R.id.tvDateFive);
         dateFiveAM=(TextView)findViewById(R.id.tvDateFiveAM);
         dateFivePM=(TextView)findViewById(R.id.tvDateFivePM);
         dateSix=(TextView)findViewById(R.id.tvDateSix);
         dateSixAM=(TextView)findViewById(R.id.tvDateSixAM);
         dateSixPM=(TextView)findViewById(R.id.tvDateSixPM);
         dateSeven=(TextView)findViewById(R.id.tvDateSeven);
         dateSevenAM=(TextView)findViewById(R.id.tvDateSevenAM);
         dateSevenPM=(TextView)findViewById(R.id.tvDateSevenPM);

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
