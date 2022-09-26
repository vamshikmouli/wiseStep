package com.scooty.controllers;

import com.scooty.exceptions.UserAlreadyExistsException;
import com.scooty.models.Location;
import com.scooty.models.Outlet;
import com.scooty.models.Vehicle;
import com.scooty.service.OutletService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class OutletController {

    OutletService outletService = new OutletService();

    @RequestMapping(value = "/add/outlet", method = RequestMethod.POST)
    public ResponseEntity addOutlet(final Long outletId, final String outletName, Location location) throws UserAlreadyExistsException {
        outletService.addOutlet(new Outlet(outletId, outletName, location));
        return ResponseEntity.ok("");
    }
    @GetMapping("/getNearbyOutletList")
    public List<Outlet> getNearByOutlets(Location userLocation, Double distance){
        return outletService.getOutlets(userLocation, distance);
    }

    @GetMapping("/addVehicles")
    public ResponseEntity addVehicles(Long vehicleId, boolean isAvailable, Long outletId, boolean isPriced){
        outletService.addVehicles(new Vehicle(vehicleId, isAvailable, outletId, isPriced));
        return ResponseEntity.ok("");
    }
    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public Vehicle book(final Long userId, final Long outletId,final Long vehicleId,
                               String bookTime) {

        Vehicle vehicle =  outletService.getVehicle(outletId, vehicleId);
        outletService.book(vehicle, bookTime);

        return vehicle;

    }
    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public String reserve(final Long userId, final Long outletId, final Long vehicleId) {
        Vehicle vehicle =  outletService.getVehicle(outletId, vehicleId);
        return outletService.reserve(vehicle);
    }
    @RequestMapping(value = "/finishRide", method = RequestMethod.POST)
    public void finishRide(long userId, Vehicle vehicle, Double kms, long outletId) {

        outletService.clearTrip(userId, vehicle, kms, outletId);
    }
}
