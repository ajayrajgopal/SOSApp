package com.example.ajay.distressapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RelativeLayout callambulance=(RelativeLayout)findViewById(R.id.callambulance);
        callambulance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),CallService.class);
                intent.putExtra("title","Ambulance Service");
                startActivity(intent);
            }
        });
        ImageButton ambulanceimage=(ImageButton)findViewById(R.id.ambulanceimage);
        ambulanceimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),CallService.class);
                intent.putExtra("title","Ambulance Service");
                startActivity(intent);
            }
        });
        RelativeLayout callfiretruck=(RelativeLayout)findViewById(R.id.callfiretruck);
        callfiretruck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),CallService.class);
                intent.putExtra("title","Fire Service");
                startActivity(intent);
            }
        });
        ImageButton firetruckimage=(ImageButton)findViewById(R.id.firetruckimage);
        firetruckimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),CallService.class);
                intent.putExtra("title","Fire Service");
                startActivity(intent);
            }
        });
        RelativeLayout callpolice=(RelativeLayout)findViewById(R.id.callpolice);
        callpolice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),CallService.class);
                intent.putExtra("title","Police");
                startActivity(intent);
            }
        });
        ImageButton policeimage=(ImageButton)findViewById(R.id.policeimage);
        policeimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),CallService.class);
                intent.putExtra("title","Police");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
