package eto.infosekolah;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import eto.helper.Geolocation;

public class ListSchoolActivity extends Activity {
	private int id;
	private String search;
	SchoolListAdapter adapter;
	ProgressDialog progress;

	private int attempt = 0;

	private String exceptionMessage;

	public void btnSearch_Click(View v) {
		String search = ((EditText) this.findViewById(R.id.search)).getText().toString();

		Intent i = new Intent(ListSchoolActivity.this, ListSchoolActivity.class);
		Bundle b = new Bundle();
		b.putInt("CategoryId", 4);
		b.putString("Search", search.trim());
		i.putExtras(b);
		ListSchoolActivity.this.startActivity(i);
		ListSchoolActivity.this.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.progress = new ProgressDialog(this);
		this.setContentView(R.layout.activity_list_school);

		Bundle b = this.getIntent().getExtras();
		this.id = b.getInt("CategoryId", 0);
		this.search = b.getString("Search");
		if (b.containsKey("Attempt")) this.attempt = b.getInt("Attempt", 0);
		if (this.search == null) this.search = "";

		Runnable r = new Runnable() {

			@Override
			public void run() {

				try {
					ListSchoolActivity.this.adapter = new SchoolListAdapter(ListSchoolActivity.this,
							R.layout.btnschoollist, ServiceHelper.GetSchoolList(ListSchoolActivity.this.id,
									Geolocation.getCurrentLocation(ListSchoolActivity.this).getLatitude(),
									Geolocation.getCurrentLocation(ListSchoolActivity.this).getLongitude(),
									ListSchoolActivity.this.search).items);
				} catch (Exception ex) {
					if (ListSchoolActivity.this.attempt < 10) {
						ListSchoolActivity.this.progress.dismiss();
						ListSchoolActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								String search = ((EditText) ListSchoolActivity.this.findViewById(R.id.search))
										.getText().toString();

								Intent i = new Intent(ListSchoolActivity.this, ListSchoolActivity.class);
								Bundle b = new Bundle();
								b.putInt("CategoryId", 4);
								b.putInt("Attempt", ListSchoolActivity.this.attempt + 1);
								b.putString("Search", search.trim());
								i.putExtras(b);
								ListSchoolActivity.this.startActivity(i);
								ListSchoolActivity.this.finish();
							}
						});
					} else {
						ListSchoolActivity.this.exceptionMessage = ex.getMessage();
						ListSchoolActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(
										ListSchoolActivity.this,
										"Cannot retrieve data. Reason: "
												+ ListSchoolActivity.this.exceptionMessage, Toast.LENGTH_LONG)
										.show();

								ListSchoolActivity.this.progress.dismiss();
							}
						});

						return;
					}
				}
				ListSchoolActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						ListView lvSchools = (ListView) ListSchoolActivity.this.findViewById(R.id.lvSchools);
						if (ListSchoolActivity.this.adapter != null)
							lvSchools.setAdapter(ListSchoolActivity.this.adapter);
						else
							Toast.makeText(
									ListSchoolActivity.this,
									"No data could be retrieved. Attempt: "
											+ (ListSchoolActivity.this.attempt + 1), Toast.LENGTH_LONG)
									.show();
					}
				});

				ListSchoolActivity.this.progress.dismiss();
			}
		};

		this.progress.setMessage("Calculating distance and retrieving data...");
		this.progress.setCancelable(false);
		this.progress.show();
		new Thread(r).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("About").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add("Help").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add("Exit").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

		return true;
	}

	public void onLvSchoolsClicked(View v) {
		// String schoolName = ((Button)v.findViewById(R.id.btnSelectSchool)).getText().toString();
		// UIHelper.showDialog(this, schoolName);

		School s = (School) v.getTag();
		Intent i = new Intent(this, SchoolMapActivity.class);
		i.putExtra("school", s);
		this.startActivity(i);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("About"))
			Toast.makeText(this, "About here later...", Toast.LENGTH_LONG).show();
		else if (item.getTitle().equals("Help"))
			Toast.makeText(this, "Help here later...", Toast.LENGTH_LONG).show();
		else if (item.getTitle().equals("Exit")) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}
}