/*
 * BSCAN - Bluetooth scanning tool
 * 
 * Here a Standard GPL header (to add ...)
 * 
 * Gianluca Moro - giangiammy@gmail.com
 */

package bci.tools.bscan;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
	
public class BscanActivity extends Activity {
	
	String TAG = "BSCAN";
    // Return Intent extra
	int REQUEST_ENABLE_BT = 1;
	int REQUEST_DISCOVER_BT = 2;
	TextView isBTEnabledText;
	TextView isThereHwText;
	TextView currentlySelectedMac;
	
	public void doDeviceListActivity() {
		Log.i(TAG, "Starting DeviceListActivity");
		Intent intent = new Intent(this, DeviceListActivity.class);
		startActivityForResult(intent, REQUEST_DISCOVER_BT); 
	}
		
	public void doEnableBluetooth() {
		Log.i(TAG, "Starting Enable BlueTooth");
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(intent, REQUEST_ENABLE_BT);
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			String log;
			if (resultCode == RESULT_OK) {
				log = getResources().getText(R.string.yes_bluetooth_enabled).toString();
			} else {
				log = getResources().getText(R.string.no_bluetooth_enabled).toString();
			}
			Log.i(TAG, log);
			isBTEnabledText.setText(log);
		}
		
		if (requestCode == REQUEST_DISCOVER_BT) {
			if (resultCode == RESULT_OK) {
				String selectedMac = data.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				currentlySelectedMac = (TextView) findViewById(R.id.text_selected_mac);
				currentlySelectedMac.setText(getResources().getText(R.string.selected_mac).toString() + 
						" " + selectedMac);
			}
		}
		
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        isThereHwText = (TextView) findViewById(R.id.text_hwsupported);
        isBTEnabledText = (TextView) findViewById(R.id.text_swenabled);
        
        Log.i(TAG, "Start Bluetooth test."); 
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
        	Log.i(TAG, "Device does not support Bluetooth");
        	isThereHwText.setText(R.string.no_bluetooth_hardware);
        } else {
        	Log.i(TAG, "OK - Device supports Bluetooth");       	
        	isThereHwText.setText(R.string.yes_bluetooth_hardware);
        	try {
        		if (!mBluetoothAdapter.isEnabled()) {
        			isBTEnabledText.setText(R.string.no_bluetooth_enabled);
        			doEnableBluetooth();
        		} else {
        			isBTEnabledText.setText(R.string.yes_bluetooth_enabled);
        		}
        	} catch (Exception e) {
        		Log.i(TAG, "java.lang.SecurityException: " + e.getMessage());
        	}
        }
       
        // Initialize the button to enable Bluetooth
        Button enableButton = (Button) findViewById(R.id.button_enablehw);
        enableButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	doEnableBluetooth();
                //v.setVisibility(View.GONE);
            }
        });
        
        // Initialize the button to perform device discovery
        Button exploreButton = (Button) findViewById(R.id.button_explore);
        exploreButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	doDeviceListActivity();
                //v.setVisibility(View.GONE);
            }
        });
        
        Log.i(TAG, "End onCreate.");
    }
}