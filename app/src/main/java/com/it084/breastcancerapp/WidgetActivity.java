package com.it084.breastcancerapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class WidgetActivity extends AppCompatActivity {
    Button browsebtn,uploadbtn;
    ImageView mamogram_image;
    Bitmap bmap;
    String encoded_image;
    private static final String url="";
    private static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        mamogram_image=(ImageView) findViewById(R.id.mamogramimage);
        browsebtn=(Button) findViewById(R.id.browsebutton);
        uploadbtn=(Button) findViewById(R.id.uploadbutton);
        browsebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(WidgetActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent,1);


                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImagetoServer();
            }
        });
    }

    private void uploadImagetoServer() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mamogram_image.setImageResource(R.drawable.ic_launcher_foreground);
                Toast.makeText(WidgetActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WidgetActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<String,String>();
                map.put("upload",encoded_image);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK){
            Uri filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bmap=BitmapFactory.decodeStream(inputStream);
                mamogram_image.setImageBitmap(bmap);
                encodeBitmapImage(bmap);
                System.out.println("Image encoded");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofImage=byteArrayOutputStream.toByteArray();
        encoded_image=android.util.Base64.encodeToString(bytesofImage, Base64.DEFAULT);

    }

}