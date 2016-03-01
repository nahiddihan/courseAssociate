package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

public class Profile extends Activity{
    String user_id;
    String userType;
    TextView teacherVarsityNameTv;TextView studentVarsityNameTv;
    TextView teacherNameTv; TextView studentNameTv;
    TextView teacherEmailTv; TextView StudentEmailTv;
    TextView designationTv; TextView teacherMobileTv;
    TextView teacherDepartmentTv; TextView studentDepartmentTv;
    TextView sessionTv; TextView yearTv; TextView termTv;
    TextView rollTv;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    HashMap<String,String> profileHashMap;
    LocalDataBaseHelper localDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
        internetConnection= networkStatusChecker.isConnectedToNetwork();

        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());


        profileHashMap=new HashMap<String,String>();

        Bundle bundle=getIntent().getExtras();
        user_id=bundle.getString("user_id");
        userType=bundle.getString("user_type");


        if(userType.equalsIgnoreCase("student")){
            setContentView(R.layout.student_profile);
        }else{
            setContentView(R.layout.teacher_profile);
        }
        initializeViews();

       requestQueue= Volley.newRequestQueue(this);

        if (internetConnection==true){
            if(userType.equalsIgnoreCase("student")){
                getStudentProfileInformation();
            }else{
                getTeacherProfileInformation();
            }
        }else{
            if(userType.equalsIgnoreCase("student")){
                profileHashMap=localDataBaseHelper.getStudentInfo(Integer.parseInt(user_id));
                if(profileHashMap.isEmpty()==false){

                    studentNameTv.setText(profileHashMap.get("student_name"));
                    studentVarsityNameTv.setText(profileHashMap.get("varsity_title"));
                    StudentEmailTv.setText("Email : "+profileHashMap.get("student_email"));
                    studentDepartmentTv.setText("Department of "+profileHashMap.get("department_title"));
                    rollTv.setText("Roll : "+profileHashMap.get("roll"));
                    sessionTv.setText("Session : "+profileHashMap.get("session"));
                    Integer semisterNo=Integer.parseInt(profileHashMap.get("semister_no"));
                    Integer term=(semisterNo%2==0)?2:1;
                    if(semisterNo==1){
                        termTv.setText("Term : "+semisterNo);
                    }else{
                        termTv.setText("Term : "+term);
                    }
                    yearTv.setText("Year : "+(semisterNo/2+semisterNo%2));
                }
            }else{
                profileHashMap=localDataBaseHelper.getTeacherInfo(Integer.parseInt(user_id));
                teacherVarsityNameTv.setText(profileHashMap.get("varsity_title"));
                teacherDepartmentTv.setText("Department of "+profileHashMap.get("department_title"));
                teacherNameTv.setText(profileHashMap.get("teacher_name"));
                teacherMobileTv.setText(profileHashMap.get("teacher_mobile"));
                teacherEmailTv.setText(profileHashMap.get("teacher_email"));
                designationTv.setText(profileHashMap.get("designation"));
            }
        }

    }

    private void getTeacherProfileInformation() {
        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/teacher_profile.php?teacher_id="+user_id;

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("profile");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String teacherName=jsonObject.getString("teacher_name");
                    String designation=jsonObject.getString("designation");
                    String email=jsonObject.getString("teacher_email");
                    String mobile=jsonObject.getString("teacher_mobile");
                    String departmentTitle=jsonObject.getString("department_title");
                    String varsityName=jsonObject.getString("varsity_title");
                    teacherDepartmentTv.setText("Department of "+departmentTitle);
                    teacherNameTv.setText(teacherName);
                    teacherMobileTv.setText(mobile);
                    teacherEmailTv.setText(email);
                    designationTv.setText(designation);
                    teacherVarsityNameTv.setText(varsityName);

                    profileHashMap=localDataBaseHelper.getTeacherInfo(Integer.parseInt(user_id));

                    if (profileHashMap.isEmpty()==true){
                        localDataBaseHelper.insertTeacherInfo(Integer.parseInt(user_id),
                                teacherName,designation,email,mobile,departmentTitle,varsityName);
                    }else{
                        localDataBaseHelper.updateTeacherInfo(Integer.parseInt(user_id),
                                teacherName,designation,email,mobile,departmentTitle,varsityName);
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
    private void getStudentProfileInformation() {
        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/student_profile.php?student_id="+user_id;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("profile");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String studentName=jsonObject.getString("student_name");
                    String varsityTitle=jsonObject.getString("varsity_title");
                    String email=jsonObject.getString("student_email");
                    String departmentTitle=jsonObject.getString("department_title");
                    String roll=jsonObject.getString("roll");
                    String session=jsonObject.getString("session");
                    studentNameTv.setText(studentName);
                    studentVarsityNameTv.setText(varsityTitle);
                    StudentEmailTv.setText("Email : "+email);
                    studentDepartmentTv.setText("Department of "+departmentTitle);
                    rollTv.setText("Roll : "+roll);
                    sessionTv.setText("Session : "+jsonObject.getString("session"));
                    Integer semisterNo=jsonObject.getInt("semister_no");
                    Integer term=(semisterNo%2==0)?2:1;
                    if(semisterNo==1){
                        termTv.setText("Term : "+semisterNo);
                    }else{
                        termTv.setText("Term : "+term);
                    }
                    yearTv.setText("Year : "+(semisterNo/2+semisterNo%2));

                    profileHashMap=localDataBaseHelper.getStudentInfo(Integer.parseInt(user_id));
                    if (profileHashMap.isEmpty()==true){
                        localDataBaseHelper.insertStudentInfo(Integer.parseInt(user_id),studentName,roll,email,departmentTitle,semisterNo,varsityTitle,session);
                    }else{
                        localDataBaseHelper.updateStudentInfo(Integer.parseInt(user_id),studentName,roll,email,departmentTitle,semisterNo,varsityTitle,session);

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

    public void initializeViews(){
         teacherVarsityNameTv=(TextView)findViewById(R.id.tvTeacherVarsityName);
         studentVarsityNameTv=(TextView)findViewById(R.id.tvStudentVarsityName);
         teacherNameTv=(TextView)findViewById(R.id.tvTeacherName);
         studentNameTv=(TextView)findViewById(R.id.tvStudentName);
         teacherEmailTv=(TextView)findViewById(R.id.tvTeacherEmail);
         StudentEmailTv=(TextView)findViewById(R.id.tvStudentEmail);
         designationTv=(TextView)findViewById(R.id.tvDesignation);
         teacherMobileTv=(TextView)findViewById(R.id.tvTeacherMobile);
         teacherDepartmentTv=(TextView)findViewById(R.id.tvTeacherDepartment);
         studentDepartmentTv=(TextView)findViewById(R.id.tvStudentDepartment);
         sessionTv=(TextView)findViewById(R.id.tvSession);
         yearTv=(TextView)findViewById(R.id.tvYear);
         termTv=(TextView)findViewById(R.id.tvTerm);
         rollTv=(TextView)findViewById(R.id.tvRoll);
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