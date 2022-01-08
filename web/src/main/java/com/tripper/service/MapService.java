package com.tripper.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tripper.domain.map.Route;
import com.tripper.domain.trip.Trip;
import com.tripper.dto.response.map.PlaceShortInfo;
import com.tripper.dto.response.map.*;
import com.tripper.repository.MapRepository;
import com.tripper.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HanJiyoung
 * 지도 관련 서비스 클래스
 */
@Service
@Transactional(readOnly = true)
@ConfigurationProperties("map")
@RequiredArgsConstructor
@Slf4j
public class MapService {

    private final TripRepository tripRepository;
    private final MapRepository mapRepository;

    private static final String TMAP_API_HOST = "apis.openapi.sk.com";
    private static final String NAME_SEARCH_URL = "/tmap/pois";
    private static final String CATEGORY_SEARCH_URL = "/tmap/pois/search/around";
    private static final String ROUTE_SEARCH_URL = "/tmap/routes";

    @Value("${map.tmap.appkey}")
    private String appkey;

    /**
     * 키워드로 장소 검색 후, 필요한 데이터만 파싱해서 GetPlaceListDto 생성한 후 반환
     */
    public GetPlaceListDto keywordSearch(String keyword) {

        String keywordSearchRslt = getKeywordSearchRslt(keyword);

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(keywordSearchRslt);
        JsonObject searchPoiInfo = (JsonObject) jsonObject.get("searchPoiInfo");
        JsonObject pois = (JsonObject) searchPoiInfo.get("pois");
        JsonArray poi = (JsonArray) pois.get("poi");

        /* GetPlaceDto 리스트 생성 */
        List<GetPlaceDto> getPlaceDtos = new ArrayList<>();
        addKeywordRsltPlaces(getPlaceDtos, poi);
        int total = getPlaceDtos.size();

        return new GetPlaceListDto(getPlaceDtos, total);
    }

