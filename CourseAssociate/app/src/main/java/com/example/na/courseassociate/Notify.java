package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notify extends Activity {
    ProgressDialog progressDialog;
    Spinner spinner;
    String userType;
    String userId;
    String semisterId;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<String> courseCodeList;
    ArrayList<String> courseIdList;
    ArrayList<String> courseTitleList;
    String courseId;
    Button cancel;
    Button notify;
    EditText notifyString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.give_notification);
        spinner=(Spinner)findViewById(R.id.spCourseList);
        notify=(Button)findViewById(R.id.btNotify);
        cancel=(Button)findViewById(R.id.btCancel);
        notifyString=(EditText)findViewById(R.id.etNotificationText);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        Bundle bundle=getIntent().getExtras();
        userType=bundle.getString("user_type");
        userId=bundle.getString("user_id");
        semisterId=bundle.getString("semister_id");
        requestQueue= Volley.newRequestQueue(this);
        courseCodeList =new ArrayList<String>();
        courseIdList =new ArrayList<String>();
        courseTitleList =new ArrayList<String>();
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int viewPosition=spinner.getSelectedItemPosition();
                courseId= courseIdList.get(viewPosition);
                Toast.makeText(getApplicationContext(),courseTitleList.get(viewPosition),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyPost();

            }
        });

        // loading spinner values...........

        loadSpinnerCourseItem();
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
                            ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>
                                    (getApplicationContext(),R.layout.notify_course_list_item,R.id.tvCourseListItem,courseCodeList );
                            spinner.setAdapter(spinnerAdapter);
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

    private void notifyPost() {

        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/notify.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(),"Successfully Notified......",Toast.LENGTH_LONG).show();
                Log.v("look at response", response.toString());
                finish();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideProgressDialog();
                error.printStackTrace();
            }
        }){

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("teacher_id", userId);
                params.put("course_id", courseId);
                params.put("status_details",notifyString.getText().toString());
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
    public void cancelNotify(View view){
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