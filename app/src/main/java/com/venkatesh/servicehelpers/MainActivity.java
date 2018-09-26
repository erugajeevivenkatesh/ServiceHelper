    package com.venkatesh.servicehelpers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

    public class MainActivity extends AppCompatActivity {

        List<BluetoothObject> availableDevices=new ArrayList<BluetoothObject>();
        private BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button OnBlueToth,OFBlueTOoth;
      //  List<Object> availableDevices=new ArrayList<Object>();
        OnBlueToth=findViewById(R.id.Onbluetooth);
        OFBlueTOoth=findViewById(R.id.OfBlueTooth);

        final BluetoothAdapter bAdapeter=BluetoothAdapter.getDefaultAdapter();
        OnBlueToth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bAdapeter==null)
                Toast.makeText(MainActivity.this,"BlueTooth Not supported" , Toast.LENGTH_SHORT).show();
                else
                {
                    if(!bAdapeter.isEnabled())
                    {
                        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),1);
                        Toast.makeText(MainActivity.this, "BlueTooth TUrned On", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        OFBlueTOoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bAdapeter.disable();
                Toast.makeText(MainActivity.this, "BlueTooth Turned OF", Toast.LENGTH_SHORT).show();
            }
        });

    }
        private void DisplayListofDevices()
        {
            mBluetoothAdapter.startDiscovery();
            final BroadcastReceiver mReciver=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action =intent.getAction();
                    if(BluetoothDevice.ACTION_FOUND.equals(action))
                    {
                        //Get the bluetoothDevice object from the Intent
                        BluetoothDevice DeviceObject=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        BluetoothObject bluetoothObject=new BluetoothObject();
                        bluetoothObject.setSetBluetoothName(DeviceObject.getName());
                        availableDevices.add(bluetoothObject);
                    }
                }
            };
            IntentFilter ifilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReciver,ifilter);

        }

}
