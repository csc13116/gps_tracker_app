package com.example.gpstrackerapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryMapActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    /*
    public double latitude;
    public double longitude;
    public double finalLat;
    public double finalLong;

     */

    public String targetName;
    public String childId;
    public Marker markerName;
    private List<Marker> allMarkers = new ArrayList<>();

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_history_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            getFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }

        getLocationForMap();

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            childId = bundle.getString("CHILD_ID_FOR_MAP");
        }

        mapFragment.getMapAsync(this);
        //setRetainInstance(true);


        return mView;
    }

    private void getLocationForMap()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://dacnpm-backend.herokuapp.com/children/" + childId + "/pings";
        //Toast.makeText(getActivity(), url, Toast.LENGTH_LONG).show();

        try {

            JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++)
                    {
                        try {
                            if (response.getJSONObject(i) != null)
                            {
                                try {
                                    JSONObject jObj = response.getJSONObject(i);
                                    if (jObj.getString("latitude") != null && jObj.getString("longitude") != null)
                                    {

                                        double latitude = Double.parseDouble(response.getJSONObject(i-1).getString("latitude"));
                                        double longitude = Double.parseDouble(response.getJSONObject(i-1).getString("longitude"));
                                        double finalLat = Double.parseDouble(response.getJSONObject(i).getString("latitude"));
                                        double finalLong = Double.parseDouble(response.getJSONObject(i).getString("longitude"));

                                        //Draw line
                                        mMap.addPolyline(new PolylineOptions()
                                                .add(new LatLng(latitude, longitude), new LatLng(finalLat, finalLong))
                                                .width(3)
                                                .color(Color.GREEN));
                                        //onMapReady(mMap);

                                        double xLat = Double.parseDouble(response.getJSONObject(0).getString("latitude"));
                                        double xLong = Double.parseDouble(response.getJSONObject(0).getString("longitude"));
                                        markerOnMap(mMap, xLat, xLong);

                                    }
                                    else
                                    {
                                        onResume();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            requestQueue.add(jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, 5000);
                //GET the location
            }
        }, 5000);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }
    */

    private void removeMarkers() {
        for (Marker marker: allMarkers)
        {
            marker.remove();
        }
        allMarkers.clear();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        /*
        if (finalLat != 0 && finalLong != 0)
        {
            LatLng trackingTarget = new LatLng(finalLat, finalLong);
            MarkerOptions mLocationMarker = new MarkerOptions().position(trackingTarget).title(targetName);

            if (allMarkers.size() != 0)
            {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackingTarget, 16)); //Zoom
                allMarkers.clear();
            }
            markerName = mMap.addMarker(mLocationMarker);
            allMarkers.add(markerName);


            //mMap.addMarker(mLocationMarker);
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(trackingTarget));
        }


         */
    }

    private void markerOnMap(GoogleMap googleMap, double mlat, double mlong) {
        mMap = googleMap;
        LatLng trackingTarget = new LatLng(mlat, mlong);
        MarkerOptions mLocationMarker = new MarkerOptions().position(trackingTarget).title(targetName);
        removeMarkers();
        markerName = mMap.addMarker(mLocationMarker);
        allMarkers.add(markerName);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackingTarget, 16)); //Zoom
    }
}
