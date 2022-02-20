package com.it084.breastcancerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView userProfileCard,predictCard,widgetCard,settingsCard,infoCard,logoutCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userProfileCard=(CardView) findViewById(R.id.card_view_userprofile);
        predictCard=(CardView) findViewById(R.id.card_view_predict);
        widgetCard=(CardView) findViewById(R.id.card_view_widget);
        settingsCard=(CardView) findViewById(R.id.card_view_settings);
        infoCard=(CardView) findViewById(R.id.card_view_info);
        logoutCard=(CardView) findViewById(R.id.card_view_logout);
        userProfileCard.setOnClickListener(this);
        predictCard.setOnClickListener(this);
        widgetCard.setOnClickListener(this);
        settingsCard.setOnClickListener(this);
        infoCard.setOnClickListener(this);
        logoutCard.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.card_view_userprofile: intent= new Intent(this,UserprofileActivity.class); startActivity(intent);break;
            case R.id.card_view_predict: intent= new Intent(this,PredictActivity.class);startActivity(intent);break;
            case R.id.card_view_widget: intent= new Intent(this,WidgetActivity.class);startActivity(intent);break;
            case R.id.card_view_settings: intent= new Intent(this,SettingsActivity.class);startActivity(intent);break;
            case R.id.card_view_info: intent= new Intent(this,InfoActivity.class);startActivity(intent);break;
            case R.id.card_view_logout: intent= new Intent(this,LoginActivity.class);startActivity(intent);finish();break;
            default: break;
        }
    }
}