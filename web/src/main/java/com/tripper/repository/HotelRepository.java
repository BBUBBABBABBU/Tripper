package com.tripper.repository;

import com.tripper.domain.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE h.id = :id")
    Hotel findByHotelId(@Param("id") Long id);

    List<Hotel> findHotelByUser_MemId(String memId);

}
