package com.tripper.controller;

import com.google.gson.JsonArray;
import com.tripper.dto.response.map.GetPlaceDto;
import com.tripper.dto.response.map.GetPlaceListDto;
import com.tripper.service.MapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "지도 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MapController {

    private final MapService mapService;

    @ApiOperation(
            value = "경유지 검색"
            , notes = "키워드를 입력하여 해당하는 경유지들을 검색한다.")
    @GetMapping("/place/keyword")
    public ResponseEntity<GetPlaceListDto> keywordSearch(@ApiParam(value = "장소 검색 키워드") @RequestParam String keyword) {

        GetPlaceListDto keywordSearchList = mapService.keywordSearch(keyword);
        return ResponseEntity.ok().body(keywordSearchList);

    }

    @ApiOperation(
            value = "관광 명소 추천"
            , notes = "현재 경유지들의 반경 1km 내에 존재하는 관광명소 목록을 구한다.")
    @GetMapping("/place/recommendation")
    public ResponseEntity<GetPlaceListDto> categorySearch(@ApiParam(value = "현재 경유지 목록") @RequestBody List<GetPlaceDto> stopovers) {

        GetPlaceListDto categorySearchList = mapService.categorySearch(stopovers);
        return ResponseEntity.ok().body(categorySearchList);

    }

    @ApiOperation(
            value = "최단 경로 구하기"
            , notes = "현재 경유지 목록으로 최단 경로를 구한다.")
    @GetMapping("/place/shortest-route")
    public ResponseEntity<JsonArray> shortestRoute(@ApiParam(value = "현재 경유지 목록") @RequestBody List<GetPlaceDto> stopovers) throws CloneNotSupportedException, InterruptedException {

        JsonArray rslt = mapService.shortestRoute(stopovers);
        return ResponseEntity.ok().body(rslt);

    }

    @ApiOperation(
            value = "최단 경로 저장"
            , notes = "최단 경로를 저장한다")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/place/shortest-route")
    public ResponseEntity saveRoute(@ApiParam(value = "여행 고유 id") @RequestParam("tripId") Long tripId,
                                    @RequestBody String shortestRoute) {

        mapService.saveRoute(tripId, shortestRoute);
        return ResponseEntity.ok().build();

    }

}
