package eto.infosekolah;

import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        ((TextView)this.findViewById(R.id.txtMapCopyright)).setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getApplicationContext()));
    }
}
