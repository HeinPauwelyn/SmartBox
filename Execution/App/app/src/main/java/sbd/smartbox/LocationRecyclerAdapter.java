package sbd.smartbox;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import sbd.smartbox.models.Location;
import butterknife.Bind;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.LocationViewHolder>{

    private List<Location> _locations;

    public LocationRecyclerAdapter(List<Location> locations) {
        _locations = locations;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_location, parent, false));
    }

    @Override
    public void onBindViewHolder(final LocationViewHolder holder, final int position) {

        if (_locations != null) {
            Location l = _locations.get(position);
            holder.longitude.setText(l.getLongitude());
            holder.latitude.setText(l.getLatitude());
            holder.time.setText(l.getTime());
        }
    }

    @Override
    public int getItemCount() {
        if (_locations != null) {
            return _locations.size();
        }
        return 0;
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.longitude) TextView longitude;
        @Bind(R.id.latitude) TextView latitude;
        @Bind(R.id.time) TextView time;

        public LocationViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
