package sbd.smartbox.helpers;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sbd.smartbox.R;

public class DeviceAdapter  extends ArrayAdapter<BluetoothDevice> {

    ArrayList<BluetoothDevice> bluetoothDevices;
    Activity context;

    public DeviceAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        bluetoothDevices = new ArrayList<>();
    }

    @Override
    public void add(BluetoothDevice object) {
        super.add(object);
        bluetoothDevices.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = context.getLayoutInflater().inflate(R.layout.row_device, parent, false);
        //TextView v = new TextView(context);
        //v.setText(bluetoothDevices.get(position).getName());
        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(bluetoothDevices.get(position).getName());
        return v;
    }
}
