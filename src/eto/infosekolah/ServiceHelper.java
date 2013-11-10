package eto.infosekolah;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import eto.helper.Geolocation;
import eto.infosekolah.Category.Categories;
import eto.infosekolah.School.Schools;

public class ServiceHelper {
	public static String BaseUrl = "http://172.16.24.222/svc_infosekolah/site/"; // "http://jun.mugipriangan.org/site/";

	public static Categories GetCategoryList() {
		return (Categories) ServiceHelper.ProcessUrl(ServiceHelper.BaseUrl + "categorylist/",
				Categories.class);
	}

	public static Schools GetSchoolList(Activity a) {
		return GetSchoolList(0, Geolocation.getCurrentLocation(a).getLatitude(), Geolocation
				.getCurrentLocation(a).getLongitude(), "");
	}

	public static Schools GetSchoolList(int CategoryId, double Latitude, double Longitude, String search) {
		String url = ServiceHelper.BaseUrl + "schoollist/"
				+ (CategoryId == 0 ? "" : String.valueOf(CategoryId)) + "?lat=" + String.valueOf(Latitude)
				+ "&lng=" + String.valueOf(Longitude) + "&search=" + search;
		Log.i("Url being called", url);
		return (Schools) ServiceHelper.ProcessUrl(url, Schools.class);
	}

	private static Object ProcessUrl(String Url, Class<?> ReturnType) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(Url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null)
					builder.append(line);

				Gson gson = new Gson();
				return gson.fromJson("{\"items\":" + builder.toString() + "}", ReturnType);
			} else
				Log.e("Http Error", "Failed to download file. Reason: " + statusLine.getReasonPhrase());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("IO Error", e.getMessage());
			e.printStackTrace();
		}

		return null;
	}
}
