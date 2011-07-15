package bci.tools.bscan;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
	
public class BscanActivity extends Activity {
	
	String TAG = "BSCAN";
	int REQUEST_ENABLE_BT = 1;
	
	public void startUsingBT() {
		Intent intent = new Intent(this, BscanStartUsingBTActivity.class);
		startActivity(intent);
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == RESULT_OK) {
				Log.i(TAG, "Bluetooth enable OK.");
				// launch activity which uses Bluetooth
				startUsingBT();
			} else {
				Log.i(TAG, "Bluetooth enable NO.");
			}
		}
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.i(TAG, "Start Bluetooth test."); 
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
        	Log.i(TAG, "Device does not support Bluetooth");
        } else {
        	Log.i(TAG, "OK - Device supports Bluetooth");       	
        	
        	try {
        		if (!mBluetoothAdapter.isEnabled()) {
        			Log.i(TAG, "Enabling BlueTooth");
        			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        			Log.i(TAG, "Enabling BlueTooth - asked");
        		} else {
        			// launch activity which uses Bluetooth
        			startUsingBT();
        		}
        	} catch (Exception e) {
        		Log.i(TAG, "java.lang.SecurityException: " + e.getMessage());
        	}
        }
              
        Log.i(TAG, "End onCreate.");
    }
}