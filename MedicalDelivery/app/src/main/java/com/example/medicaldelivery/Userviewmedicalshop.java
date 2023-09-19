package com.example.medicaldelivery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Userviewmedicalshop extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] shopname,sid,place,city,email,phone,value;
    public static String sids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewmedicalshop);
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Userviewmedicalshop.this;
        String q = "/userviewmedicalshop";
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
                sid = new String[ja1.length()];
                shopname = new String[ja1.length()];
                place = new String[ja1.length()];
                city = new String[ja1.length()];
                phone = new String[ja1.length()];
                email = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    sid[i] = ja1.getJSONObject(i).getString("medicalshop_id");
                    shopname[i] = ja1.getJSONObject(i).getString("shopname");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    city[i] = ja1.getJSONObject(i).getString("city");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    value[i] = "Shop Name: " + shopname[i]+ "\nPlace:" + place[i] + "\nCity:" + city[i]+ "\nPhone:" + phone[i] + "\nEmail:" + email[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
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
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sids=sid[position];
        final CharSequence[] items = {"Upload Doctor Prescription","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewmedicalshop.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Upload Doctor Prescription")) {
                    startActivity(new Intent(getApplicationContext(),Useruploadprescription.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();

    }
}