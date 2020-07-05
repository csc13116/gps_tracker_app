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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryMapActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public double latitude;
    public double longitude;
    public double latitude1;
    public double longitude1;
    public double finalLat;
    public double finalLong;
    public String targetName;
    public String childID;
    private LatLng trackingTarget;
    ArrayList<LatLng> locationArrayList = new ArrayList<>();

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_history_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            getFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            targetName = bundle.getString("CHILD_NAME_FOR_MAP");
            childID = bundle.getString("CHILD_ID_FOR_MAP");
        }
        else
        {
            targetName = "Bạn đang ở đây";
        }

        mapFragment.getMapAsync(this);
        //setRetainInstance(true);
        return mView;
    }

    private void getLocationForMap()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://dacnpm-backend.herokuapp.com/children/" + childID + "/pings";

        /*
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jArr = response.getJSONArray("savedPosition");
                        for (int i = 0; i < jArr.length() - 1; i++)
                        {
                            JSONObject jObj = jArr.getJSONObject(i);
                            latitude = Double.parseDouble(jObj.getString("latitude"));
                            longitude = Double.parseDouble(jObj.getString("longitude"));
                            for(int j = 1; j < jArr.length(); j++)
                            {
                                JSONObject jObj1 = jArr.getJSONObject(j);
                                latitude1 = Double.parseDouble(jObj1.getString("latitude"));
                                longitude1 = Double.parseDouble(jObj1.getString("longitude"));

                                //Draw line
                                mMap.addPolyline(new PolylineOptions()
                                        .add(new LatLng(latitude, longitude), new LatLng(latitude1, longitude1))
                                        .width(1)
                                        .color(Color.RED));;
                            }
                        }
                        JSONObject jObjFinal = jArr.getJSONObject(jArr.length());
                        finalLat = Double.parseDouble(jObjFinal.getString("latitude"));
                        finalLong = Double.parseDouble(jObjFinal.getString("longitude"));
                        onMapReady(mMap);
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
         */

        try {
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Toast.makeText(getActivity(), (CharSequence) response, Toast.LENGTH_LONG).show();
                        JSONArray jArr = new JSONArray(response);
                        for (int i = 0; i < jArr.length() - 1; i++)
                        {
                            JSONObject jObj = jArr.getJSONObject(i);
                            latitude = Double.parseDouble(jObj.getString("latitude"));
                            longitude = Double.parseDouble(jObj.getString("longitude"));
                            for(int j = 1; j < jArr.length(); j++)
                            {
                                JSONObject jObj1 = jArr.getJSONObject(j);
                                latitude1 = Double.parseDouble(jObj1.getString("latitude"));
                                longitude1 = Double.parseDouble(jObj1.getString("longitude"));

                                //Draw line
                                mMap.addPolyline(new PolylineOptions()
                                        .add(new LatLng(latitude, longitude), new LatLng(latitude1, longitude1))
                                        .width(1)
                                        .color(Color.RED));;
                            }
                        }
                        JSONObject jObjFinal = jArr.getJSONObject(jArr.length());
                        finalLat = Double.parseDouble(jObjFinal.getString("latitude"));
                        finalLong = Double.parseDouble(jObjFinal.getString("longitude"));
                        onMapReady(mMap);
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
        if (finalLat != 0 && finalLong != 0)
        {
            trackingTarget = new LatLng(finalLat, finalLong);
            mMap.addMarker(new MarkerOptions().position(trackingTarget).title(targetName));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(trackingTarget));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackingTarget, 15)); //Zoom
        }

    }
}
