package com.tripper.domain.trip;

import com.tripper.domain.budget.Budget;
import com.tripper.domain.map.Route;
import com.tripper.domain.user.User;
import com.tripper.domain.schedule.Schedule;
import com.tripper.domain.user.UserInfo;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private User user;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();

    @OneToOne(mappedBy = "trip")
    private Budget budget;

    @OneToOne(mappedBy = "trip")
    private Route route;

    protected Trip() {}

    public Trip(String destination, LocalDate startDate, LocalDate endDate, User user) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }

    public void updateTrip(String destination, LocalDate startDate, LocalDate endDate) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setBudget(Budget budget){
        this.budget = budget;
    }

    public void setRoute(Route route){
        this.route = route;
    }

    public void addSchedule(Schedule schedule) {
        int i = 0;
        for (; i < this.schedules.size(); i++){
            if(schedules.get(i).getEndTime().isAfter(schedule.getStartTime())) {
                break;
            }
        }

        if (i == this.schedules.size()) {
            schedules.add(i, schedule);
        } else if (schedules.get(i).getStartTime().isAfter(schedule.getEndTime())) {
            schedules.add(i, schedule);
        } else {
            throw new IllegalArgumentException("일정이 겹칩니다.");
        }
    }

    public void updateSchedule(Long scheduleId, String name, String content,
                               LocalDateTime startTime, LocalDateTime endTime) {
        int i = 0;
        for (; i < this.schedules.size(); i++){
            if(schedules.get(i).getId().equals(scheduleId)) {
                break;
            }
        }

        if (i == 0) {
            if (endTime.isAfter(schedules.get(i+1).getStartTime())) {
                throw new IllegalArgumentException("일정이 겹칩니다.");
            }
        } else if (i == schedules.size()-1) {
            if (startTime.isBefore(schedules.get(i-1).getEndTime())) {
                throw new IllegalArgumentException("일정이 겹칩니다.");
            }
        } else {
            if (endTime.isAfter(schedules.get(i+1).getStartTime()) ||
                    startTime.isBefore(schedules.get(i-1).getEndTime())) {
                throw new IllegalArgumentException("일정이 겹칩니다.");
            }
        }

        schedules.get(i).updateSchedule(name, content, startTime, endTime);
    }

    public void deleteSchedule(Long scheduleId) {
        if (!this.schedules.removeIf(schedule -> schedule.getId().equals(scheduleId))) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }
    }
}
