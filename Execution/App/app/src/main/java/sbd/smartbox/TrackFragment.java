package sbd.smartbox;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import sbd.smartbox.helpers.ApiHelper;
import sbd.smartbox.helpers.SmartboxApiHelper;
import sbd.smartbox.models.Location;

public class TrackFragment extends Fragment {

    @Bind(R.id.rvLocaties) RecyclerView rcvLocaties;

    private OnNewLocationsListener _newLocationListener;
    private RecyclerView.LayoutManager _layoutManager;
    private LocationRecyclerAdapter _locationRecyclerAdapter;

    public TrackFragment() {
    }

    public static TrackFragment newInstance() {
        TrackFragment fragment = new TrackFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        ButterKnife.bind(this, view);

        _layoutManager = new LinearLayoutManager(getActivity());

        rcvLocaties.clearDisappearingChildren();

        if (_locationRecyclerAdapter != null) {
            _locationRecyclerAdapter.notifyDataSetChanged();
        }

        ApiHelper.subscribe(SmartboxApiHelper.getSmartboxServiceInstance().getLocations(), new Action1<List<Location>>() {
            @Override
            public void call(List<Location> locations) {
                rcvLocaties.setLayoutManager(null);
                rcvLocaties.setLayoutManager(_layoutManager);
                rcvLocaties.setItemAnimator(new DefaultItemAnimator());
                rcvLocaties.setAdapter(new LocationRecyclerAdapter(locations));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnNewLocationsListener {

    }
}
