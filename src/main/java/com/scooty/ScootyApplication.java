package com.scooty;

import com.scooty.controllers.OutletController;
import com.scooty.controllers.UserController;
import com.scooty.models.Location;
import com.scooty.models.Outlet;
import com.scooty.models.Vehicle;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScootyApplication {
    @SneakyThrows
    public static void main(String[] args) {
        try {

            UserController userController = new UserController();
            OutletController outletController = new OutletController();
            Location locationUser = new Location(0.0,0.0);
            // ADD USER
            userController.addUser(01L, "Vamshi");

            List<Vehicle> vehicleList = new ArrayList<>();

            //ADD OUTLET
            Location locationO1 = new Location(0.0,15.0);
            outletController.addOutlet(01L,"Sun", locationO1);
            Location locationO2 = new Location(0.0,30.0);
            outletController.addOutlet(02L,"Star", locationO2);
            Location locationO3 = new Location(0.0,45.0);
            outletController.addOutlet(03L,"Moon", locationO3);

            //ADD VEHICLES

            outletController.addVehicles(01L, true, 01L, false);
            outletController.addVehicles(02L, true, 01L, true);
            List<Outlet> outlets =  outletController.getNearByOutlets(locationUser, 30.0);
            //SHOWING NEARBY OUTLETS AND FREE VEHICLES
            outlets.stream().forEach(outlet -> System.out.println("Outlet Name: "+outlet.getOutletName()+" Id:"+outlet.getId()));
            outlets.stream().forEach(outlet -> System.out.println("Free Vehicles available: "+ String.valueOf(outlet.getVehicleList().size())));

            String bookTime = outletController.reserve(01L, 01L, 01L);
            System.out.println("Your vehicle is booked and reservation is valid for 10 mins pic the vehicle before reserve time");
            Vehicle vehicle = outletController.book(01L, 01L, 01L, bookTime);

            outlets =  outletController.getNearByOutlets(locationUser, 30.0);
            //SHOWING NEARBY OUTLETS TO PARK
            outlets.stream().forEach(outlet -> System.out.println("Outlet Name: "+outlet.getOutletName()+" Id:"+outlet.getId()));

            Double kms = 10.0;
            outletController.finishRide(01L, vehicle, kms, 01L);
        }
        catch (Exception err)
        {
            System.out.println(err.getMessage());
        }

    }
}
