package sbd.smartbox.api;

import java.util.List;
import retrofit2.http.GET;
import rx.Observable;
import sbd.smartbox.models.Location;

public interface SmartBoxService {
    @GET("locations/all")
    Observable<List<Location>> getLocations();
}
