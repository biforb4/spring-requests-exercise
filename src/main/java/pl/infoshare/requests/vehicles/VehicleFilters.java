package pl.infoshare.requests.vehicles;

import lombok.Value;
import lombok.With;

@Value
public class VehicleFilters {
    String type;
    Integer mileage;
    @With
    String city;
    @With
    boolean needsReview;
}
