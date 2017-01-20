package sbd.smartbox.helpers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {

    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final UUID MY_UUID;
    private final BluetoothAdapter mBluetoothAdapter;
    private final ConnectThreadListener listener;

    public ConnectThread(ConnectThreadListener listener, BluetoothDevice device,UUID MY_UUID,BluetoothAdapter mBluetoothAdapter) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;

        this.MY_UUID = MY_UUID;
        this.mBluetoothAdapter = mBluetoothAdapter;
        this.listener = listener;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
        }
        catch (IOException e) { }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        //mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            mBluetoothAdapter.cancelDiscovery();
            // until it succeeds or throws an exception
            mmSocket.connect();
        }
        catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            Log.e("Status", "Could not connect!");
            listener.CouldNotConnectToSocket();
            try {
                mmSocket.close();

            } catch (IOException closeException) {

                Log.e("Status", "Could not close!");
            }
            return;
        }
        Log.e("Status", "Connected");

        // Do work to manage the connection (in a separate thread)
        listener.manageConnectedSocket(mmSocket);
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        }
        catch (IOException e) { }
    }

    public interface ConnectThreadListener {
        public void manageConnectedSocket(BluetoothSocket mmSocket);
        public void CouldNotConnectToSocket();
    }
}
