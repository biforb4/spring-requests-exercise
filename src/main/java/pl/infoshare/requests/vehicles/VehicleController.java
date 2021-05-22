package pl.infoshare.requests.vehicles;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.infoshare.requests.vehicles.model.Vehicle;
import pl.infoshare.requests.vehicles.model.VehicleUpdateRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final VehicleFindService vehicleFindService;

    @RequestMapping("/vehicles")
    public List<Vehicle> findAllVehicles() {
        return vehicleFindService.findVehicles();
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
