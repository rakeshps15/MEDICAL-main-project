package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Deliveryregister extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    Button b1;
    RadioButton r1,r2;

    String name,licence,gender,dob,housename,place,phone,email,user,password,lname,pincode;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryregister);

     //   startService(new Intent(getApplicationContext(),LocationService.class));
        e1=(EditText)findViewById(R.id.etfname);
//        e2=(EditText)findViewById(R.id.etlice);
        e3=(EditText)findViewById(R.id.etlname);
//        e4=(EditText)findViewById(R.id.etpin);
//        e10=(EditText)findViewById(R.id.dob);
//        e5=(EditText)findViewById(R.id.etplace);
        e6=(EditText)findViewById(R.id.etphone);
        e7=(EditText)findViewById(R.id.etemail);
        e8=(EditText)findViewById(R.id.etuser);
        e9=(EditText)findViewById(R.id.etpass);
        b1=(Button)findViewById(R.id.button);
//        r1=(RadioButton)findViewById(R.id.radioButton);
//        r2=(RadioButton)findViewById(R.id.radioButton2);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=e1.getText().toString();
//                licence=e2.getText().toString();
//                pincode=e4.getText().toString();
                lname=e3.getText().toString();
//                dob=e10.getText().toString();
//                place=e5.getText().toString();
                phone=e6.getText().toString();
                email=e7.getText().toString();
                user=e8.getText().toString();
                password=e9.getText().toString();
//                if(r1.isChecked())
//                {
//                    gender="Female";
//                }
//                else{
//                    gender="Male";
//                }

                if(name.equalsIgnoreCase("")|| !name.matches("[a-zA-Z ]+"))
                {
                    e1.setError("Enter your name");
                    e1.setFocusable(true);
                }
//                else if(licence.equalsIgnoreCase(""))
//                {
//                    e2.setError("Enter your licence");
//                    e2.setFocusable(true);
//                }

//                else if(place.equalsIgnoreCase(""))
//                {
//                    e5.setError("Enter your place");
//                    e5.setFocusable(true);
//                }
                else if(phone.equalsIgnoreCase("")|| phone.length()!=10)
                {
                    e6.setError("Enter your phone");
                    e6.setFocusable(true);
                }
                else if(email.equalsIgnoreCase("")|| !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))
                {
                    e7.setError("Enter your email");
                    e7.setFocusable(true);
                }
                else if(user.equalsIgnoreCase(""))
                {
                    e8.setError("Enter your username");
                    e8.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e9.setError("Enter your password");
                    e9.setFocusable(true);
                }
                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Deliveryregister.this;
                    String q ="/deliveryregister?name="+name+"&phone="+phone+"&email="+email+"&username="+user+"&password="+password+"&lname="+lname;
                    q = q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });

    }



    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), Deliveryregister.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            } else {
                startActivity(new Intent(getApplicationContext(), Deliveryregister.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
    }
