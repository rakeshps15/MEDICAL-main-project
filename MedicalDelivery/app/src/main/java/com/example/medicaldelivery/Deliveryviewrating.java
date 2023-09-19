package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONObject;

public class Deliveryviewrating extends AppCompatActivity implements JsonResponse{
    RatingBar r1;
    String rate;
    // ListView l1;
    //String[] rates;
    Float rated;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryviewrating);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        r1 = (RatingBar) findViewById(R.id.etrr);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Deliveryviewrating.this;
        String q="/deliveryviewrating?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {

                    rate=jo.getString("data");
                    rated=Float.parseFloat(rate);
                    Toast.makeText(getApplicationContext(),rated+"", Toast.LENGTH_SHORT).show();
                    r1.setRating(rated);

                }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Deliveryhome.class);
        startActivity(b);
    }
}