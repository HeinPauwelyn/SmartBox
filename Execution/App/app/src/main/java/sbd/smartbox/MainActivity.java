package sbd.smartbox;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;
import java.util.UUID;

import sbd.smartbox.helpers.ConnectThread;
import sbd.smartbox.helpers.ConnectedThread;
import sbd.smartbox.helpers.DeviceAdapter;

public class MainActivity extends AppCompatActivity  implements  ConnectThread.ConnectThreadListener , LockerFragment.OnFragmentInteractionListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    BluetoothAdapter myBluetooth;
    BluetoothDevice bluetoothDevice;
    ConnectedThread connectedThread;
    Set<BluetoothDevice> bluetoothDevices;
    static UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void manageConnectedSocket(BluetoothSocket mmSocket) {
        Toast.makeText(getApplicationContext(),"CONNECTED",Toast.LENGTH_SHORT).show();
        connectedThread = new ConnectedThread(mmSocket);

        LockerFragment fragment = new LockerFragment();
        fragment.setConnectedThread(connectedThread);

        // android.app.FragmentManager manager = getFragmentManager();

        // android.app.FragmentTransaction fragmentTx = manager.beginTransaction();

        // The fragment will have the ID of Resource.Id.fragment_container.
        // fragmentTx.replace(R.id.fragmentholder, fragment);

        // Commit the transaction.
        // fragmentTx.commit();
    }

    @Override
    public void CouldNotConnectToSocket() {
        Toast.makeText(getApplicationContext(),"COULD NOT CONNECT",Toast.LENGTH_SHORT).show();
    }

    private void getPairedDevices(){
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null){
            Toast.makeText(getApplicationContext(),"BLUETHOOTH NOT SUPPORTED",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            if(!myBluetooth.isEnabled()){
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,1);
            }
        }

        bluetoothDevices = myBluetooth.getBondedDevices();

        DeviceAdapter bluetoothDeviceArrayAdapter = new DeviceAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        if(bluetoothDevices.size()!=0){

            for(BluetoothDevice device:bluetoothDevices){
                bluetoothDeviceArrayAdapter.add(device);
                Log.e("Bluethooth device",device.toString());
            }

            LockerFragment startFragment  = new LockerFragment();

            startFragment.setDeviceAdapter(bluetoothDeviceArrayAdapter);
            startFragment.setmListener(this);
            // android.app.FragmentManager manager = getFragmentManager();

            // android.app.FragmentTransaction fragmentTx = manager.beginTransaction();

            // The fragment will have the ID of Resource.Id.fragment_container.
            // fragmentTx.replace(R.id.fragmentholder, startFragment);

            // Commit the transaction.
            // fragmentTx.commit();
        }
    }

    @Override
    public void ItemSelected(int position) {
        bluetoothDevice = (BluetoothDevice) bluetoothDevices.toArray()[position];
        Log.e("bd selected", bluetoothDevice.getName().toString());
        ConnectThread connectThread = new ConnectThread(this, bluetoothDevice,applicationUUID, myBluetooth);
        connectThread.run();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return TrackFragment.newInstance();
                case 1:
                    return LockerFragment.newInstance();
                case 2:
                    return AuthFragment.newInstance();
            }
            return  null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tracker";
                case 1:
                    return "Locker";
                case 2:
                    return "Auth (alfa)";
            }
            return null;
        }
    }
}
