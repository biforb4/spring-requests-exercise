package pl.infoshare.requests.vehicles.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class Vehicle {
    @With
    Integer id;
    VehicleType type;
    BigDecimal mileage;
    String registrationNumber;
    @With
    LocalDate lastReviewDate;
    @With
    BigDecimal lastReviewMileage;
    String city;

    public boolean shouldBeReviewed() {
        return type.shouldBeReviewed(this);
    }
}
