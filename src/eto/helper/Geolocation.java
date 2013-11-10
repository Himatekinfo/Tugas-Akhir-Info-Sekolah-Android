package eto.helper;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class Geolocation {
	public static Location getCurrentLocation(Activity a) {
		// Getting LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) a.getSystemService(Context.LOCATION_SERVICE);

		// Getting the name of the best provider
		String provider = locationManager.getBestProvider(new Criteria(), true);

		// Getting Current Location
		Location location = locationManager.getLastKnownLocation(provider);

		return location;
	}
}
