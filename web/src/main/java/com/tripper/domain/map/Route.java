package com.tripper.domain.map;

import com.tripper.domain.trip.Trip;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Entity
@Getter
public class Route {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "route_id")
    private Long id;

    @Lob
    private String route;

    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Builder
    public Route(String route, Trip trip) {
        this.route = route;
        this.trip = trip;
        trip.setRoute(this);
    }

}
