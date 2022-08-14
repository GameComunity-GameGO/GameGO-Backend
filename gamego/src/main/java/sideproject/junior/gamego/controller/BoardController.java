package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.junior.gamego.model.dto.board.RequestBoardDTO;
import sideproject.junior.gamego.model.dto.board.ResponseBoardDTO;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.service.BoardService;
import sideproject.junior.gamego.service.LikesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Log4j2
public class BoardController {

    private final BoardService boardService;
    private final LikesService likesService;
    private final SecurityUtil securityUtil;

    @GetMapping("/all/board")
    public ResponseEntity<?> getBoardList(@PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.ASC) Pageable pageable){

        Page<ResponseBoardDTO> boardList = boardService.getBoardList(pageable);

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<?> getBoard(@PathVariable String id){

        Long memberId = securityUtil.getMemberId();

        ResponseBoardDTO board = boardService.getBoard(Long.parseLong(id));

        int check = likesService.checkLike(memberId, id);

        board.setCheckLikes(check);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping("/board")
    public ResponseEntity<?> createBoard(@RequestBody RequestBoardDTO dto){

        log.info("/api/board 호출");

        Long memberId = securityUtil.getMemberId();

        log.info("memberId = " + memberId);

        ResponseBoardDTO board = boardService.createBoard(memberId, dto);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PutMapping("/board/{boardId}")
    public ResponseEntity<?> updateBoard(@RequestBody RequestBoardDTO dto,
                                         @PathVariable String boardId){

        Long memberId = securityUtil.getMemberId();

        if(boardService.updateBoard(memberId, dto, Long.parseLong(boardId))!=null){
            return new ResponseEntity<>(boardService.updateBoard(memberId, dto, Long.parseLong(boardId)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("작성된 게시물의 사용자가 아닙니다", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable String boardId){

        Long memberId = securityUtil.getMemberId();

        int check = boardService.deleteBoard(Long.parseLong(boardId), memberId);

        if(check == 1) {
            return new ResponseEntity<>(check, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("작성된 게시물의 사용자가 아닙니다", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/board/{id}/like")
    public ResponseEntity<?> boardLike(@PathVariable String id){

        Long memberId = securityUtil.getMemberId();

        int boardLikeCount = likesService.boardLike(Long.parseLong(id), memberId);

        return new ResponseEntity<>(boardLikeCount, HttpStatus.OK);
    }

    @DeleteMapping("/board/{id}/like")
    public ResponseEntity<?> boardLikeDelete(@PathVariable String id){

        Long memberId = securityUtil.getMemberId();

        int boardLikeCount = likesService.boardLikeDelete(Long.parseLong(id), memberId);

        if(boardLikeCount == 1) {
            return new ResponseEntity<>(boardLikeCount, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("이미 좋아요를 취소하였습니다", HttpStatus.BAD_REQUEST);
        }
    }
}
