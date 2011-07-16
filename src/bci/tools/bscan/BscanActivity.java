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
	int REQUEST_ENABLE_BT = 1;
	TextView isBTEnabledText;
	
	public void startUsingBT() {
		//Intent intent = new Intent(this, BscanStartUsingBTActivity.class);
		Intent intent = new Intent(this, DeviceListActivity.class);
		startActivity(intent); 
	}
		
	public void enableBluetooth() {
		Log.i(TAG, "Enabling BlueTooth");
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		Log.i(TAG, "Enabling BlueTooth - asked");
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == RESULT_OK) {
				Log.i(TAG, "Bluetooth enable OK.");
				// launch activity which uses Bluetooth
				//startUsingBT();
				isBTEnabledText.setText(R.string.yes_bluetooth_enabled);
			} else {
				Log.i(TAG, "Bluetooth enable NO.");
				isBTEnabledText.setText(R.string.no_bluetooth_enabled);
			}
		}
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView isThereHwText = (TextView) findViewById(R.id.text_hwsupported);
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
        			enableBluetooth();
        		} else {
        			// launch activity which uses Bluetooth
        			//startUsingBT();
        			isBTEnabledText.setText(R.string.yes_bluetooth_enabled);
        		}
        	} catch (Exception e) {
        		Log.i(TAG, "java.lang.SecurityException: " + e.getMessage());
        	}
        }
            
        // Initialize the button to perform device discovery
        Button exploreButton = (Button) findViewById(R.id.button_explore);
        exploreButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startUsingBT();
                //v.setVisibility(View.GONE);
            }
        });

        
        
        // Initialize the button to perform device discovery
        Button enableButton = (Button) findViewById(R.id.button_enablehw);
        enableButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	enableBluetooth();
                //v.setVisibility(View.GONE);
            }
        });

        
        Log.i(TAG, "End onCreate.");
    }
}