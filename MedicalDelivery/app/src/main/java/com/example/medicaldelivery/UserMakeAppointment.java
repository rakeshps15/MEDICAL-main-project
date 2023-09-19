package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class UserMakeAppointment extends AppCompatActivity implements JsonResponse{

    EditText e1,e2;
    Button b1;
    String date,time;
    SharedPreferences sh;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_make_appointment);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.cno);


        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current bb  day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(UserMakeAppointment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                e1.setText(year+ "/"
                                        + (monthOfYear + 1) + "/" +dayOfMonth);
                            }
                        }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        e2=(EditText)findViewById(R.id.ettime);

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(UserMakeAppointment.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Set the selected time to the EditText
                                e2.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
            // Create a new TimePickerDialog instance and show it
        });

        b1=(Button)findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=e1.getText().toString();
                time=e2.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) UserMakeAppointment.this;
                String q="/usermakeappointments?did="+UserViewDoctors.docc_ids+"&date="+date+"&time="+time+"&lid="+Login.logid;
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {


            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), UserViewAppointments.class));

            } else {

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }



        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}