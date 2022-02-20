package com.it084.breastcancerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PredictActivity extends AppCompatActivity {
    EditText radius_mean,texture_mean,perimeter_mean,area_mean,smoothness_mean,compactness_mean,concavity_mean,cpoints_mean;
    Button btnpredict;
    TextView predictans;
    String url ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        radius_mean=(EditText) findViewById(R.id.radius_mean);
        texture_mean=(EditText) findViewById(R.id.texture_mean);
        perimeter_mean=(EditText) findViewById(R.id.perimeter_mean);
        area_mean=(EditText) findViewById(R.id.area_mean);
        smoothness_mean=(EditText) findViewById(R.id.smoothness_mean);
        compactness_mean=(EditText) findViewById(R.id.compactness_mean);
        concavity_mean=(EditText) findViewById(R.id.concavity_mean);
        cpoints_mean=(EditText) findViewById(R.id.cpoints_mean);
        btnpredict=(Button) findViewById(R.id.btnpredict);
        predictans=(TextView) findViewById(R.id.predictanstext);

        btnpredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String result=jsonObject.getString("result");
                            if (result.equals("Malignant")){
                                predictans.setText("Malignant");
                            }
                            else{
                                predictans.setText("Benign");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PredictActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("radius mean",radius_mean.getText().toString());
                        params.put("texture mean",texture_mean.getText().toString());
                        params.put("perimeter mean",perimeter_mean.getText().toString());
                        params.put("area mean",area_mean.getText().toString());
                        params.put("smoothness mean",smoothness_mean.getText().toString());
                        params.put("cpmoactness mean",compactness_mean.getText().toString());
                        params.put("concavity mean",concavity_mean.getText().toString());
                        params.put("cpoints mean",cpoints_mean.getText().toString());

                        return params;

                    }
                };
                RequestQueue queue= Volley.newRequestQueue(PredictActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}