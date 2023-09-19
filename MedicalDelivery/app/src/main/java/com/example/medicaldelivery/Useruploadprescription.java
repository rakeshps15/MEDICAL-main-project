package com.example.medicaldelivery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Useruploadprescription extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ImageButton i1;
    Button b1;
    String[] image,date,statuss,amount,pid,amounts;
    ListView l1;
    public static String pids;
    SharedPreferences sh;

    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useruploadprescription);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=(Button)findViewById(R.id.button);

        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR1 = new JsonReq();
        JR1.json_response = (JsonResponse) Useruploadprescription.this;
        String q1 = "/userviewuploadedfilesprescription?sid=" + Userviewmedicalshop.sids+"&lid="+sh.getString("log_id","");
        q1 = q1.replace(" ", "%20");
        JR1.execute(q1);
        i1=(ImageButton)findViewById(R.id.imageButton);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectImageOption();
            }


        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAttach();
            }


        });
    }

    private void sendAttach() {
        try {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");


//	        	rid=View_waste_disposal_request.report_id;


            String q = "http://" + Ipsettings.text + "/api/useruploadprescriptions";
//            String q = "http://" +IpSetting.ip+"/api/user_upload_file";
//	            String q = "/user_upload_file/?image="+imagename+"&desc="+des+"&yts="+yt;
//	        	String q = "http://" +IPSetting.ip+"/TeachersHelper/api.php?action=teacher_upload_notes";
//	        	String q= "api.php?action=teacher_upload_notes&sh_id="+Teacher_view_timetable.s_id+"&desc="+des;

            Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray.length, Toast.LENGTH_LONG).show();


            Map<String, byte[]> aa = new HashMap<>();

            aa.put("image", byteArray);
              aa.put("logid", sh.getString("log_id", "").getBytes());
//            aa.put("bikeno", bikeno.getBytes());
//            aa.put("description",description.getBytes());

            aa.put("sid", Userviewmedicalshop.sids.getBytes());

//                    aa.put("tid",types_id.getBytes());

            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) Useruploadprescription.this;
            fua.execute(aa);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }
    private void selectImageOption() {
         /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Useruploadprescription.this);
        builder.setTitle("Take Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
                path = FileUtils.getPath(this, uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                i1.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                i1.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("useruploadprescriptions")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "UPLOADED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Useruploadprescription.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("userviewuploadedfilesprescription"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    pid=new String[ja1.length()];

                    image=new String[ja1.length()];
                    amount=new String[ja1.length()];
                    statuss=new String[ja1.length()];
                    date=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        pid[i]=ja1.getJSONObject(i).getString("prescription_id");
                        image[i]=ja1.getJSONObject(i).getString("uploadfile");
                        amount[i]=ja1.getJSONObject(i).getString("totalamount");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        statuss[i]=ja1.getJSONObject(i).getString("status");

                    }

                    Custimage cc=new Custimage(this,image,amount,date,statuss);
                    l1.setAdapter(cc);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pids=pid[position];

        SharedPreferences.Editor e = sh.edit();
        e.putString("pid", pids);
        e.commit();
        final CharSequence[] items = {"View Medicine Details","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Useruploadprescription.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Medicine Details")) {
                    startActivity(new Intent(getApplicationContext(),Userviewuploadedmedicine.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
}