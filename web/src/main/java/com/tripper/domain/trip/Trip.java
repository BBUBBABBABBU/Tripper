package com.tripper.domain.trip;

import com.tripper.domain.schedule.Schedule;
import com.tripper.domain.user.UserInfo;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Trip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;

    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();

    protected Trip() {}

    public Trip(String destination, LocalDate startDate, LocalDate endDate, UserInfo userInfo) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userInfo = userInfo;
    }

    public void updateTrip(String destination, LocalDate startDate, LocalDate endDate) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}