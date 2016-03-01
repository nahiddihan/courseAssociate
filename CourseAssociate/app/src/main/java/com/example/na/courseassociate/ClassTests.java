package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class ClassTests extends Activity {
    String userId;
    String userType;
    String semisterId;
    ImageView newClassTestIv;
    ListView classTestListView;
    ArrayList<HashMap<String, String>> classTestsList;
    HashMap<String, String> classTestHashMap;
    RequestQueue requestQueue;
    String url;
    ProgressDialog progressDialog;
    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    ArrayList<HashMap<String, String>> classTestLocalDataList;
    LocalDataBaseHelper localDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_test);
        classTestListView=(ListView)findViewById(R.id.lvClassTest);
        newClassTestIv=(ImageView)findViewById(R.id.ivNewCT);
        requestQueue= Volley.newRequestQueue(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);

        classTestsList =new ArrayList<HashMap<String, String>>();
        Bundle bundle=getIntent().getExtras();
        userId=bundle.getString("user_id");
        userType=bundle.getString("user_type");
        semisterId=bundle.getString("semister_id");

        if(userType.equalsIgnoreCase("teacher")){
            newClassTestIv.setVisibility(View.VISIBLE);
        }
        networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
        internetConnection= networkStatusChecker.isConnectedToNetwork();
        localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        classTestLocalDataList=new ArrayList<HashMap<String, String>>();

        if (internetConnection==true){

            if(userType.equalsIgnoreCase("student")){
                url="http://nahiddihanbd.comlu.com/course_associate/student_class_test.php?semister_id="+semisterId;
            }else{
                url="http://nahiddihanbd.comlu.com/course_associate/teacher_class_test.php?teacher_id="+userId;
            }
            getClassTestsList();

        }else{
            classTestLocalDataList=localDataBaseHelper.getClassTestInfo();
            if (classTestLocalDataList.isEmpty()==false){
                ListAdapter adapter = new SimpleAdapter(
                        ClassTests.this, classTestLocalDataList,
                        R.layout.class_test_list_item, new String[] { "course_title", "date","time","given_time","given_date","topics"},
                        new int[] { R.id.tvClassTestTitle, R.id.tvClassTestDate,R.id.tvClassTestTime,R.id.tvClassTestGivenTime,R.id.tvClassTestGivenDate,R.id.tvClassTestDetails});

                classTestListView.setAdapter(adapter);
            }
        }
        classTestListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("entered","first");
                if (userType.equalsIgnoreCase("teacher")) {
                    String date = classTestsList.get(position).get("date");
                    String time = classTestsList.get(position).get("time");
                    String topics = classTestsList.get(position).get("topics");
                    String course_code = classTestsList.get(position).get("course_code");
                    String classTestId = classTestsList.get(position).get("class_test_id");
                    Bundle updateClassTestBundle = new Bundle();
                    updateClassTestBundle.putString("course_code", course_code);
                    updateClassTestBundle.putString("date", date);
                    updateClassTestBundle.putString("time", time);
                    updateClassTestBundle.putString("topics", topics);
                    updateClassTestBundle.putString("class_test_id", classTestId);
                    updateClassTestBundle.putString("teacher_id", userId);
                    updateClassTestBundle.putBoolean("update", true);
                    Intent intent = new Intent(ClassTests.this, NewClassTest.class);
                    intent.putExtras(updateClassTestBundle);
                    startActivityForResult(intent, 1);
                }

           return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                finish();
                startActivity(getIntent());
            }
            if (resultCode == RESULT_CANCELED) {
                // Cancel
            }
        }
    }

    private void getClassTestsList() {
        showProgressDialog();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                  classTestLocalDataList=localDataBaseHelper.getClassTestInfo();
                    if (classTestLocalDataList.isEmpty()==false){
                        localDataBaseHelper.deleteClassTestInfo();
                    }
                    JSONArray jsonArray=response.getJSONArray("class_test");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String courseCode=jsonObject.getString("course_code");
                        String courseTitle = jsonObject.getString("course_title");
                        String date =jsonObject.getString("date");
                        String time = jsonObject.getString("time");
                        String givenTime = jsonObject.getString("given_time");
                        String givenDate =jsonObject.getString("given_date");
                        String details =jsonObject.getString("topics");
                        String classTestId=jsonObject.getString("class_test_id");

                        localDataBaseHelper.insertClassTest(Integer.parseInt(classTestId),courseTitle,details,date,time,givenDate,givenTime);
                        classTestHashMap = new HashMap<String, String>();
                        classTestHashMap.put("course_code", courseCode);
                        classTestHashMap.put("course_title", courseTitle);
                        classTestHashMap.put("date", date);
                        classTestHashMap.put("time", time);
                        classTestHashMap.put("given_time", givenTime);
                        classTestHashMap.put("given_date", givenDate);
                        classTestHashMap.put("topics", details);
                        classTestHashMap.put("class_test_id", classTestId);
                        classTestsList.add(classTestHashMap);
                    }

                    ListAdapter adapter = new SimpleAdapter(
                            ClassTests.this, classTestsList,
                            R.layout.class_test_list_item, new String[] { "course_title", "date","time","given_time","given_date","topics"},
                            new int[] { R.id.tvClassTestTitle, R.id.tvClassTestDate,R.id.tvClassTestTime,R.id.tvClassTestGivenTime,R.id.tvClassTestGivenDate,R.id.tvClassTestDetails});

                    classTestListView.setAdapter(adapter);
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
                error.printStackTrace();
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
  public void  newClassTest(View view){

      if (internetConnection==true){
          Intent intent=new Intent(ClassTests.this,NewClassTest.class);
          intent.putExtra("user_id",userId);
          intent.putExtra("user_type",userType);
          startActivityForResult(intent, 1);
      }else{
          Toast.makeText(getApplicationContext(),"Connect to internet",Toast.LENGTH_LONG).show();
      }
    }
}