    /**
     * Tmap api 사용해서 키워드로 장소 검색하기
     */
    public String getKeywordSearchRslt(String keyword) {

        Mono<String> mono = WebClient.builder()
                .baseUrl(TMAP_API_HOST)
                .build().get()
                .uri(uriBuilder -> uriBuilder.path(NAME_SEARCH_URL)
                        .queryParam("searchKeyword", keyword)
                        .queryParam("appKey", appkey)
                        .build()
                )
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));

        return mono.block();

    }

    /**
     * 파싱한 데이터의 escape 문자 삭제
     */
    public String removeEscapeString(String str) {

        str = str.replace("\\\\", "");
        str = str.replace("\\", "");
        str = str.replace("\"", "");

        return str;

    }

    /**
     * 매개변수로 받은 각 경유지들의 반경 1km에 있는 관광명소 검색 후, 필요한 데이터만 파싱해서 GetPlaceListDto 생성한 후 반환
     */
    public GetPlaceListDto categorySearch(List<GetPlaceDto> stopovers) {

        List<GetPlaceDto> recommendations = new ArrayList<>();

        for(int i = 0; i < stopovers.size(); i++) {

            Double x = Double.parseDouble(stopovers.get(i).getX());
            Double y = Double.parseDouble(stopovers.get(i).getY());

            String categorySearchRslt = getCategorySearchRslt(x, y);

            /* 카테고리 검색 결과 파싱 */
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(categorySearchRslt);
            JsonObject searchPoiInfo = (JsonObject) jsonObject.get("searchPoiInfo");
            JsonObject pois = (JsonObject) searchPoiInfo.get("pois");
            JsonArray poi = (JsonArray) pois.get("poi");

            /* GetPlaceDto 리스트 생성 */
            addCategoryRsltPlaces(recommendations, poi);
        }
        int total = recommendations.size();

        return new GetPlaceListDto(recommendations, total);

    }

    /**
     * Tmap api 사용해서 카테고리로 반경 1km 관광명소 검색하기
     */
    public String getCategorySearchRslt(Double x, Double y) {

        Mono<String> mono = WebClient.builder()
                .baseUrl(TMAP_API_HOST)
                .build().get()
                .uri(uriBuilder -> uriBuilder.path(CATEGORY_SEARCH_URL)
                        .queryParam("centerLon", x)
                        .queryParam("centerLat", y)
                        .queryParam("categories", "관광명소")
                        .queryParam("radius", 1)
                        .queryParam("appKey", appkey)
                        .build()
                )
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));

        return mono.block();

    }

    /**
     * GetPlaceDto 리스트에 키워드 검색 결과 장소 추가하기
     */
    public void addKeywordRsltPlaces(List<GetPlaceDto> placeDtos, JsonArray poi) {

        for(int i = 0; i < poi.size(); i++) {
            JsonObject place = (JsonObject) poi.get(i);
            JsonObject newAddressList = (JsonObject) place.get("newAddressList");
            JsonArray newAddress = (JsonArray) newAddressList.get("newAddress");
            JsonObject newAddr = (JsonObject) newAddress.get(0);

            GetPlaceDto getPlaceDto = GetPlaceDto.builder()
                    .id(removeEscapeString(place.get("id").toString()))
                    .name(removeEscapeString(place.get("name").toString()))
                    .tel(removeEscapeString(place.get("telNo").toString()))
                    .roadAddress(removeEscapeString(newAddr.get("fullAddressRoad").toString()))
                    .x(removeEscapeString(newAddr.get("frontLon").toString()))
                    .y(removeEscapeString(newAddr.get("frontLat").toString()))
                    .build();
            placeDtos.add(getPlaceDto);
        }

    }

    /**
     * GetPlaceDto 리스트에 카테고리 검색 결과 장소 추가하기
     */
    public void addCategoryRsltPlaces(List<GetPlaceDto> placeDtos, JsonArray poi) {

        boolean alreadyAdded = false;

        for(int i = 0; i < poi.size(); i++) {
            JsonObject place = (JsonObject) poi.get(i);

            /* 이미 추가되어있는 장소면 그 장소의 하위 장소들은 추가하지 X (ex.주차장..)*/
            for (GetPlaceDto pl : placeDtos) {
                if (pl.getName().contains(removeEscapeString(place.get("name").toString()))) {
                    alreadyAdded = true;
                    break;
                }
            }

            if(!alreadyAdded) {
                GetPlaceDto getPlaceDto = GetPlaceDto.builder()
                        .id(removeEscapeString(place.get("id").toString()))
                        .name(removeEscapeString(place.get("name").toString()))
                        .tel(removeEscapeString(place.get("telNo").toString()))
                        .roadAddress(removeEscapeString(place.get("upperAddrName").toString())
                                + " " + removeEscapeString(place.get("middleAddrName").toString())
                                + " " + removeEscapeString(place.get("roadName").toString())
                                + " " + removeEscapeString(place.get("buildingNo1").toString())
                                + " " + removeEscapeString(place.get("buildingNo2").toString())
                        )
                        .x(removeEscapeString(place.get("frontLon").toString()))
                        .y(removeEscapeString(place.get("frontLat").toString()))
                        .build();
                placeDtos.add(getPlaceDto);
            }
            alreadyAdded = false;
        }

    }

    /**
     * 최단 경로 생성 후 반환
     */
    public JsonArray shortestRoute(List<GetPlaceDto> stopovers) throws CloneNotSupportedException, InterruptedException {

        ArrayList<PlaceShortInfo> startPlace = new ArrayList<PlaceShortInfo>();
        for(int i = 0; i < stopovers.size(); i++) {
            PlaceShortInfo placeShortInfo = new PlaceShortInfo();
            placeShortInfo.setName(stopovers.get(i).getName());
            placeShortInfo.setX(stopovers.get(i).getX());
            placeShortInfo.setY(stopovers.get(i).getY());
            placeShortInfo.setAlreadyUsed(false);
            startPlace.add(placeShortInfo);
        }

        ArrayList<PlaceShortInfo> endPlace = new ArrayList<PlaceShortInfo>();
        for(PlaceShortInfo p : startPlace) {
            endPlace.add((PlaceShortInfo) p.clone());
        }

        endPlace.get(0).setAlreadyUsed(true);
        int startIdx = 0;
        int endIdx = 0;
        int cnt = 0;
        int shortestDistance = Integer.MAX_VALUE;
        JsonArray shortestData = new JsonArray();
        JsonArray rslt = new JsonArray();
        String getDistanceInfo;

        for(int i = 0; i < startPlace.size() - 1; i++) {
            Double startX = Double.parseDouble(startPlace.get(startIdx).getX());
            Double startY = Double.parseDouble(startPlace.get(startIdx).getY());

            if(cnt == startPlace.size() - 2) {
                endIdx = endPlace.size() - 1;
                Double endX = Double.parseDouble(endPlace.get(endIdx).getX());
                Double endY = Double.parseDouble(endPlace.get(endIdx).getY());

                getDistanceInfo = getDistance(startX, startY, endX, endY);

                /* 두 지점 간 거리 정보 파싱 */
                JsonParser jsonParser = new JsonParser();
                JsonObject obj1 = (JsonObject) jsonParser.parse(getDistanceInfo);
                JsonArray features = (JsonArray) obj1.get("features");

                shortestData = features;
                rslt.addAll(shortestData);
                break;
            }
            else {
                for (int j = 0; j < endPlace.size() - 1; j++) {
                    if (endPlace.get(j).getAlreadyUsed() == false) {
                        Double endX = Double.parseDouble(endPlace.get(j).getX());
                        Double endY = Double.parseDouble(endPlace.get(j).getY());

                        getDistanceInfo = getDistance(startX, startY, endX, endY);

                        /* 두 지점 간 거리 정보 파싱 */
                        JsonParser jsonParser = new JsonParser();
                        JsonObject obj1 = (JsonObject) jsonParser.parse(getDistanceInfo);
                        JsonArray features = (JsonArray) obj1.get("features");
                        JsonObject feature = (JsonObject) features.get(0);
                        JsonObject properties = (JsonObject) feature.get("properties");
                        int totalDistance = properties.get("totalDistance").getAsInt();

                        if (shortestDistance > totalDistance) {
                            shortestDistance = totalDistance;
                            shortestData = features;
                            endIdx = j;
                        }
                    }
                }
                rslt.addAll(shortestData);
                shortestDistance = Integer.MAX_VALUE;
                startIdx = endIdx;
                endPlace.get(endIdx).setAlreadyUsed(true);
                cnt++;
            }
        }

        return rslt;

    }

    /**
     * Tmap api 사용해서 두 지점간 거리 구하기
     */
    public String getDistance(Double startX, Double startY, Double endX, Double endY) throws InterruptedException {

        Thread.sleep(1000);
        Mono<String> mono = WebClient.builder()
                .baseUrl(TMAP_API_HOST)
                .build().get()
                .uri(uriBuilder -> uriBuilder.path(ROUTE_SEARCH_URL)
                        .queryParam("startX", startX)
                        .queryParam("startY", startY)
                        .queryParam("endX", endX)
                        .queryParam("endY", endY)
                        .queryParam("resCoordType", "WGS84GEO")
                        .queryParam("searchOption", 10)
                        .queryParam("totalValue", 1)
                        .queryParam("appKey", appkey)
                        .build()
                )
                .exchangeToMono(clientResponse -> {
                    return clientResponse.bodyToMono(String.class);
                });

        return mono.block();

    }

    /**
     * 최단 경로 저장하기
     */
    @Transactional
    public void saveRoute(Long tripId, String shortestRoute) {

        /* 엔티티 조회 */
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행 입니다."));

        Route route1 = Route.builder()
                .route(shortestRoute)
                .trip(trip)
                .build();

        mapRepository.save(route1);

    }
}
