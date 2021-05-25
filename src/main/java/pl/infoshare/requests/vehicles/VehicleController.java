package pl.infoshare.requests.vehicles;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.infoshare.requests.vehicles.model.Vehicle;
import pl.infoshare.requests.vehicles.model.VehicleUpdateRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final VehicleFindService vehicleFindService;

    @GetMapping(value = "/vehicles")
    public ResponseEntity<List<Vehicle>> findAllVehicles(
            VehicleFilters filters,
            @RequestHeader(value = "X-CITY", defaultValue = "") String city,
            @RequestParam(name= "page", defaultValue = "0") long page,
            @RequestParam(name= "perPage", defaultValue = "2") long perPage
    ) {
        var allFilters = filters.withCity(city);

        var cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS).cachePrivate();

        List<Vehicle> vehicles = vehicleFindService.findVehicles(allFilters).stream()
                .skip(page * perPage)
                .limit(perPage)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .header("X-TOTAL-COUNT", String.valueOf(vehicles.size()))
                .body(vehicles);
    }

    @GetMapping(value = "/vehicles", params = "needsReview")
    public ResponseEntity<List<Vehicle>> findAllVehicles() {

        var cacheControl = CacheControl.maxAge(1, TimeUnit.HOURS).cachePrivate();

        List<Vehicle> vehicles = vehicleFindService.findVehiclesDueToReview();

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .header("X-TOTAL-COUNT", String.valueOf(vehicles.size()))
                .body(vehicles);
    }

    @PostMapping("/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public void createVehicle(@RequestBody Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @PutMapping("/vehicles/{registration}-{city}-{id}")
    public void updateVehicle(@RequestBody VehicleUpdateRequest vehicleUpdateRequest, @PathVariable int id) {
        vehicleRepository.update(id, vehicleUpdateRequest);
    }

    @DeleteMapping("/vehicles/{identifier}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable int identifier) {
        vehicleRepository.delete(identifier);
    }
}
