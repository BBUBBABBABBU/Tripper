package com.tripper.service;

import com.tripper.domain.Photo;
import com.tripper.domain.hotel.*;
import com.tripper.domain.user.User;
import com.tripper.dto.request.hotel.*;
import com.tripper.dto.response.GetPhotoDto;
import com.tripper.dto.response.GetPhotoListDto;
import com.tripper.dto.response.hotel.*;
import com.tripper.handler.FileHandler;
import com.tripper.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HanJiyoung
 * 호텔 관련 서비스 클래스
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class HotelService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final PhotoRepository photoRepository;
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final FileHandler fileHandler;

    /**
     * 호텔 등록
     */
    @Transactional
    public void createHotel(CreateHotelDto createHotelDto, String memId) throws Exception {

        User user = userRepository.findByMemId(memId);

        Hotel hotel = Hotel.builder()
                .name(createHotelDto.getName())
                .address(createHotelDto.getAddress())
                .user(user)
                .build();

        List<Photo> photoList = fileHandler.parseFileInfo(createHotelDto.getPhotos());

        // 파일이 존재할 때에만 처리
        if(!photoList.isEmpty()){
            for(Photo photo : photoList) {
                // 파일을 DB에 저장
                hotel.addPhoto(photoRepository.save(photo));
            }
        }

        hotelRepository.save(hotel);
    }

    /**
     * 객실 등록
     */
    @Transactional
    public void createRoom(Long hotel_id, CreateRoomDto createRoomDto)  throws Exception {

        /* 엔티티 조회 */
        Hotel hotel = hotelRepository.findById(hotel_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 호텔 입니다."));

        /* 객실 분류 저장*/
        RoomType roomType = RoomType.builder()
                .name(createRoomDto.getName())
                .maxCapacity(createRoomDto.getMaxCapacity())
                .standardCapacity(createRoomDto.getStandardCapacity())
                .hotel(hotel)
                .build();

        List<Photo> photoList = fileHandler.parseFileInfo(createRoomDto.getPhotos());

        // 파일이 존재할 때에만 처리
        if(!photoList.isEmpty()){
            for(Photo photo : photoList) {
                // 파일을 DB에 저장
                roomType.addPhoto(photoRepository.save(photo));
            }
        }

        roomTypeRepository.save(roomType);

        /* 객실 저장 */
        List<Integer> roomNumList = createRoomDto.getRoomNum();
        if(!roomNumList.isEmpty()) {
            for(Integer i : roomNumList) {
                Room room = Room.builder()
                        .roomType(roomType)
                        .roomNum(i)
                        .price(createRoomDto.getPrice())
                        .build();
                roomRepository.save(room);
            }
        }

    }

    /**
     * 호텔 목록 조회
     */
    public GetHotelListDto findAllHotels() {

        List<Hotel> hotels = hotelRepository.findAll();
        List<GetHotelDto> getHotelDtos = new ArrayList<>();

        for (Hotel hotel : hotels) {

            Long id = hotel.getId();
            String name = hotel.getName();
            String address = hotel.getAddress();

            List<Photo> photoList = photoRepository.findByHotel_Id(id);
            GetPhotoListDto getPhotoListDto = setPhotoListDto(photoList);

            List<RoomType> roomTypeList = hotel.getRoomTypes();
            GetRoomListDto getRoomListDto = setRoomListDto(roomTypeList);

            GetHotelDto getHotelDto = new GetHotelDto(id, name, address, getPhotoListDto, getRoomListDto);
            getHotelDtos.add(getHotelDto);
        }
        int total = getHotelDtos.size();

        return new GetHotelListDto(getHotelDtos, total);
    }

    /**
     * 호텔 매니저가 등록한 호텔들만 조회
     */
    public GetHotelListDto findManagersHotel(String memId) {

        List<Hotel> hotels = hotelRepository.findHotelByUser_MemId(memId);
        List<GetHotelDto> getHotelDtos = new ArrayList<>();

        for (Hotel hotel : hotels) {

            Long id = hotel.getId();
            String name = hotel.getName();
            String address = hotel.getAddress();

            List<Photo> photoList = photoRepository.findByHotel_Id(id);
            GetPhotoListDto getPhotoListDto = setPhotoListDto(photoList);

            List<RoomType> roomTypeList = hotel.getRoomTypes();
            GetRoomListDto getRoomListDto = setRoomListDto(roomTypeList);

            GetHotelDto getHotelDto = new GetHotelDto(id, name, address, getPhotoListDto, getRoomListDto);
            getHotelDtos.add(getHotelDto);
        }
        int total = getHotelDtos.size();

        return new GetHotelListDto(getHotelDtos, total);
    }

    /**
     * hotel_id로 호텔 1개 조회
     */
    public GetHotelDto findByHotelId(Long hotel_id) {

        /* 엔티티 조회 */
        Hotel hotel = hotelRepository.findById(hotel_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 호텔 입니다."));

        Long id = hotel.getId();
        String name = hotel.getName();
        String address = hotel.getAddress();

        List<Photo> photoList = hotel.getPhotos();
        GetPhotoListDto getPhotoListDto = setPhotoListDto(photoList);

        List<RoomType> roomTypeList = hotel.getRoomTypes();
        GetRoomListDto getRoomListDto = setRoomListDto(roomTypeList);

        GetHotelDto getHotelDto = new GetHotelDto(id, name, address, getPhotoListDto, getRoomListDto);

        return getHotelDto;
    }

    /**
     * roomtype_id로 특정 객실 조회
     */
    public GetRoomDto findByRoomTypeId(Long roomtype_id) {

        /* 엔티티 조회 */
        RoomType roomType = roomTypeRepository.findById(roomtype_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실 분류 입니다."));

        Long id = roomType.getId();
        String name = roomType.getName();
        int standardCapacity = roomType.getStandardCapacity();
        int maxCapacity = roomType.getMaxCapacity();

        List<Room> rooms = roomType.getRooms();
        List<Integer> roomNums = new ArrayList<>();
        for(Room room : rooms) {
            roomNums.add(room.getRoomNum());
        }

        int price = roomType.getRooms().get(0).getPrice();

        List<Photo> photoList = photoRepository.findByRoomType_Id(id);
        GetPhotoListDto getPhotoListDto = setPhotoListDto(photoList);

        GetRoomDto getRoomDto = new GetRoomDto(id, name, standardCapacity, maxCapacity, roomNums, price, getPhotoListDto);

        return getRoomDto;
    }

    /**
     * 호텔 정보 수정
     */
    @Transactional
    public void updateHotel(Long hotel_id, UpdateHotelDto updateHotelDto) throws Exception {

        /* 엔티티 조회 */
        Hotel hotel = hotelRepository.findById(hotel_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 호텔 입니다."));

        String updateName = updateHotelDto.getName();
        String updateAddress = updateHotelDto.getAddress();

        hotel.updateHotelInfo(updateName, updateAddress);

        List<Photo> photoList = fileHandler.parseFileInfo(updateHotelDto.getPhotos());

        if(!photoList.isEmpty()){
            for(Photo photo : photoList) {
                hotel.addPhoto(photoRepository.save(photo));
            }
        }

    }

    /**
     * 객실 수정
     */
    @Transactional
    public void updateRoom(Long roomtype_id, UpdateRoomDto updateRoomDto) throws Exception {

        /* 객실 분류 엔티티 조회 */
        RoomType roomType = roomTypeRepository.findById(roomtype_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실 분류 입니다."));

        String updateName = updateRoomDto.getName();
        int updateStandardCapacity = updateRoomDto.getStandardCapacity();
        int updateMaxCapacity = updateRoomDto.getMaxCapacity();
        roomType.updateRoomTypeInfo(updateName, updateStandardCapacity, updateMaxCapacity);

        List<Photo> photoList = fileHandler.parseFileInfo(updateRoomDto.getPhotos());

        if(!photoList.isEmpty()){
            for(Photo photo : photoList) {
                roomType.addPhoto(photoRepository.save(photo));
            }
        }

        /* 객실 엔티티 조회 */
        List<Integer> roomNumList = updateRoomDto.getRoomNum();

        for(Integer i : roomNumList) {
            Room room = Room.builder()
                    .roomType(roomType)
                    .roomNum(i)
                    .price(updateRoomDto.getPrice())
                    .build();

            roomRepository.save(room);
        }

    }

    /**
     * 호텔 삭제
     */
    @Transactional
    public void deleteHotelById(Long hotel_id) {

        /* 엔티티 조회 */
        Hotel hotel = hotelRepository.findById(hotel_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 호텔 입니다."));

        /* 호텔 삭제 */
        hotelRepository.delete(hotel);

    }

    /**
     * 객실 분류 삭제
     */
    @Transactional
    public void deleteRoomTypeById(Long roomtype_id) {

        /* 엔티티 조회 */
        RoomType roomType = roomTypeRepository.findById(roomtype_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실 분류 입니다."));

        photoRepository.deleteByRoomType_Id(roomtype_id);

        /* 객실 분류 삭제 */
        roomTypeRepository.delete(roomType);

    }

    /**
     * 방 호수 삭제
     */
    @Transactional
    public void deleteRoomById(Long room_id) {

        /* 엔티티 조회 */
        Room room = roomRepository.findById(room_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 객실 입니다."));

        /* 객실 삭제 */
        roomRepository.delete(room);

    }

    /**
     *  사진 response dto 세팅
     */
    public GetPhotoListDto setPhotoListDto(List<Photo> photoList) {

        List<GetPhotoDto> getPhotoDtos = new ArrayList<>();

        if(!photoList.isEmpty()){
            for(Photo photo : photoList) {
                Long id = photo.getId();
                String originName = photo.getOriginName();
                String filePath = photo.getFilePath();

                GetPhotoDto getPhotoDto = new GetPhotoDto(id, originName, filePath);
                getPhotoDtos.add(getPhotoDto);
            }
        }

        int photoTotal = getPhotoDtos.size();
        GetPhotoListDto getPhotoListDto = new GetPhotoListDto(getPhotoDtos, photoTotal);

        return getPhotoListDto;
    }

    /**
     *  객실 response dto 세팅
     */
    public GetRoomListDto setRoomListDto(List<RoomType> roomTypeList) {
        List<GetRoomDto> getRoomDtos = new ArrayList<>();

        if(!roomTypeList.isEmpty()){
            for(RoomType roomType : roomTypeList) {
                Long id = roomType.getId();
                String name = roomType.getName();
                int standardCapacity = roomType.getStandardCapacity();
                int maxCapacity = roomType.getMaxCapacity();
                List<Room> rooms = roomType.getRooms();
                List<Integer> roomNums = new ArrayList<>();
                for(Room room : rooms) {
                    roomNums.add(room.getRoomNum());
                }
                int price = roomType.getRooms().get(0).getPrice();

                List<Photo> photoList = photoRepository.findByRoomType_Id(id);
                GetPhotoListDto getPhotoListDto = setPhotoListDto(photoList);

                GetRoomDto getRoomDto = new GetRoomDto(id, name, standardCapacity, maxCapacity, roomNums, price, getPhotoListDto);
                getRoomDtos.add(getRoomDto);
            }
        }

        int roomTotal = getRoomDtos.size();
        GetRoomListDto getRoomListDto = new GetRoomListDto(getRoomDtos, roomTotal);

        return getRoomListDto;
        
    }

}
