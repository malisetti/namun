package com.abbiya.proximity.proximity;

import android.app.Application;
import com.proximity5.px5.sdk.PX5Foreground;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

/**
 * Created by seshachalam on 14/10/14.
 */
public class ProximityApplication extends Application {
    private BackgroundPowerSaver backgroundPowerSaver;

    public void onCreate(){
        super.onCreate();
        PX5Foreground.init(this);
        backgroundPowerSaver = new BackgroundPowerSaver(this);
    }

}
