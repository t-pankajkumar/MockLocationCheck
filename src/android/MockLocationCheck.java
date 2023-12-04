package cordova.plugin.mocklocationcheck;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;


import java.util.List;
import android.location.Location;
import android.location.LocationManager;

import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class MockLocationCheck extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // Your initialization code here
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("isMockLocationOn".equals(action)) {
            Context context = cordova.getActivity().getApplicationContext();
            boolean isMockLocationOn = isMockLocationEnabled();
            callbackContext.success(isMockLocationOn ? 1 : 0);
            return true;
        }
        return false;
    }

    private boolean isMockLocationEnabled() throws JSONException{
        LocationManager locationManager = (LocationManager) cordova.getActivity().getSystemService(android.content.Context.LOCATION_SERVICE);

        if (locationManager != null) {
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(Build.VERSION.SDK_INT <= 30)
                    return location != null && location.isFromMockProvider();
                else if(Build.VERSION.SDK_INT >= 31)
                    return location != null && location.isMock();
            } catch (SecurityException e) {
                // Handle the exception if the app doesn't have the necessary permissions
                return false;
            }
        }
        return false;
    }
}
