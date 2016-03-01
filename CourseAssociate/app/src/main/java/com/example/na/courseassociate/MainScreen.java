package com.example.na.courseassociate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class MainScreen extends AppCompatActivity{
    GridView gridView;
    String[] gridItemTitle;
    int[] gridItemImage;
    String userId;
    String userType;
    String semisterId;
    String departmentId;
    Bundle allBundle;Bundle bundle;
    RequestQueue profReqQueue;
    SharedPreferences sharedPreferences;
    HashMap<String,String> studentInfo;
    private static final String SHARED_PREF_NAME="Course_associate";
    NetworkStatusChecker networkStatusChecker;
    Boolean internetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        gridView=(GridView)findViewById(R.id.grid);
        allBundle=new Bundle();


         networkStatusChecker=new NetworkStatusChecker(getApplicationContext());
         internetConnection= networkStatusChecker.isConnectedToNetwork();

        LocalDataBaseHelper localDataBaseHelper=new LocalDataBaseHelper(getApplicationContext());
        studentInfo=new HashMap<String,String>();

        bundle=getIntent().getExtras();
        userId=bundle.getString("user_id");
        userType=bundle.getString("user_type");
        departmentId=bundle.getString("department_id");

        if(internetConnection==true){

            if(userType.equalsIgnoreCase("student")){
                String url="http://nahiddihanbd.comlu.com/course_associate/student_profile.php?student_id="+userId;
                profReqQueue= Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("profile");
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            semisterId=jsonObject.getString("semister_id");
                            allBundle.putString("semister_id",semisterId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                profReqQueue.add(jsonObjectRequest);

            }
        }else{

            studentInfo=localDataBaseHelper.getStudentInfo(Integer.parseInt(userId));
            if (studentInfo.isEmpty()==true){
                Toast.makeText(getApplicationContext(),"connect to Internet",Toast.LENGTH_LONG).show();
            }else{
                semisterId=studentInfo.get("semister_id");
            }

        }


        allBundle.putString("user_type",userType);
        allBundle.putString("user_id",userId);
        allBundle.putString("department_id",departmentId);



        if(userType.equalsIgnoreCase("student")){
            // student code
            gridItemTitle=new String[]{ "Profile", "Courses", "Classes","syllabus",
                    "Class Routine","Notifications","Class Tests","Exam Routine","Result" } ;
            gridItemImage= new int[]{ R.drawable.profile, R.drawable.courses,
                    R.drawable.class_schedule,R.drawable.syllabus, R.drawable.class_routine,
                    R.drawable.notifications,R.drawable.class_test,R.drawable.exam_routine_2,R.drawable.result};
        }else{
            // teacher code
            gridItemTitle=new String[]{ "Profile", "Courses", "Classes","syllabus",
                    "Class Routine","Notify","Class Tests","Exam Routine","Review" } ;
            gridItemImage= new int[]{ R.drawable.profile, R.drawable.courses,
                    R.drawable.class_schedule,R.drawable.syllabus, R.drawable.class_routine,
                    R.drawable.notifications,R.drawable.class_test,R.drawable.exam_routine_2,R.drawable.review2};
        }

        CustomGridView customGridViewAdapter=new CustomGridView(this,gridItemTitle,gridItemImage);
        gridView.setAdapter(customGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent profileIntent=new Intent(MainScreen.this,Profile.class);
                        profileIntent.putExtras(bundle);
                        startActivity(profileIntent);
                        break;
                    case 1:
                        Intent courseIntent=new Intent(MainScreen.this,Courses.class);
                        courseIntent.putExtras(allBundle);
                        startActivity(courseIntent);
                        break;
                    case 2:
                        Intent CourseSchedule=new Intent(MainScreen.this,CourseSchedule.class);
                        CourseSchedule.putExtras(allBundle);
                        startActivity(CourseSchedule);
                        break;
                    case 3:
                        Intent syllabus=new Intent(MainScreen.this,Syllabus.class);
                        syllabus.putExtras(allBundle);
                        startActivity(syllabus);
                        break;
                    case 4:
                        Intent classRoutine=new Intent(MainScreen.this,ClassRoutine.class);
                        classRoutine.putExtras(allBundle);
                        startActivity(classRoutine);
                        break;
                    case 5:
                        if(userType.equalsIgnoreCase("student")){
                            Intent notificationIntent=new Intent(MainScreen.this,Notifications.class);
                            notificationIntent.putExtras(allBundle);
                            startActivity(notificationIntent);
                        }else{
                            internetConnection= networkStatusChecker.isConnectedToNetwork();
                            if (internetConnection==true){
                                Intent notifyIntent=new Intent(MainScreen.this,Notify.class);
                                notifyIntent.putExtras(allBundle);
                                startActivity(notifyIntent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Connect to internet",Toast.LENGTH_LONG).show();
                            }

                        }

                        break;
                    case 6:
                        Intent classTestIntent=new Intent(MainScreen.this,ClassTests.class);
                        classTestIntent.putExtras(allBundle);
                        startActivity(classTestIntent);
                        break;
                    case 7:
                        Intent intent=new Intent(MainScreen.this,ExamRoutine.class);
                        intent.putExtras(allBundle);
                        startActivity(intent);
                        break;
                    case 8:
                        if(userType.equalsIgnoreCase("teacher")){
                            Intent reviewIntent=new Intent(MainScreen.this,Review.class);
                            reviewIntent.putExtras(allBundle);
                            startActivity(reviewIntent);
                        }else{
                            Intent resultIntent=new Intent(MainScreen.this,Result.class);
                            resultIntent.putExtras(allBundle);
                            startActivity(resultIntent);
                        }
                        break;

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut) {
            sharedPreferences=getApplicationContext().getSharedPreferences(SHARED_PREF_NAME,1);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }else if(id == R.id.help){


        }else if(id == R.id.about){

        }
        return super.onOptionsItemSelected(item);
    }
}
