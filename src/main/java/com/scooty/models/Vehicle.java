package com.scooty.models;

import lombok.*;

@ToString
@RequiredArgsConstructor
public class Vehicle {
    public final static int RESERVE_TIME = 10;
    
    public enum VehicleEnum{
        TRIP_START,
        TRIP_END,
        TRIP_IN_PROGRESS
    }

    @NonNull
    @Getter private long id;
    @NonNull
    @Getter @Setter private boolean isAvailable;
    @NonNull
    @Getter @Setter private long outletId;
    @Getter @Setter private double kms;
    @NonNull
    @Getter private boolean isPriced;
    @Getter @Setter private static String tripStatus;
}
