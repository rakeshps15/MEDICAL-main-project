package com.example.medicaldelivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Userviewdeliveryboys extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] agentname,place,phone,date,value,bid;
    public static String bids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewdeliveryboys);
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Userviewdeliveryboys.this;
        String q = "/userviewdeliveryboys?pid="+Userviewuploadedmedicine.pids;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                place = new String[ja1.length()];
                phone = new String[ja1.length()];
                bid = new String[ja1.length()];
                agentname = new String[ja1.length()];
                date = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    place[i] = ja1.getJSONObject(i).getString("email");
                    bid[i] = ja1.getJSONObject(i).getString("boy_id");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    agentname[i] = ja1.getJSONObject(i).getString("firstname")+" "+ja1.getJSONObject(i).getString("lastname");
                    value[i] = "Name: " + agentname[i] + "\nEmail: " + place[i] + "\nphone: " + phone[i] + "\nDate: " + date[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
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

        bids=bid[position];

        final CharSequence[] items = {"Rate","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewdeliveryboys.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Rate")) {

                    startActivity(new Intent(getApplicationContext(),Usermakerate.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}