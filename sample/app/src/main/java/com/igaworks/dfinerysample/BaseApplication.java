package com.igaworks.dfinerysample;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.igaworks.dfinery.Dfinery;
import com.igaworks.dfinery.DfineryConfig;
import com.igaworks.dfinery.DfineryProperties;

import java.io.IOException;

public class BaseApplication extends Application {
    private static final String TAG = Utils.getTagFromClass(BaseApplication.class);
    @Override
    public void onCreate() {
        super.onCreate();
        DfineryConfig config = new DfineryConfig.Builder()
                .setLogEnable(true)
                .setLogLevel(Log.VERBOSE)
                .build();
        Dfinery.getInstance().init(this, "{service_id}", config);
        refreshGoogleAdvertisementId();
    }
    private void refreshGoogleAdvertisementId(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                    DfineryProperties.setGoogleAdvertisingId(idInfo.getId(), idInfo.isLimitAdTrackingEnabled());
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, e.toString());
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
        thread.start();
    }
}
