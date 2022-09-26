package com.scooty.models;

import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
public class User {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @Setter private Location location;
}
