package com.tripper.controller;

import com.tripper.dto.request.board.CreateFindMateBoardDto;
import com.tripper.dto.request.board.UpdateFindMateBoardDto;
import com.tripper.dto.response.board.GetFindMateBoardDto;
import com.tripper.dto.response.board.GetFindMateBoardListDto;
import com.tripper.service.boardservice.FindMateBoardService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Api(tags = "여행메이트 찾기 게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board/find-mate")
@Slf4j
public class FindMateBoardController {

    private final FindMateBoardService boardService;

    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성합니다.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "ok: 글 등록 성공.") })
    @PostMapping
    public ResponseEntity create(Authentication authentication,
                         @ApiParam(value = "게시글 폼에 입력한 정보를 담고있는 객체") @RequestBody CreateFindMateBoardDto dto) {

        /* 현재 로그인한 사용자 정보 가져오기 */
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        /* 글 등록하기 */
        boardService.registerPost(dto, userDetails.getUsername());

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글 목록 조회", notes = "db에 저장된 게시글 목록을 전체 조회한다.")
    @GetMapping
    public ResponseEntity<GetFindMateBoardListDto> getBoardList() {

        GetFindMateBoardListDto boards = boardService.findAllBoards();

        return ResponseEntity.ok().body(boards);
    }

    @ApiOperation(value = "특정 게시글 1개 조회", notes = "board_id가 일치하는 게시글을 조회한다.")
    @GetMapping("/{board_id}")
    public ResponseEntity<GetFindMateBoardDto> getBoardPost(@ApiParam(value = "조회할 게시글의 고유 id") @PathVariable("board_id") Long board_id) {

        GetFindMateBoardDto getBoardDto = boardService.findByBoardId(board_id);

        return ResponseEntity.ok().body(getBoardDto);
    }

    @ApiOperation(value = "좋아요수 증가", notes = "해당 게시글의 좋아요수를 증가시킨다.")
    @PostMapping("/{board_id}/addLikes")
    public ResponseEntity<GetFindMateBoardDto> addLikes(@ApiParam(value = "좋아요수를 증가시킬 게시글의 고유 id") @PathVariable("board_id") Long board_id) {

        boardService.addLikes(board_id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "좋아요수 감소", notes = "해당 게시글의 좋아요수를 감소시킨다.")
    @PostMapping("/{board_id}/subtractLikes")
    public ResponseEntity subtractLikes(@ApiParam(value = "좋아요수를 감소시킬 게시글의 고유 id") @PathVariable("board_id") Long board_id) {

        boardService.subtractLikes(board_id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글 수정을 실행한다.")
    @PutMapping("/{board_id}")
    public ResponseEntity updateItem(@ApiParam(value = "수정할 게시글의 고유 id") @PathVariable("board_id") Long board_id,
                                     @ApiParam(value = "게시글 수정 폼에 입력한 정보를 갖고있는 객체") @RequestBody UpdateFindMateBoardDto updateBoardDto) {

        boardService.updatePost(board_id, updateBoardDto);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제를 실행한다.")
    @DeleteMapping("/{board_id}")
    public ResponseEntity deletePost(@ApiParam(value = "삭제할 게시글의 고유 id") @PathVariable("board_id") Long board_id) {

        boardService.deletePostById(board_id);

        return ResponseEntity.ok().build();
    }

}
