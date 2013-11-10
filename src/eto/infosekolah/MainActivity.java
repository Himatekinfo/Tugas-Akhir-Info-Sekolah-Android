package eto.infosekolah;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	final Context context = this;

	public void btnAbout_Click(View v) {
		if (v.getId() == R.id.btnAbout) {
			Intent i = new Intent(this.context, AboutActivity.class);
			this.startActivity(i);
		}
	}

	public void btnExit_Click(View v) {
		if (v.getId() == R.id.btnExit) {
			System.runFinalization();
			System.exit(0);
		}
	}

	public void btnSchools_Click(View v) {
		int[] schools = { R.id.btnTk, R.id.btnSd, R.id.btnSmp, R.id.btnSma, R.id.btnSmk };
		for (int btn : schools)
			if (v.getId() == btn) {
				Intent i = new Intent(this.context, ListSchoolActivity.class);
				Bundle b = new Bundle();
				b.putInt("CategoryId", Integer.parseInt(v.findViewById(btn).getTag().toString()));
				i.putExtras(b);
				this.startActivity(i);
			}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		// Since a change in the logic, and it might be temporary, lets skip this page directly for now
		Intent i = new Intent(this.context, ListSchoolActivity.class);
		Bundle b = new Bundle();
		b.putInt("CategoryId", 4);
		i.putExtras(b);
		this.startActivity(i);
		this.finish();
	}
}
