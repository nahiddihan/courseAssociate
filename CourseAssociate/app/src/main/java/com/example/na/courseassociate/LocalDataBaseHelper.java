package com.example.na.courseassociate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class LocalDataBaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "course_associate";

    public LocalDataBaseHelper(Context context) {

        super(context, DATABASE_NAME,null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{

            db.execSQL("create table if not exists student " +
                    "(student_id integer primary key, student_name text,roll text," +
                    "student_email text,department_title text,semester_no integer," +
                    "session text,varsity_name text)");

            db.execSQL("create table if not exists teacher " +
                    "(teacher_id integer primary key, teacher_name text,designation text," +
                    "teacher_email text,mobile text,department_title text," +
                    "teacher_password text,varsity_name text)");

            db.execSQL("create table if not exists course " +
                    "(course_id integer primary key, course_code text,course_title text)");

            db.execSQL("create table if not exists syllabus " +
                    "(course_id integer primary key, course_code text,course_title text,credit text,credit_hour text,syllabus text)");

            db.execSQL("create table if not exists schedule " +
                    "(course_title text,schedule text)");


            db.execSQL("create table if not exists result " +
                    "(course_code text,credit text ,grade_point text)");

            db.execSQL("create table if not exists post " +
                    "(post_id integer primary key, status_details text,date text,time text,course_id integer,teacher_id integer)");

            db.execSQL("create table if not exists teacher_course_post " +
                    "(post_id integer , teacher_id integer,course_id integer)");

            db.execSQL("create table if not exists course_class_hour " +
                    "(varsity_id integer primary key, varsity_name text)");

            db.execSQL("create table if not exists class_test " +
                    "(class_test_id integer,course_title text, date text,time text,topics text,given_date text,given_time text)");

            db.execSQL("create table if not exists class_hour " +
                    "(class_hour_id integer primary key, day text,time text)");

            db.execSQL("create table if not exists notification " +
                    "(course_title text, date text,time text,status text)");

            db.execSQL("create table if not exists final_exam " +
                    "(course_code text, date text,time text)");

            db.execSQL("create table if not exists sunday " +
                    "(course_code text,time text)");
            db.execSQL("create table if not exists monday " +
                    "(course_code text,time text)");
            db.execSQL("create table if not exists tuesday " +
                    "(course_code text,time text)");
            db.execSQL("create table if not exists wednesday " +
                    "(course_code text,time text)");
            db.execSQL("create table if not exists thursday " +
                    "(course_code text,time text)");

        }catch (SQLiteException se){

            Log.v("Database Creation Error",se.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS teacher");
        db.execSQL("DROP TABLE IF EXISTS teacher_course_post");
        db.execSQL("DROP TABLE IF EXISTS result");
        db.execSQL("DROP TABLE IF EXISTS post");
        db.execSQL("DROP TABLE IF EXISTS course_class_hour");
        db.execSQL("DROP TABLE IF EXISTS course");
        db.execSQL("DROP TABLE IF EXISTS syllabus");
        db.execSQL("DROP TABLE IF EXISTS class_test");
        db.execSQL("DROP TABLE IF EXISTS class_hour");
        db.execSQL("DROP TABLE IF EXISTS schedule");
        db.execSQL("DROP TABLE IF EXISTS notification");
        db.execSQL("DROP TABLE IF EXISTS final_exam");
        onCreate(db);
    }

    public void insertStudentInfo(int studentId,String studentName,String roll,String email,String departmentTitle,int semisterNo,String varsityName,String session){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("student_id", studentId);
        contentValues.put("student_name", studentName);
        contentValues.put("roll", roll);
        contentValues.put("student_email", email);
        contentValues.put("department_title", departmentTitle);
        contentValues.put("semester_no", semisterNo);
        contentValues.put("varsity_name", varsityName);
        contentValues.put("session", session);
        db.insert("student",null,contentValues);

    }
    public HashMap<String,String> getStudentInfo(int id){

        HashMap<String, String> map = new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from student where student_id="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            map.put("student_id",res.getString(res.getColumnIndex("student_id")));
            map.put("student_name",res.getString(res.getColumnIndex("student_name")));
            map.put("roll",res.getString(res.getColumnIndex("roll")));
            map.put("student_email",res.getString(res.getColumnIndex("student_email")));
            map.put("department_title",res.getString(res.getColumnIndex("department_title")));
            map.put("semister_no",res.getString(res.getColumnIndex("semester_no")));
            map.put("session",res.getString(res.getColumnIndex("session")));
            map.put("varsity_name",res.getString(res.getColumnIndex("varsity_name")));
            res.moveToNext();
        }
        return map;

    }

    public void updateStudentInfo(int studentId,String studentName,String roll,String email,String departmentTitle,int semisterNo,String varsityName,String session)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("student_id", studentId);
        contentValues.put("student_name", studentName);
        contentValues.put("roll", roll);
        contentValues.put("student_email", email);
        contentValues.put("department_title", departmentTitle);
        contentValues.put("semester_no", semisterNo);
        contentValues.put("varsity_name", varsityName);
        contentValues.put("session", session);
        db.update("student", contentValues, "student_id = ? ", new String[] { Integer.toString(studentId) } );

    }

    public void insertTeacherInfo(int teacherId,String teacherName,
                              String designation,String email,
                              String mobile,String departmentTitle,String varsityName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("teacher_id", teacherId);
        contentValues.put("teacher_name", teacherName);
        contentValues.put("designation", designation);
        contentValues.put("teacher_email", email);
        contentValues.put("mobile", mobile);
        contentValues.put("department_title", departmentTitle);
        contentValues.put("varsity_name", varsityName);
        db.insert("teacher",null,contentValues);
    }
    public HashMap<String,String> getTeacherInfo(int id){

        HashMap<String, String> map = new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from teacher where teacher_id="+id+"", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            map.put("teacher_id",res.getString(res.getColumnIndex("teacher_id")));
            map.put("teacher_name",res.getString(res.getColumnIndex("teacher_name")));
            map.put("designation",res.getString(res.getColumnIndex("designation")));
            map.put("teacher_email",res.getString(res.getColumnIndex("teacher_email")));
            map.put("department_title",res.getString(res.getColumnIndex("department_title")));
            map.put("teacher_mobile",res.getString(res.getColumnIndex("mobile")));
            map.put("varsity_name",res.getString(res.getColumnIndex("varsity_name")));
            res.moveToNext();
        }
        return map;
    }
    public void updateTeacherInfo(int teacherId,String teacherName,String designation,String email,String mobile,String departmentTitle,String varsityName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("teacher_id", teacherId);
        contentValues.put("teacher_name", teacherName);
        contentValues.put("designation", designation);
        contentValues.put("teacher_email", email);
        contentValues.put("mobile", mobile);
        contentValues.put("department_title", departmentTitle);
        contentValues.put("varsity_name", varsityName);
        db.update("teacher", contentValues, "teacher_id = ? ", new String[] { Integer.toString(teacherId) } );
    }
    public void insertCourse(int courseId,String courseCode,String courseTitle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_id", courseId);
        contentValues.put("course_code", courseCode);
        contentValues.put("course_title", courseTitle);
        db.insert("course",null,contentValues);
    }
    public void insertCourse(ArrayList<HashMap<String,String>> courseList){

        SQLiteDatabase db = this.getWritableDatabase();
      for(int i=0;i<courseList.size();i++){
          ContentValues contentValues = new ContentValues();
          contentValues.put("course_id", Integer.parseInt(courseList.get(i).get("course_id")));
          contentValues.put("course_code", courseList.get(i).get("course_code"));
          contentValues.put("course_title", courseList.get(i).get("course_title"));
          db.insert("course",null,contentValues);
      }
    }

    public ArrayList<HashMap<String,String>> getCourseInfo(){

        ArrayList<HashMap<String, String>> courseList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from course", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_id",res.getString(res.getColumnIndex("course_id")));
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("course_title",res.getString(res.getColumnIndex("course_title")));
            courseList.add(map);
            res.moveToNext();
        }
        return courseList;

    }
    public void deleteCourseInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from course");
    }

    public void insertSyllabus(ArrayList<HashMap<String,String>> syllabusList){

        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<syllabusList.size();i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("course_id", Integer.parseInt(syllabusList.get(i).get("course_id")));
            contentValues.put("course_code", syllabusList.get(i).get("course_code"));
            contentValues.put("course_title", syllabusList.get(i).get("course_title"));
            contentValues.put("credit", syllabusList.get(i).get("credit"));
            contentValues.put("credit_hour", syllabusList.get(i).get("credit_hour"));
            contentValues.put("syllabus", syllabusList.get(i).get("syllabus"));
            db.insert("syllabus",null,contentValues);
        }
    }

    public ArrayList<HashMap<String,String>> getSyllabusListInfo(){

        ArrayList<HashMap<String, String>> syllabusList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from syllabus", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_id",res.getString(res.getColumnIndex("course_id")));
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("course_title",res.getString(res.getColumnIndex("course_title")));
            map.put("credit",res.getString(res.getColumnIndex("credit")));
            map.put("credit_hour",res.getString(res.getColumnIndex("credit_hour")));
            map.put("syllabus",res.getString(res.getColumnIndex("syllabus")));
            syllabusList.add(map);
            res.moveToNext();
        }
        return syllabusList;

    }
    public void deleteSyllabusInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from syllabus");
    }
    public HashMap<String,String> getSyllabus(String courseId){
        HashMap<String, String> map = new HashMap<String, String>();
        int syllabusCourseId=Integer.parseInt(courseId);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from syllabus where course_id="+syllabusCourseId+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("course_title",res.getString(res.getColumnIndex("course_title")));
            map.put("credit",res.getString(res.getColumnIndex("credit")));
            map.put("credit_hour",res.getString(res.getColumnIndex("credit_hour")));
            map.put("syllabus",res.getString(res.getColumnIndex("syllabus")));
            res.moveToNext();
        }
        return map;
    }

    public void insertSchedule(String courseTitle,String schedule){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_title", courseTitle);
        contentValues.put("schedule", schedule);
        db.insert("schedule",null,contentValues);
    }
    public ArrayList<HashMap<String,String>> getScheduleInfo(){

        ArrayList<HashMap<String, String>> scheduleList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from schedule", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_title",res.getString(res.getColumnIndex("course_title")));
            map.put("schedule",res.getString(res.getColumnIndex("schedule")));
            scheduleList.add(map);
            res.moveToNext();
        }
        return scheduleList;
    }
    public void deleteScheduleInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from schedule");
    }

    public void insertNotification(String courseTitle,String date,String time,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_title", courseTitle);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("status", status);
        db.insert("notification",null,contentValues);
    }
    public ArrayList<HashMap<String,String>> getNotificationInfo(){

        ArrayList<HashMap<String, String>> notificationList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from notification", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_title",res.getString(res.getColumnIndex("course_title")));
            map.put("date",res.getString(res.getColumnIndex("date")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            map.put("status_details",res.getString(res.getColumnIndex("status")));

            notificationList.add(map);
            res.moveToNext();
        }
        return notificationList;

    }
    public void deleteNotificationInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from notification");
    }
    public void insertClassTest(int classTestId,String courseTitle,String topics,String date ,String time, String givenDate,String givenTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("class_test_id", classTestId);
        contentValues.put("course_title", courseTitle);
        contentValues.put("topics", topics);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("given_date", givenDate);
        contentValues.put("given_time", givenTime);
        db.insert("class_test",null,contentValues);
    }

    public ArrayList<HashMap<String,String>> getClassTestInfo(){

        ArrayList<HashMap<String, String>> classTestList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from class_test", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_title",res.getString(res.getColumnIndex("course_title")));
            map.put("date",res.getString(res.getColumnIndex("date")));
            map.put("class_test_id",res.getString(res.getColumnIndex("class_test_id")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            map.put("topics",res.getString(res.getColumnIndex("topics")));
            map.put("given_date",res.getString(res.getColumnIndex("given_date")));
            map.put("given_time",res.getString(res.getColumnIndex("given_time")));

            classTestList.add(map);
            res.moveToNext();
        }
        return classTestList;

    }
    public void deleteClassTestInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from class_test");
    }

    public void insertFinalExam(String courseCode,String date,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_code", courseCode);
        contentValues.put("date", date);
        contentValues.put("time", time);
        db.insert("final_exam",null,contentValues);
    }
    public ArrayList<HashMap<String,String>> getFinalExamInfo(){

        ArrayList<HashMap<String, String>> finalExamList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from final_exam", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("date",res.getString(res.getColumnIndex("date")));
            map.put("time",res.getString(res.getColumnIndex("time")));

            finalExamList.add(map);
            res.moveToNext();
        }
        return finalExamList;

    }

    public void deleteFinalExamInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from final_exam");
    }
    public void insertResult(String courseCode,String credit,String gradePoint ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_code", courseCode);
        contentValues.put("credit", credit);
        contentValues.put("grade_point", gradePoint);
        db.insert("result",null,contentValues);
    }
    public ArrayList<HashMap<String,String>> getResultInfo(){

        ArrayList<HashMap<String, String>> resultList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from result", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("credit",res.getString(res.getColumnIndex("credit")));
            map.put("grade_point",res.getString(res.getColumnIndex("grade_point")));

            resultList.add(map);
            res.moveToNext();
        }
        return resultList;

    }

    public void deleteResultInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from result");
    }

    public void insertSundayRoutine(String courseCode,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_code", courseCode);
        contentValues.put("time", time);
        db.insert("sunday",null,contentValues);
    }
    public ArrayList<HashMap<String,String>> getSundayRoutine(){

        ArrayList<HashMap<String, String>> sundayClassList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from sunday", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            sundayClassList.add(map);
            res.moveToNext();
        }
        return sundayClassList;

    }

    public void deleteSundayInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from sunday");
    }
    public void insertMondayRoutine(String courseCode,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_code", courseCode);
        contentValues.put("time", time);
        db.insert("monday",null,contentValues);
    }
    public ArrayList<HashMap<String,String>> getMondayRoutine(){

        ArrayList<HashMap<String, String>> mondayClassList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from monday", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            mondayClassList.add(map);
            res.moveToNext();
        }
        return mondayClassList;

    }

    public void deleteMondayInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from monday");
    }
    public void insertTuesdayRoutine(String courseCode,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_code", courseCode);
        contentValues.put("time", time);
        db.insert("tuesday",null,contentValues);
    }

    public ArrayList<HashMap<String,String>> getTuesdayRoutine(){

        ArrayList<HashMap<String, String>> tuesdayClassList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tuesday", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            tuesdayClassList.add(map);
            res.moveToNext();
        }
        return tuesdayClassList;

    }
    public void deleteTuesdayInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from tuesday");
    }
    public void insertWednesdayRoutine(String courseCode,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_code", courseCode);
        contentValues.put("time", time);
        db.insert("wednesday",null,contentValues);
    }
    public ArrayList<HashMap<String,String>> getWednesdayRoutine(){

        ArrayList<HashMap<String, String>> wednesdayClassList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from wednesday", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            wednesdayClassList.add(map);
            res.moveToNext();
        }
        return wednesdayClassList;

    }
    public void deleteWednesdayInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from wednesday");
    }
    public void insertThursdayRoutine(String courseCode,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_code", courseCode);
        contentValues.put("time", time);
        db.insert("thursday",null,contentValues);
    }
    public ArrayList<HashMap<String,String>> getThursdayRoutine(){

        ArrayList<HashMap<String, String>> thursdayClassList=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from thursday", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("course_code",res.getString(res.getColumnIndex("course_code")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            thursdayClassList.add(map);
            res.moveToNext();
        }
        return thursdayClassList;

    }
    public void deleteThursdayInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from thursday");
    }
}
