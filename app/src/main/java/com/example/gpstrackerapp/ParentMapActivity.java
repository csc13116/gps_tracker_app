package com.example.gpstrackerapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParentMapActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public double latitude;
    public double longitude;
    public String targetName;
    public String childID;
    private LatLng trackingTarget;

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            getFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            targetName = bundle.getString("CHILD_NAME_FOR_MAP");
            childID = bundle.getString("CHILD_ID_FOR_MAP");
        } else {
            targetName = "Con bạn đang ở đây";
        }

        mapFragment.getMapAsync(this);
        //setRetainInstance(true);
        return mView;
    }

    private void getLocationForMap()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://dacnpm-backend.herokuapp.com/children/" + childID + "/ping";//"https://dacnpm-backend.herokuapp.com/children/5e92c4b13d0b35496c7722f6/ping";//

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        latitude = Double.parseDouble(response.getString("latitude"));
                        longitude = Double.parseDouble(response.getString("longitude"));
                        //Toast.makeText(getActivity(), childID, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                handler.postDelayed(runnable, 5000);
                //GET the location
                getLocationForMap();
                onMapReady(mMap);
            }
        }, 5000);
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
        if (latitude != 0 && longitude != 0)
        {
            mMap.clear();
            trackingTarget = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(trackingTarget).title(targetName));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(trackingTarget));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackingTarget, 15)); //Zoom
        }

    }
}
