package com.example.na.courseassociate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class MainActivity extends Activity {
    ProgressDialog progressDialog;
    String userId;String departmentId;
    String userType;
    EditText email;
    EditText password;
    Button btnLogIn;
    TextView errorTextView;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="Course_associate";
    public static final String KEY_USER = "user";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USER_TYPE = "userType";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_DEPT_ID = "departmentId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       sharedPreferences=getApplicationContext().getSharedPreferences(SHARED_PREF_NAME,1);
       String prefUser= sharedPreferences.getString(KEY_USER,null);
        String prefPassword=sharedPreferences.getString(KEY_PASSWORD,null);
        String prefUserType=sharedPreferences.getString(KEY_USER_TYPE,null);
        String prefUserId=sharedPreferences.getString(KEY_USER_ID,null);
        String prefDepartmentId=sharedPreferences.getString(KEY_DEPT_ID,null);

        Log.v("shared preff",sharedPreferences.getAll().toString());

        if(prefUser!=null&&prefPassword!=null){

            Intent intent=new Intent(MainActivity.this,MainScreen.class);
            Bundle bundle=new Bundle();
            bundle.putString("user_type",prefUserType);
            bundle.putString("user_id",prefUserId);
            bundle.putString("department_id",prefDepartmentId);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        email =(EditText)findViewById(R.id.etEmail);
        password=(EditText)findViewById(R.id.etPassword);
        btnLogIn=(Button)findViewById(R.id.btLogin);
        errorTextView=(TextView)findViewById(R.id.tvLogError);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        requestQueue= Volley.newRequestQueue(this);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               verifyLoginInformation();
            }
        });
    }

    private void verifyLoginInformation() {
        showProgressDialog();
         String emailText=email.getText().toString();
        String passwordText=password.getText().toString();
       String logInUrl = "http://nahiddihanbd.comlu.com/course_associate/login.php?email="+emailText+"&"+"password="+passwordText;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,logInUrl,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer success=response.getInt("success");
                    if (success==0){
                        errorTextView.setVisibility(View.VISIBLE);
                        Log.v("success=0",response.toString());
                        hideProgressDialog();
                    }else{
                        JSONArray jsonArray=response.getJSONArray("user");
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        userId=jsonObject.getString("user_id");
                        userType=jsonObject.getString("user_type");
                        departmentId=jsonObject.getString("department_id");
                        Intent intent=new Intent(MainActivity.this,MainScreen.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("user_type",userType);
                        bundle.putString("user_id",userId);
                        bundle.putString("department_id",departmentId);

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(KEY_USER,email.getText().toString());
                        editor.putString(KEY_PASSWORD,password.getText().toString());
                        editor.putString(KEY_USER_TYPE,userType);
                        editor.putString(KEY_USER_ID,userId);
                        editor.putString(KEY_DEPT_ID,departmentId);
                        editor.commit();
                        hideProgressDialog();
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
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




