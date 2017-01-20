package sbd.smartbox;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import sbd.smartbox.helpers.ConnectedThread;
import sbd.smartbox.helpers.DeviceAdapter;

public class LockerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ConnectedThread connectedThread;
    private Button btnOpen;
    private boolean isOpen = false;
    private DeviceAdapter deviceAdapter;

    public LockerFragment() {
    }

    public static LockerFragment newInstance() {
        LockerFragment fragment = new LockerFragment();
        return fragment;
    }

    public void setConnectedThread(ConnectedThread connectedThread) {
        this.connectedThread = connectedThread;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_locker, container, false);
        btnOpen = (Button) v.findViewById(R.id.btnopen);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedThread.write("1".getBytes());
            }
        });

        ListView listView = (ListView) v.findViewById(R.id.list);
        deviceAdapter = new DeviceAdapter(this.getActivity(), R.layout.row_device);

        if(isOpen) {
            btnOpen.setText("Close smartbox");
        }
        else {
            btnOpen.setText("Open smartbox");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.ItemSelected((int)id);
            }
        });

        if(deviceAdapter != null){
            listView.setAdapter(deviceAdapter);
        }

        return v;
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void ItemSelected(int position);
    }
}
