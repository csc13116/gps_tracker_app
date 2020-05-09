package com.example.gpstrackerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public double latitude;
    public double longitude;
    public String targetName;
    

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
            //Toast.makeText(getActivity(), targetName, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), "Không lấy được tên !", Toast.LENGTH_LONG).show();
        }

        mapFragment.getMapAsync(this);

        return mView;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void getRequestQueueForMap() {
        String urlRequest = "https://dacnpm-backend.herokuapp.com/users/5e92c0641c9d44000027dae1/getchildrenping";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, urlRequest, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            latitude = Double.parseDouble(response.getString("latitude"));;
                            longitude = Double.parseDouble(response.getString("longitude"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Can not get coordinates !", Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //GET the location from server
        getRequestQueueForMap();

        // Add a marker in Sydney and move the camera
        LatLng trackingTarget = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(trackingTarget).title(targetName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trackingTarget));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(30));
    }
}