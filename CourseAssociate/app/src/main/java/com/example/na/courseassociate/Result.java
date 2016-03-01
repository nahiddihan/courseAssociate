package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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


public class Result extends Activity {
    String userId;
    String userType;
    String semisterId;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    HashMap<String, String> resultHashMap;
    ArrayList<HashMap<String, String>> resultList;
    ListView resultListView;
    Float totalGradePoint=0.00f;
    Float creditCompleted=0.00f;
    TextView tvCreditEarned;
    TextView tvCGPA;
    Float gradeCredit=0.00f;
    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    LocalDataBaseHelper localDataBaseHelper;
    ArrayList<HashMap<String, String>> resultLocalDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        resultListView=(ListView)findViewById(R.id.lvResult);
        tvCreditEarned=(TextView)findViewById(R.id.tvCreditEarned);
        tvCGPA=(TextView)findViewById(R.id.tvCGPA);

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
        resultLocalDataList=new ArrayList<HashMap<String, String>>();
        resultList=new ArrayList<HashMap<String, String>>();
        requestQueue= Volley.newRequestQueue(this);

        if (internetConnection==true){
            if(userType.equalsIgnoreCase("student")){
                getResult();
            }
        }else{
            resultLocalDataList=localDataBaseHelper.getResultInfo();
            if (resultLocalDataList.isEmpty()==false){

                for (int i = 0; i < resultLocalDataList.size(); i++) {
                    String credit = resultLocalDataList.get(i).get("credit");
                    String gradePoint = resultLocalDataList.get(i).get("grade_point");
                    Float grade=Float.parseFloat(gradePoint);
                    totalGradePoint=totalGradePoint+grade;
                    if(grade>0){
                        creditCompleted=creditCompleted+Float.parseFloat(credit);
                        gradeCredit=gradeCredit+(grade*Float.parseFloat(credit));
                    }
                }
                tvCreditEarned.setText("Credit Earned:"+String.format("%.2f",creditCompleted));
                tvCGPA.setText("CGPA :"+String.format("%.2f",gradeCredit/creditCompleted));
                ListAdapter adapter = new SimpleAdapter(
                        Result.this, resultLocalDataList,
                        R.layout.result_list_item, new String[] { "course_code", "credit","grade_point"},
                        new int[] { R.id.tvResultCourseCode, R.id.tvResultCredit,R.id.tvResultGradePoint });
                resultListView.setAdapter(adapter);
            }
        }
    }
    private void getResult() {
        showProgressDialog();
        String url="http://nahiddihanbd.comlu.com/course_associate/result.php?student_id="+userId;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    resultLocalDataList=localDataBaseHelper.getResultInfo();
                    if (resultLocalDataList.isEmpty()==false){
                        localDataBaseHelper.deleteResultInfo();
                    }
                    if(response.getInt("success")==1){
                        Log.v("Look",response.toString());
                        JSONArray jsonArray=response.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String courseCode = jsonObject.getString("course_code");
                            String credit = jsonObject.getString("credit");
                            String gradePoint = jsonObject.getString("grade_point");
                            Float grade=Float.parseFloat(gradePoint);
                            localDataBaseHelper.insertResult(courseCode,credit,gradePoint);
                            totalGradePoint=totalGradePoint+grade;
                            if(grade>0){
                                creditCompleted=creditCompleted+Float.parseFloat(credit);
                               gradeCredit=gradeCredit+(grade*Float.parseFloat(credit));
                            }
                            resultHashMap = new HashMap<String, String>();
                            resultHashMap.put("course_code", courseCode);
                            resultHashMap.put("credit", credit);
                            resultHashMap.put("grade_point", gradePoint);
                            resultList.add(resultHashMap);
                        }

                        tvCreditEarned.setText("Credit Earned:"+String.format("%.2f",creditCompleted));
                        tvCGPA.setText("CGPA :"+String.format("%.2f",gradeCredit/creditCompleted));
                        ListAdapter adapter = new SimpleAdapter(
                                Result.this, resultList,
                                R.layout.result_list_item, new String[] { "course_code", "credit","grade_point"},
                                new int[] { R.id.tvResultCourseCode, R.id.tvResultCredit,R.id.tvResultGradePoint });

                        resultListView.setAdapter(adapter);

                        hideProgressDialog();
                    }else{
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(),"Nothing Found",Toast.LENGTH_SHORT).show();
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
