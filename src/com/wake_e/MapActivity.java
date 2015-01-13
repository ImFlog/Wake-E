package com.wake_e;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.test_map);

	GoogleMapOptions options = new GoogleMapOptions();
	options.mapType(GoogleMap.MAP_TYPE_NORMAL).compassEnabled(true)
	.rotateGesturesEnabled(true).tiltGesturesEnabled(true).mapToolbarEnabled(true);

	//MapFragment mapFragment = MapFragment.newInstance(options);
	MapFragment mapFragment = (MapFragment) getFragmentManager()
	.findFragmentById(R.id.map);
	mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
	LatLng sydney = new LatLng(-33.867, 151.206);

	map.setMyLocationEnabled(true);
	map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

	map.addMarker(new MarkerOptions()
	.title("Sydney")
	.snippet("The most populous city in Australia.")
	.position(sydney));
    }
}
