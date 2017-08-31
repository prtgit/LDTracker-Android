package com.example.prashanttanksali.csc750_project1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.security.Timestamp;

public class MainActivity extends AppCompatActivity {
    EditText txtIpAddress, txtUserName;
    Button btnStart;
    TextView jsonOutput,txtLatitude,txtLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtIpAddress= (EditText) findViewById(R.id.txtIpAddress);
        txtUserName = (EditText)findViewById(R.id.txtUsername);
        btnStart = (Button) findViewById(R.id.btnStart);
        jsonOutput = (TextView) findViewById(R.id.txtJsonOutput);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        /*start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });*/
    }
    public void onStartButtonClick(View view){
        String ipAddress = String.valueOf(txtIpAddress.getText());
        String jsonUrl = "http://"+ipAddress+"/locationUpdate";
        String userName = String.valueOf(txtUserName.getText());
        JSONObject json = new JSONObject();

        GPSLocator g = new GPSLocator(getApplicationContext());
        Location l = g.getLocation();


        System.out.print(String.valueOf(l.getLatitude()));
        txtLatitude.setText(String.valueOf(l.getLatitude()));
        txtLongitude.setText(String.valueOf(l.getLongitude()));
        try{
            json.put("ipAddress",ipAddress);
            json.put("userName",userName);
            json.put("latitude",""+l.getLatitude());
            json.put("longitude",""+l.getLongitude());
            json.put("timestamp",""+System.currentTimeMillis());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        jsonOutput.setText(String.valueOf(""+json));
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,jsonUrl,""+json,
                                        new Response.Listener<JSONObject>(){
                                            @Override
                                            public void onResponse(JSONObject response){
                                                Toast.makeText(getApplicationContext(),"Response received ="+response,Toast.LENGTH_SHORT).show();
                                            }
                                        },new Response.ErrorListener(){
                                            @Override
                                            public void onErrorResponse(VolleyError error){
                                                Toast.makeText(getApplicationContext(),"Error in request",Toast.LENGTH_LONG).show();
                                                error.printStackTrace();
                                            }
                                        });

        SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonRequest);

    }

}
