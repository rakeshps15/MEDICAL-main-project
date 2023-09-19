package com.example.medicaldelivery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Deliveryvieworderspickedup extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    String[] date,statuss,value,pid,username,place,phone;
    public static String pids,ostatus;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryvieworderspickedup);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1 = (ListView) findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Deliveryvieworderspickedup.this;
        String q = "/deliveryboyvieworderpickup?lid="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

                String method = jo.getString("method");
                if (method.equalsIgnoreCase("deliveryboyupdatestatustodeliverd")) {

                    String status = jo.getString("status");
                    Log.d("pearl", status);


                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getApplicationContext(), "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Deliveryvieworderspickedup.class));

                    } else {

                        Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                    }
                } else if (method.equalsIgnoreCase("deliveryboyvieworderpickup")) {

                    String status = jo.getString("status");
                    Log.d("pearl", status);


                    if (status.equalsIgnoreCase("success")) {
                        JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                        pid = new String[ja1.length()];
                        username = new String[ja1.length()];
                        date = new String[ja1.length()];
                        statuss = new String[ja1.length()];
                        place = new String[ja1.length()];
                        phone = new String[ja1.length()];
                        value = new String[ja1.length()];

                        for (int i = 0; i < ja1.length(); i++) {

                            pid[i] = ja1.getJSONObject(i).getString("prescription_id");
                            username[i] = ja1.getJSONObject(i).getString("firstname") + " " + ja1.getJSONObject(i).getString("lastname");
                            date[i] = ja1.getJSONObject(i).getString("pdate");
                            statuss[i] = ja1.getJSONObject(i).getString("status");
                            place[i] = ja1.getJSONObject(i).getString("place");
                            phone[i] = ja1.getJSONObject(i).getString("phone");
                            value[i] = "Username: " + username[i]+"\nPlace: " + place[i]+"\nPhone: " + phone[i]  + "\nDate: " + date[i] + "\nStatuss: " + statuss[i];

                        }
                        ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                        l1.setAdapter(ar);
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){

            pids=pid[position];
            ostatus=statuss[position];
            final CharSequence[] items = {"Delivered","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Deliveryvieworderspickedup.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Delivered")) {

                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Deliveryvieworderspickedup.this;
                        String q = "/deliveryboyupdatestatustodeliverd?pid="+Deliveryvieworderspickedup.pids+"&lid="+sh.getString("log_id","");
                        q = q.replace(" ", "%20");
                        JR.execute(q);

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
        Intent b=new Intent(getApplicationContext(),Deliveryhome.class);
        startActivity(b);
    }
}