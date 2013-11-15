package eto.infosekolah;

import java.text.NumberFormat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SchoolMapActivity extends FragmentActivity {
	final Context context = this;
	GoogleMap map;
	private School s;

	public void btnDirection_click(View v) {
		Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q="
				+ this.s.Latitude + "," + this.s.Longitude));
		this.startActivity(i);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_map);

		this.s = (School) this.getIntent().getExtras().get("school");

		this.map = ((SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map))
				.getMap();

		TextView txtSchoolName = (TextView) this.findViewById(R.idMap.SchoolName);
		txtSchoolName.setText(SchoolMapActivity.this.s.Name);

		TextView txtDistance = (TextView) this.findViewById(R.idMap.Distance);
		txtDistance.setText(SchoolMapActivity.this.s.Distance);

		TextView txtTime = (TextView) this.findViewById(R.idMap.Time);
		txtTime.setText(String.valueOf(Math.round((Double.valueOf(SchoolMapActivity.this.s.Distance) * 40) / 60)));

		TextView txtAkreditasi = (TextView) this.findViewById(R.idMap.Akreditasi);
		txtAkreditasi.setText(SchoolMapActivity.this.s.Accreditation);

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);

		TextView txtInitialCost = (TextView) this.findViewById(R.idMap.InitialCost);
		txtInitialCost.setText(nf.format(Long.parseLong(SchoolMapActivity.this.s.InitialCost)));

		TextView txtPeriodicalCost = (TextView) this.findViewById(R.idMap.PeriodicalCost);
		txtPeriodicalCost.setText(nf.format(Long.parseLong(SchoolMapActivity.this.s.PeriodicalCost)));

		TextView txtAlamat = (TextView) this.findViewById(R.idMap.Alamat);
		txtAlamat.setText(SchoolMapActivity.this.s.Address);

		TextView txtDetails = (TextView) this.findViewById(R.idMap.Details);
		if (SchoolMapActivity.this.s.CostDetails.contains(","))
			txtDetails.setText(SchoolMapActivity.this.s.CostDetails);
		else
			txtDetails.setText("-");

		TextView txtLink = (TextView) this.findViewById(R.idMap.Link);
		txtLink.setText(SchoolMapActivity.this.s.Website);
		txtLink.setLinksClickable(true);
		txtLink.setClickable(true);
		txtLink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView) v).getText()
						.toString()));
				SchoolMapActivity.this.startActivity(browserIntent);
			}
		});

		if (this.map == null)
			Toast.makeText(this, "Google Maps not available", Toast.LENGTH_LONG).show();
		else {
			this.map.setMyLocationEnabled(true);
			this.map.getMyLocation();
			LatLng myLatLng = new LatLng(Double.parseDouble(this.s.Latitude),
					Double.parseDouble(this.s.Longitude));

			CameraPosition myPosition = new CameraPosition.Builder().target(myLatLng).zoom(12).build();
			this.map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));

			this.map.addMarker(new MarkerOptions()
					.position(
							new LatLng(Double.parseDouble(this.s.Latitude), Double
									.parseDouble(this.s.Longitude))).title(this.s.Name).draggable(false)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

			// this.map.setInfoWindowAdapter(new InfoWindowAdapter() {
			//
			// @Override
			// public View getInfoContents(Marker arg0) {
			// // TODO Auto-generated method stub
			// return null;
			// }
			//
			// @Override
			// public View getInfoWindow(Marker arg0) {
			// Toast.makeText(SchoolMapActivity.this, "Hi", Toast.LENGTH_LONG).show();
			// View v = SchoolMapActivity.this.getLayoutInflater().inflate(R.layout.screen_map, null);
			//
			// TextView txtSchoolName = (TextView) v.findViewById(R.idMap.SchoolName);
			// txtSchoolName.setText(SchoolMapActivity.this.s.Name);
			//
			// TextView txtDistance = (TextView) v.findViewById(R.idMap.Distance);
			// txtDistance.setText(SchoolMapActivity.this.s.Distance);
			//
			// TextView txtTime = (TextView) v.findViewById(R.idMap.Time);
			// txtTime.setText(String.valueOf(Math.round((Double
			// .valueOf(SchoolMapActivity.this.s.Distance) * 40) / 60)));
			//
			// TextView txtAkreditasi = (TextView) v.findViewById(R.idMap.Akreditasi);
			// txtAkreditasi.setText(SchoolMapActivity.this.s.Accreditation);
			//
			// TextView txtInitialCost = (TextView) v.findViewById(R.idMap.InitialCost);
			// txtInitialCost.setText(SchoolMapActivity.this.s.InitialCost);
			//
			// TextView txtPeriodicalCost = (TextView) v.findViewById(R.idMap.PeriodicalCost);
			// txtPeriodicalCost.setText(SchoolMapActivity.this.s.PeriodicalCost);
			//
			// TextView txtLink = (TextView) v.findViewById(R.idMap.Link);
			// txtLink.setText(SchoolMapActivity.this.s.Website);
			//
			// // Toast.makeText(SchoolMapActivity.this, "there", Toast.LENGTH_LONG).show();
			// return v;
			// }
			// });
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// this.getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
