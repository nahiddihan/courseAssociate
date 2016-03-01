package com.example.na.courseassociate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewClassTest extends Activity{

    String userId;
    String userType="teacher";
    String courseId;
    Spinner spinner;
    ProgressDialog progressDialog;
    ArrayList<String> courseCodeList;
    ArrayList<String> courseIdList;
    ArrayList<String> courseTitleList;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ArrayAdapter<String> spinnerAdapter;
    Button btDate;Button btDone;
    Button btTime;
    EditText etTopics;
    TextView tvTopTitle;
    Boolean update=false;
    Bundle bundle;

    String courseCode;
    String classTestId;
    String date;
    String time;
    String topics;
    SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_class_test);
        spinner=(Spinner)findViewById(R.id.spClassTestCourseList);
        btDate=(Button)findViewById(R.id.btCTDate);
        btTime=(Button)findViewById(R.id.btCTTime);
        etTopics=(EditText)findViewById(R.id.etCTTopics);
        tvTopTitle=(TextView)findViewById(R.id.tvNewClassTestTitle);
        btDone=(Button)findViewById(R.id.btDone);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        bundle=getIntent().getExtras();
        update=bundle.getBoolean("update");

        if(update==false){
            userId=getIntent().getStringExtra("user_id");
            userType=getIntent().getStringExtra("user_type");
        }else{
            userId=bundle.getString("teacher_id");
            date=bundle.getString("date");
            time=bundle.getString("time");
            topics=bundle.getString("topics");
            classTestId=bundle.getString("class_test_id");
            tvTopTitle.setText("Update Class Test");
            btDone.setText("Update");
            spinner.setEnabled(false);
            btDate.setText(date);
            btTime.setText(time);
            etTopics.setText(topics);
        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue= Volley.newRequestQueue(this);
        courseCodeList =new ArrayList<String>();
        courseIdList =new ArrayList<String>();
        courseTitleList =new ArrayList<String>();
        loadSpinnerCourseItem();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int viewPosition = spinner.getSelectedItemPosition();
                courseId = courseIdList.get(viewPosition);
                Toast.makeText(getApplicationContext(), courseTitleList.get(viewPosition), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void loadSpinnerCourseItem() {
        showProgressDialog();
        if(userType.equalsIgnoreCase("teacher")){
            String url="http://nahiddihanbd.comlu.com/course_associate/teacher_course.php?teacher_id="+userId;
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getInt("success")==1){
                            JSONArray jsonArray=response.getJSONArray("teacher_course");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String courseId = jsonObject.getString("course_id");
                                String courseCode = jsonObject.getString("course_code");
                                String courseTitle = jsonObject.getString("course_title");
                                courseCodeList.add(courseCode);
                                courseIdList.add(courseId);
                                courseTitleList.add(courseTitle);
                            }
                            spinnerAdapter=new ArrayAdapter<String>
                                    (getApplicationContext(),R.layout.spinner_course_code_list,R.id.tvSpinnerCourseCode,courseCodeList);
                            spinner.setAdapter(spinnerAdapter);
                            if(update==true){
                                int spinnerPosition= spinnerAdapter.getPosition(courseCode);
                                spinner.setSelection(spinnerPosition);
                                spinner.setEnabled(false);
                            }
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
        }
        requestQueue.add(jsonObjectRequest);
    }

    public void getTime(View view){

        Calendar calender = Calendar.getInstance();
        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int minute = calender.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(NewClassTest.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String AmPm="";
                int hour;
                if(selectedHour >0 && selectedHour<12 ){
                    AmPm="AM";
                    hour=selectedHour;
                }else {
                    AmPm = "PM";
                    hour=selectedHour-12;
                }
                btTime.setText( hour + ":" + selectedMinute+"  "+AmPm);
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    public void getDate(View view){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                btDate.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void saveNewClassTest(View view){
        if(update==true){
            // update class test
            showProgressDialog();
            String url="http://nahiddihanbd.comlu.com/course_associate/update_class_test.php";
            StringRequest stringRequest=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                    Log.v("look at response", response.toString());
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideProgressDialog();

                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String>  params = new HashMap<>();
                    // the POST parameters:
                    params.put("teacher_id", userId);
                    params.put("course_id", courseId);
                    params.put("class_test_id",classTestId);
                    params.put("topics",etTopics.getText().toString());
                    params.put("date",btDate.getText().toString());
                    params.put("time",btTime.getText().toString());
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }else
        {
        // Add new class test
            showProgressDialog();
            String url="http://nahiddihanbd.comlu.com/course_associate/new_class_test.php";
            StringRequest stringRequest=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(),"Successfully Done ",Toast.LENGTH_LONG).show();
                    Log.v("look at response", response.toString());
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideProgressDialog();

                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String>  params = new HashMap<>();
                    // the POST parameters:
                    params.put("teacher_id", userId);
                    params.put("course_id", courseId);
                    params.put("topics",etTopics.getText().toString());
                    params.put("date",btDate.getText().toString());
                    params.put("time",btTime.getText().toString());
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
    public void cancelButtonAction(View view){
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
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
