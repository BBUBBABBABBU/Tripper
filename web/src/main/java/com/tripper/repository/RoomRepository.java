package com.tripper.repository;

import com.tripper.domain.hotel.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long>  {

    @Query("SELECT r FROM Room r WHERE r.roomType.id = :roomtype_id")
    List<Room> findAllByRoomTypeId(@Param("roomtype_id") Long roomtype_id);

}
