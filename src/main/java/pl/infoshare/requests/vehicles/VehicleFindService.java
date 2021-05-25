package pl.infoshare.requests.vehicles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.infoshare.requests.vehicles.model.Vehicle;
import pl.infoshare.requests.vehicles.model.VehicleType;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VehicleFindService {

    private final VehicleRepository vehicleRepository;

    public List<Vehicle> findVehicles(VehicleFilters filters) {
        return vehicleRepository.findAll().stream()
                .filter(v -> filters.getType() == null || v.getType().equals(VehicleType.valueOf(filters.getType())))
                .filter(v -> filters.getMileage() == null || v.getMileage().compareTo(new BigDecimal(filters.getMileage())) >= 0)
                .filter(v -> filters.getCity().isEmpty() || v.getCity().equals(filters.getCity()))
                .collect(Collectors.toList());
    }

    public List<Vehicle> findVehiclesDueToReview() {
        return vehicleRepository.findAll().stream()
                .filter(Vehicle::shouldBeReviewed)
                .collect(Collectors.toList());
    }
}
