package com.example.medicaldelivery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class DocatorAddTiming extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1;
    Button b1;
    String date;
    SharedPreferences sh;
    ListView l1;
    String[] sdate,a_date_id;
    public static String available_date_id;

    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docator_add_timing);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.cno);
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) DocatorAddTiming.this;
        String q = "/doctor_view_dates?lid="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);


        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current bb  day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(DocatorAddTiming.this,
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



        b1=(Button)findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=e1.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) DocatorAddTiming.this;
                String q="/doctor_add_available_dates?date="+date+"&lid="+Login.logid;
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {


            String method=jo.getString("method");
            if(method.equalsIgnoreCase("doctor_add_available_dates")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADD SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), DocatorAddTiming.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("doctor_view_dates"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()]
                    sdate=new String[ja1.length()];
                    a_date_id=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        sdate[i]=ja1.getJSONObject(i).getString("date");
                        a_date_id[i]=ja1.getJSONObject(i).getString("availables_date_id");


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,sdate);
                    l1.setAdapter(ar);
                }
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        available_date_id=a_date_id[position];


        final CharSequence[] items = {"Add Available Times","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DocatorAddTiming.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Add Available Times")) {


                  startActivity(new Intent(getApplicationContext(),AddAvailableTimes.class));

//
                }
                else if (items[item].equals("View Prescription")) {

                    startActivity(new Intent(getApplicationContext(),UserViewDoctorPrescription.class));
//
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }
}