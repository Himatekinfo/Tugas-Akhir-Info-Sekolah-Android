package eto.infosekolah;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SchoolListAdapter extends ArrayAdapter<School> {

	private final List<School> items;
	private final int layoutResourceId;
	private final Context context;

	public SchoolListAdapter(Context context, int layoutResourceId, List<School> objects) {
		super(context, layoutResourceId, objects);

		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
		row = inflater.inflate(this.layoutResourceId, parent, false);

		School school = this.items.get(position);

		/* Item list control values */
		Button btnSelectSchool = (Button) row.findViewById(R.id.btnSelectSchool);
		btnSelectSchool.setTag(school);
		btnSelectSchool.setText(school.Name);

		TextView txtAkreditasi = (TextView) row.findViewById(R.id.txtAlamat);
		txtAkreditasi.setText(school.Address);

		TextView txtJarak = (TextView) row.findViewById(R.id.txtJarak);
		txtJarak.setText(school.Distance);

		TextView txtWaktu = (TextView) row.findViewById(R.id.txtWaktu);
		txtWaktu.setText(String.valueOf(Math.round((Double.valueOf(school.Distance) / 40) * 60)));

		return row;
	}
}
