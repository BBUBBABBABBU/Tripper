package com.tripper.domain.schedule;

import com.tripper.domain.trip.Trip;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Schedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    private String name;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    protected Schedule() {
    }

    public Schedule(String name, String content, LocalDateTime startTime, LocalDateTime endTime, Trip trip) {
        this.name = name;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trip = trip;
        trip.getSchedules().add(this);  // 연관관계 설정
    }

    public void updateSchedule(String name, String content, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}