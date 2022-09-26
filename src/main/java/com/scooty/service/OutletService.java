package com.scooty.service;

import com.scooty.exceptions.OutletAlreadyExistsException;
import com.scooty.exceptions.VehicleAlreadyExistsException;
import com.scooty.models.Location;
import com.scooty.models.Outlet;
import com.scooty.models.Vehicle;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OutletService {
    Map<Long, Outlet> outletMap = new HashMap<>();
    Map<Long, List<Vehicle>> vehicleMap = new HashMap<>();
    public void addOutlet(Outlet outlet) throws OutletAlreadyExistsException {
        if(outletMap.containsKey(outlet.getId())){
            throw new OutletAlreadyExistsException();
        }
        outletMap.put(outlet.getId(), outlet);
    }
     public List<Outlet> getOutlets(@NonNull final Location fromPoint, @NonNull final Double distance) {
        List<Outlet> result = new ArrayList<>();
         List<Vehicle> freeVehicles = new ArrayList<>();
        for (Outlet outlet : outletMap.values()) {
            if(outlet.getVehicleList() != null && !outlet.getVehicleList().isEmpty()){
                List<Vehicle> vehicles = outlet.getVehicleList();
                for (Vehicle vehicle: vehicles) {
                    if(vehicle.isAvailable() && !vehicle.isPriced()){
                        freeVehicles.add(vehicle);
                    }
                }
                outlet.setVehicleList(freeVehicles);
            }

            if (outlet.getVehicleList() != null && outlet.getLocation().distance(fromPoint) <= distance) {
                result.add(outlet);
            }
        }
        return result;
    }
    public void addVehicles(Vehicle vehicle) {
        List<Vehicle> vehicles = new ArrayList<>();
        List<Vehicle> vehicleLocalList = new ArrayList<>();
        if(vehicleMap.containsKey(vehicle.getId())){
            throw new VehicleAlreadyExistsException();
        }
        vehicles.add(vehicle);
        vehicleMap.put(vehicle.getId(), vehicles);
        Collection<List<Vehicle>> collectionVehicleValues = vehicleMap.values();
        for (List<Vehicle> vehicleList: collectionVehicleValues) {
            vehicleLocalList.addAll(vehicleList);
            outletMap.get(vehicle.getOutletId()).setVehicleList(vehicleLocalList);
        }
    }

    public Vehicle getVehicle(Long outletId, Long vehicleId) throws OutletAlreadyExistsException {
        Vehicle vehicle = new Vehicle(vehicleId,true,outletId,false);
        if(!outletMap.containsKey(outletId)){
            throw new OutletAlreadyExistsException();
        }
        if(outletMap.get(outletId).getVehicleList() !=null){
            return outletMap.get(outletId).getVehicleList().get(0);
        }
            return vehicle;
    }
    public void book(Vehicle vehicle, String bookTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm");
        String expireTime = String.valueOf(Integer.parseInt(bookTime) + Vehicle.RESERVE_TIME);
        Vehicle.setTripStatus(String.valueOf(Vehicle.VehicleEnum.TRIP_IN_PROGRESS));

        if(Integer.parseInt(expireTime) > Integer.parseInt(dtf.format(LocalDateTime.now())))
        {
            vehicle.setAvailable(false);
        }
        else{
            vehicle.setAvailable(true);
        }
    }
    public String reserve(Vehicle vehicle) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm");
        LocalDateTime now = LocalDateTime.now();
        Vehicle.setTripStatus(String.valueOf(Vehicle.VehicleEnum.TRIP_START));
        vehicle.setAvailable(false);
        return dtf.format(now);
    }

    public void clearTrip(long userId, Vehicle vehicle, Double kms, long outletId) {
        Vehicle.setTripStatus(String.valueOf(Vehicle.VehicleEnum.TRIP_END));
        vehicle.setAvailable(true);
        vehicle.setKms(kms);

    }
}
