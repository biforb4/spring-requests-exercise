package pl.infoshare.requests.vehicles.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;

public enum VehicleType {
    BUS {
        public boolean shouldBeReviewed(Vehicle vehicle) {
            return reviewDueToMileage(vehicle, new BigDecimal(30000)) || reviewDueToTime(vehicle, 12);
        }
    },
    TRAM {
        public boolean shouldBeReviewed(Vehicle vehicle) {
            return reviewDueToMileage(vehicle, new BigDecimal(15000)) || reviewDueToTime(vehicle, 6);
        }
    };

    protected boolean reviewDueToMileage(Vehicle vehicle, BigDecimal mileageLimit) {
        return vehicle.getMileage().subtract(vehicle.getLastReviewMileage()).compareTo(mileageLimit) > 0;
    }

    protected boolean reviewDueToTime(Vehicle vehicle, int limitInMonths) {
        var now = LocalDate.now();
        return Period.between(vehicle.getLastReviewDate(), now).getMonths() > limitInMonths;
    }

    public boolean shouldBeReviewed(Vehicle vehicle) {
        return false;
    }
}
