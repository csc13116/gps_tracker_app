package com.example.gpstrackerapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.ScatteringByteChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public double latitude;
    public double longitude;
    public String targetName;
    public String childID;
    private LatLng trackingTarget;
    boolean isPermissionAsked = false;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private final long MIN_TIME = 10000; //1000 = 1 second
    private final long MIN_DISTANCE = 3; //3 meters

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (isPermissionAsked == false)
        {
            getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            isPermissionAsked = true;
        }

        if (mapFragment == null) {
            getFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            targetName = bundle.getString("CHILD_NAME_FOR_MAP");
            childID = bundle.getString("CHILD_ID_FOR_MAP");
            //Toast.makeText(getActivity(), targetName, Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(getActivity(), "Không lấy được tên !", Toast.LENGTH_LONG).show();
            targetName = "Bạn đang ở đây";
        }

        mapFragment.getMapAsync(this);
        //setRetainInstance(true);
        return mView;
    }

    private void getLocationForMap()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://dacnpm-backend.herokuapp.com/children/ping";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("latitude", String.valueOf(latitude));
            jsonBody.put("longitude", String.valueOf(longitude));
            jsonBody.put("id", childID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, 10000);
                //GET the location
                onMapReady(mMap);
                getLocationForMap();
            }
        }, 10000);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //GET the location
        //getRequestQueueForMap();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                // Add a marker & move camera
                mMap.clear();
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                trackingTarget = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(trackingTarget).title(targetName));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(trackingTarget));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackingTarget, 15)); //Zoom

                /*
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        String latitude = String.valueOf(location.getLatitude());
                        String longitude = String.valueOf(location.getLongitude());
                        params.put("latitude", latitude);
                        params.put("longitude", longitude);
                        params.put("children", childID);
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
                 */
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }
}
