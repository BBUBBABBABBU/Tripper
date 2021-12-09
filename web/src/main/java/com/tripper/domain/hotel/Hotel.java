package com.tripper.domain.hotel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tripper.domain.BaseTimeEntity;
import com.tripper.domain.Photo;
import com.tripper.domain.user.User;
import com.tripper.dto.request.hotel.UpdateHotelDto;
import com.tripper.handler.FileHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Entity
@Getter @Setter
public class Hotel extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "hotel_id")
    private Long id;

    private String name;
    private String address;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    private List<RoomType> roomTypes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    private List<Photo> photos = new ArrayList<>();

    public Hotel(String name, String address, User user) {
        this.name = name;
        this.address = address;
        this.user = user;
        user.getHotels().add(this);
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);

        if(photo.getHotel() != this) {
            photo.setHotel(this);
        }
    }

//    /* 호텔 수정 */
//    public void updateHotel(UpdateHotelDto updateHotelDto) {
//        this.name = updateHotelDto.getName();
//        this.address = updateHotelDto.getAddress();
//        List<Photo> photoList = fileHandler.parseFileInfo(updateHotelDto.getPhotos());
//        this.photos = updateHotelDto.getPhotos());
//    }

}
