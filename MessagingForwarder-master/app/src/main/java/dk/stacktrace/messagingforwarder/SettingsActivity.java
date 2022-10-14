package dk.stacktrace.messagingforwarder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity {
    private static final String TAG = IncomingMessageReceiver.class.getName();
    private static final int REQUEST_RECEIVE_SMS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Creating settings activity");
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Requesting permissions to receive SMS messages");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_RECEIVE_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int code, String permissions[], int[] results) {
        if (code != REQUEST_RECEIVE_SMS) {
            Log.w(TAG, "Unexpected request code received: " + code);
            return;
        }
        if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "RECEIVE_SMS permission granted. Will forward SMS messages");
        } else {
            Log.i(TAG, "RECEIVE_SMS permission denied. No messages will be forwarded");
        }
    }

    public static class SettingsFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
