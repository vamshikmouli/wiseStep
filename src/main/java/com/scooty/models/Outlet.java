package com.scooty.models;

import lombok.*;

import java.util.Collection;
import java.util.List;

@ToString
@RequiredArgsConstructor
public class Outlet {
    @NonNull
    @Getter private long id;
    @Setter @Getter private List<Vehicle> vehicleList;
    @NonNull
    @Getter private String outletName;
    @NonNull
    @Getter Location location;

}
