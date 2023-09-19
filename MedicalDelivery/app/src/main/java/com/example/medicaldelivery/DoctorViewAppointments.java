package com.example.medicaldelivery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONObject;

public class DoctorViewAppointments extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] name,phone,email,date,time,value,app_id;
    public static String appoint_id,pns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_appointments);
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) DoctorViewAppointments.this;
        String q = "/doctorviewappoinments?lid="+Login.logid;
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
                name = new String[ja1.length()];
                phone = new String[ja1.length()];
                email = new String[ja1.length()];
                date = new String[ja1.length()];
                time = new String[ja1.length()];
                app_id = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    name[i] = ja1.getJSONObject(i).getString("firstname")+" "+ja1.getJSONObject(i).getString("lastname");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    time[i] = ja1.getJSONObject(i).getString("time");
                    app_id[i] = ja1.getJSONObject(i).getString("appoinment_id");
                    value[i] = "Name: " + name[i]  + "\nphone: " + phone[i] + "\nEmail: " + email[i] + "\nDate: " + date[i] + "\nTime: " + time[i];

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

        appoint_id=app_id[position];

        final CharSequence[] items = {"Customer Details","Make a Call","Upload Prescription","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DoctorViewAppointments.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Customer Details")) {

                    startActivity(new Intent(getApplicationContext(),DoctorViewCustomers.class));
                }
                else if (items[item].equals("Make a Call")) {

                    pns=phone[item];

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+pns));//change the number
                    startActivity(callIntent);

//
                }
                if (items[item].equals("Upload Prescription")) {

                    startActivity(new Intent(getApplicationContext(),DoctorUploadPrescription.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();



    }
}