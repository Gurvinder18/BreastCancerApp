package com.it084.breastcancerapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class UserprofileActivity extends AppCompatActivity {
    ImageView back_arrow;
    TextView logout,image_username,edit_profile;
    EditText user_name,user_email;
    CircularImageView circularImageView;
    DBhelperprofile DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        back_arrow=(ImageView) findViewById(R.id.toolbar_back_arrow);
        circularImageView=(CircularImageView) findViewById(R.id.circular_image);
        logout=(TextView) findViewById(R.id.toolbar_logout_textview);
        image_username=(TextView) findViewById(R.id.imageusername);
        user_name=(EditText) findViewById(R.id.Name_user);
        user_email=(EditText) findViewById(R.id.email_user);
        edit_profile=(TextView) findViewById(R.id.editprofile);
        DB=new DBhelperprofile(this);
        int flag=0;
        if(flag==0) {
            edit_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameTXT = user_name.getText().toString();
                    String emailTXT = user_email.getText().toString();
                    String imageTXT = user_name.getText().toString();
                    Boolean checkInsertData = DB.insertUserData(nameTXT, emailTXT, imageTXT);
                    if (checkInsertData == true) {
                        Toast.makeText(UserprofileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserprofileActivity.this, "Profile not updated:error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            flag=1;
        }
        else{
            edit_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameTXT = user_name.getText().toString();
                    String emailTXT = user_email.getText().toString();
                    String imageTXT = user_name.getText().toString();
                    Boolean checkUpdateData = DB.updateUserData(nameTXT,emailTXT, imageTXT);
                    if (checkUpdateData == true) {
                        Toast.makeText(UserprofileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserprofileActivity.this, "Profile not updated:error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (flag==1){
            Cursor res=DB.getUserData();
            if (res.getCount()==0){
                Toast.makeText(UserprofileActivity.this,"Error no entry present DB error",Toast.LENGTH_SHORT).show();
            }else{

                while(res.moveToNext()){
                    user_name.setText(res.getString(0));
                    user_email.setText(res.getString(1));
                }
            }
        }

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_to_home();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout_clicked();
            }
        });

    }

    private void logout_clicked() {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void back_to_home() {
        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_userprofile);
        back_arrow=(ImageView) findViewById(R.id.toolbar_back_arrow);
        circularImageView=(CircularImageView) findViewById(R.id.circular_image);
        logout=(TextView) findViewById(R.id.toolbar_logout_textview);
        image_username=(TextView) findViewById(R.id.imageusername);
        user_name=(EditText) findViewById(R.id.Name_user);
        user_email=(EditText) findViewById(R.id.email_user);
        edit_profile=(TextView) findViewById(R.id.editprofile);
        DB=new DBhelperprofile(this);
        int flag=0;
        if(flag==0) {
            edit_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameTXT = user_name.getText().toString();
                    String emailTXT = user_email.getText().toString();
                    String imageTXT = user_name.getText().toString();
                    Boolean checkInsertData = DB.insertUserData(nameTXT, emailTXT, imageTXT);
                    if (checkInsertData == true) {
                        Toast.makeText(UserprofileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserprofileActivity.this, "Profile not updated:error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            flag=1;
        }
        else{
            edit_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameTXT = user_name.getText().toString();
                    String emailTXT = user_email.getText().toString();
                    String imageTXT = user_name.getText().toString();
                    Boolean checkUpdateData = DB.updateUserData(nameTXT,emailTXT, imageTXT);
                    if (checkUpdateData == true) {
                        Toast.makeText(UserprofileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserprofileActivity.this, "Profile not updated:error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (flag==1){
            Cursor res=DB.getUserData();
            if (res.getCount()==0){
                Toast.makeText(UserprofileActivity.this,"Error no entry present DB error",Toast.LENGTH_SHORT).show();
            }else{

                while(res.moveToNext()){
                    user_name.setText(res.getString(0));
                    user_email.setText(res.getString(1));
                }
            }
        }

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_to_home();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout_clicked();
            }
        });
    }
}