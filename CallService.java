package com.example.ajay.distressapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ajay on 21/8/16.
 */
public class CallService extends ActionBarActivity implements LocationListener {

    LocationManager locationManager;
    String provider;

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> placeid = new ArrayList<String>();
    ArrayList<String> phone = new ArrayList<String>();
    ArrayList<String> lat = new ArrayList<String>();
    ArrayList<String> lng = new ArrayList<String>();
    ArrayList<TableRow> row = new ArrayList<TableRow>();

    TableRow tr;
    int n, x, j = 0;
    String cont, ptype = "";
    String[] pname, paddress, plat, plng, pid, pphone;
    int flag = 0;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_service);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        String title = getIntent().getStringExtra("title");
        SpannableString s = new SpannableString(title);
        getSupportActionBar().setTitle(s);
        TextView tv = (TextView) findViewById(R.id.list);

        tv.setMovementMethod(new ScrollingMovementMethod());
        TextView helpline = (TextView) findViewById(R.id.helplinename);
        ImageButton call = (ImageButton) findViewById(R.id.callhelpline);
        if (title.equalsIgnoreCase("Ambulance Service")) {
            helpline.setText("Medical Helpline");

            ptype = "hospital";
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:108"));
                    startActivity(intent);
                }
            });
        } else if (title.equalsIgnoreCase("Fire Service")) {
            helpline.setText("Fire Service");

            ptype = "fire_station";
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:102"));
                    startActivity(intent);
                }
            });
        } else {
            helpline.setText("Police Control Room");

            ptype = "police";
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:100"));
                    startActivity(intent);
                }
            });
        }
        // Getting LocationManager object
        if (flag == 0)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (flag == 0) provider = locationManager.getBestProvider(criteria, false);
            if (provider != null && !provider.equals("")) {

                // Get the location from the given provider
                if (flag == 0) {
                    flag = 1;
                    Location location = locationManager.getLastKnownLocation(provider);
                    locationManager.requestLocationUpdates(provider, 300, 1000, this);
                    if (location != null) {
                        onLocationChanged(location);
                    } else {
                        tv = (TextView) findViewById(R.id.list);
                        tv.setText("Location can't be retrived");
                    }
                }
            } else {
                tv = (TextView) findViewById(R.id.list);
                tv.setText("No Provider Found");
            }
        } else {
            tv = (TextView) findViewById(R.id.list);
            tv.setText("Please give location permission and retry.");
            permissioncheck();
        }

    }

    public void permissioncheck() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        }
    }

    public void onLocationChanged(Location location) {
        // Getting reference to TextView tv_longitude
        TextView tv = (TextView) findViewById(R.id.list);
        location.getAccuracy();
        // Setting Current Longitude
        tv.setText("Longitude:" + location.getLongitude() + " Latitude:" + location.getLatitude());
        if (ptype.equals("hospital"))
            new LocationDet().execute("https://maps.googleapis.com/maps/api/place/search/json?rankby=distance&orderby=distance&location=" + location.getLatitude() + "," + location.getLongitude() + "&sensor=false&type=" + ptype + "&name=" + ptype + "&key=AIzaSyBcOYgK6Uhk0ImB8cWmpjC-B0kN0aWXNc8");
        else
            new LocationDet().execute("https://maps.googleapis.com/maps/api/place/search/json?rankby=distance&orderby=distance&location=" + location.getLatitude() + "," + location.getLongitude() + "&sensor=false&type=" + ptype + "&key=AIzaSyBcOYgK6Uhk0ImB8cWmpjC-B0kN0aWXNc8");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.removeUpdates(this);
    }

    public class LocationDet extends AsyncTask<String, String, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setUseCaches(true);
                connection.addRequestProperty("Cache-Control", "max-age=60");
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String json = buffer.toString();

                JSONObject parentObject = new JSONObject(json);
                JSONArray parentArray = parentObject.getJSONArray("results");
                int i;
                for (i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    name.add(finalObject.getString("name"));
                    address.add(finalObject.getString("vicinity"));
                    placeid.add(finalObject.getString("place_id"));
                    JSONObject geo = finalObject.getJSONObject("geometry");
                    JSONObject loc = geo.getJSONObject("location");
                    lat.add(loc.getString("lat"));
                    lng.add(loc.getString("lng"));
                }
                n=i;
                pname = new String[name.size()];
                pname = name.toArray(pname);

                pid = new String[placeid.size()];
                pid = placeid.toArray(pid);

                paddress = new String[address.size()];
                paddress = address.toArray(paddress);

                plat = new String[lat.size()];
                plat = lat.toArray(plat);

                plng = new String[lng.size()];
                plng = lng.toArray(plng);

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected  void onPostExecute(String[] result) {

            super.onPostExecute(result);

            TextView tv = (TextView)findViewById(R.id.list);
            for(int i=0;i<n;i++){
                new GetPhone().execute("https://maps.googleapis.com/maps/api/place/details/json?placeid="+pid[i]+"&key=AIzaSyBcOYgK6Uhk0ImB8cWmpjC-B0kN0aWXNc8");
            }
         /*
            for(int i=0;i<n;i++){
                cont= ""+tv.getText();
                cont = cont + "\n" + pname[i] + "\n" + paddress[i] + "\n" + plat[i] + "," + plng[i] + "\n"+pid[i]+"\n"+x+"\n";
                tv.setText(cont);
            }
            */


        }
    }

    public class GetPhone extends AsyncTask<String, String, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setUseCaches(true);
                connection.addRequestProperty("Cache-Control", "max-age=0");
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String json = buffer.toString();
                JSONObject parentObject = new JSONObject(json);
                JSONObject res = parentObject.getJSONObject("result");

                phone.add(res.getString("formatted_phone_number"));
                pphone = new String[phone.size()];
                pphone = phone.toArray(pphone);
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected  void onPostExecute(String[] result) {
            super.onPostExecute(result);
            TextView tv = (TextView)findViewById(R.id.list);
            try{
               // tv.setText(cont);
                table(j);
            }catch (ArrayIndexOutOfBoundsException e){

            }
            j++;
            /*CharSequence text = ""+x;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();*/
        }
    }
    public void table(final int o){
        TableLayout tl= (TableLayout)findViewById(R.id.table);
        cont =pname[o];
        tr=new TableRow(this);
        TextView title= new TextView(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int)(metrics.xdpi);
        int density=(int)metrics.density;
        title.setLayoutParams((new TableRow.LayoutParams(width*density, TableRow.LayoutParams.WRAP_CONTENT)));

        title.setText(cont);

        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        ImageButton callbtn=new ImageButton(this);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+pphone[o]));
                startActivity(intent);
            }
        });
        callbtn.setLayoutParams((new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)));
        callbtn.setImageResource(R.drawable.callanswer);
        callbtn.setScaleType(ImageView.ScaleType.FIT_XY);
        callbtn.setBackgroundResource(R.color.background);

        tr.addView(title);
        tr.addView(callbtn);
        tl.addView(tr);

        TextView tv = (TextView)findViewById(R.id.list);
        tv.setText(""+width);

    }
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
