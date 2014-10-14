package com.abbiya.proximity.proximity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import com.proximity5.px5.sdk.PX5EventListener;
import com.proximity5.px5.sdk.PX5Foreground;
import com.proximity5.px5.sdk.PX5LocationManager;
import com.proximity5.px5.sdk.PX5NotificationManager;
import com.proximity5.px5.sdk.PX5RootActivity;


public class MyActivity extends PX5RootActivity implements PX5EventListener {

    PX5LocationManager px5LocationManager;
    PX5NotificationManager px5NotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        if (this.px5NotificationManager.getInstance() == null || this.px5LocationManager.getInstance() == null) {
            String px5apiKey = "ce2252632249957f61253e04bfcbdf32a6d79bca";
            String px5apiSecret = "2360c07042e49ab4187f731475b62c016bff78e0";
            String px5apiProjectId = "1412552073";
            String gooleGCMProject = "457604416822"; //SET GOOGLE PROJECT NUMBER FOR GCM PUSH
            this.px5NotificationManager = PX5NotificationManager.getInstance(this,
                    px5apiKey,
                    px5apiSecret,
                    px5apiProjectId,
                    gooleGCMProject,
                    R.drawable.ic_launcher,
                    R.layout.activity_px5_dialog
            );
            this.px5NotificationManager.registerPX5EventListener(this);

            this.px5LocationManager = PX5LocationManager.getInstance(this, px5apiKey, px5apiSecret, px5apiProjectId);
            String uniqueID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            this.px5LocationManager.setPx5IdentityExternalID(uniqueID);

            //SETTING FOR WHEN IN RANGE OF ACTIVE BEACON AREA AND APP IN BACKGROUND
            //this.px5LocationManager.setActiveBackgroundBetweenScanPeriod(5100l); //OPTIONAL - 5 seconds

            //SETTING APP IN BACKGROUND
            //this.px5LocationManager.setBackgroundBetweenScanPeriod(15100l); //OPTIONAL 15 seconds

            //this.px5LocationManager.setLoggingFlag(false); //OPTIONAL
            this.px5LocationManager.initialize();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            px5LocationManager.restart();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            px5LocationManager.unload();
        } catch (Exception e) {

        }
    }

    @Override
    public void px5EventUpdate(Bundle bundle) {

        boolean usewebview = false;
        if (usewebview) {
            //Bundle contains "url" key for opening urls
            //Intent intent = new Intent(this, SomeWebViewActivity.class);
            //intent.putExtras(bundle);
            //this.startActivity(intent);
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bundle.getString("url")));
            this.startActivity(browserIntent);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
