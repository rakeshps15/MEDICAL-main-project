package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONObject;

public class Usermakerate extends AppCompatActivity implements JsonResponse{

    RatingBar r1;
    Button b1;
    String rate;
    // ListView l1;
    //String[] rates;
    Float rated;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermakerate);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        r1 = (RatingBar) findViewById(R.id.etrr);
        b1=(Button)findViewById(R.id.button);
        // l1=(ListView)findViewById(R.id.lvview);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Usermakerate.this;
        String q="/viewrating?lid="+sh.getString("log_id","")+"&bid="+Userviewdeliveryboys.bids;
        q=q.replace(" ","%20");
        JR.execute(q);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating =  r1.getRating();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Usermakerate.this;
                String q ="/userrating?lid="+sh.getString("log_id","")+"&rate="+rating+"&bid="+Userviewdeliveryboys.bids;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("userrating")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Usermakerate.class));

                } else {


                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            } else if (method.equalsIgnoreCase("viewrating")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {

                    rate=jo.getString("data");
                    rated=Float.parseFloat(rate);
                    Toast.makeText(getApplicationContext(),rated+"", Toast.LENGTH_SHORT).show();
                    r1.setRating(rated);

                }

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
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}